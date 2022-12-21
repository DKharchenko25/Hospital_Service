<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty sessionScope.lang ? sessionScope.lang : 'en'}" scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${lang}">
<head>
    <title>Doctor's patients</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="adminNavbar.jsp"/>
<div class="d-flex">
    <div class="card bg-light mb-3 border-info d-inline-block" style="width: 40rem;">
        <h2 class="card-header text-info"><fmt:message key="staff.details"/> </h2>
        <ul class="list-group list-group-flush">
            <li class="list-group-item"><b><fmt:message key="all_staff.table.id"/>:</b> ${requestScope.hospitalStaff.id}</li>
            <li class="list-group-item"><b><fmt:message key="all_staff.table.username"/>:</b> ${requestScope.hospitalStaff.username}</li>
            <li class="list-group-item"><b><fmt:message key="login.password"/>:</b> ${requestScope.hospitalStaff.password}</li>
            <li class="list-group-item"><b><fmt:message key="all_staff.table.first_name"/>:</b> ${requestScope.hospitalStaff.firstName}</li>
            <li class="list-group-item"><b><fmt:message key="all_staff.table.last_name"/>:</b> ${requestScope.hospitalStaff.lastName}</li>
            <li class="list-group-item"><b><fmt:message key="all_staff.table.role"/>:</b> ${requestScope.hospitalStaff.role}</li>
            <li class="list-group-item"><b><fmt:message key="all_staff.table.category"/>:</b> ${requestScope.hospitalStaff.category}</li>
            <li class="list-group-item"><b><fmt:message key="all_staff.table.number"/>:</b> ${requestScope.hospitalStaff.numberOfPatients}</li>
        </ul>
        <div class="card-body">
            <a href="${pageContext.request.contextPath}/update_hospital_staff?id=${requestScope.hospitalStaff.id}"
               class="btn btn-secondary">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                     class="bi bi-pencil-square"
                     viewBox="0 0 16 16">
                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"></path>
                    <path fill-rule="evenodd"
                          d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"></path>
                </svg>
                <fmt:message key="patient.edit"/></a>
            <button type="button" class="btn btn-primary" disabled>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                     class="bi bi-person-fill" viewBox="0 0 16 16">
                    <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3Zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z"></path>
                </svg>
                <fmt:message key="staff.patients"/>
            </button>
        </div>
    </div>
    <div class="card bg-light mb-3 border-info d-inline-block" style="width: 50rem;">
        <h2 class="card-header text-info"><fmt:message key="staff.patients"/></h2>
        <ul class="list-group list-group-flush">
            <c:forEach var="patient" items="${requestScope.patients}">
                <li class="list-group-item">${patient.firstName} ${patient.lastName}
                    (${patient.email}, ${patient.phoneNumber})
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
