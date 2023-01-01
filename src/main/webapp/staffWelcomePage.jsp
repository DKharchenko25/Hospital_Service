<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <title>Hospital Staff Details</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="staffNavbar.jsp"/>
<jsp:include page="languageSelector.jsp"/>
<div class="card bg-light mb-3 border-info" style="width: 30rem;">
    <h2 class="card-header text-info"><fmt:message key="staff.details"/> </h2>
    <ul class="list-group list-group-flush">
        <li class="list-group-item"><b><fmt:message key="all_staff.table.id"/>:</b> ${sessionScope.user.id}</li>
        <li class="list-group-item"><b><fmt:message key="all_staff.table.username"/>:</b> ${sessionScope.user.username}</li>
        <li class="list-group-item"><b><fmt:message key="all_staff.table.first_name"/>:</b> ${sessionScope.user.firstName}</li>
        <li class="list-group-item"><b><fmt:message key="all_staff.table.last_name"/>:</b> ${sessionScope.user.lastName}</li>
        <li class="list-group-item"><b><fmt:message key="staff.details"/>:</b> ${sessionScope.user.role}</li>
        <li class="list-group-item"><b><fmt:message key="all_staff.table.category"/>:</b> ${sessionScope.user.category}</li>
        <li class="list-group-item"><b><fmt:message key="all_staff.table.number"/>:</b> ${sessionScope.user.numberOfPatients}</li>
    </ul>
    <div class="card-body">
        <a href="${pageContext.request.contextPath}/controller?command=all_doctors_patients&id=${sessionScope.user.id}"
           class="btn btn-primary">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3Zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z"></path>
            </svg>
            <fmt:message key="staff.patients"/></a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</div>
</body>
</html>
