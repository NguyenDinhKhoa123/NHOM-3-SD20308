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
<%-- Sửa lại đoạn này trong vòng lặp c:forEach --%>
<tr>
    <td>
        <img src="${pageContext.request.contextPath}/uploads/${item.image}" width="50" class="img-thumbnail">
    </td>
    <td class="fw-bold">${item.name}</td>
    <td><fmt:formatNumber value="${item.price}" type="number"/> VNĐ</td>
    <td>
        <%-- Nên thêm input để sau này làm chức năng cập nhật số lượng --%>
        <input type="number" value="${item.quantity}" style="width: 60px" class="form-control d-inline">
    </td>
    <td class="text-danger fw-bold">
        <fmt:formatNumber value="${subtotal}" type="number"/> VNĐ
    </td>
    <td>
        <a href="${pageContext.request.contextPath}/cart/remove?id=${item.id}"
           class="btn btn-sm btn-outline-danger"
           onclick="return confirm('Xóa món này khỏi giỏ hàng?')">
           <i class="bi bi-trash"></i> Xóa
        </a>
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