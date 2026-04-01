<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<%-- Ép JSP tự đọc biến history từ URL để chắc chắn không bị nhầm trang --%>
<c:set var="isHistory" value="${param.history == 'true'}" />

<div class="container-fluid px-4">
    <div class="card shadow-sm border-0 my-4">
        <div class="card-header bg-dark text-white py-3 d-flex justify-content-between align-items-center">
            <%-- Đổi tên tiêu đề dựa trên biến isHistory --%>
            <h4 class="mb-0">${isHistory ? 'Lịch sử bán hàng' : 'Quản lý duyệt đơn hàng'}</h4>
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
                        <c:if test="${not empty orders}">
                            <c:forEach var="b" items="${orders}">

                                <%-- BƯỚC QUAN TRỌNG: Cắt bỏ khoảng trắng thừa của SQL Server (Ví dụ "paid   " thành "paid") --%>
                                <c:set var="stt" value="${b.status.trim()}" />

                                <%-- BỘ LỌC HIỂN THỊ (Sử dụng biến stt vừa tạo) --%>
                                <c:if test="${(isHistory && (stt == 'paid' || stt == 'canceled' || stt == 'cancelled')) ||
                                              (!isHistory && (stt == 'pending' || stt == 'confirmed'))}">

                                    <tr>
                                        <td class="ps-3"><span class="fw-bold text-primary">${b.code}</span></td>

                                        <td>
                                            <fmt:parseDate value="${b.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm" />
                                        </td>

                                        <td>
                                            <div class="fw-bold">${b.user.fullname}</div>
                                            <small class="text-muted">${b.user.email}</small>
                                        </td>

                                        <td class="text-danger fw-bold"><fmt:formatNumber value="${b.total}" type="number"/> VNĐ</td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${stt == 'pending'}"><span class="badge bg-warning text-dark px-3">Chờ xác nhận</span></c:when>
                                                <c:when test="${stt == 'confirmed'}"><span class="badge bg-info px-3">Đã xác nhận</span></c:when>
                                                <c:when test="${stt == 'paid'}"><span class="badge bg-success px-3">Đã thanh toán</span></c:when>
                                                <c:otherwise><span class="badge bg-secondary px-3">Đã hủy</span></c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td class="text-center">
                                            <%-- Nút Chi tiết: Luôn hiện để đáp ứng yêu cầu đề bài --%>
                                            <a href="${pageContext.request.contextPath}/admin/orders/detail?id=${b.id}" class="btn btn-sm btn-outline-secondary">Chi tiết</a>

                                            <%-- Nút thao tác: Chỉ hiện ở trang Duyệt đơn --%>
                                            <c:if test="${!isHistory}">
                                                <c:if test="${stt == 'pending'}">
                                                    <a href="${pageContext.request.contextPath}/admin/orders/update?id=${b.id}&status=confirmed" class="btn btn-sm btn-primary" onclick="return confirm('Xác nhận đơn hàng này?')">Xác nhận</a>
                                                    <a href="${pageContext.request.contextPath}/admin/orders/cancel?id=${b.id}" class="btn btn-sm btn-danger" onclick="return confirm('Hủy đơn?')">Hủy</a>
                                                </c:if>

                                                <c:if test="${stt == 'confirmed'}">
                                                    <a href="${pageContext.request.contextPath}/admin/orders/update?id=${b.id}&status=paid" class="btn btn-sm btn-success" onclick="return confirm('Khách đã thanh toán đơn này?')">Thanh toán</a>
                                                </c:if>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>

                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <%-- PHÂN TRANG 10 ĐƠN/TRANG --%>
        <c:if test="${totalPages > 1}">
            <div class="card-footer bg-white py-3">
                <nav>
                    <ul class="pagination pagination-sm justify-content-center mb-0">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="?history=${isHistory}&page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </c:if>
    </div>
</div>