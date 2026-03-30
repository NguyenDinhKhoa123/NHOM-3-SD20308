<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<h2 class="my-4">Giỏ hàng của bạn</h2>

<table class="table table-hover">
    <thead class="table-light">
        <tr>
            <th>Ảnh</th>
            <th>Sản phẩm</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Thành tiền</th>
            <th>Thao tác</th>
        </tr>
    </thead>
    <tbody>
        <c:set var="total" value="0" />
        <c:forEach var="entry" items="${sessionScope.cart}">
            <c:set var="item" value="${entry.value}" />
            <c:set var="subtotal" value="${item.price * item.quantity}" />
            <c:set var="total" value="${total + subtotal}" />
            <tr>
                <img src="${pageContext.request.contextPath}/uploads/${item.image}" width="50">
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${item.quantity}</td>
                <td>${subtotal}</td>
                <td>
                    <a href="cart/remove?id=${item.id}" class="text-danger">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<div class="d-flex justify-content-between align-items-center mt-4 p-3 bg-light border">
    <h4>Tổng thanh toán: <span class="text-danger">${total} VNĐ</span></h4>
    <c:if test="${not empty sessionScope.cart}">
        <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary btn-lg">Tiến hành đặt hàng</a>
    </c:if>
</div>