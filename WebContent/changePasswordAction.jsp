<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="loginBean" class="net.etf.ip.beans.LoginBean" scope="session" />

<%
    String oldPw = request.getParameter("oldPassword");
    String newPw = request.getParameter("newPassword");

    if (oldPw == null || newPw == null) {
        response.sendRedirect("dashboard.jsp?status=error");
        return;
    }

    boolean success = loginBean.changePassword(oldPw, newPw);

    if (success) {
        response.sendRedirect("dashboard.jsp?status=password_changed");
    } else {
        response.sendRedirect("change-password.jsp?status=wrong_old_password");
    }
%>