<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card shadow-sm border-0 mt-5">
            <div class="card-body p-5">
                <h2 class="text-center mb-2 fw-bold" style="color: var(--pc-text);">PolyCafe Login</h2>
                <p class="text-center small mb-4" style="color: var(--pc-muted);">Đăng nhập để tiếp tục</p>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger py-2 text-center small">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Email đăng nhập</label>
                        <input type="email" name="email" class="form-control form-control-lg"
                               placeholder="example@gmail.com" required>
                    </div>
                    <div class="mb-4">
                        <label class="form-label small fw-bold">Mật khẩu</label>
                        <input type="password" name="password" class="form-control form-control-lg" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-lg w-100">Đăng nhập ngay</button>
                    <div class="text-center mt-4 d-flex justify-content-center gap-4">
                        <a href="${pageContext.request.contextPath}/forgot-password" class="text-decoration-none small">
                            Quên mật khẩu?
                        </a>
                        <a href="${pageContext.request.contextPath}/register" class="text-decoration-none small">
                            Đăng ký tài khoản
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>