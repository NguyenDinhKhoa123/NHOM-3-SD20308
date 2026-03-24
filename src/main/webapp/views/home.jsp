<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PolyCafe - ${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
        <div class="container">
            <a class="navbar-brand text-warning fw-bold" href="${pageContext.request.contextPath}/home">POLY CAFE</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                    <c:if test="${user.role eq 'admin'}">
                        <li class="nav-item"><a class="nav-link text-info" href="${pageContext.request.contextPath}/admin/dashboard">Quản trị</a></li>
                    </c:if>
                    <c:if test="${user.role eq 'staff' || user.role eq 'admin'}">
                        <li class="nav-item"><a class="nav-link text-success" href="${pageContext.request.contextPath}/staff/orders">Nhân viên</a></li>
                    </c:if>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <c:choose>
                        <c:when test="${not empty user}">
                            <li class="nav-item"><span class="nav-link text-white">Chào, ${user.fullname}</span></li>
                            <li class="nav-item"><a class="btn btn-danger btn-sm mt-1" href="${pageContext.request.contextPath}/logout">Thoát</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item"><a class="btn btn-primary btn-sm mt-1" href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5 flex-grow-1">
        <div class="card shadow p-5 text-center">
            <c:choose>
                <c:when test="${type eq 'ADMIN'}">
                    <h1 class="text-danger">ĐÂY LÀ KHU VỰC QUẢN TRỊ (ADMIN)</h1>
                    <p>Chào mừng sếp! Bạn có toàn quyền quản lý hệ thống PolyCafe.</p>
                </c:when>
                <c:when test="${type eq 'STAFF'}">
                    <h1 class="text-success">ĐÂY LÀ KHU VỰC NHÂN VIÊN (STAFF)</h1>
                    <p>Chào bạn! Hãy thực hiện các nghiệp vụ bán hàng và đơn hàng.</p>
                </c:when>
                <c:otherwise>
                    <h1 class="text-primary">CHÀO MỪNG KHÁCH HÀNG (GUEST)</h1>
                    <p>Hệ thống PolyCafe với hơn 50 món đồ uống hấp dẫn đang chờ bạn!</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <footer class="bg-dark text-white text-center py-3 mt-4">
        <p class="mb-0">© 2026 PolyCafe - Assignment 1</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>