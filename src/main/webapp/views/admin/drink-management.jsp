<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">
            <div class="card shadow">
                <div class="card-header bg-dark text-white">
                    <h5 class="mb-0">Quản lý món ăn</h5>
                </div>
                <div class="card-body">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show">
                            ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/admin/drinks"
                          method="post"
                          enctype="multipart/form-data">

                        <div class="mb-3">
                            <label class="form-label fw-bold">Tên món đồ uống</label>
                            <input type="text" name="name" class="form-control" placeholder="Nhập tên món..." required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Giá tiền (VNĐ)</label>
                            <input type="number" name="price" class="form-control" placeholder="Ví dụ: 25000" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Loại danh mục</label>
                            <select name="categoryId" class="form-select" required>
                                <option value="">--- Chọn loại ---</option>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.id}">${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Hình ảnh sản phẩm</label>
                            <input type="file" name="image" class="form-control" accept="image/*">
                            <small class="text-muted">Định dạng: jpg, png. Dung lượng < 5MB</small>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Mô tả món ăn</label>
                            <textarea name="description" class="form-control" rows="3" placeholder="Mô tả ngắn về món này..."></textarea>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-save"></i> Lưu món ăn
                            </button>
                            <button type="reset" class="btn btn-outline-secondary">Làm mới form</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Danh sách thực đơn (${drinks.size()} món)</h5>
                </div>
                <div class="card-body p-0">
                    <div class="table-responsive" style="max-height: 600px; overflow-y: auto;">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light sticky-top">
                                <tr>
                                    <th>Ảnh</th>
                                    <th>Tên món</th>
                                    <th>Loại</th>
                                    <th>Giá (VNĐ)</th>
                                    <th class="text-center">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="d" items="${drinks}">
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty d.image}">
                                                    <img src="${pageContext.request.contextPath}/uploads/${d.image}" width="60">
                                                         class="rounded shadow-sm" width="60" height="50" style="object-fit: cover;">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/assets/img/no-image.png"
                                                         class="rounded" width="60" height="50">
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="fw-bold">${d.name}</div>
                                            <small class="text-muted text-truncate d-inline-block" style="max-width: 150px;">
                                                ${d.description}
                                            </small>
                                        </td>
                                        <td><span class="badge bg-secondary">${d.category.name}</span></td>
                                        <td class="text-danger fw-bold">
                                            <fmt:formatNumber value="${d.price}" type="number"/>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/drinks/delete?id=${d.id}"
                                               class="btn btn-sm btn-outline-danger"
                                               onclick="return confirm('Bạn có chắc chắn muốn xóa món này không?')">
                                                🗑 Xóa
                                            </a>
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
</div>