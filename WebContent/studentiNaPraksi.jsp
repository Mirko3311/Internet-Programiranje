<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, net.etf.ip.models.StudentResponse" %>
<jsp:useBean id="internshipBean" class="net.etf.ip.beans.InternshipBean" scope="request" />
<jsp:setProperty name="internshipBean" property="*" />

<div class="container-fluid p-4">
    
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">
           Naziv prakse: <span class="text-primary"><%= request.getParameter("title") %></span>
        </h2>
    </div>

    <div class="row mb-3">
  	  <div class="col-md-4">
        <div class="input-group shadow-sm">
            <span class="input-group-text bg-white border-end-0">
                <i class="fa fa-search text-muted"></i>
            </span>
            <input type="text" id="studentSearchInput" class="form-control border-start-0" 
                   placeholder="Pretraga po imenu i prezimenu..." onkeyup="filterStudents()">
        </div>
  	  </div>
	</div>

    <div class="card shadow-sm border-0">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0" id="studentsTable">
                    <thead class="table-light">
                        <tr>
                            <th class="ps-4 py-3">Ime studenta</th>
                            <th class="py-3">Email</th>
                            <th class="text-end pe-4 py-3">Akcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<StudentResponse> studenti = internshipBean.getStudentsForInternship();
                            if (studenti != null && !studenti.isEmpty()) {
                                for (StudentResponse s : studenti) {
                        %>
                            <tr class="student-row">
                                <td class="ps-4">
                                    <div class="d-flex align-items-center">
                                        <div class="avatar-sm me-3 bg-primary text-white rounded-circle d-flex align-items-center justify-content-center" style="width: 35px; height: 35px; font-size: 0.8rem;">
                                            <%= s.getFirstName().substring(0,1) %><%= s.getLastName().substring(0,1) %>
                                        </div>
                                        <div class="student-name-cell">
                                            <strong><%= s.getFirstName() %> <%= s.getLastName() %></strong>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <a href="mailto:<%= s.getEmail() %>" class="text-decoration-none text-muted small">
                                        <i class="fa fa-envelope me-1"></i><%= s.getEmail() %>
                                    </a>
                                </td>
                                <td class="text-end pe-4">
                                    <button class="btn btn-outline-primary btn-sm px-3" 
                                            onclick="loadPage('workLog.jsp?studentId=<%= s.getId() %>&internshipId=<%= internshipBean.getInternshipId() %>&studentName=<%= s.getFirstName() + ' ' + s.getLastName() %>')">
                                        <i class="fa-solid fa-book-open me-1"></i> Dnevnik rada
                                    </button>
                                </td>
                            </tr>
                        <% 
                                }
                            } else { 
                        %>
                            <tr>
                                <td colspan="3" class="text-center py-5 text-muted">
                                    <i class="fa fa-users-slash fa-2x mb-3 d-block"></i>
                                    No students are currently enrolled in this internship.
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>


</script>