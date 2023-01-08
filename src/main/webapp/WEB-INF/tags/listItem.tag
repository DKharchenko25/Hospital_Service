<%@ attribute name="title"%>
<%@ attribute name="value"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<li class="list-group-item"><b><fmt:message key="${title}"/>:</b> ${value}</li>