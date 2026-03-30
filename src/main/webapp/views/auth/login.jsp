<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - PolyCafe</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-secondary p-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-4 card p-4 shadow-lg border-0">
                <h3 class="text-center mb-4 text-primary fw-bold">PolyCafe Login</h3>

                <c:if test="${not empty message}">
                    <div class="alert alert-danger py-2 text-center small">${message}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3">
                        <label class="form-label small">Email</label>
                        <input type="email" name="email" class="form-control" placeholder="" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small">Mật khẩu</label>
                        <input type="password" name="password" class="form-control" placeholder="" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                    <div class="text-center mt-3">
                        <a href="${pageContext.request.contextPath}/home" class="text-decoration-none small">Quay lại trang chủ</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>