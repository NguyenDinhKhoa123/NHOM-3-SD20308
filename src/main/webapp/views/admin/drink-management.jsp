<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<div class="container-fluid py-4">
    <div class="row">
        <div class="col-md-4">
            <div class="card shadow border-0">
                <div class="card-header bg-dark text-white py-3">
                    <h5 class="mb-0 fw-bold text-uppercase">
                        <i class="bi ${empty drinkForm ? 'bi-plus-circle' : 'bi-pencil-square'} me-2"></i>
                        ${empty drinkForm ? 'Thêm món mới' : 'Cập nhật món ăn'}
                    </h5>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/drinks/${empty drinkForm ? 'create' : 'update'}"
                          method="post" enctype="multipart/form-data">

                        <input type="hidden" name="id" value="${drinkForm.id}">

                        <div class="mb-3">
                            <label class="form-label fw-bold small text-muted">TÊN ĐỒ UỐNG</label>
                            <input type="text" name="name" class="form-control" value="${drinkForm.name}" placeholder="VD: Trà đào cam sả" required>
                        </div>

                        <div class="row mb-3">
                            <div class="col-6">
                                <label class="form-label fw-bold small text-muted">GIÁ (VNĐ)</label>
                                <input type="number" name="price" class="form-control" value="${drinkForm.price}" required>
                            </div>
                            <div class="col-6">
                                <label class="form-label fw-bold small text-muted">DANH MỤC</label>
                                <select name="categoryId" class="form-select" required>
                                    <option value="">-- Chọn loại --</option>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.id}" ${drinkForm.category.id == cat.id ? 'selected' : ''}>
                                            ${cat.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold small text-muted">HÌNH ẢNH</label>
                            <c:if test="${not empty drinkForm.image}">
                                <div class="mb-2">
                                    <img src="${pageContext.request.contextPath}/images/${drinkForm.image}"
                                         class="rounded border" width="80" height="80" style="object-fit: cover;">
                                    <div class="small text-muted mt-1 italic">Ảnh hiện tại: ${drinkForm.image}</div>
                                </div>
                            </c:if>
                            <input type="file" name="image" class="form-control form-control-sm">
                        </div>

                        <div class="form-check form-switch mb-3">
                            <input class="form-check-input" type="checkbox" name="active" id="active"
                                   ${drinkForm == null || drinkForm.active ? 'checked' : ''}>
                            <label class="form-check-label fw-bold small" for="active">TRẠNG THÁI KINH DOANH</label>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold small text-muted">MÔ TẢ NGẮN</label>
                            <textarea name="description" class="form-control" rows="2">${drinkForm.description}</textarea>
                        </div>

                        <div class="d-grid gap-2 pt-3">
                            <button type="submit" class="btn ${empty drinkForm ? 'btn-success' : 'btn-warning'} fw-bold shadow-sm">
                                <i class="bi bi-save me-1"></i> ${empty drinkForm ? 'LƯU MÓN' : 'CẬP NHẬT'}
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/drinks" class="btn btn-light btn-sm text-muted">
                                <i class="bi bi-arrow-counterclockwise"></i> Làm mới Form
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow border-0">
                <div class="card-header bg-white py-3 border-bottom-0">
                    <div class="d-flex justify-content-between align-items-center">
                        <h5 class="mb-0 fw-bold text-primary">THỰC ĐƠN POLYCAFE</h5>
                        <span class="badge bg-primary rounded-pill">${drinks.size()} sản phẩm</span>
                    </div>
                </div>

                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/drinks" method="get" class="row g-2 mb-4 bg-light p-3 rounded border">
                        <div class="col-md-5">
                            <div class="input-group input-group-sm">
                                <span class="input-group-text bg-white border-end-0"><i class="bi bi-search text-muted"></i></span>
                                <input type="text" name="searchName" class="form-control border-start-0"
                                       placeholder="Tìm tên món..." value="${searchName}">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <select name="searchCategoryId" class="form-select form-select-sm">
                                <option value="">-- Tất cả loại --</option>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.id}" ${searchCateId == cat.id ? 'selected' : ''}>${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <button type="submit" class="btn btn-sm btn-primary w-100 fw-bold">
                                LỌC DANH SÁCH
                            </button>
                        </div>
                    </form>

                    <div class="table-responsive" style="max-height: 550px;">
                        <table class="table table-hover align-middle border-top">
                            <thead class="table-light">
                                <tr class="small text-muted text-uppercase">
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
                                                 class="rounded shadow-sm" width="50" height="45" style="object-fit: cover;">
                                        </td>
                                        <td>
                                            <div class="fw-bold text-dark">${d.name}</div>
                                            <div class="small text-muted text-truncate" style="max-width: 150px;">${d.description}</div>
                                        </td>
                                        <td><span class="badge bg-info-subtle text-info border border-info-subtle px-2">${d.category.name}</span></td>
                                        <td class="fw-bold text-danger"><fmt:formatNumber value="${d.price}" type="number"/> đ</td>
                                        <td>
                                            <span class="badge rounded-pill ${d.active ? 'bg-success' : 'bg-danger'}">
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
                                                   class="btn btn-sm btn-outline-danger"
                                                   onclick="return confirm('Bạn có muốn xóa món [${d.name}] không?')" title="Xóa">
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