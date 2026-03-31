<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid px-4">
    <div class="row mt-4">
        <div class="col-md-4">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-dark text-white">
                    <h5 class="mb-0">${empty userForm ? 'Thêm người dùng' : 'Cập nhật người dùng'}</h5>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/users/${empty userForm.id ? 'create' : 'update'}" method="post">
                        <input type="hidden" name="id" value="${userForm.id}">

                        <div class="mb-3">
                            <label class="form-label">Họ và tên</label>
                            <input type="text" name="fullname" class="form-control" value="${userForm.fullname}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" value="${userForm.email}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input type="password" name="password" class="form-control" placeholder="Để trống nếu không đổi">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Vai trò</label>
                            <select name="role" class="form-select">
                                <option value="customer" ${userForm.role == 'customer' ? 'selected' : ''}>Khách hàng</option>
                                <option value="staff" ${userForm.role == 'staff' ? 'selected' : ''}>Nhân viên</option>
                                <option value="admin" ${userForm.role == 'admin' ? 'selected' : ''}>Quản trị viên</option>
                            </select>
                        </div>
                        <hr>
                        <button class="btn btn-primary w-100">${empty userForm ? 'Thêm mới' : 'Lưu thay đổi'}</button>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-link w-100 mt-2">Làm mới Form</a>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Danh sách tài khoản</h5>
                </div>
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th>Họ tên</th> <th>Vai trò</th> <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td>
                                        <strong>${u.fullname}</strong><br>
                                        <small class="text-muted">${u.email}</small>
                                    </td>
                                    <td><span class="badge ${u.role == 'admin' ? 'bg-danger' : 'bg-info'}">${u.role}</span></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/users/edit?id=${u.id}" class="btn btn-sm btn-outline-warning">Sửa</a>
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