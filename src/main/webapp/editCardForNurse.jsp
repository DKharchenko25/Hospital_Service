<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <title>Edit Hospital Card</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="staffNavbar.jsp"/>
<jsp:include page="languageSelector.jsp"/>
<div class="card bg-light mb-3 border-info" style="width: 50rem;">
    <h2 class="card-header text-info"><fmt:message key="card.edit"/> </h2>
    <div class="card-body">
        <p class="card-text"><b><fmt:message key="card.id"/>:</b> ${requestScope.card.id}</p>
        <p class="card-text"><b><fmt:message key="login.role_doctor"/>:</b> ${requestScope.card.doctor.firstName} ${requestScope.card.doctor.lastName}
            (${requestScope.card.doctor.category})</p>
        <p class="card-text">
            <b><fmt:message key="login.role_patient"/>:</b> ${requestScope.card.patient.firstName} ${requestScope.card.patient.lastName}</p>
    </div>
    <form action="${pageContext.request.contextPath}/controller?command=edit_card" method="post">
        <input type="hidden" name="id" value="${requestScope.card.id}">
        <input type="hidden" name="patientId" value="${requestScope.card.patient.id}">
        <input type="hidden" name="doctorId" value="${requestScope.card.doctor.id}">
        <div class="card-body">
            <label for="procedures" class="form-label"><fmt:message key="patient.procedures"/></label>
            <textarea class="form-control" id="procedures" name="procedures"
                      rows="4">${requestScope.card.procedures}</textarea>
        </div>
        <div class="card-body">
            <label for="medications" class="form-label"><fmt:message key="patient.medications"/></label>
            <textarea class="form-control" id="medications" name="medications"
                      rows="4">${requestScope.card.medications}</textarea>
        </div>
        <div class="card-body">
            <label for="operations" class="form-label"><fmt:message key="patient.operations"/></label>
            <textarea class="form-control" id="operations" name="operations"
                      rows="4" disabled>${requestScope.card.operations}</textarea>
        </div>
        <div class="card-body">
            <label for="diagnosis" class="form-label"><fmt:message key="patient.diagnosis"/></label>
            <textarea class="form-control" id="diagnosis" name="diagnosis"
                      rows="4" disabled>${requestScope.card.diagnosis}</textarea>
        </div>
        <div class="card-body">
            <button type="submit" class="btn btn-primary"><fmt:message key="patient.button.done"/></button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
