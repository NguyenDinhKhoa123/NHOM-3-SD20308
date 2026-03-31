<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card shadow-lg border-0 mt-5">
            <div class="card-body p-5">
                <h2 class="text-center mb-4 text-primary fw-bold">Quên mật khẩu</h2>
                <p class="text-muted text-center small mb-4">Nhập email của bạn, chúng tôi sẽ gửi lại mật khẩu mới qua thư điện tử.</p>

                <c:if test="${not empty message}">
                    <div class="alert alert-success py-2 text-center small">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger py-2 text-center small">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                    <div class="mb-4">
                        <label class="form-label small fw-bold">Email tài khoản</label>
                        <input type="email" name="email" class="form-control form-control-lg" placeholder="Nhập email đã đăng ký..." required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-lg w-100 shadow-sm">Gửi yêu cầu</button>

                    <div class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/login" class="text-decoration-none small">Quay lại Đăng nhập</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>