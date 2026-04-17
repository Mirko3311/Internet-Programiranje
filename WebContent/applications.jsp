<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="applicationBean" class="net.etf.ip.beans.ApplicationBean" scope="request" />
<jsp:setProperty name="applicationBean" property="filterStatus" param="status" />
<jsp:setProperty name="applicationBean" property="searchQuery" param="query" />
<div class="text-center">
    <h4 class="fw-bold text-primary">
        <i class="fa-solid fa-list-check me-2"></i>Prijave studenata
    </h4>
</div>

<div class="container-fluid mt-4">
    <div class="card shadow-sm border-0">
        <div class="card-header bg-white py-3">
            <div class="row g-2">
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text bg-light border-end-0"><i class="fa fa-search text-muted"></i></span>
                        <input type="text" id="searchInput" class="form-control border-start-0" 
     					  placeholder="Pretraži studente ili prakse..." 
   						  value="${param.query}" 
     					  onkeyup="if(event.key === 'Enter') executeCombinedFilter()">
                    </div>
                </div>
                <div class="col-md-4">
                   <select id="statusFilter" class="form-select" onchange="applyM1Filter(this.value)">
   					 <option value="" ${empty param.status ? 'selected' : ''}>Svi statusi</option>
   					 <option value="ACCEPTED" ${param.status == 'ACCEPTED' ? 'selected' : ''}>Prihvaćeno</option>
   					 <option value="REJECTED" ${param.status == 'REJECTED' ? 'selected' : ''}>Odbijeno</option>
   					 <option value="PENDING" ${param.status == 'PENDING' ? 'selected' : ''}>Na čekanju</option>
					</select>
                </div>
                <div class="col-md-4 text-end">
                    <button class="btn btn-outline-secondary w-100" onclick="resetFilters()">Poništi filtere</button>
                </div>
            </div>
        </div>

        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0" id="appsTable">
                    <thead class="table-light">
                        <tr>
                            <th class="ps-4 cursor-pointer" onclick="sortTable(0)" style="cursor:pointer">Student <i class="fa-solid fa-sort small ms-1"></i></th>
                            <th class="cursor-pointer" onclick="sortTable(1)" style="cursor:pointer">Praksa <i class="fa-solid fa-sort small ms-1"></i></th>
                            <th>Status</th>
                            <th class="text-center">CV</th>
                            <th class="text-end pe-4">Akcije</th>
                        </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${applicationBean.applications}" var="app">
                        <tr data-status="${app.status}">
                            <td class="ps-4">
                                <div class="fw-bold">${app.studentFullName}</div>
                            </td>
                            <td>${app.internshipTitle}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${app.status == 'ACCEPTED'}">
                                        <span class="badge bg-light text-success border border-success"><i class="fa fa-check me-1"></i>Prihvaćeno</span>
                                    </c:when>
                                    <c:when test="${app.status == 'REJECTED'}">
                                        <span class="badge bg-light text-danger border border-danger"><i class="fa fa-times me-1"></i>Odbijeno</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-light text-warning border border-warning"><i class="fa fa-clock me-1"></i>Na čekanju</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <button class="btn btn-sm btn-outline-primary" 
     							   onclick="window.open('cv-template.jsp?id=${app.studentId}', '_blank', 'width=900,height=1000')">
   								 <i class="fa fa-file-pdf"></i> Pogledaj CV
								</button>
                            </td>
                            <td class="text-end pe-4">
                                <c:if test="${app.status == 'PENDING'}">
                                    <div class="btn-group">
                                       <button class="btn btn-sm btn-success" 
        onclick="updateAppStatus(${app.applicationId}, 'ACCEPTED')" 
        title="Prihvati">
    <i class="fa fa-user-check"></i>
</button>

<button class="btn btn-sm btn-danger" 
        onclick="updateAppStatus(${app.applicationId}, 'REJECTED')" 
        title="Odbij">
    <i class="fa fa-user-xmark"></i>
</button>
                                    </div>
                                </c:if>
                                <c:if test="${app.status != 'PENDING'}">
                                    <span class="text-muted small">Odluka donijeta</span>
                                </c:if>
                            </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>