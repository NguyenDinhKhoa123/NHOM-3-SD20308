<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
  <div class="container-fluid">
    <a class="navbar-brand fw-bold text-success" href="${pageContext.request.contextPath}/home">🌿 PolyCafe</a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/drinks">Thực đơn</a>
        </li>

        <%-- 1. CHỈ HIỆN KHI ĐÃ ĐĂNG NHẬP (Dành cho khách xem 3 hóa đơn cũ) --%>
        <c:if test="${not empty sessionScope.user}">
            <li class="nav-item">
                <a class="nav-link text-primary fw-bold" href="${pageContext.request.contextPath}/my-orders">
                    Đơn hàng của tôi
                </a>
            </li>
        </c:if>

        <%-- 2. CHỈ HIỆN CHO ADMIN/STAFF (Quản lý 50 món và duyệt đơn) --%>
        <c:if test="${sessionScope.user.role == 'admin' || sessionScope.user.role == 'staff'}">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle text-danger" href="#" id="adminDropdown" role="button" data-bs-toggle="dropdown">
                    Quản trị
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/drinks">Quản lý món ăn</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/orders">Quản lý đơn hàng</a></li>
                </ul>
            </li>
        </c:if>
      </ul>

      <%-- 3. KHỐI ĐĂNG NHẬP / NGƯỜI DÙNG --%>
      <div class="d-flex align-items-center">
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary px-4">Đăng nhập</a>
            </c:when>
            <c:otherwise>
                <div class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle fw-bold" href="#" id="userMenu" role="button" data-bs-toggle="dropdown">
                        Chào, ${sessionScope.user.fullname}
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</nav>