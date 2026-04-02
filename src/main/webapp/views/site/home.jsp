<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<div class="p-5 mb-4 rounded-3 shadow-sm border text-center"
     style="background-color: #eafaf1;">
    <div class="container-fluid py-4">
        <h1 class="display-4 fw-bold" style="color: #27ae60;">🌿 PolyCafe System</h1>

        <p class="fs-5 text-muted mt-2">
            Chào mừng bạn:
            <span class="fw-bold" style="color: #219150;">
                ${empty sessionScope.user ? 'Khách quý' : sessionScope.user.fullname}
            </span>
        </p>

        <p class="col-md-8 mx-auto" style="color: #2c3e50;">
            Khám phá thực đơn phong phú với hơn 50 loại đồ uống từ Cà phê truyền thống
            đến các loại Trà trái cây thanh mát. Đặt hàng ngay để nhận nhiều ưu đãi!
        </p>

        <div class="mt-4 d-flex gap-3 justify-content-center">
            <a href="${pageContext.request.contextPath}/drinks"
               class="btn btn-lg px-5"
               style="background: #27ae60; color: #fff;">
                Xem thực đơn ngay
            </a>
            <c:if test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/login"
                   class="btn btn-lg btn-outline-secondary px-5">
                    Đăng nhập
                </a>
            </c:if>
        </div>
    </div>
</div>

<div class="row g-4 py-4 row-cols-1 row-cols-lg-3">
    <div class="col d-flex align-items-start">
        <div class="fs-2 me-3" style="color: #27ae60;"><i class="bi bi-award"></i></div>
        <div>
            <h5 class="fw-bold" style="color: #2c3e50;">Chất lượng hàng đầu</h5>
            <p class="text-muted">Cà phê được tuyển chọn từ những hạt Robusta và Arabica chất lượng nhất
               từ vùng cao nguyên Việt Nam.</p>
        </div>
    </div>
    <div class="col d-flex align-items-start">
        <div class="fs-2 me-3" style="color: #27ae60;"><i class="bi bi-lightning-charge"></i></div>
        <div>
            <h5 class="fw-bold" style="color: #2c3e50;">Phục vụ nhanh chóng</h5>
            <p class="text-muted">Đội ngũ nhân viên chuyên nghiệp, đảm bảo đồ uống được giao đến tay bạn
               trong thời gian ngắn nhất.</p>
        </div>
    </div>
    <div class="col d-flex align-items-start">
        <div class="fs-2 me-3" style="color: #27ae60;"><i class="bi bi-phone"></i></div>
        <div>
            <h5 class="fw-bold" style="color: #2c3e50;">Đặt hàng online</h5>
            <p class="text-muted">Hệ thống đặt hàng thông minh, dễ dàng theo dõi trạng thái đơn hàng
               và lịch sử mua sắm của bạn.</p>
        </div>
    </div>
</div>