<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.etf.ip.models.InternshipRequest" %>
<%@ page import="java.time.LocalDate" %>

<jsp:useBean id="internshipBean" class="net.etf.ip.beans.InternshipBean" scope="session" />

<%
    String title = request.getParameter("title");
    String desc = request.getParameter("description");
    String startStr = request.getParameter("startDate");
    String endStr = request.getParameter("endDate");
    String[] skillsArray = request.getParameterValues("skill");
    java.util.List<String> skillsList = java.util.Arrays.asList(skillsArray);
    
    if (title == null || title.trim().isEmpty() || 
            desc == null || desc.trim().isEmpty() || 
            startStr == null || startStr.isEmpty() ||
            endStr == null || endStr.isEmpty()) {
             
            response.sendRedirect("dashboard.jsp?status=error&msg=missing_fields");
            return; 
        }
    try
    {
    InternshipRequest req = new InternshipRequest();
    req.setTitle(title);
    req.setDescription(desc);
    req.setStartDate(LocalDate.parse(startStr));
    req.setEndDate(LocalDate.parse(endStr));
    req.setSkills(skillsList);

    boolean success = internshipBean.createinternship(req);
    if(success)
    {
    	response.sendRedirect("dashboard.jsp");
    	return;
    } else
    {
    	response.sendRedirect("dashboard.jsp?status=error");
    }
    
    }catch(Exception ex)
    {
    	response.sendRedirect("dashboard.jsp?status=error");
    	return;
    }
  
%>