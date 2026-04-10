<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<div class="container my-5">
    <h3 class="fw-bold mb-4"><i class="bi bi-cart3 me-2 text-success"></i>Giỏ hàng của bạn</h3>

    <c:choose>
        <c:when test="${empty sessionScope.cart}">
            <div class="alert alert-info text-center py-5">
                <h4>Giỏ hàng đang trống</h4>
                <p>Hãy dạo một vòng thực đơn và chọn cho mình đồ uống yêu thích nhé!</p>
                <a href="${pageContext.request.contextPath}/drinks" class="btn btn-success mt-3">Xem thực đơn</a>
            </div>
        </c:when>

        <c:otherwise>
            <div class="card shadow-sm border-0">
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th class="ps-4">Sản phẩm</th>
                                <th class="text-end">Đơn giá</th>
                                <th class="text-center" style="width: 150px;">Số lượng</th>
                                <th class="text-end">Thành tiền</th>
                                <th class="text-center">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="total" value="0" />
                            <c:forEach var="entry" items="${sessionScope.cart}">
                                <c:set var="item" value="${entry.value}" />
                                <c:set var="subtotal" value="${item.price * item.quantity}" />
                                <c:set var="total" value="${total + subtotal}" />

                                <tr>
                                    <td class="ps-4">
                                        <div class="d-flex align-items-center">
                                            <img src="${pageContext.request.contextPath}/images/${item.image}"
                                                 class="rounded border me-3" style="width: 60px; height: 60px; object-fit: cover;">
                                            <span class="fw-bold text-dark">${item.name}</span>
                                        </div>
                                    </td>
                                    <td class="text-end"><fmt:formatNumber value="${item.price}" type="number"/> đ</td>
                                    <td>
                                        <%-- FORM CẬP NHẬT SỐ LƯỢNG --%>
                                        <form action="${pageContext.request.contextPath}/cart/update" method="post" class="d-flex justify-content-center">
                                            <input type="hidden" name="id" value="${item.id}">
                                            <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control form-control-sm text-center" style="width: 60px;" onchange="this.form.submit()">
                                        </form>
                                    </td>
                                    <td class="text-end fw-bold text-danger">
                                        <fmt:formatNumber value="${subtotal}" type="number"/> đ
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/cart/remove?id=${item.id}"
                                           class="text-danger" title="Xóa"
                                           onclick="return confirm('Xóa món này?')">
                                           <i class="bi bi-trash fs-5"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="card shadow-sm border-0 mt-4 p-4 bg-light">
                <div class="d-flex justify-content-between align-items-center">
                    <a href="${pageContext.request.contextPath}/drinks" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left me-1"></i> Mua thêm
                    </a>
                    <div class="text-end">
                        <span class="fs-5 me-3">Tổng thanh toán:</span>
                        <span class="fs-4 fw-bold text-danger"><fmt:formatNumber value="${total}" type="number"/> VNĐ</span>
                        <br>
                        <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success btn-lg mt-3 px-5 shadow">
                            Tiến hành Đặt hàng <i class="bi bi-arrow-right ms-2"></i>
                        </a>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>