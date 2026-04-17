<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.etf.ip.models.InternshipRequest" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="internshipBean" class="net.etf.ip.beans.InternshipBean" scope="session" />

<%
    String idStr = request.getParameter("id");
    String title = request.getParameter("title");
    String description = request.getParameter("description");
    String startDateStr = request.getParameter("startDate");
    String endDateStr = request.getParameter("endDate");
    
    String[] skillArray = request.getParameterValues("skill");

    if (idStr != null && !idStr.isEmpty()) {
        try {
            Integer id = Integer.parseInt(idStr);
            
            InternshipRequest updateRequest = new InternshipRequest();
            updateRequest.setTitle(title);
            updateRequest.setDescription(description);
           
            if (startDateStr != null && !startDateStr.isEmpty()) {
                updateRequest.setStartDate(LocalDate.parse(startDateStr));
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                updateRequest.setEndDate(LocalDate.parse(endDateStr));
            }

            List<String> skillList = new ArrayList<>();
            if (skillArray != null) {
                for (String s : skillArray) {
                    if (s != null && !s.trim().isEmpty()) {
                        skillList.add(s.trim());
                    }
                }
            }
            updateRequest.setSkills(skillList);
            boolean success = internshipBean.updateInternship(id, updateRequest);     
            response.sendRedirect("dashboard.jsp?status=" + (success ? "updated" : "error"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard.jsp?status=error&msg=InvalidData");
        }
    }
%>