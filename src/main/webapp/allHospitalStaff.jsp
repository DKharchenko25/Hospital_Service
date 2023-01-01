<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE HTML>
<html lang="${sessionScope.lang}">
<head>
    <title>All Hospital Staff</title>
</head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<body>
<jsp:include page="adminNavbar.jsp"/>
<jsp:include page="languageSelector.jsp"/>
<div class="container align-content-center">
    <h1><fmt:message key="all_staff.h1" /></h1>
    <table class="table table-hover mb-3">
        <tr>
            <th scope="col"><fmt:message key="all_staff.table.id"/> </th>
            <th scope="col"><fmt:message key="all_staff.table.username"/></th>
            <th scope="col"><fmt:message key="all_staff.table.first_name"/></th>
            <th scope="col"><fmt:message key="all_staff.table.last_name"/></th>
            <th scope="col"><fmt:message key="all_staff.table.category"/></th>
            <th scope="col"><fmt:message key="all_staff.table.role"/></th>
            <th scope="col"><fmt:message key="all_staff.table.number"/></th>
            <th scope="col"><fmt:message key="all_staff.table.details"/></th>
            <th scope="col"><fmt:message key="all_staff.table.remove"/></th>
        </tr>
        <c:forEach var="hospitalStaff" items="${requestScope.allHospitalStaff}">
            <tr>
                <td>${hospitalStaff.id}</td>
                <td>${hospitalStaff.username}</td>
                <td>${hospitalStaff.firstName}</td>
                <td>${hospitalStaff.lastName}</td>
                <td>${hospitalStaff.category}</td>
                <td>${hospitalStaff.role}</td>
                <td>${hospitalStaff.numberOfPatients}</td>
                <td><a href="${pageContext.request.contextPath}/controller?command=get_hospital_staff&id=${hospitalStaff.id}"
                       class="btn btn-info"
                       role="button"><fmt:message key="all_staff.table.details"/></a></td>
                <td><a href="${pageContext.request.contextPath}/controller?command=delete_hospital_staff&id=${hospitalStaff.id}"
                       class="btn btn-danger"
                       role="button"><fmt:message key="all_staff.table.remove"/></a></td>
            </tr>
        </c:forEach> <br>
        <form class="w-75 p-3" action="${pageContext.request.contextPath}/controller?command=all_hospital_staff" method="get">
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="sortingButton"
                        data-bs-toggle="dropdown"
                        aria-expanded="false">
                    <fmt:message key="all_staff.table.sort"/>
                </button>
                <ul class="dropdown-menu" aria-labelledby="sortingButton">
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=all_hospital_staff&page=${requestScope.currentPage}&sorting=alphabetically"><fmt:message key="all_staff.table.alph"/></a>
                    </li>
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=all_hospital_staff&page=${requestScope.currentPage}&sorting=byCategory"><fmt:message key="all_staff.table.category"/></a>
                    </li>
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=all_hospital_staff&page=${requestScope.currentPage}&sorting=byNumberOfPatients"><fmt:message key="all_staff.table.number"/></a>
                    </li>
                </ul>
            </div>
        </form>
    </table>
    <div class="row">
        <div class="col-auto">
            <a href="${pageContext.request.contextPath}/controller?command=staff_registration" class="btn btn-primary" role="button">
                <fmt:message key="all_staff.button.add_staff"/></a>
        </div>
        <div class="col-auto">
            <nav aria-label="...">
                <ul class="pagination">
                    <c:choose>
                        <c:when test="${requestScope.currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=all_hospital_staff&page=${requestScope.currentPage - 1}&sorting=${requestScope.sorting}">
                                    <fmt:message key="all_staff.pagination.previous"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1" aria-disabled="true"><fmt:message key="all_staff.pagination.previous"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach begin="1" end="${requestScope.numberOfPages}" var="pageNumber">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=all_hospital_staff&page=${pageNumber}&sorting=${requestScope.sorting}">${pageNumber}</a>
                        </li>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${requestScope.currentPage != requestScope.numberOfPages}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=all_hospital_staff&page=${requestScope.currentPage + 1}&sorting=${requestScope.sorting}">
                                    <fmt:message key="all_staff.pagination.next"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1" aria-disabled="true"><fmt:message key="all_staff.pagination.next"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
