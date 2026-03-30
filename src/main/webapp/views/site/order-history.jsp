<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<div class="container mt-4">
    <h3 class="mb-4">Lịch sử đơn hàng của bạn</h3>

    <c:if test="${empty orders}">
        <div class="alert alert-info">Bạn chưa có đơn hàng nào. <a href="drinks">Mua sắm ngay!</a></div>
    </c:if>

    <c:if test="${not empty orders}">
        <table class="table table-hover border">
            <thead class="table-dark">
                <tr>
                    <th>Mã đơn</th>
                    <th>Ngày đặt</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Chi tiết</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="b" items="${orders}">
                    <tr>
                        <td><strong>${b.code}</strong></td>
                        <td>${b.createDate}</td>
                        <td class="text-danger fw-bold">
                            <fmt:formatNumber value="${b.total}" type="number"/> VNĐ
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${b.status == 'pending'}"><span class="badge bg-warning text-dark">Chờ xác nhận</span></c:when>
                                <c:when test="${b.status == 'confirmed'}"><span class="badge bg-info">Đã xác nhận</span></c:when>
                                <c:when test="${b.status == 'paid'}"><span class="badge bg-success">Đã thanh toán</span></c:when>
                                <c:otherwise><span class="badge bg-danger">Đã hủy</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-primary" onclick="alert('Tính năng đang phát triển: Hiển thị 4 bill_details của đơn này')">Xem</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>