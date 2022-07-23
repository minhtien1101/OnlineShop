<%-- 
    Document   : sideBar
    Created on : May 25, 2022, 5:34:33 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="left-side sidebar-offcanvas">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">

            <c:if test="${sessionScope.user.avatar != null}">
                <div class="pull-left image">
                    <img src="${sessionScope.user.avatar}" class="img-circle" alt="User Image" />
                </div>
            </c:if>
            <c:if test="${sessionScope.user.avatar == null}">
                <div class="pull-left image">
                    <img src="../assets/img/defaultUserAvatar.png" class="img-circle" alt="User Image" />
                </div>
            </c:if>
            <div class="pull-left info" style="width: 60%">
                <p>${sessionScope.user.fullname}</p>
                <a href="#">${sessionScope.user.role.name}</a>
            </div>
        </div>
        <ul class="sidebar-menu">
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <c:forEach items="${sessionScope.user.role.allowFeatures}" var="s">
                <c:if test="${s.key.url == '/admin/dashboard' && s.value == true}">
                    <li id="nav-dashboard">
                        <a href="${s.key.url}">
                            <i class="fa fa-dashboard"></i> <span>${s.key.name}</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${s.key.url == '/admin/userList' && s.value == true}">
                    <li id="nav-user-list">
                        <a href="userList">
                            <i class="fa fa-users"></i><span>${s.key.name}</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${s.key.url == '/admin/addRole' && s.value == true}">
                    <li id="nav-add-feature-group">
                        <a href="addRole">
                            <i class="fa fa-plus-circle"></i> <span>${s.key.name}</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${s.key.url == '/admin/editRole' && s.value == true}">
                    <li id="nav-add-feature-group">
                        <a href="${s.key.url}">
                            <i class="fa fa-pencil-square-o"></i> <span>${s.key.name}</span>
                        </a>
                    </li>
                </c:if>

                <c:if test="${s.key.url == '/customer/list' && s.value == true}">
                    <li id="nav-add-feature-group">
                        <a href="${s.key.url}">
                            <i class="fa fa-user-circle-o"></i> <span>${s.key.name}</span>
                        </a>
                    </li>
                </c:if>
            </c:forEach>
    </section>
    <!-- /.sidebar -->
</aside>
