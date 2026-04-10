<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<style>
    .product-card { transition: transform 0.3s ease, box-shadow 0.3s ease; }
    .product-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important; }
    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button { -webkit-appearance: none; margin: 0; }
</style>

<div class="container my-5">
    <div class="text-center mb-5">
        <h3 class="fw-bold" style="color: #27ae60;">
            <i class="bi bi-cup-hot me-2"></i>Thực đơn PolyCafe
        </h3>
        <p style="color: #7f8c8d;">Lựa chọn thức uống yêu thích và thêm vào giỏ hàng ngay!</p>
    </div>

    <form action="${pageContext.request.contextPath}/drinks" method="get"
          class="row g-3 mb-5 p-4 rounded shadow-sm align-items-end"
          style="background-color: #eafaf1; border: 1px solid #dee2e6;">
        <div class="col-md-5">
            <label class="form-label small fw-bold" style="color: #7f8c8d;">Tên đồ uống</label>
            <input type="text" name="name" class="form-control"
                   placeholder="Bạn muốn uống gì hôm nay..." value="${param.name}">
        </div>
        <div class="col-md-4">
            <label class="form-label small fw-bold" style="color: #7f8c8d;">Danh mục</label>
            <select name="categoryId" class="form-select">
                <option value="">-- Tất cả loại --</option>
                <c:forEach var="c" items="${categories}">
                    <option value="${c.id}" ${param.categoryId == c.id ? 'selected' : ''}>${c.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-3">
            <button type="submit" class="btn w-100 fw-bold text-white"
                    style="background-color: #27ae60;">
                <i class="bi bi-search me-1"></i>Tìm kiếm
            </button>
        </div>
    </form>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
        <c:forEach var="item" items="${drinks}">
            <div class="col">
                <div class="card h-100 shadow-sm border-0 product-card">
                    <img src="${pageContext.request.contextPath}/images/${item.image}"
                         class="card-img-top p-2 rounded-4"
                         style="height: 220px; object-fit: cover;" alt="${item.name}">
                    <div class="card-body d-flex flex-column text-center">
                        <h5 class="card-title fw-bold mb-1" style="color: #2c3e50;">${item.name}</h5>
                        <div class="mb-2">
                            <span class="badge rounded-pill px-3 py-1"
                                  style="background: #eafaf1; color: #219150;">
                                ${item.category.name}
                            </span>
                        </div>
                        <h4 class="fw-bold mb-3" style="color: #e74c3c;">
                            <fmt:formatNumber value="${item.price}" type="number"/> đ
                        </h4>

                        <c:choose>
                            <c:when test="${item.active}">
                                <form action="${pageContext.request.contextPath}/cart/add"
                                      method="post" class="mt-auto">
                                    <input type="hidden" name="id" value="${item.id}">
                                    <div class="input-group input-group-sm mb-3 w-75 mx-auto">
                                        <button class="btn btn-outline-secondary fw-bold" type="button"
                                                onclick="this.nextElementSibling.stepDown()">−</button>
                                        <input type="number" name="quantity"
                                               class="form-control text-center fw-bold"
                                               value="1" min="1" max="50">
                                        <button class="btn btn-outline-secondary fw-bold" type="button"
                                                onclick="this.previousElementSibling.stepUp()">+</button>
                                    </div>
                                    <div class="d-grid gap-2">
                                        <button type="submit" name="action" value="add"
                                                class="btn btn-outline-success btn-sm fw-bold py-2">
                                            <i class="bi bi-cart-plus me-1"></i>Thêm vào giỏ
                                        </button>
                                        <button type="submit" name="action" value="buy"
                                                class="btn btn-sm fw-bold py-2 text-white"
                                                style="background-color: #27ae60;">
                                            Mua ngay
                                        </button>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="mt-auto d-grid">
                                    <button class="btn btn-secondary fw-bold py-2" disabled>
                                        <i class="bi bi-x-circle me-1"></i>Tạm hết hàng
                                    </button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty drinks}">
            <div class="col-12 text-center py-5">
                <i class="bi bi-emoji-frown" style="font-size: 3rem; color: #7f8c8d;"></i>
                <h5 class="mt-3" style="color: #7f8c8d;">
                    Không tìm thấy đồ uống nào phù hợp!
                </h5>
            </div>
        </c:if>
    </div>

    <c:if test="${totalPages > 1}">
        <nav class="mt-5">
            <ul class="pagination justify-content-center">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                        <a class="page-link"
                           href="?name=${param.name}&categoryId=${param.categoryId}&page=${i}"
                           style="${currentPage == i
                               ? 'background-color:#27ae60; border-color:#27ae60; color:#fff;'
                               : 'color:#27ae60;'}">
                            ${i}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </c:if>
</div>