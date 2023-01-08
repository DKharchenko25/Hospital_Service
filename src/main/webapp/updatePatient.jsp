<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<%@ taglib prefix="date" uri = "/WEB-INF/custom.tld"%>
<html lang="${sessionScope.lang}">
<head>
    <title>Update Patient Details</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="adminNavbar.jsp"/>
<jsp:include page="languageSelector.jsp"/>
<div class="row">
    <div class="col-md-auto">
        <div class="card bg-light mb-3 border-info d-inline-block" style="width: 30rem;">
            <h2 class="card-header text-info"><fmt:message key="patient.details"/></h2>
            <ul class="list-group list-group-flush">
                <tags:listItem title="all_staff.table.id" value="${requestScope.patient.id}"/>
                <tags:listItem title="all_staff.table.username" value="${requestScope.patient.username}"/>
                <tags:listItem title="all_staff.table.first_name" value="${requestScope.patient.firstName}"/>
                <tags:listItem title="all_staff.table.last_name" value="${requestScope.patient.lastName}"/>
                <tags:listItem title="patients.birth_date" value="${requestScope.patient.birthDate}"/>
                <tags:listItem title="patient.email" value="${requestScope.patient.email}"/>
                <tags:listItem title="patient.phone_number" value="${requestScope.patient.phoneNumber}"/>
            </ul>
            <div class="card-body">
                <button type="button" class="btn btn-secondary" disabled>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-pencil-square"
                         viewBox="0 0 16 16">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"></path>
                        <path fill-rule="evenodd"
                              d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"></path>
                    </svg>
                    <fmt:message key="patient.edit"/>
                </button>
                <a href="${pageContext.request.contextPath}/controller?command=appoint_patient&patientId=${requestScope.patient.id}"
                   class="btn btn-primary">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-card-checklist" viewBox="0 0 16 16">
                        <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z"></path>
                        <path d="M7 5.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-1.496-.854a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0zM7 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-1.496-.854a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"></path>
                    </svg>
                    <fmt:message key="patient.appoint"/>
                </a>
                <a href="${pageContext.request.contextPath}/controller?command=all_patients_doctors&id=${requestScope.patient.id}"
                   class="btn btn-success">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-person-fill" viewBox="0 0 16 16">
                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3Zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z"></path>
                    </svg>
                    <fmt:message key="patient.doctors"/>
                </a><br><br>
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
                <a href="${pageContext.request.contextPath}/controller?command=discharge_patient&id=${requestScope.patient.id}"
                   class="btn btn-primary">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-send-check" viewBox="0 0 16 16">
                        <path d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855a.75.75 0 0 0-.124 1.329l4.995 3.178 1.531 2.406a.5.5 0 0 0 .844-.536L6.637 10.07l7.494-7.494-1.895 4.738a.5.5 0 1 0 .928.372l2.8-7Zm-2.54 1.183L5.93 9.363 1.591 6.602l11.833-4.733Z"></path>
                        <path d="M16 12.5a3.5 3.5 0 1 1-7 0 3.5 3.5 0 0 1 7 0Zm-1.993-1.679a.5.5 0 0 0-.686.172l-1.17 1.95-.547-.547a.5.5 0 0 0-.708.708l.774.773a.75.75 0 0 0 1.174-.144l1.335-2.226a.5.5 0 0 0-.172-.686Z"></path>
                    </svg>
                    <fmt:message key="patient.discharge"/>
                </a>
            </div>
        </div>
    </div>
    <br>
    <div class="col">
        <div class="card bg-light mb-3 border-info d-inline-block" style="width: 40rem;">
            <h2 class="card-header text-info"><fmt:message key="patient.edit"/></h2>
            <form action="${pageContext.request.contextPath}/controller?command=update_patient" method="post">
                <input type="hidden" name="id" value="${requestScope.patient.id}">
                <div class="card-body">

                    <input class="form-control" type="text" id="username" name="newUsername"
                           path="username"
                           placeholder="<fmt:message key="all_staff.table.username"/>"
                           aria-label="<fmt:message key="all_staff.table.username"/>"
                           pattern="^[A-Za-z][A-Za-z0-9_]{5,29}$"
                           title="<fmt:message key="validation.username"/>"/>

                    <br>
                    <c:if test="${sessionScope.existingUsername == true}">
                        <div class="alert alert-danger" role="alert">
                            <fmt:message key="username.alert"/>
                        </div>
                        <br>
                    </c:if>


                    <input class="form-control" id="password" type="password" name="newPassword"
                           path="password"
                           placeholder="<fmt:message key="login.password"/>"
                           aria-label="<fmt:message key="login.password"/>"
                           pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
                           title="<fmt:message key="validation.password"/>"/>

                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" onclick="myFunction()" id="flexCheckDefault">
                        <label class="form-check-label" for="flexCheckDefault">
                            <fmt:message key="show.password"/>
                        </label>
                    </div>
                    <br>

                    <input class="form-control" type="text" id="firstName"
                           name="newFirstName"
                           path="firstName"
                           placeholder="<fmt:message key="all_staff.table.first_name"/>"
                           aria-label="<fmt:message key="all_staff.table.first_name"/>"
                           pattern="[a-zA-ZА-ЩЬЮЯҐЄІЇа-щьюяґєії']+"
                           title="<fmt:message key="validation.name.title"/>"/><br>


                    <input class="form-control" type="text" id="lastName" name="newLastName"
                           path="lastName"
                           placeholder="<fmt:message key="all_staff.table.last_name"/>"
                           aria-label="<fmt:message key="all_staff.table.last_name"/>"
                           pattern="[a-zA-ZА-ЩЬЮЯҐЄІЇа-щьюяґєії']+"
                           title="<fmt:message key="validation.name.title"/>"/><br>


                    <input id="birthDate" class="form-control" type="date"
                           name="newBirthDate"
                           path="birthDate"
                           aria-label="<fmt:message key="patients.birth_date"/>"
                           max="<date:now/>"/><br>


                    <input type="email" class="form-control" id="email" name="newEmail"
                           path="email"
                           placeholder="<fmt:message key="patient.email"/>"
                           aria-label="<fmt:message key="patient.email"/>"/><br>


                    <input type="text" class="form-control" id="phoneNumber"
                           name="newPhoneNumber"
                           path="phoneNumber"
                           placeholder="<fmt:message key="patient.phone_number"/>"
                           aria-label="<fmt:message key="patient.phone_number"/>"
                           pattern="^((8|\+38)-?)?(\(?0\d{2}\)?)?-?\d{3}-?\d{2}-?\d{2}$"
                           title="<fmt:message key="validation.number.title"/>"/>

                </div>
                <div class="card-body">
                    <button type="submit" class="btn btn-primary"><fmt:message
                            key="patient.button.done"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<script lang="java_script">
    function myFunction() {
        var x = document.getElementById("password");
        if (x.type === "password") {
            x.type = "text";
        } else {
            x.type = "password";
        }
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
