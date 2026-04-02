<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<div class="container-fluid px-4">
    <div class="row mt-4">
        <div class="col-md-4">
            <div class="card shadow-sm border-0">
                <div class="card-header text-white py-3"
                     style="background-color: #219150;">
                    <h6 class="mb-0 fw-bold">
                        ${empty userForm ? 'Thêm người dùng' : 'Cập nhật người dùng'}
                    </h6>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/users/${empty userForm.id ? 'create' : 'update'}"
                          method="post">
                        <input type="hidden" name="id" value="${userForm.id}">

                        <div class="mb-3">
                            <label class="form-label small fw-bold">Họ và tên</label>
                            <input type="text" name="fullname" class="form-control"
                                   value="${userForm.fullname}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small fw-bold">Email</label>
                            <input type="email" name="email" class="form-control"
                                   value="${userForm.email}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small fw-bold">Mật khẩu</label>
                            <input type="password" name="password" class="form-control"
                                   placeholder="Để trống nếu không đổi">
                        </div>
                        <div class="mb-4">
                            <label class="form-label small fw-bold">Vai trò</label>
                            <select name="role" class="form-select">
                                <option value="customer" ${userForm.role == 'customer' ? 'selected' : ''}>Khách hàng</option>
                                <option value="staff"    ${userForm.role == 'staff'    ? 'selected' : ''}>Nhân viên</option>
                                <option value="admin"    ${userForm.role == 'admin'    ? 'selected' : ''}>Quản trị viên</option>
                            </select>
                        </div>
                        <hr>
                        <button class="btn w-100 text-white fw-bold"
                                style="background-color: #27ae60;">
                            ${empty userForm ? 'Thêm mới' : 'Lưu thay đổi'}
                        </button>
                        <a href="${pageContext.request.contextPath}/admin/users"
                           class="btn btn-link w-100 mt-1" style="color: #7f8c8d;">
                            Làm mới form
                        </a>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow-sm border-0">
                <div class="card-header text-white py-3"
                     style="background-color: #27ae60;">
                    <h6 class="mb-0 fw-bold">Danh sách tài khoản</h6>
                </div>

                <div class="card-body border-bottom py-3">
                    <form action="${pageContext.request.contextPath}/admin/users" method="get"
                          class="d-flex gap-2">
                        <input type="text" name="keyword" value="${keyword}"
                               class="form-control form-control-sm"
                               placeholder="Tìm tên nhân viên...">
                        <button type="submit" class="btn btn-sm text-white px-4"
                                style="background-color: #27ae60;">
                            Tìm
                        </button>
                    </form>
                </div>

                <div class="card-body p-0">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th class="ps-3">Họ tên</th>
                                <th>Vai trò</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td class="ps-3">
                                        <div class="fw-bold" style="color: #2c3e50;">${u.fullname}</div>
                                        <small style="color: #7f8c8d;">${u.email}</small>
                                    </td>
                                    <td>
                                        <span class="badge"
                                              style="background: ${u.role == 'admin' ? '#e74c3c' : '#27ae60'};">
                                            ${u.role}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/users/edit?id=${u.id}"
                                           class="btn btn-sm btn-outline-warning">Sửa</a>
                                        <a href="${pageContext.request.contextPath}/admin/users/delete?id=${u.id}"
                                           class="btn btn-sm btn-outline-danger"
                                           onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
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