<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container-fluid mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="mb-0">Chi tiết mã đơn: <span class="text-primary">${bill.code}</span></h4>
        <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-secondary btn-sm">Quay lại danh sách</a>
    </div>

    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header bg-light fw-bold">Thông tin đơn hàng</div>
                <div class="card-body">
                    <p><strong>Ngày tạo:</strong>
                        <fmt:parseDate value="${bill.createDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="pDate" type="both" />
                        <fmt:formatDate value="${pDate}" pattern="dd/MM/yyyy HH:mm" />
                    </p>
                    <p><strong>Trạng thái:</strong>
                        <span class="badge ${bill.status == 'paid' ? 'bg-success' : (bill.status == 'cancelled' ? 'bg-danger' : 'bg-warning')}">
                            ${bill.status}
                        </span>
                    </p>
                    <p><strong>Tổng tiền:</strong> <span class="text-danger fw-bold"><fmt:formatNumber value="${bill.total}" type="number"/> đ</span></p>
                    <hr>
                    <p><strong>Khách / Nhân viên tạo:</strong> ${bill.user.fullname}</p>
                    <p><strong>Số điện thoại:</strong> ${bill.user.phone}</p>
                </div>
            </div>
        </div>

        <div class="col-md-8 mb-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header bg-light fw-bold">Danh sách sản phẩm</div>
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Đồ uống</th>
                                <th class="text-center">Số lượng</th>
                                <th class="text-end">Đơn giá</th>
                                <th class="text-end">Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${details}">
                                <tr>
                                    <td>
                                        <div class="fw-bold">${item.drink.name}</div>
                                    </td>
                                    <td class="text-center">${item.quantity}</td>
                                    <td class="text-end"><fmt:formatNumber value="${item.price}" type="number"/> đ</td>
                                    <td class="text-end text-danger fw-bold">
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