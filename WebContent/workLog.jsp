<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, net.etf.ip.models.WorkLogResponse" %>
<jsp:useBean id="workLogBean" class="net.etf.ip.beans.WorkLogBean" scope="request" />

<%
    String studentName = request.getParameter("studentName");
    String sId = request.getParameter("studentId");
    String iId = request.getParameter("internshipId");
    
    // Pretpostavimo da dohvatamo listu preko beana
    List<WorkLogResponse> logs = workLogBean.getLogsForStudent(Integer.parseInt(sId), Integer.parseInt(iId));
%>
<div class="container-fluid p-4">
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb bg-light p-2 rounded shadow-sm">
        <li class="breadcrumb-item"><a href="javascript:void(0)" onclick="loadPage('studentiNaPraksi.jsp?internshipId=<%= iId %>')">Spisak studenata</a></li>
        <li class="breadcrumb-item active">Dnevnik rada: <%= studentName %></li>
      </ol>
    </nav>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold">Dnevnik rada</h3>
        <span class="badge bg-info p-2">Student: <%= studentName %></span>
    </div>

    <% if (logs == null || logs.isEmpty()) { %>
        <div class="alert alert-warning text-center">
            <i class="fa-solid fa-circle-exclamation me-2"></i> Nema unosa za izabranog  studenta
        </div>
    <% } else { %>
        <div class="timeline-container">
            <% for (WorkLogResponse log : logs) { %>
                <div class="card mb-3 border-0 shadow-sm">
                    <div class="card-header bg-white border-0 d-flex justify-content-between align-items-center pt-3">
                        <h5 class="mb-0 text-primary">Sedmica <%= log.getWeekNumber() %></h5>
                        <small class="text-muted"><%= log.getDatesubmitted() %></small>
                    </div>
                    <div class="card-body">
                        <p class="card-text text-dark" style="white-space: pre-wrap;"><%= log.getDescription() %></p>
                        
<div class="mt-3 p-3 bg-light rounded border-start border-4 border-info shadow-sm">
    <h6 class="fw-bold text-info">
        <i class="fa-solid fa-comment-dots me-2"></i>Komentar mentora
    </h6>
    
    <form onsubmit="submitComment(event, <%= log.getId() %>)">
        <div class="mb-2">
            <textarea id="comment-<%= log.getId() %>" 
                      class="form-control form-control-sm" 
                      rows="2" 
                      placeholder="Napisite komentar..."><%= (log.getMentorComment() != null) ? log.getMentorComment() : "" %></textarea>
        </div>
        <div class="text-end">
            <button type="submit" class="btn btn-info btn-sm text-white">
                <i class="fa-solid fa-save me-1"></i> Sačuvaj komentar
            </button>
        </div>
    </form>
</div>
                    </div>
                </div>
            <% } %>
        </div>
    <% } %>
</div>

<style>
    .timeline-container { border-left: 3px solid #dee2e6; padding-left: 20px; margin-left: 10px; }
    .card { position: relative; }
    /* Mali krug na liniji za svaki unos */
    .card::before {
        content: "";
        position: absolute;
        left: -29px;
        top: 20px;
        width: 15px;
        height: 15px;
        background-color: #0d6efd;
        border-radius: 50%;
        border: 3px solid #fff;
    }
</style>