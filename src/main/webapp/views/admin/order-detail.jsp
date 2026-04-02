<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<div class="container-fluid mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h5 class="mb-0 fw-bold" style="color: #2c3e50;">
            Chi tiết mã đơn: <span style="color: #27ae60;">${bill.code}</span>
        </h5>
        <a href="${pageContext.request.contextPath}/admin/orders"
           class="btn btn-sm btn-outline-secondary">Quay lại danh sách</a>
    </div>

    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header fw-bold py-3"
                     style="background-color: #eafaf1; color: #219150;">
                    Thông tin đơn hàng
                </div>
                <div class="card-body">
                    <p>
                        <strong>Ngày tạo:</strong>
                        <fmt:parseDate value="${bill.createDate}"
                                       pattern="yyyy-MM-dd'T'HH:mm:ss"
                                       var="pDate" type="both"/>
                        <fmt:formatDate value="${pDate}" pattern="dd/MM/yyyy HH:mm"/>
                    </p>
                    <p>
                        <strong>Trạng thái:</strong>
                        <span class="badge"
                              style="background: ${bill.status == 'paid' ? '#27ae60' : (bill.status == 'cancelled' ? '#e74c3c' : '#f39c12')};">
                            ${bill.status}
                        </span>
                    </p>
                    <p>
                        <strong>Tổng tiền:</strong>
                        <span class="fw-bold" style="color: #e74c3c;">
                            <fmt:formatNumber value="${bill.total}" type="number"/> đ
                        </span>
                    </p>
                    <hr>
                    <p><strong>Khách hàng:</strong> ${bill.user.fullname}</p>
                    <p><strong>Số điện thoại:</strong> ${bill.user.phone}</p>
                </div>
            </div>
        </div>

        <div class="col-md-8 mb-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header fw-bold py-3"
                     style="background-color: #eafaf1; color: #219150;">
                    Danh sách sản phẩm
                </div>
                <div class="card-body p-0">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th class="ps-3">Đồ uống</th>
                                <th class="text-center">Số lượng</th>
                                <th class="text-end">Đơn giá</th>
                                <th class="text-end pe-3">Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${details}">
                                <tr>
                                    <td class="ps-3 fw-bold" style="color: #2c3e50;">
                                        ${item.drink.name}
                                    </td>
                                    <td class="text-center">${item.quantity}</td>
                                    <td class="text-end">
                                        <fmt:formatNumber value="${item.price}" type="number"/> đ
                                    </td>
                                    <td class="text-end pe-3 fw-bold" style="color: #e74c3c;">
                                        <fmt:formatNumber value="${item.quantity * item.price}" type="number"/> đ
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>