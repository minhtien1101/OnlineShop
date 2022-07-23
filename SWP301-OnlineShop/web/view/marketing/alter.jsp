<%-- 
    Document   : alter
    Created on : Jun 6, 2022, 2:43:48 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${requestScope.alter != null}">
    <div class="fixed float-end" id="showAlter">
        <div class="alert alert-success alert-dismissible fade in" id="alterfade">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${requestScope.alterSuccess}
        </div>
    </div>
</c:if>


<c:if test="${requestScope.alter != null}">
    <div class="fixed float-end" id="showAlter">
        <div class="alert alert-danger alert-dismissible fade in" id="alterfade">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${requestScope.alterFail}
        </div>
    </div>
</c:if>


<script>
    setTimeout(function () {
    const element = document.getElementById('showAlter');
    element.remove();
}, 3000);
</script>