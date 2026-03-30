<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="p-5 mb-4 bg-light rounded-3 shadow-sm border">
    <div class="container-fluid py-5 text-center">
        <h1 class="display-4 fw-bold text-success">🌿 PolyCafe System</h1>
        <p class="fs-4 text-muted">Chào mừng bạn: <span class="text-primary fw-bold">${empty sessionScope.user ? 'Khách quý' : sessionScope.user.fullname}</span></p>
        <p class="col-md-10 mx-auto fs-5">
            Khám phá thực đơn phong phú với hơn 50 loại đồ uống từ Cà phê truyền thống đến các loại Trà trái cây thanh mát.
            Đặt hàng ngay để nhận nhiều ưu đãi!
        </p>
        <div class="mt-4">
            <a href="drinks" class="btn btn-success btn-lg px-5">Xem thực đơn ngay</a>
            <c:if test="${empty sessionScope.user}">
                <a href="login" class="btn btn-outline-primary btn-lg px-5">Đăng nhập</a>
            </c:if>
        </div>
    </div>
</div>

<div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
  <div class="col d-flex align-items-start">
    <div class="icon-square text-bg-light d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">

    </div>
    <div>
      <h3 class="fs-4">Chất lượng hàng đầu</h3>
      <p>Cà phê được tuyển chọn từ những hạt Robusta và Arabica chất lượng nhất từ vùng cao nguyên Việt Nam.</p>
    </div>
  </div>
  <div class="col d-flex align-items-start">
    <div class="icon-square text-bg-light d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">

    </div>
    <div>
      <h3 class="fs-4">Phục vụ nhanh chóng</h3>
      <p>Đội ngũ nhân viên chuyên nghiệp, đảm bảo đồ uống được giao đến tay bạn trong thời gian ngắn nhất.</p>
    </div>
  </div>
  <div class="col d-flex align-items-start">
    <div class="icon-square text-bg-light d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">

    </div>
    <div>
      <h3 class="fs-4">Đặt hàng online</h3>
      <p>Hệ thống đặt hàng thông minh, dễ dàng theo dõi trạng thái đơn hàng và lịch sử mua sắm của bạn.</p>
    </div>
  </div>
</div>