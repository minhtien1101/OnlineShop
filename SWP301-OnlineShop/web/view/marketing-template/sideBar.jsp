<%-- 
    Document   : sideBar
    Created on : May 25, 2022, 5:34:33 AM
    Author     : Admin
--%>

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
            <li id="nav-dashboard">
                <a href="/marketing/dashboard">
                    <i class="fa fa-tachometer"></i> <span>Dashboard</span>
                </a>
            </li>
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <c:forEach items="${sessionScope.user.role.allowFeatures}" var="s">
                <c:if test="${s.key.url == '/marketing/sliderList' && s.value == true}">
                    <li id="nav-user-list">
                        <a href="/marketing/sliderList">
                            <i class="fa fa-th-list" aria-hidden="true"></i>    <span>${s.key.name}</span>        
                        </a>
                    </li>
                </c:if>

                <%--<c:if test="${s.key.url == '/marketing/productlist' && s.value == true}">--%>
                <!--                    <li id="nav-user-list">
                                        <a href="productlist">
                                            <i class="fa-solid fa-box-open"></i> <span>Product List</span>
                                        </a>
                                    </li>-->
                <%--</c:if>--%>

                <c:if test="${s.key.url == '/marketing/postlist' && s.value == true}">
                    <li id="nav-user-list">
                        <a href="/marketing/postlist">
                            <i class="fa-solid fa-rss"></i>   <span>  Post List</span>

                        </a>
                    </li>
                </c:if>    

                <c:if test="${s.key.url == '/marketing/feedbacklist' && s.value == true}">
                    <li id="nav-user-list">
                        <a href="/marketing/feedbacklist">
                            <i class="fas fa-comment"></i>    <span>Feedback List</span>
                        </a>
                    </li>
                </c:if>    

                <c:if test="${s.key.url == '/customer/list' && s.value == true}">
                    <li id="nav-user-list">
                        <a href="../customer/list">
                            <i class="glyphicon glyphicon-user"></i>    <span>Customer List</span>
                        </a>
                    </li>
                </c:if>    
            </c:forEach>
            <li id="nav-user-list">
                <a href="productlist">
                    <i class="fa-solid fa-box-open"></i> <span>Product List</span>
                </a>
            </li>
        </ul>         

    </section>
    <!-- /.sidebar -->
</aside>
