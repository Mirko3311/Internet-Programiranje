<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="workLogBean" class="net.etf.ip.beans.WorkLogBean" scope="page" />

<%
    // Ručno preuzimamo parametre jer trebamo pozvati metodu sa argumentima
    String logIdStr = request.getParameter("logId");
    String comment = request.getParameter("comment");
    String studentId = request.getParameter("studentId");
    String internshipId = request.getParameter("internshipId");

    if (logIdStr != null && comment != null) {
        int logId = Integer.parseInt(logIdStr);
        // Pozivamo metodu koju smo ranije definisali u Beanu
        boolean success = workLogBean.saveComment(logId, comment);
        
        if (success) {
            // Vraćamo se na pregled sa istim parametrima da bi se lista osvježila
            response.sendRedirect("pregledDnevnika.jsp?studentId=" + studentId + "&internshipId=" + internshipId);
        } else {
            out.println("<script>alert('Greška pri čuvanju!'); window.history.back();</script>");
        }
    }
%>