<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<meta charset="UTF-8">
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Loại đồ uống - PolyCafe</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7f6; padding-top: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .card { border: none; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.08); }
        .table thead { background-color: #212529; color: white; }
        .btn-edit { background-color: #4fc3f7; color: white; border: none; }
        .btn-delete { background-color: #e57373; color: white; border: none; }
    </style>
</head>
<body class="container">

    <div class="row">
        <div class="col-md-4">
            <div class="card p-4">
                <h4 class="mb-4 text-primary" style="font-weight: 600;">
                    ${not empty item.id ? 'CẬP NHẬT' : 'THÊM MỚI'}
                </h4>

                <form action="${pageContext.request.contextPath}/category/${not empty item.id ? 'update' : 'create'}" method="post">
                    <input type="hidden" name="id" value="${item.id}">

                    <div class="mb-3">
                        <label class="form-label">Tên loại đồ uống:</label>
                        <input type="text" name="name" class="form-control" value="${item.name}" required>
                    </div>

                    <div class="mb-3 form-check">
                        <input type="checkbox" name="active" class="form-check-input" id="activeCheck" ${item.active ? 'checked' : ''}>
                        <label class="form-check-label" for="activeCheck">Đang kinh doanh</label>
                    </div>

                    <div class="d-grid gap-2 mt-4">
                        <button type="submit" class="btn ${not empty item.id ? 'btn-warning text-white' : 'btn-primary'}">
                            ${not empty item.id ? 'Cập nhật' : 'Thêm mới'}
                        </button>
                        <a href="${pageContext.request.contextPath}/category/index" class="btn btn-light border">Làm mới</a>
                    </div>
                </form>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card p-4">
                <h3 class="mb-4" style="color: #2e7d32; font-weight: bold;">DANH SÁCH LOẠI ĐỒ UỐNG</h3>

                <c:if test="${not empty message}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <table class="table table-hover align-middle">
                    <thead>
                        <tr>
                            <th width="10%">ID</th>
                            <th width="50%">Tên loại</th>
                            <th width="20%">Trạng thái</th>
                            <th width="20%">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- Duyệt danh sách items từ Servlet gửi sang --%>
                        <c:forEach var="c" items="${items}">
                            <tr>
                                <td>${c.id}</td>
                                <td>${c.name}</td>
                                <td>
                                    <span class="badge ${c.active ? 'bg-success' : 'bg-secondary'}">
                                        ${c.active ? 'Hoạt động' : 'Ngưng'}
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/category/edit/${c.id}" class="btn btn-sm btn-edit px-3">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/category/delete/${c.id}"
                                       class="btn btn-sm btn-delete px-3"
                                       onclick="return confirm('Bạn chắc chắn muốn xóa?')">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty items}">
                            <tr>
                                <td colspan="4" class="text-center text-muted p-4">Chưa có dữ liệu hiển thị.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>