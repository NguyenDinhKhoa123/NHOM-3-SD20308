<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<form action="${pageContext.request.contextPath}/drinks" method="get"
      class="row g-3 mb-4 p-3 rounded shadow-sm"
      style="background-color: #eafaf1; border: 0.5px solid #dee2e6;">
    <div class="col-md-4">
        <input type="text" name="name" class="form-control"
               placeholder="Tên món..." value="${name}">
    </div>
    <div class="col-md-3">
        <select name="categoryId" class="form-select">
            <option value="">-- Tất cả loại --</option>
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}" ${selectedCateId == c.id ? 'selected' : ''}>${c.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-md-3">
        <select name="active" class="form-select">
            <option value="">-- Trạng thái --</option>
            <option value="true"  ${selectedActive == 'true'  ? 'selected' : ''}>Đang kinh doanh</option>
            <option value="false" ${selectedActive == 'false' ? 'selected' : ''}>Tạm ngưng</option>
        </select>
    </div>
    <div class="col-md-2">
        <button type="submit" class="btn w-100"
                style="background: #27ae60; color: #fff;">
            <i class="bi bi-search me-1"></i>Tìm kiếm
        </button>
    </div>
</form>

<div class="row">
    <c:forEach var="item" items="${drinks}">
        <div class="col-md-3 mb-4">
            <div class="card h-100 shadow-sm border-0">
                <img src="${pageContext.request.contextPath}/images/${item.image}"
                     class="card-img-top" style="height: 180px; object-fit: cover;">
                <div class="card-body">
                    <h6 class="fw-bold" style="color: #2c3e50;">${item.name}</h6>
                    <p class="fw-bold mb-3" style="color: #e74c3c;">${item.price} VNĐ</p>
                    <c:choose>
                        <c:when test="${item.active}">
                            <a href="${pageContext.request.contextPath}/purchase?id=${item.id}"
                               class="btn btn-sm w-100"
                               style="background: #27ae60; color: #fff;">
                                Mua ngay
                            </a>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-sm btn-secondary w-100" disabled>Hết hàng</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<nav>
    <ul class="pagination justify-content-center">
        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item ${currentPage == i ? 'active' : ''}">
                <a class="page-link"
                   href="?name=${name}&categoryId=${selectedCateId}&active=${selectedActive}&page=${i}"
                   style="${currentPage == i ? 'background:#27ae60; border-color:#27ae60;' : 'color:#27ae60;'}">
                    ${i}
                </a>
            </li>
        </c:forEach>
    </ul>
</nav>