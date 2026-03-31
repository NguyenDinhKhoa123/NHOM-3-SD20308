<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<h2 class="my-4">Danh sách đồ uống</h2>

<form action="drinks" method="get" class="row g-3 mb-4">
    <div class="col-auto">
        <input type="text" name="keyword" class="form-control" placeholder="Nhập tên món..." value="${param.keyword}">
    </div>
    <div class="col-auto">
        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    </div>
</form>

<div class="row">
    <c:forEach var="item" items="${drinks}">
        <div class="col-md-3 mb-4">
            <div class="card">
                <img src="${pageContext.request.contextPath}/images/${item.image}"
                     class="card-img-top"
                     style="height: 200px; object-fit: cover;">
                     <div class="card-body">
                    <h5 class="card-title">${item.name}</h5>
                    <p class="card-text text-danger">${item.price} VNĐ</p>
                    <a href="${pageContext.request.contextPath}/purchase?id=${item.id}"
                       class="btn btn-success w-100"
                       onclick="return confirm('Bạn muốn đặt mua món này ngay lập tức?')">
                        Mua ngay
                    </a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>