<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="internshipBean" class="net.etf.ip.beans.InternshipBean" scope="session" />

<%
    String idStr = request.getParameter("id");
    if (idStr != null && !idStr.isEmpty()) {
        try {
            Integer id = Integer.parseInt(idStr);
            System.out.println("Brisanje: " + id);
            boolean success = internshipBean.deleteInternship(id);
            
            if (success) {
                response.sendRedirect("dashboard.jsp?status=deleted");
            } else {
                response.sendRedirect("dashboard.jsp?status=error");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("dashboard.jsp?status=invalid_id");
        }
    } else {
        response.sendRedirect("dashboard.jsp");
    }
%>