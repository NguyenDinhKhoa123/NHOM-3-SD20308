<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center mt-5">
    <div class="col-md-5">
        <div class="card shadow-sm border-0">
            <div class="card-header border-0 text-white text-center py-3"
                 style="background: var(--pc-green); border-radius: 12px 12px 0 0;">
                <h5 class="mb-0 fw-bold">Đăng Ký Thành Viên</h5>
            </div>
            <div class="card-body p-4">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger py-2 small">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Email</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Mật khẩu</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Họ và tên</label>
                        <input type="text" name="fullname" class="form-control" required>
                    </div>
                    <div class="mb-4">
                        <label class="form-label small fw-bold">Số điện thoại</label>
                        <input type="text" name="phone" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Đăng Ký Ngay</button>
                </form>

                <div class="mt-3 text-center small" style="color: var(--pc-muted);">
                    Đã có tài khoản?
                    <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Đăng nhập</a>
                </div>
            </div>
        </div>
    </div>
</div>