<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { display: flex; flex-direction: column; height: 100vh; margin: 0; }
        .wrapper { display: flex; flex: 1; overflow: hidden; margin-top: 50.7px; }
        #sidebar-container { width: 250px; background: #212529; overflow-y: auto; }
        #main-content { flex: 1; padding: 20px; overflow-y: auto; background: #f8f9fa; }
    
    .sidebar {
    height: 100%;
    background-color: #343a40; 
    min-height: 100vh;
}

.sidebar .nav-link {
    color: rgba(255,255,255,.8);
    padding: 12px 15px;
    border-radius: 5px;
    margin-bottom: 5px;
    transition: all 0.3s;
}

.sidebar .nav-link:hover {
    background-color: rgba(255,255,255,.1);
    color: #fff;
}

.sidebar .nav-link.active {
    background-color: #0d6efd; 
    color: white !important;
}

/* Stil za FontAwesome ikonice da budu poravnate */
.fa-fw {
    width: 1.25em;
    text-align: center;
} 

.floating-alert {
    position: fixed;
    top: 70px;
    left: 50%;
    transform: translateX(-50%);
    z-index: 2000;
    min-width: 400px;
    box-shadow: 0 4px 12px rgba(0,0,0,0.2);
}
 </style>
</head>
<body>
    <jsp:include page="/common/navbar.jsp" />

    <div class="wrapper">
        <div id="sidebar-container">
            <jsp:include page="/common/sidebar.jsp" />
        </div>

        <main id="main-content">
            <div id="ajax-content">
                
            </div>
        </main>
    </div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
    
    function executeCombinedFilter() {
        const status = document.getElementById('statusFilter').value;
        const query = document.getElementById('searchInput').value;
        loadPage('applications.jsp?status=' + encodeURIComponent(status) + '&query=' + encodeURIComponent(query));
    }

    function applyM1Filter(statusValue) {
        const query = document.getElementById('searchInput').value;
        loadPage('applications.jsp?status=' + statusValue + '&query=' + encodeURIComponent(query));
    }
    
    function resetFilters() {
        loadPage('applications.jsp');
    }
    let trenutnaKolona = 'title';
    let trenutniSmijer = 'asc';

    function podesiSortiranje(kolona) {
        if (trenutnaKolona === kolona) {
            trenutniSmijer = (trenutniSmijer === 'asc') ? 'desc' : 'asc';
        } else {
            trenutnaKolona = kolona;
            trenutniSmijer = 'asc';
        }
        const input = document.getElementById('pretragaInput');
        const query = input ? input.value : "";
        const url = "prakse-list.jsp?sort=" + trenutnaKolona + 
                    "&order=" + trenutniSmijer + 
                    "&query=" + encodeURIComponent(query);
        loadPage(url);
    }
        function loadPage(pageName) {
            const contentDiv = document.getElementById('ajax-content');
            
           
            const context = "${pageContext.request.contextPath}";
            
            const fullPath = context + "/" + pageName;  
            fetch(fullPath)
                .then(response => {
                    if (!response.ok) throw new Error('Stranica nije pronađena');
                    return response.text();
                })
                .then(html => {
                    contentDiv.innerHTML = html;
                    const modalEl = document.getElementById('addInternshipModal');
                    if(modalEl) { new bootstrap.Modal(modalEl);}
                })
                .catch(err => {
                    contentDiv.innerHTML = '<div class="alert alert-danger">Greška pri učitavanju: ' + err.message + '</div>';
                });
        }

        
        function pokreniPretragu() {
            const input = document.getElementById('pretragaInput');
            if (input) {
                const pojam = input.value;
                loadPage("prakse-list.jsp?query=" + encodeURIComponent(pojam));
            }
        }
        
        function openAddModal() {
            document.getElementById('modalTitle').innerText = "Kreiranje nove prakse";
            document.querySelector('#addInternshipModal form').reset();

            let hiddenId = document.getElementById('edit-id-field');
            if (hiddenId) hiddenId.remove();
            
            document.getElementById('skillsContainer').innerHTML = '';
            addNewSkillField(); 
            
            var modal = new bootstrap.Modal(document.getElementById('addInternshipModal'));
            modal.show();
        }

        function openEditModal(id, title, desc, skillsString, start, end) {
            document.getElementById('modalTitle').innerText = "Uredi praksu: " + title;
            
            document.querySelector('input[name="title"]').value = title;
            document.querySelector('textarea[name="description"]').value = desc;
            document.querySelector('input[name="startDate"]').value = start;
            document.querySelector('input[name="endDate"]').value = end;
               
            document.getElementById('skillsContainer').innerHTML = '';
            if (skillsString && skillsString !== "[]") {
                let cistaLista = skillsString.replace('[', '').replace(']', '');
                let vjestine = cistaLista.split(',');
                vjestine.forEach(v => {
                    addNewSkillField(v.trim());
                });
            } else {
                addNewSkillField();
            }

            document.querySelector('#addInternshipModal form').action = "update-internship-action.jsp";
            
            let form = document.querySelector('#addInternshipModal form');
            let hiddenId = document.getElementById('edit-id-field');
            if (!hiddenId) {
                hiddenId = document.createElement('input');
                hiddenId.type = 'hidden';
                hiddenId.name = 'id';
                hiddenId.id = 'edit-id-field';
                form.appendChild(hiddenId);
            }
            hiddenId.value = id;

            var modal = new bootstrap.Modal(document.getElementById('addInternshipModal'));
            modal.show();
        }

        function addNewSkillField(value = "") {
            const container = document.getElementById('skillsContainer');
            const div = document.createElement('div');
            div.className = 'input-group mb-2';
            div.innerHTML = `
                <input type="text" name="skill" class="form-control" placeholder="Vještina" value="${value}">
                <button class="btn btn-outline-danger" type="button" onclick="this.parentElement.remove()">
                    <i class="fa-solid fa-xmark"></i>
                </button>
            `;
            container.appendChild(div);
        }
        
        function deleteInternship(id)
        {
        	if (confirm("Da li ste sigurni da želite obrisati ovu praksu (ID: #" + id + ")?")) {
                window.location.href = "delete-internship-action.jsp?id=" + id;
            }
        }
        
        const formElement = document.querySelector('#addInternshipModal form');
        
        if(formElement)
        {
        formElemrnt.addEventListener('submit', function(e) {
            const title = this.title.value.trim();
            const desc = this.description.value.trim();
            const start = this.startDate.value;
            const end = this.endDate.value;

            if (!title || !desc || !start || !end) {
                e.preventDefault(); // Zaustavlja slanje na add-internship-action.jsp
                alert("Sva polja (Naslov, Opis i Datumi) moraju biti popunjena!");
                return;
            }

            if (new Date(end) <= new Date(start)) {
                e.preventDefault();
                alert("Datum završetka mora biti nakon datuma početka!");
            }
        });
        }
        
        window.addEventListener('DOMContentLoaded', (event) => {
          
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.has('status')) {
                const statusModal = new bootstrap.Modal(document.getElementById('statusModal'));
                statusModal.show();
                window.history.replaceState({}, document.title, window.location.pathname);
            }
        });
        
        function handlePasswordChange(event) {
            event.preventDefault();
            
            const oldP = document.getElementById('oldPass').value;
            const newP = document.getElementById('newPass').value;
            const confirmP = document.getElementById('confirmPass').value;
            const msgDiv = document.getElementById('statusMessage');
            
            console.log("Funkcija je pokrenuta!");
            if (newP !== confirmP) {
                msgDiv.innerHTML = '<div class="alert alert-danger">Nove lozinke se ne podudaraju!</div>';
                return;
            }
            if (newP.length < 6) {
                msgDiv.innerHTML = '<div class="alert alert-warning">Nova lozinka mora imati bar 6 karaktera!</div>';
                return;
            }

            fetch('change-password.jsp?oldPassword=' + encodeURIComponent(oldP) + '&newPassword=' + encodeURIComponent(newP), {
                method: 'POST'
            })
            .then(response => {
                if (response.ok) {
                    msgDiv.innerHTML = '<div class="alert alert-success">Lozinka uspješno promijenjena!</div>';
                    document.getElementById('passwordForm').reset();
                } else {
                    msgDiv.innerHTML = '<div class="alert alert-danger">Trenutna lozinka nije ispravna.</div>';
                }
            })
            .catch(err => {
                msgDiv.innerHTML = '<div class="alert alert-danger">Došlo je do greške na serveru.</div>';
            });
        }
        
        function updateAppStatus(appId, newStatus) {
            if (!confirm("Da li ste sigurni da želite da promijenite status u: " + newStatus + "?")) {
                return;
            }
            fetch('update-status-application.jsp?id=' + appId + '&status=' + newStatus, {
                method: 'GET' 
            })
            .then(response => {
                if (response.ok) {
                    alert("Status uspješno ažuriran!");
                    loadPage('applications.jsp');
                } else {
                    alert("Došlo je do greške na serveru prilikom ažuriranja.");
                }
            })
            .catch(error => {
                console.error('Greška:', error);
                alert("Greška u komunikaciji sa serverom.");
            });
        }
        
        
        //funkcija iz studentiNaPraksi.jsp:
        	function filterStudents() {
    const input = document.getElementById("studentSearchInput");
    const filter = input.value.toLowerCase();
    const rows = document.querySelectorAll(".student-row");

    rows.forEach(row => {
        // We select the div containing the full name to ensure accuracy
        const nameText = row.querySelector(".student-name-cell").textContent.toLowerCase();
        
        if (nameText.includes(filter)) {
            row.style.display = ""; // Show row
        } else {
            row.style.display = "none"; // Hide row
        }
    });
}
    </script>
<div class="modal fade" id="statusModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header <%= "error".equals(request.getParameter("status")) ? "bg-danger" : "bg-success" %> text-white">
        <h5 class="modal-title">
            <i class="fa-solid <%= "error".equals(request.getParameter("status")) ? "fa-circle-xmark" : "fa-circle-check" %> me-2"></i>
            <%= "error".equals(request.getParameter("status")) ? "Greška" : "Obavještenje" %>
        </h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center py-4">
        <% 
            String status = request.getParameter("status");
            if ("updated".equals(status)) { %>
                <p class="fs-5">Praksa je uspješno <strong>ažurirana</strong>!</p>
            <% } else if ("created".equals(status)) { %>
                <p class="fs-5">Nova praksa je uspješno <strong>kreirana</strong>!</p>
            <% } else if ("error".equals(status)) { %>
                <p class="fs-5 text-danger">Došlo je do greške prilikom obrade podataka.</p>
            <% } %>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>