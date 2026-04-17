<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="applicationBean" class="net.etf.ip.beans.ApplicationBean" scope="session" />
<%-- Prvo setujemo ID, a onda dohvatamo cijeli CVResponse --%>
<jsp:setProperty name="applicationBean" property="selectedId" param="id" />
<c:set var="cv" value="${applicationBean.detailedCV}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>EuroPass CV - ${cv.firstName} ${cv.lastName}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
    .step-content
{
    margin: 10px;
}

.row
{
    margin: 10px;
}

.single-button
{
    display:flex;
    justify-content: flex-end;
    width: 100%;
    margin-top: 20px;

}
.example-full-width
{
    width: 100%;
    margin: 10px;
    justify-content: left;
}
.cv-main-container
{
    display: flex;
    flex-direction: row;
    min-height: 297mm;
    height: 100%;
    align-items: stretch;
}

.cv-left-col
{
    background-color: #96b4d8;
    flex:2;
}

.sidebar-header
{
    background-color: #3e5c84;
    padding: 40px 20px 60px 20px;
    color: white;
    text-align: center;
    position: relative;
    overflow: hidden;
    clip-path:ellipse(100% 100% at 50% 0%)
  
   
}

.cv-body
{
    flex:7;
    background-color: antiquewhite;
    display: flex;
    flex-direction: column;
}
.cv-container
{
    width: 100%;
    background-color: white;
    margin: 0px 0px;
    display: flex;
    flex:1;
    flex-direction: row;
    align-items: stretch;
    margin: 0;
    padding:0;
    overflow: hidden;
}
mat-stepper {
    height: 100%;
    overflow-y: auto;
}
.wrapper
{
    height: 100vh;
    width: 99.7%;
    display: flex;
    flex-direction: column;
    margin: 0;
    padding: 0;
    overflow: hidden;
}
form {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
}
.grid-container
{
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap:10px 20px;  
}

.button-container
{
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 12px;
    margin: 5px;
}
.top-buttons
{
    background-color: rgb(231, 250, 248);  
    border-radius: 20px;
    border-color: #445ff4;
    justify-content: flex-end;
    margin-bottom: 10px;
    border-width: 100px;
}
.top-action
{
    display: flex;
    justify-content: flex-end;
    padding-right: 20px;
    background-color: white;
}

.stepper-actions
{
    display: flex;
    justify-content: space-between;
    align-items:  center;
    width: 100%;
    margin-top: 30px;
}

.pill-button {

  border-radius: 50px !important; 
  padding: 0 30px !important;
  height: 44px !important;
  font-weight: 500;
  font-size: 16px;
  letter-spacing: 0.5px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.pill-button mat-icon {
  margin-left: 8px;
  font-size: 20px;
  width: 20px;
  height: 20px;
}

.dynamic-item
{
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    align-items: stretch;
    gap:8px;
}
.image
{
    height: 20vh;
    background-color: bisque;
    border-width: 12px;
    border-radius: 400px;
   
}
.image-information
{
    justify-content: center;
    align-content: center;
}
.name-surname-information
{
    height: 10vh;
    background-color: blueviolet;
}
.left-side-bar
{
    flex:4;
    background-color: rgb(231, 250, 248);    
}
.name_surname_information
{
    padding: 25px;
 
}
.right-content
{
    flex:8;
    background-color: rgb(244, 240, 240);
}

.cv-right-main-col
{
    flex:6;
    background-color : rgb(244,244,244);
}
.image-information
{
    margin: 20px;
}

.upload-container
{
    width: 150px;
    height: 180px;
    border: 2px dashed #3f51b5;
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    position: relative;
    overflow: hidden;
    padding: 20;
    text-align: center;
    cursor: pointer;
}

.preview-img
{
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
    position: absolute;
    top:0;
    left: 0;
}
.upload-container mat-icon {
  font-size: 40px;
  width: 40px;
  height: 40px;
  margin-bottom: 8px;
}

.upload-container p {
  font-size: 12px;
  text-align: center;
  padding: 0 10px;
  margin: 0;
}
.form-input-section {
    flex: 1; /* 60% širine za formu */
    display: flex; /* Zadržavamo tvoj left-sidebar i right-content poredak */
    overflow-y: auto; /* Samo se forma može skrolati */
    padding: 0px;
    background-color: #f5f5f5;
    flex-direction: column;
    overflow-y: auto;
}

.form-output-section {
    flex: 1; /* 40% širine za Preview */
    background-color: #faf7f7; /* Tamna pozadina da "istakne" papir */
    display: flex;
    justify-content: stretch;
    align-items: flex-start;
    overflow: hidden;
   
}

.a4-paper {
    width: 210mm;
    height: 297mm;
    box-shadow: 0 0 20px rgba(0,0,0,0.5);
    margin: 0 auto; /* Promijeni na 0 auto */
    transform-origin: top center;
    background-color: white;
    overflow: hidden;
    position: relative; /* Dodaj ovo */
}

.header-curve {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 40px;
  background: white;
  border-radius: 50% 50% 0 0 / 100% 100% 0 0;
  transform: translateY(50%) scaleX(1.5);
}

.info-row
{
    display: flex;
    align-items: center;
    gap:10px;
    margin-bottom: 12px;
}

.info-row mat-icon
{
    color:white;
    font-size: 20px;
    padding:5px;
    margin-left: 10px;
}

.sidebar-header h2
{
    color: white;
    margin-bottom: 5px;
    font-weight: 400;
}

.profile-photo-wrapper
{
    width: 150px;
    height: 130px;
    border-radius: 30%;
    background-color: white;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
}

.profile-photo
{
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
}

.main-line
{
    height: 1px;
    background-color: #3f51b5;
    width: 95%;
    margin-bottom: 15px;
}

.main-section
{
    margin-bottom: 30px;
    margin-left: 20px;
    margin-right: 25px;
}

.item-period
{
    font-weight: bold;
    font-size: 0.8rem;
    color: #444;
}

.item-header
{
    display: flex;
    justify-content: space-between;
    font-size: 18px;
    margin-bottom: 5px;
    align-items: baseline;
}
.item-sub
{
  color: #08034a;
  font-size: 16px;
  margin-top: 2px;
  margin-bottom: 15px;
  
  
}
.main-section-header h2
{
    color: #3e5c84;
    font-size: 1rem;
    margin-bottom: 5px;
    text-transform: capitalize;
}

.fab-export {
  position: fixed; 
  bottom: 30px;  
  right: 30px;    
  z-index: 1000;   
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3); 
  transition: transform 0.2s ease-in-out;
}

.fab-export:hover {
  transform: scale(1.1);
}
    </style>
</head>
<body class="bg-secondary bg-opacity-10">

    <div class="a4-paper" id="cv-card">
        <div class="cv-main-container">
            <div class="cv-left-col">
                <div class="sidebar-header">
                    <div class="profile-photo-wrapper mx-auto">
                        <i class="fa fa-user fa-5x text-muted mt-3"></i>
                        <%-- Ako budesa slikama: <img src="${cv.photoPath}" class="profile-photo"> --%>
                    </div>
                </div>
                
                <div class="sidebar-content p-4">
                    <h5 class="text-white border-bottom pb-2 mb-3">PERSONAL DETAILS</h5>
                    <div class="info-row text-white mb-2">
                        <i class="fa fa-user"></i> <span>${cv.firstName} ${cv.lastName}</span>
                    </div>
                    <div class="info-row text-white mb-2">
                        <i class="fa fa-envelope"></i> <span>${cv.email}</span>
                    </div>
                    <div class="info-row text-white mb-4">
                        <i class="fa fa-phone"></i> <span>${cv.phoneNumber}</span>
                    </div>

                    <h5 class="text-white border-bottom pb-2 mb-3 text-uppercase">Vještine</h5>
                    <ul class="text-white ps-3">
                        <c:forEach items="${cv.skills}" var="skill">
                            <li>${skill}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <div class="cv-right-main-col p-4 bg-white">
                <section class="main-section mb-4">
                    <h2 class="text-primary fw-bold">${cv.firstName} ${cv.lastName}</h2>
                    <div class="main-line mb-3"></div>
                    <p class="text-muted">${cv.summary}</p>
                </section>

                <section class="main-section mb-4">
                    <h5 class="text-primary border-bottom pb-2">RADNO ISKUSTVO</h5>
                    <c:forEach items="${cv.workExperiences}" var="work">
                        <div class="mb-3">
                            <div class="item-header d-flex justify-content-between fw-bold">
                                <span>${work.mainActivities}</span>
                                <span class="text-muted small">${work.period}</span>
                            </div>
                            <div class="item-sub text-secondary">${work.employer}, ${work.city}</div>
                        </div>
                    </c:forEach>
                </section>

                <section class="main-section mb-4">
                    <h5 class="text-primary border-bottom pb-2">OBRAZOVANJE</h5>
                    <c:forEach items="${cv.educations}" var="edu">
                        <div class="mb-3">
                            <div class="item-header d-flex justify-content-between fw-bold">
                                <span>${edu.institution}</span>
                                <span class="text-muted small">${edu.period}</span>
                            </div>
                            <div class="item-sub text-secondary">${edu.degree} - ${edu.fieldOfStudy}</div>
                        </div>
                    </c:forEach>
                </section>
                
                <section class="main-section">
                    <h5 class="text-primary border-bottom pb-2">PRAKSE</h5>
            	   <c:forEach items="${applicationBean.detailedCV.internships}" var="intern">
  				  <div class="main-section">
      				  <div class="item-header">
       			     <span>${intern.companyName}</span>
         		   <span class="text-muted small">${intern.period}</span>
      				  </div>
      			  <c:if test="${not empty intern.position}">
      			      <div class="item-sub text-secondary">${intern.position}</div>
    			    </c:if>
    			    <p class="small text-muted">${intern.description}</p>
    </div>
</c:forEach>
                </section>
            </div>
        </div>
    </div>

<div class="text-center my-4 d-print-none">
    <button onclick="exportToPDF()" class="btn btn-primary btn-lg shadow">
        <i class="fa-solid fa-download me-2"></i> Preuzmi kao PDF
    </button>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
<script>
function exportToPDF() {
    const element = document.getElementById('cv-card');
    
    const originalTransform = element.style.transform;
    const originalMargin = element.style.margin;

    element.style.transform = "none";
    element.style.margin = "0";
    element.style.boxShadow = "none";
    element.style.width = '210mm';
    element.style.minHeight = '297mm';

    const opt = {
        margin: 0,
        filename: 'CV_${cv.firstName}_${cv.lastName}.pdf',
        image: { type: 'jpeg', quality: 0.98 },
        html2canvas: { 
            scale: 2, 
            useCORS: true, 
            letterRendering: true,
            scrollX: 0,
            scrollY: 0,
            y: 0,     
            x: 0,
            windowWidth: element.scrollWidth, 
            windowHeight: element.scrollHeight
        },
        jsPDF: { 
            unit: 'mm', 
            format: 'a4', 
            orientation: 'portrait' 
        }
    };

    // 3. Generisanje uz brisanje eventualnih viška stranica
    html2pdf().set(opt).from(element).toPdf().get('pdf').then(function (pdf) {
        const totalPages = pdf.internal.getNumberOfPages();
        for (let i = totalPages; i > 1; i--) {
            pdf.deletePage(i);
        }
    }).save().then(() => {
        // 4. Vrati vizuelni prikaz u browseru
        element.style.transform = originalTransform;
        element.style.margin = originalMargin;
        element.style.boxShadow = "0 0 20px rgba(0,0,0,0.5)";
    });
}
</script>
</body>
</html>