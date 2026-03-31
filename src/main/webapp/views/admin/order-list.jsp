<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<div class="container-fluid mt-4">
    <h2 class="mb-4">Thanh toán đơn hàng </h2>

    <div class="card shadow-sm border-0">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-primary">
                <tr>
                    <th>Mã đơn</th>
                    <th>Khách hàng</th>
                    <th>Ngày đặt</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="b" items="${orders}">
                    <tr>
                        <td class="fw-bold text-primary">${b.code}</td>
                        <td>
                            <div>${b.user.fullname}</div>
                            <small class="text-muted">${b.user.phone}</small>
                        </td>
                        <td>
                            <fmt:parseDate value="${b.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="pDate" type="both" />
                            <fmt:formatDate value="${pDate}" pattern="dd/MM HH:mm" />
                        </td>
                        <td class="fw-bold"><fmt:formatNumber value="${b.total}" type="number"/> đ</td>
                        <td>
                            <c:choose>
                                <c:when test="${b.status == 'pending'}"><span class="badge bg-warning text-dark">Chờ xác nhận</span></c:when>
                                <c:when test="${b.status == 'confirmed'}"><span class="badge bg-info">Đã xác nhận</span></c:when>
                                <c:when test="${b.status == 'paid'}"><span class="badge bg-success">Đã thanh toán</span></c:when>
                                <c:otherwise><span class="badge bg-secondary">Đã hủy</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-primary">Chi tiết</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>