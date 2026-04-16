<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<div class="container-fluid py-4">
    <div class="row">
        <div class="col-md-4">
            <div class="card shadow-sm border-0">
                <div class="card-header text-white py-3"
                     style="background-color: #219150;">
                    <h6 class="mb-0 fw-bold">
                        <i class="bi ${empty drinkForm ? 'bi-plus-circle' : 'bi-pencil-square'} me-2"></i>
                        ${empty drinkForm ? 'Thêm món mới' : 'Cập nhật món'}
                    </h6>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/drinks/${empty drinkForm ? 'create' : 'update'}"
                          method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="${drinkForm.id}">

                        <div class="mb-3">
                            <label class="form-label small fw-bold" style="color: #7f8c8d;">Tên đồ uống</label>
                            <input type="text" name="name" class="form-control"
                                   value="${drinkForm.name}" placeholder="VD: Trà đào cam sả" required>
                        </div>

                        <div class="row mb-3">
                            <div class="col-6">
                                <label class="form-label small fw-bold" style="color: #7f8c8d;">Giá (VNĐ)</label>
                                <input type="number" name="price" class="form-control"
                                       value="${drinkForm.price}" required min="0" step="1000">
                            </div>
                            <div class="col-6">
                                <label class="form-label small fw-bold" style="color: #7f8c8d;">Danh mục</label>
                                <select name="categoryId" class="form-select" required>
                                    <option value="">-- Chọn loại --</option>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.id}"
                                                ${drinkForm.category.id == cat.id ? 'selected' : ''}>
                                            ${cat.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label small fw-bold" style="color: #7f8c8d;">Hình ảnh</label>
                            <c:if test="${not empty drinkForm.image}">
                                <div class="mb-2">
                                    <img src="${pageContext.request.contextPath}/images/${drinkForm.image}"
                                         class="rounded border" width="80" height="80"
                                         style="object-fit: cover;">
                                    <div class="small mt-1" style="color: #7f8c8d;">
                                        Ảnh hiện tại: ${drinkForm.image}
                                    </div>
                                </div>
                            </c:if>
                            <input type="file" name="image" class="form-control form-control-sm">
                        </div>

                        <div class="form-check form-switch mb-3">
                            <input class="form-check-input" type="checkbox" name="active" id="active"
                                   ${drinkForm == null || drinkForm.active ? 'checked' : ''}>
                            <label class="form-check-label small fw-bold" for="active">
                                Trạng thái kinh doanh
                            </label>
                        </div>

                        <div class="mb-3">
                            <label class="form-label small fw-bold" style="color: #7f8c8d;">Mô tả ngắn</label>
                            <textarea name="description" class="form-control" rows="2">
                                ${drinkForm.description}
                            </textarea>
                        </div>

                        <div class="d-grid gap-2 pt-2">
                            <button type="submit" class="btn fw-bold text-white"
                                    style="background-color: ${empty drinkForm ? '#27ae60' : '#f39c12'};">
                                <i class="bi bi-save me-1"></i>
                                ${empty drinkForm ? 'Lưu món' : 'Cập nhật'}
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/drinks"
                               class="btn btn-light btn-sm" style="color: #7f8c8d;">
                                <i class="bi bi-arrow-counterclockwise"></i> Làm mới form
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-white py-3">
                    <div class="d-flex justify-content-between align-items-center">
                        <h6 class="mb-0 fw-bold" style="color: #27ae60;">Thực đơn PolyCafe</h6>
                        <span class="badge rounded-pill"
                              style="background: #eafaf1; color: #219150;">
                            ${drinks.size()} sản phẩm
                        </span>
                    </div>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/drinks" method="get"
                          class="row g-2 mb-4 p-3 rounded"
                          style="background-color: #eafaf1;">
                        <div class="col-md-5">
                            <div class="input-group input-group-sm">
                                <span class="input-group-text bg-white border-end-0">
                                    <i class="bi bi-search" style="color: #7f8c8d;"></i>
                                </span>
                                <input type="text" name="searchName"
                                       class="form-control border-start-0"
                                       placeholder="Tìm tên món..." value="${searchName}">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <select name="searchCategoryId" class="form-select form-select-sm">
                                <option value="">-- Tất cả loại --</option>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.id}"
                                            ${searchCateId == cat.id ? 'selected' : ''}>
                                        ${cat.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <button type="submit" class="btn btn-sm w-100 text-white fw-bold"
                                    style="background-color: #27ae60;">
                                Lọc danh sách
                            </button>
                        </div>
                    </form>

                    <div class="table-responsive" style="max-height: 550px;">
                        <table class="table table-hover align-middle border-top">
                            <thead class="table-light">
                                <tr class="small" style="color: #7f8c8d;">
                                    <th>Hình</th>
                                    <th>Tên món</th>
                                    <th>Loại</th>
                                    <th>Giá</th>
                                    <th>Trạng thái</th>
                                    <th class="text-center">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="d" items="${drinks}">
                                    <tr>
                                        <td>
                                            <img src="${pageContext.request.contextPath}/images/${not empty d.image ? d.image : 'no-image.png'}"
                                                 class="rounded" width="50" height="45"
                                                 style="object-fit: cover;">
                                        </td>
                                        <td>
                                            <div class="fw-bold" style="color: #2c3e50;">${d.name}</div>
                                            <div class="small text-truncate" style="max-width:150px; color:#7f8c8d;">
                                                ${d.description}
                                            </div>
                                        </td>
                                        <td>
                                            <span class="badge rounded-pill"
                                                  style="background:#eafaf1; color:#219150;">
                                                ${d.category.name}
                                            </span>
                                        </td>
                                        <td class="fw-bold" style="color: #e74c3c;">
                                            <fmt:formatNumber value="${d.price}" type="number"/> đ
                                        </td>
                                        <td>
                                            <span class="badge rounded-pill"
                                                  style="background:${d.active ? '#27ae60' : '#e74c3c'};">
                                                ${d.active ? 'Bán' : 'Ngưng'}
                                            </span>
                                        </td>
                                        <td class="text-center">
                                            <div class="btn-group">
                                                <a href="${pageContext.request.contextPath}/admin/drinks/edit?id=${d.id}"
                                                   class="btn btn-sm btn-outline-warning" title="Sửa">
                                                    <i class="bi bi-pencil"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/drinks/delete?id=${d.id}"
                                                   class="btn btn-sm btn-outline-danger" title="Xóa"
                                                   onclick="return confirm('Bạn có muốn xóa món [${d.name}] không?')">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </div>
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