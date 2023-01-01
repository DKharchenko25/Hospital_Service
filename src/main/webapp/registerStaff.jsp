<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <title>Hospital Staff Registration</title>
</head>
<meta charset="utf-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="adminNavbar.jsp"/>
<jsp:include page="languageSelector.jsp"/>
<div class="container h-100">
    <div class="row d-flex justify-content-center align-items-center h-75">
        <div class="col-12 col-md-9 col-lg-7 col-xl-6">
            <div class="card" style="border-radius: 15px;">
                <div class="card-body p-5">
                    <h2 class="text text-center mb-5"><fmt:message key="registration.staff"/></h2>
                    <form action="${pageContext.request.contextPath}/controller?command=staff_registration"
                          method="post">
                        <div class="form-floating mb-4">
                            <input required="required" class="form-control" type="text" id="username" name="username"
                                   path="username"
                                   placeholder="<fmt:message key="all_staff.table.username"/>"
                                   pattern="^[A-Za-z][A-Za-z0-9_]{5,29}$"
                                   title="<fmt:message key="validation.username"/>"/>
                            <label for="username"><fmt:message key="all_staff.table.username"/></label>
                        </div>

                        <c:if test="${requestScope.existingUsername == true}">
                            <div class="alert alert-danger" role="alert">
                                <fmt:message key="username.alert"/>
                            </div>
                            <br>
                        </c:if>

                        <div class="row g-2">
                            <div class="form-floating col-md">
                                <input required="required" class="form-control" id="password" type="password"
                                       name="password"
                                       path="password"
                                       placeholder="<fmt:message key="login.password"/>"
                                       pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
                                       title="<fmt:message key="validation.password"/>"/>
                                <label for="password"><fmt:message key="login.password"/></label>
                            </div>
                            <div class="form-floating col-md">
                                <input required="required" class="form-control" type="password" id="confirmPassword"
                                       name="confirmPassword"
                                       path="confirmPassword" placeholder="<fmt:message key="registration.confirm"/>"/>
                                <label for="confirmPassword"><fmt:message key="registration.confirm"/></label>
                            </div>
                        </div>

                        <div class="form-check-inline">
                            <input class="form-check-input" type="checkbox" onclick="myFunction()"
                                   id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                <fmt:message key="show.password"/>
                            </label>
                        </div><br><br>

                        <c:if test="${requestScope.isWrongPassword == true}">
                            <div class="alert alert-danger" role="alert">
                                <fmt:message key="password.alert"/>
                            </div>
                            <br>
                        </c:if>

                        <div class="row g-2">
                            <div class="form-floating col-md">
                                <input required="required" class="form-control" type="text" id="firstName"
                                       name="firstName" path="firstName"
                                       placeholder="First Name"
                                       pattern="[a-zA-ZА-ЩЬЮЯҐЄІЇа-щьюяґєії']+"
                                       title="<fmt:message key="validation.name.title"/>"/>
                                <label for="firstName"><fmt:message key="all_staff.table.first_name"/></label>
                            </div>
                            <div class="form-floating col-md">
                                <input required="required" class="form-control" type="text" id="lastName"
                                       name="lastName" path="lastName"
                                       placeholder="Last Name"
                                       pattern="[a-zA-ZА-ЩЬЮЯҐЄІЇа-щьюяґєії']+"
                                       title="<fmt:message key="validation.name.title"/>"/>
                                <label for="lastName"><fmt:message key="all_staff.table.last_name"/></label>
                            </div>
                        </div><br>

                        <div class="row g-2">
                            <div class="col-md">
                                <label for="roles" class="form-label"><fmt:message key="all_staff.table.role"/></label>
                                <input class="form-control" id="roles" name="role" list="role">
                                <datalist id="role">
                                    <c:forEach items="${sessionScope.roles}" var="role">
                                        <option value="${role.name}">${role.name}</option>
                                    </c:forEach>
                                </datalist>
                            </div>
                            <div class="col-md">
                                <label for="categories" class="form-label"><fmt:message
                                        key="all_staff.table.category"/></label>
                                <input class="form-control" id="categories" name="category" list="category">
                                <datalist id="category">
                                    <c:forEach items="${sessionScope.categories}" var="category">
                                        <option value="${category.name}">${category.name}</option>
                                    </c:forEach>
                                </datalist>
                            </div>
                        </div><br>
                        <div class="g-recaptcha" data-sitekey="6LeSp8EjAAAAALplFtsvOwZYmypnSZMtgNOOUdo-"></div><br>
                        <c:if test="${requestScope.wrongRecaptcha == true}">
                            <div class="alert alert-danger" role="alert">
                                <fmt:message key="recaptcha.wrong"/>
                            </div>
                            <br>
                        </c:if>
                        <div class="mb-4 justify-content-center">
                            <button type="submit" class="btn btn-primary btn-block btn-lg col-12"><fmt:message
                                    key="registration.button"/></button>
                        </div>

                    </form>
                </div>
            </div>
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
<script src="https://www.google.com/recaptcha/api.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>

