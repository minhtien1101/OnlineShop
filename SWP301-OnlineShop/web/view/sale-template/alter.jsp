<%-- 
    Document   : alter
    Created on : Jun 6, 2022, 2:43:48 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${requestScope.alterSuccess != null}">
    <div class="fixed float-end" id="showAlterSuccess">
        <div class="alert alert-success alert-dismissible fade in" id="alterfade">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${requestScope.alterSuccess}
        </div>
    </div>
</c:if>


<c:if test="${requestScope.alterFail != null}">
    <div class="fixed float-end" id="showAlterFail">
        <div class="alert alert-danger alert-dismissible fade in" id="alterfade">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${requestScope.alterFail}
        </div>
    </div>
</c:if>


<script>
    <c:if test="${requestScope.alterSuccess != null}">
    setTimeout(function () {
        const element = document.getElementById('showAlterSuccess');
        element.remove();
    }, 3000);
    </c:if>
    <c:if test="${requestScope.alterFail != null}">
    setTimeout(function () {
        const element = document.getElementById('showAlterFail');
        element.remove();
    }, 3000);
    </c:if>
</script>