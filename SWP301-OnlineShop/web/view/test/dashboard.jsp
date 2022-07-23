<%-- 
    Document   : dashboard
    Created on : May 26, 2022, 11:58:59 PM
    Author     : hoan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simple dashboard</title>
    </head>
    <body>
        <h1>Hello - ${sessionScope.user.fullname}</h1>
        <div class="navbar">
            <c:forEach items="${sessionScope.user.role.allowFeatures}" var="s">
                 <a href="${pageContext.request.contextPath}${s.key.url}">${s.key.url} - ${s.key.name} ||</a>
                <c:if test="${s.key.url == '/page1'}">
                    <a href="${pageContext.request.contextPath}${s.key.url}">${s.key.name} ||</a>
                </c:if>
                <c:if test="${s.key.url == '/page2'}">
                    <a href="${pageContext.request.contextPath}${s.key.url}">${s.key.name} ||</a>
                </c:if>
                <c:if test="${s.key.url == '/page3'}">
                    <a href="${pageContext.request.contextPath}${s.key.url}">${s.key.name} ||</a>
                </c:if>
                <c:if test="${s.key.url == '/page4'}">
                    <a href="${pageContext.request.contextPath}${s.key.url}">${s.key.name} ||</a>
                </c:if>
            </c:forEach>
        </div>
    </body>
</html>
