<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<div class="container mt-4">
    <h2 class="mb-4">Bảng điều khiển hệ thống</h2>

    <div class="row">
        <div class="col-md-4">
            <div class="card text-white bg-success mb-3 shadow">
                <div class="card-body text-center">
                    <h5 class="card-title">Tổng Doanh Thu</h5>
                    <h2 class="card-text">
                        <fmt:formatNumber value="${totalRevenue}" type="number"/> VNĐ
                    </h2>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card text-white bg-primary mb-3 shadow">
                <div class="card-body text-center">
                    <h5 class="card-title">Tổng Đơn Hàng</h5>
                    <h2 class="card-text">${totalOrders}</h2>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card text-white bg-info mb-3 shadow">
                <div class="card-body text-center">
                    <h5 class="card-title">Món Trong Thực Đơn</h5>
                    <h2 class="card-text">${totalDrinks}</h2>
                </div>
            </div>
        </div>
    </div>

    <div class="mt-5 p-4 border rounded bg-light text-center">
        <h4>Chào mừng quay trở lại, Quản trị viên!</h4>
        <p>Hệ thống hiện đang hoạt động ổn định với dữ liệu thực tế.</p>
        <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-outline-dark">Duyệt đơn hàng ngay</a>
    </div>
</div>