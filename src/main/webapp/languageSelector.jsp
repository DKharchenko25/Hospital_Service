<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <title>Title</title>
</head>
<body>
<div class="position-absolute top-0 end-0">
    <form action="${pageContext.request.contextPath}/controller?command=locale_handler" method="post">
        <select class="form-select form-select-sm" name="lang" onchange="submit()" aria-label="...">
            <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}>English</option>
            <option value="ua" ${sessionScope.lang == 'ua' ? 'selected' : ''}>Українська</option>
        </select>
    </form>
</div>
</body>
</html>
