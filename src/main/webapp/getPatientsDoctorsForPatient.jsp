<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <title>Patient's Doctors</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="patientNavbar.jsp"/>
<jsp:include page="languageSelector.jsp"/>
<div class="row">
    <div class="col-md-auto">
        <div class="card bg-light mb-3 border-info d-inline-block" style="width: 30rem;">
            <h2 class="card-header text-info"><fmt:message key="patient.details"/></h2>
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><b><fmt:message key="all_staff.table.id"/>:</b> ${sessionScope.user.id}</li>
                <li class="list-group-item"><b><fmt:message
                        key="all_staff.table.username"/>:</b> ${sessionScope.user.username}
                </li>
                <li class="list-group-item"><b><fmt:message
                        key="all_staff.table.first_name"/>:</b> ${sessionScope.user.firstName}</li>
                <li class="list-group-item"><b><fmt:message
                        key="all_staff.table.last_name"/>:</b> ${sessionScope.user.lastName}
                </li>
                <li class="list-group-item"><b><fmt:message
                        key="patients.birth_date"/>:</b> ${sessionScope.user.birthDate}
                </li>
                <li class="list-group-item"><b><fmt:message key="patient.email"/>:</b> ${sessionScope.user.email}</li>
                <li class="list-group-item"><b><fmt:message
                        key="patient.phone_number"/>:</b> ${sessionScope.user.phoneNumber}
                </li>
            </ul>
            <div class="card-body">
                <button type="button" class="btn btn-primary" disabled>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-person-fill" viewBox="0 0 16 16">
                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3Zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z"></path>
                    </svg>
                    <fmt:message key="patient.doctors"/>
                </button>
                <a href="${pageContext.request.contextPath}/controller?command=all_patients_cards&id=${requestScope.patient.id}"
                   class="btn btn-success">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-clipboard2-plus" viewBox="0 0 16 16">
                        <path d="M9.5 0a.5.5 0 0 1 .5.5.5.5 0 0 0 .5.5.5.5 0 0 1 .5.5V2a.5.5 0 0 1-.5.5h-5A.5.5 0 0 1 5 2v-.5a.5.5 0 0 1 .5-.5.5.5 0 0 0 .5-.5.5.5 0 0 1 .5-.5h3Z"></path>
                        <path d="M3 2.5a.5.5 0 0 1 .5-.5H4a.5.5 0 0 0 0-1h-.5A1.5 1.5 0 0 0 2 2.5v12A1.5 1.5 0 0 0 3.5 16h9a1.5 1.5 0 0 0 1.5-1.5v-12A1.5 1.5 0 0 0 12.5 1H12a.5.5 0 0 0 0 1h.5a.5.5 0 0 1 .5.5v12a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5v-12Z"></path>
                        <path d="M8.5 6.5a.5.5 0 0 0-1 0V8H6a.5.5 0 0 0 0 1h1.5v1.5a.5.5 0 0 0 1 0V9H10a.5.5 0 0 0 0-1H8.5V6.5Z"></path>
                    </svg>
                    <fmt:message key="patient.card"/>
                </a>
            </div>
        </div>
    </div>
    <br>
    <div class="col">
        <div class="card bg-light mb-3 border-info d-inline-block" style="width: 40rem;">
            <h2 class="card-header text-info"><fmt:message key="patient.doctors"/></h2>
            <ul class="list-group list-group-flush">
                <c:forEach var="doctor" items="${requestScope.doctors}">
                    <li class="list-group-item">${doctor.firstName} ${doctor.lastName} (${doctor.category})</li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
