<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<nav class="navbar navbar-expand-lg navbar-dark shadow-sm py-2"
     style="background-color: #219150;">
    <div class="container">
        <a class="navbar-brand fw-bold fs-4 text-white"
           href="${pageContext.request.contextPath}/home">
            🌿 PolyCafe
        </a>

        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link text-white"
                       href="${pageContext.request.contextPath}/home">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white"
                       href="${pageContext.request.contextPath}/drinks">Thực đơn</a>
                </li>

                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item border-start ms-2 ps-2">
                        <a class="nav-link text-white"
                           href="${pageContext.request.contextPath}/my-orders">Đơn của tôi</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.user.role == 'admin' || sessionScope.user.role == 'staff'}">
                    <li class="nav-item dropdown ms-lg-3">
                        <a class="nav-link dropdown-toggle fw-bold text-white" href="#"
                           id="adminDropdown" role="button" data-bs-toggle="dropdown">
                            Quản trị
                        </a>
                        <ul class="dropdown-menu shadow border-0">
                            <c:if test="${sessionScope.user.role == 'admin'}">
                                <li>
                                    <a class="dropdown-item py-2"
                                       href="${pageContext.request.contextPath}/admin/dashboard">
                                        <i class="bi bi-speedometer2 me-2"></i>Dashboard
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item py-2"
                                       href="${pageContext.request.contextPath}/admin/drinks">
                                        <i class="bi bi-cup-hot me-2"></i>Quản lý đồ uống
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item py-2"
                                       href="${pageContext.request.contextPath}/admin/users">
                                        <i class="bi bi-people me-2"></i>Quản lý người dùng
                                    </a>
                                </li>
                                <li><hr class="dropdown-divider"></li>
                            </c:if>
                            <li>
                                <a class="dropdown-item py-2"
                                   href="${pageContext.request.contextPath}/admin/orders">
                                    <i class="bi bi-receipt me-2"></i>Quản lý đơn hàng
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item py-2"
                                   href="${pageContext.request.contextPath}/admin/orders?history=true">
                                    <i class="bi bi-clock-history me-2"></i>Lịch sử bán hàng
                                </a>
                            </li>
                        </ul>
                    </li>
                </c:if>
            </ul>

            <div class="d-flex align-items-center">
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/login"
                           class="btn btn-outline-light btn-sm px-4 rounded-pill">
                            Đăng nhập
                        </a>
                    </c:when>
                    <c:otherwise>
                        <div class="dropdown">
                            <a class="nav-link dropdown-toggle fw-bold text-white d-flex align-items-center"
                               href="#" id="userMenu" role="button" data-bs-toggle="dropdown">
                                <i class="bi bi-person-circle fs-5 me-2"></i>
                                ${sessionScope.user.fullname}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end shadow border-0 mt-2">
                                <li class="px-3 py-2 small border-bottom" style="color: #7f8c8d;">
                                    Quyền:
                                    <span class="badge text-uppercase"
                                          style="background-color: #27ae60;">
                                        ${sessionScope.user.role}
                                    </span>
                                </li>
                                <li>
                                    <a class="dropdown-item py-2 text-danger"
                                       href="${pageContext.request.contextPath}/logout">
                                        <i class="bi bi-box-arrow-right me-2"></i>Đăng xuất
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>