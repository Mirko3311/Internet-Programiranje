<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 1. Povezivanje sa Beanom u tvom net.etf.ip.beans paketu --%>
<jsp:useBean id="loginBean" class="net.etf.ip.beans.LoginBean" scope="session" />
<%-- 2. Automatsko mapiranje polja iz forme u Bean --%>
<jsp:setProperty name="loginBean" property="*" />

<%
    String errorMsg = "";
    // 3. Logika koja se okida kada se klikne na dugme (ako postoji parametar 'submit')
    if (request.getParameter("submit") != null) {
        if (loginBean.logIn()) {
            // Ako je login uspješan, idemo na tvoj Dashboard sa Sidebarom
            response.sendRedirect("dashboard.jsp");
        } else {
            errorMsg = "Pogrešno korisničko ime ili lozinka!";
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prijava</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f0f2f5; height: 100vh; display: flex; align-items: center; }
        .card { border: none; border-radius: 1rem; }
        .btn-primary { padding: 0.8rem; font-weight: bold; }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-12 col-sm-8 col-md-6 col-lg-4">
            <div class="card shadow-lg">
                <div class="card-body p-5">
                    <h3 class="text-center mb-4 fw-bold text-primary">Prijava za kompanije</h3>
                    
                    <%-- 4. Važno: action je usmjeren na samu ovu stranicu --%>
                    <form method="POST" action="login.jsp">
                        <div class="mb-3">
                            <label for="username" class="form-label text-muted">Korisničko ime</label>
                            <input type="text" class="form-control" id="username" name="username" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label text-muted">Lozinka</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        
                        <%-- Prikaz greške --%>
                        <% if (!errorMsg.isEmpty()) { %>
                            <div class="alert alert-danger py-2" role="alert">
                                <%= errorMsg %>
                            </div>
                        <% } %>

                        <div class="d-grid gap-2 mt-4">
                            <%-- Dugme mora imati name="submit" da bi Java gore prepoznala klik --%>
                            <button type="submit" name="submit" class="btn btn-primary shadow-sm">Prijavi se</button>
                        </div>
                    </form>
                </div>
            </div>
            <p class="text-center mt-4 text-muted small">&copy; 2026 Studentske Prakse | ETF BL</p>
        </div>
    </div>
</div>
</body>
</html>