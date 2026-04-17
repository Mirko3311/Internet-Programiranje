<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="internshipBean" class="net.etf.ip.beans.InternshipBean" scope="request" />
<jsp:setProperty name="internshipBean" property="*" />
<div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-dark fw-bold">Prakse</h2>
        <button class="btn btn-primary shadow-sm" onclick="openAddModal()">
            <i class="fa-solid fa-plus me-2"></i> Dodaj novu praksu
        </button>
    </div>

<div class="row mb-3">
    <div class="col-md-4">
        <form onsubmit="event.preventDefault(); pokreniPretragu();">
            <div class="input-group shadow-sm">
                <input type="text" id="pretragaInput" class="form-control" placeholder="Ukucaj pojam za pretragu...">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-search"></i> Traži
                </button>
            </div>
        </form>
    </div>
</div>
    <div class="card shadow border-0 overflow-hidden">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
               <thead class="bg-light">
    <tr>
        <th style="cursor:pointer" onclick="podesiSortiranje('title')">
            Naziv <i class="fa-solid fa-sort fa-xs text-muted"></i>
        </th>
        
        <th>Opis</th>
        
        <th style="cursor:pointer" onclick="podesiSortiranje('companyName')">
            Kompanija <i class="fa-solid fa-sort fa-xs text-muted"></i>
        </th>
        
        <th>Vještine</th>
        
        <th style="cursor:pointer" onclick="podesiSortiranje('startDate')">
            Trajanje <i class="fa-solid fa-sort fa-xs text-muted"></i>
        </th>
        
        <th class="text-center">Akcije</th>
    </tr>
</thead>
                    <tbody>
                        <c:forEach var="praksa" items="${internshipBean.internships}">
                            <tr>
                            
                                <td>
                                    <div class="fw-bold text-primary">${praksa.title}</div>
                                </td>
                                <td>
                                    <div class="small text-muted">${praksa.description}</div>
                                </td>
                                <td><span class="badge bg-secondary opacity-75">${praksa.companyName}</span></td>
                                <td>
                                    <c:forEach var="skill" items="${praksa.skillNames}">
                                        <span class="badge border border-primary text-primary p-1 me-1" style="font-size: 0.7rem;">${skill}</span>
                                    </c:forEach>
                                </td>
                                <td>
                                    <div class="small">${praksa.startDate} <br> <span class="text-muted">do</span> ${praksa.endDate}</div>
                                </td>
                                <td class="text-center">
                                    <div class="btn-group btn-group-sm">
                                        <button class="btn btn-outline-info" title="Uredi" 
                                                onclick="openEditModal('${praksa.id}', '${praksa.title}', '${praksa.description}', '${praksa.skillNames}', '${praksa.startDate}', '${praksa.endDate}')">
                                            <i class="fa-solid fa-pen"></i>
                                        </button>
                                        <button class="btn btn-outline-danger" title="Obriši" onclick="deleteInternship(${praksa.id})">
                                            <i class="fa-solid fa-trash"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addInternshipModal" aria-hidden="true" tabindex="-1">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <form action="add-internship-action.jsp" method="POST">
        <div class="modal-header">
          <h5 class="modal-title" id="modalTitle">Nova praksa</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body p-4">
          <div class="mb-3">
            <label class="form-label fw-bold">Naslov</label>
            <input type="text" name="title" class="form-control" required>
          </div>
          <div class="mb-3">
            <label class="form-label fw-bold">Opis</label>
            <textarea name="description" class="form-control" rows="3"></textarea>
          </div>
          <div class="row">
            <div class="col-md-6 mb-3">
              <label class="form-label fw-bold">Datum početka</label>
              <input type="date" name="startDate" class="form-control" required>
            </div>
            <div class="col-md-6 mb-3">
              <label class="form-label fw-bold">Datum završetka</label>
              <input type="date" name="endDate" class="form-control" required>
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label fw-bold">Potrebne vještine</label>
            <div id="skillsContainer">
                </div>
            <button type="button" class="btn btn-outline-secondary btn-sm mt-2" onclick="addNewSkillField()">
                <i class="fa-solid fa-plus me-1"></i> Dodaj vještinu
            </button>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
          <button type="submit" class="btn btn-primary">Sačuvaj</button>
        </div>
      </form>
    </div>
  </div>
</div>
