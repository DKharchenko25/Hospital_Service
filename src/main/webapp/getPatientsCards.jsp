<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <title>Patient's Hospital Cards</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<c:choose>
    <c:when test="${sessionScope.role eq 'ADMIN'}">
        <jsp:include page="adminNavbar.jsp"/>
    </c:when>
    <c:when test="${sessionScope.role eq 'PATIENT'}">
        <jsp:include page="patientNavbar.jsp"/>
    </c:when>
</c:choose>
<jsp:include page="languageSelector.jsp"/>
<div class="container align-content-center">
    <div class="card bg-light mb-3 border-info" style="width: 80rem;">
        <h2 class="card-header text-info"><fmt:message key="patient.card"/></h2>
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
