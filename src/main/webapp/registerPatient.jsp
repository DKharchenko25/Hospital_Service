<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty sessionScope.lang ? sessionScope.lang : 'en'}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${lang}">
<head>
    <title>Patient Registration</title>
</head>
<meta charset="utf-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="adminNavbar.jsp"/>
<jsp:include page="languageSelector.jsp"/>
<form class="w-25 p-3" action="${pageContext.request.contextPath}/patient_registration" method="post">
    <h2><fmt:message key="registration.patient"/></h2>
    <div class="form-floating">
        <input required="required" class="form-control" type="text" id="username" name="username" path="username"
               placeholder="<fmt:message key="login.password"/>"
               pattern="^[A-Za-z][A-Za-z0-9_]{5,29}$"
               title="<fmt:message key="validation.username"/>"/>
        <label for="username"><fmt:message key="all_staff.table.username"/> </label>
    </div>
    <br>

    <c:if test="${requestScope.existingUsername == true}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="username.alert"/>
        </div><br>
    </c:if>

    <div class="row g-2">
        <div class="form-floating col-md">
            <input required="required" class="form-control" id="password" type="password" name="password"
                   path="password"
                   placeholder="<fmt:message key="login.password"/> "
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
                   title="<fmt:message key="validation.password"/>"/>
            <label for="password"><fmt:message key="login.password"/> </label>
        </div>
        <div class="form-floating col-md">
            <input required="required" class="form-control" type="password" id="confirmPassword" name="confirmPassword"
                   path="confirmPassword" placeholder="<fmt:message key="registration.confirm"/>"/>
            <label for="confirmPassword"><fmt:message key="registration.confirm"/> </label>
        </div>
    </div>
    <br>
    <c:if test="${requestScope.isWrongPassword == true}">
        <div class="alert alert-danger" role="alert">
                <fmt:message key="password.alert"/>
        </div>
        <br>
    </c:if>

    <div class="row g-2">
        <div class="form-floating col-md">
            <input required="required" class="form-control" type="text" id="firstName" name="firstName" path="firstName"
                   placeholder="<fmt:message key="all_staff.table.first_name"/>"
                   pattern="[a-zA-ZА-ЩЬЮЯҐЄІЇа-щьюяґєії']+"
                   title="<fmt:message key="validation.name.title"/>"/>
            <label for="firstName"><fmt:message key="all_staff.table.first_name"/> </label>
        </div>
        <div class="form-floating col-md">
            <input required="required" class="form-control" type="text" id="lastName" name="lastName" path="lastName"
                   placeholder="<fmt:message key="all_staff.table.last_name"/>"
                   pattern="[a-zA-ZА-ЩЬЮЯҐЄІЇа-щьюяґєії']+"
                   title="<fmt:message key="validation.name.title"/>"/>
            <label for="lastName"><fmt:message key="all_staff.table.last_name"/> </label>
        </div>
    </div>
    <br>

    <div class="col-md">
        <label for="birthDate" class="form-label">Birth date</label>
        <input id="birthDate" required="required" class="form-control" type="date" name="birthDate"
               path="birthDate"/>
    </div>
    <br>

    <div class="row g-2">
        <div class="form-floating col-md">
            <input required="required" type="email" class="form-control" id="email" name="email" path="email"
                   placeholder="<fmt:message key="patient.email"/>"/>
            <label for="email"><fmt:message key="patient.email"/> </label>
        </div>
        <div class="form-floating col-md">
            <input required="required" type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                   path="phoneNumber" placeholder="<fmt:message key="patient.phone_number"/>"
                   pattern="^((8|\+38)-?)?(\(?0\d{2}\)?)?-?\d{3}-?\d{2}-?\d{2}$"
                   title="<fmt:message key="validation.number.title"/>"/>
            <label for="phoneNumber"><fmt:message key="patient.phone_number"/> </label>
        </div>
    </div>
    <br>

    <button type="submit" class="btn btn-primary"><fmt:message key="registration.button"/></button>

</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
