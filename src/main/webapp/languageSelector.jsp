<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty sessionScope.lang ? sessionScope.lang : 'en'}" scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${lang}">
<head>
    <title>Title</title>
</head>
<body>
<div class="position-absolute top-0 end-0">
    <form>
        <select class="form-select form-select-sm" name="lang" onchange="submit()" aria-label="...">
            <option value="en" ${lang == 'en' ? 'selected' : ''}>English</option>
            <option value="ua" ${lang == 'ua' ? 'selected' : ''}>Українська</option>
        </select>
    </form>
</div>
</body>
</html>
