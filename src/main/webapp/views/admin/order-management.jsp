<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<div class="container-fluid px-4">
    <div class="card shadow-sm border-0 my-4">
        <div class="card-header bg-dark text-white py-3 d-flex justify-content-between align-items-center">
            <h4 class="mb-0">Quản lý đơn hàng</h4>

        </div>

        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th class="ps-3" style="width: 15%">Mã đơn</th>
                            <th style="width: 20%">Ngày đặt</th>
                            <th style="width: 20%">Khách hàng</th>
                            <th style="width: 15%">Tổng tiền</th>
                            <th style="width: 15%">Trạng thái</th>
                            <th class="text-center" style="width: 15%">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty orders}">
                                <c:forEach var="b" items="${orders}">

                                    <%-- BỘ LỌC THÔNG MINH (Thêm đoạn này vào) --%>
                                    <%-- Nếu là trang Lịch sử (isHistory=true): Chỉ hiện đơn đã xong (paid/canceled) --%>
                                    <%-- Nếu là trang Duyệt đơn (isHistory=false): Chỉ hiện đơn đang chờ (pending/confirmed) --%>
                                    <c:if test="${(isHistory && (b.status == 'paid' || b.status == 'canceled' || b.status == 'cancelled')) ||
                                                  (!isHistory && (b.status == 'pending' || b.status == 'confirmed'))}">

                                        <tr>
                                            <td class="ps-3">
                                                <span class="fw-bold text-primary">${b.code}</span>
                                            </td>

                                            <td>
                                                <fmt:parseDate value="${b.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                                                <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm" />
                                            </td>

                                            <td>
                                                <div class="fw-bold">${b.user.fullname}</div>
                                                <small class="text-muted">${b.user.email}</small>
                                            </td>

                                            <td class="text-danger fw-bold">
                                                <fmt:formatNumber value="${b.total}" type="number"/> VNĐ
                                            </td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${b.status == 'pending'}">
                                                        <span class="badge bg-warning text-dark px-3">Chờ xác nhận</span>
                                                    </c:when>
                                                    <c:when test="${b.status == 'confirmed'}">
                                                        <span class="badge bg-info px-3">Đã xác nhận</span>
                                                    </c:when>
                                                    <c:when test="${b.status == 'paid'}">
                                                        <span class="badge bg-success px-3">Đã thanh toán</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary px-3">${b.status}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td class="text-center">
                                                <%-- Chỉ hiện nút thao tác nếu KHÔNG PHẢI trang lịch sử --%>
                                                <c:if test="${!isHistory}">
                                                    <c:if test="${b.status == 'pending'}">
                                                        <a href="${pageContext.request.contextPath}/admin/orders/update?id=${b.id}&status=confirmed"
                                                           class="btn btn-sm btn-primary shadow-sm"
                                                           onclick="return confirm('Xác nhận đơn hàng này?')">
                                                            <i class="bi bi-check-circle"></i> Xác nhận
                                                        </a>
                                                    </c:if>

                                                    <c:if test="${b.status == 'confirmed'}">
                                                        <a href="${pageContext.request.contextPath}/admin/orders/update?id=${b.id}&status=paid"
                                                           class="btn btn-sm btn-success shadow-sm"
                                                           onclick="return confirm('Khách đã thanh toán đơn này?')">
                                                            <i class="bi bi-cash-stack"></i> Thanh toán
                                                        </a>
                                                    </c:if>
                                                </c:if>

                                                <%-- Nếu là trang lịch sử, có thể hiện nút xem chi tiết --%>

                                            </td>
                                        </tr>
                                    </c:if> <%-- Kết thúc bộ lọc --%>

                                </c:forEach>
                            </c:when>

                            <c:otherwise>
                                </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>