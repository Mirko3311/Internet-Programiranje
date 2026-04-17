<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="loginBean" class="net.etf.ip.beans.LoginBean" scope="session" />

<%
    
    String oldPw = request.getParameter("oldPassword");
    String newPw = request.getParameter("newPassword");

    if (oldPw != null && newPw != null) {
    	System.out.println("Pokusaj promjene lozinke!");
        boolean success = loginBean.changePassword(oldPw, newPw);
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("SUCCESS");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("ERROR");
        }
        return; 
    }
%>

<div class="container-fluid mt-4">
    <div class="card shadow-sm p-4" style="max-width: 500px; margin: auto;">
        <h2 class="mb-4">Promjena lozinke</h2>
        
        <form id="passwordForm" onsubmit="handlePasswordChange(event)">
            <div class="mb-3">
                <label class="form-label">Trenutna lozinka:</label>
                <input type="password" id="oldPass" required class="form-control">
            </div>
            <hr>
            <div class="mb-3">
                <label class="form-label">Nova lozinka:</label>
                <input type="password" id="newPass" required class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Potvrdite novu lozinku:</label>
                <input type="password" id="confirmPass" required class="form-control">
            </div>
            <button type="submit" class="btn btn-primary w-100">Sačuvaj promjene</button>
        </form>
        
        <div id="statusMessage" class="mt-3"></div>
    </div>
</div>
