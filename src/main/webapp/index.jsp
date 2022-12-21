<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty sessionScope.lang ? sessionScope.lang : 'en'}" scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${lang}">
<head>
    <title>Login</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="languageSelector.jsp"/>
<div class="text-center">
    <h1>
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hospital"
             viewBox="0 0 16 16">
            <path d="M8.5 5.034v1.1l.953-.55.5.867L9 7l.953.55-.5.866-.953-.55v1.1h-1v-1.1l-.953.55-.5-.866L7 7l-.953-.55.5-.866.953.55v-1.1h1ZM13.25 9a.25.25 0 0 0-.25.25v.5c0 .138.112.25.25.25h.5a.25.25 0 0 0 .25-.25v-.5a.25.25 0 0 0-.25-.25h-.5ZM13 11.25a.25.25 0 0 1 .25-.25h.5a.25.25 0 0 1 .25.25v.5a.25.25 0 0 1-.25.25h-.5a.25.25 0 0 1-.25-.25v-.5Zm.25 1.75a.25.25 0 0 0-.25.25v.5c0 .138.112.25.25.25h.5a.25.25 0 0 0 .25-.25v-.5a.25.25 0 0 0-.25-.25h-.5Zm-11-4a.25.25 0 0 0-.25.25v.5c0 .138.112.25.25.25h.5A.25.25 0 0 0 3 9.75v-.5A.25.25 0 0 0 2.75 9h-.5Zm0 2a.25.25 0 0 0-.25.25v.5c0 .138.112.25.25.25h.5a.25.25 0 0 0 .25-.25v-.5a.25.25 0 0 0-.25-.25h-.5ZM2 13.25a.25.25 0 0 1 .25-.25h.5a.25.25 0 0 1 .25.25v.5a.25.25 0 0 1-.25.25h-.5a.25.25 0 0 1-.25-.25v-.5Z"></path>
            <path d="M5 1a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v1a1 1 0 0 1 1 1v4h3a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1V8a1 1 0 0 1 1-1h3V3a1 1 0 0 1 1-1V1Zm2 14h2v-3H7v3Zm3 0h1V3H5v12h1v-3a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3Zm0-14H6v1h4V1Zm2 7v7h3V8h-3Zm-8 7V8H1v7h3Z"></path>
        </svg>
        Hospital Service
    </h1>
    <br>
    <h5 class="text-center"><fmt:message key="login.message" /></h5>
    <div class="row justify-content-center">
        <form class="w-25" action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-floating">
                <div class="form-floating">
                    <input required="required" class="form-control" type="text" id="username" name="username"
                           path="username"
                           placeholder="Username"
                           pattern="^[A-Za-z][A-Za-z0-9_]{5,29}$"
                           title="<fmt:message key="validation.username"/>"/>
                    <label for="username"><fmt:message key="login.username"/> </label>
                    <c:if test="${requestScope.isUsernameWrong == true}">
                        <br><div class="alert alert-danger" role="alert">
                                <fmt:message key="username.wrong"/>
                        </div>
                    </c:if>
                </div>
                <br>
                <div class="form-floating">
                    <input required="required" class="form-control" id="password" type="password" name="password"
                           path="password"
                           placeholder="Password"
                           pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
                           title="<fmt:message key="validation.password"/>"/>
                    <label for="password"><fmt:message key="login.password"/> </label>
                    <c:if test="${requestScope.isPasswordWrong == true}">
                        <br><div class="alert alert-danger" role="alert">
                                <fmt:message key="password.wrong"/>
                        </div>
                    </c:if>
                </div>
            </div>
            <br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="role" value="HOSPITAL_STAFF" id="role2">
                <label class="form-check-label" for="role2"><fmt:message key="login.hospital_staff"/> </label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="role" value="PATIENT" id="role3">
                <label class="form-check-label" for="role3"><fmt:message key="login.role_patient"/> </label>
            </div><br>
            <c:if test="${requestScope.isRoleNull == true}">
                <div class="alert alert-danger" role="alert">
                        <fmt:message key="role.choose"/>
                </div>
            </c:if>
            <c:if test="${requestScope.isRoleWrong == true}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="role.wrong"/>
                </div>
            </c:if>
            <br>
            <div>
                <button type="submit" class="btn btn-primary"><fmt:message key="login.log_in"/> </button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
