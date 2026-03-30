<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<h2 class="my-4">Quản lý Đơn hàng</h2>

<table class="table table-striped border">
    <thead class="table-dark">
        <tr>
            <th>Mã đơn</th>
            <th>Ngày đặt</th>
            <th>Khách hàng</th>
            <th>Tổng tiền</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="b" items="${orders}">
            <tr>
                <td><strong>${b.code}</strong></td>
                <td>${b.createDate}</td>
                <td>${b.user.fullname}</td>
                <td class="text-danger fw-bold">${b.total} VNĐ</td>
                <td>
                    <span class="badge ${b.status == 'pending' ? 'bg-warning' : (b.status == 'paid' ? 'bg-success' : 'bg-info')}">
                        ${b.status}
                    </span>
                </td>
                <td>
                    <c:if test="${b.status == 'pending'}">
                        <a href="orders/update?id=${b.id}&status=confirmed" class="btn btn-sm btn-outline-primary">Xác nhận</a>
                    </c:if>
                    <c:if test="${b.status == 'confirmed'}">
                        <a href="orders/update?id=${b.id}&status=paid" class="btn btn-sm btn-outline-success">Thanh toán</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>