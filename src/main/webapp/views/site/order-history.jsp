<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<div class="container mt-4">

    <c:if test="${param.message == 'canceled_success'}">
        <div class="alert alert-dismissible fade show" role="alert"
             style="background: #eafaf1; border-color: #27ae60; color: #219150;">
            <strong>Thành công!</strong> Đơn hàng của bạn đã được hủy.
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    <c:if test="${param.error == 'cannot_cancel'}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Lỗi!</strong> Không thể hủy đơn hàng (đơn đã được xử lý hoặc không hợp lệ).
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="mb-4">
        <h2 class="fw-bold" style="color: #27ae60;">
            <i class="bi bi-clock-history me-2"></i>Lịch sử mua hàng
        </h2>
        <p class="text-muted">Danh sách các đơn hàng bạn đã đặt tại PolyCafe.</p>
    </div>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="card border-0 shadow-sm text-center p-5">
                <div class="card-body">
                    <h5 class="text-muted">Bạn chưa có đơn hàng nào.</h5>
                    <a href="${pageContext.request.contextPath}/drinks"
                       class="btn btn-sm mt-3 px-4"
                       style="background: #27ae60; color: #fff;">
                        Khám phá thực đơn ngay
                    </a>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <div class="card shadow-sm border-0">
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead style="background-color: #219150; color: #fff;">
                            <tr>
                                <th class="ps-3">Mã đơn</th>
                                <th>Ngày đặt</th>
                                <th>Tổng tiền</th>
                                <th>Trạng thái</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${orders}">
                                <tr>
                                    <td class="ps-3 fw-bold">${b.code}</td>
                                    <td>
                                        <fmt:parseDate value="${b.createDate}"
                                                       pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDate" type="both"/>
                                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm"/>
                                    </td>
                                    <td class="fw-bold" style="color: #e74c3c;">
                                        <fmt:formatNumber value="${b.total}" type="number"/> VNĐ
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${b.status == 'pending'}">
                                                <span class="badge" style="background: #f39c12;">Chờ xác nhận</span>
                                            </c:when>
                                            <c:when test="${b.status == 'confirmed'}">
                                                <span class="badge bg-info">Đã xác nhận</span>
                                            </c:when>
                                            <c:when test="${b.status == 'paid'}">
                                                <span class="badge" style="background: #27ae60;">Đã thanh toán</span>
                                            </c:when>
                                            <c:when test="${b.status == 'canceled'}">
                                                <span class="badge bg-secondary">Đã hủy</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${b.status == 'pending'}">
                                            <a href="${pageContext.request.contextPath}/my-orders/cancel?id=${b.id}"
                                               class="btn btn-sm btn-outline-danger"
                                               onclick="return confirm('Bạn có chắc chắn muốn hủy đơn hàng này không?')">
                                                <i class="bi bi-x-circle me-1"></i>Hủy đơn
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>