<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="sidebar text-white p-3">
  <ul class="nav flex-column">
    <li class="nav-item">
      <a class="nav-link active" href="javascript:void(0)" onclick="loadPage('prakse-list.jsp')">
        <i class="fa-solid fa-briefcase fa-fw me-2"></i> Prakse
      </a>
    </li>
    <li class="nav-item">
      <a class="nav-link text-white" href="javascript:void(0)" onclick="loadPage('applications.jsp')">
        <i class="fa-solid fa-user-graduate fa-fw me-2"></i> Prijave studenata
      </a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link text-white" href="javascript:void(0)" onclick="loadPage('mojePrakse.jsp')">
        <i class="fa-solid fa-book fa-fw me-2"></i> Dnevnici rada
      </a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link text-white" href="javascript:void(0)" onclick="loadPage('change-password.jsp')">
        <i class="fa-solid fa-key fa-fw me-2"></i> Podešavanja
      </a>
    </li>
  </ul>
</div>