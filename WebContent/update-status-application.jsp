<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="applicationBean" class="net.etf.ip.beans.ApplicationBean" scope="session" />

<%
    String idStr = request.getParameter("id");
    String status = request.getParameter("status");

    if (idStr != null && status != null) {
        try {
            Integer appId = Integer.parseInt(idStr);
            boolean success = applicationBean.updateStatus(appId, status);      
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Greška pri ažuriranju");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nevalidni parametri");
        }
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nedostaju parametri");
    }
%>