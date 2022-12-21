<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty sessionScope.lang ? sessionScope.lang : 'en'}" scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${lang}">
<head>
    <title>Patient's Hospital Cards</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="staffNavbar.jsp"/>
<div class="container align-content-center">
    <div class="card bg-light mb-3 border-info" style="width: 80rem;">
        <h2 class="card-header text-info"><fmt:message key="patient.card"/> </h2>
        <ul class="list-group list-group-flush">
            <c:forEach var="card" items="${requestScope.cards}">
                <li class="list-group-item">
                    <div class="row">
                        <div class="col">
                            <h5 class="text-dark"><fmt:message key="login.role_doctor"/>:</h5>
                            <p class="card-text">${card.doctor.firstName} ${card.doctor.lastName}
                                (${card.doctor.category})</p>
                        </div>
                        <div class="col">
                            <h5 class="text-dark"><fmt:message key="patient.record_date"/>:</h5>
                            <p class="card-text">${card.recordDate}</p>
                        </div>
                        <div class="col">
                            <h5 class="text-dark"><fmt:message key="patient.procedures"/>:</h5>
                            <p class="card-text">${card.procedures}</p>
                        </div>
                        <div class="col">
                            <h5 class="text-dark"><fmt:message key="patient.medications"/>:</h5>
                            <p class="card-text">${card.medications}</p>
                        </div>
                        <div class="col">
                            <h5 class="text-dark"><fmt:message key="patient.operations"/>:</h5>
                            <p class="card-text">${card.operations}</p>
                        </div>
                        <div class="col">
                            <h5 class="text-dark"><fmt:message key="patient.diagnosis"/>:</h5>
                            <p class="card-text">${card.diagnosis}</p>
                        </div>
                        <div class="col">
                            <a href="${pageContext.request.contextPath}/edit_card?id=${card.id}"
                               class="btn btn-secondary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                     class="bi bi-pencil-square"
                                     viewBox="0 0 16 16">
                                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"></path>
                                    <path fill-rule="evenodd"
                                          d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"></path>
                                </svg>
                                <fmt:message key="patient.edit"/></a>
                        </div>
                        <div class="col">
                            <a href="${pageContext.request.contextPath}/remove_card?id=${card.id}"
                               class="btn btn-danger">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-minus" viewBox="0 0 16 16">
                                    <path d="M5.5 9a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z"></path>
                                    <path d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"></path>
                                </svg>
                                <fmt:message key="all_staff.table.remove"/>
                            </a>
                        </div>
                    </div>
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
