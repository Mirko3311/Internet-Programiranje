<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, net.etf.ip.models.InternshipResponse" %>

<%-- Inicijalizacija Beana --%>
<jsp:useBean id="internshipBean" class="net.etf.ip.beans.InternshipBean" scope="request" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Moje Prakse - Kompanija</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .internship-card { transition: transform 0.2s; }
        .internship-card:hover { transform: scale(1.02); }
    </style>
</head>
<body>
   
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Vaše aktivne prakse</h2>
        </div>

        <div class="row">
            <%
                List<InternshipResponse> prakse = internshipBean.getMyInternships();
                if (prakse != null && !prakse.isEmpty()) {
                    for (InternshipResponse p : prakse) {
            %>
                <div class="col-md-6 col-lg-4 mb-4">
                    <div class="card h-100 shadow-sm internship-card">
                        <div class="card-body">
                            <h5 class="card-title text-primary"><%= p.getTitle() %></h5>
                            <p class="card-text text-truncate"><%= p.getDescription() %></p>
                            <p class="small text-muted">
                                Od: <%= p.getStartDate() %> <br>
                                Do: <%= p.getEndDate() %>
                            </p>
                        </div>
                        <div class="card-footer bg-transparent border-top-0">
<button onclick="loadPage('studentiNaPraksi.jsp?internshipId=<%= p.getId() %>&title=<%= java.net.URLEncoder.encode(p.getTitle()) %>')" 
        class="btn btn-outline-primary w-100">
    Prikaži studente
</button>
                        </div>
                    </div>
                </div>
            <% 
                    }
                } else { 
            %>
                <div class="col-12">
                    <div class="alert alert-info text-center">
                        Trenutno nemate objavljenih praksi.
                    </div>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>