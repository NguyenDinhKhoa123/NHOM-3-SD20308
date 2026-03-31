<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PolyCafe - Hệ thống quản lý</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; }
        .main-content { min-height: 70vh; } /* Giúp footer luôn nằm dưới cùng */
    </style>
</head>
<body>
    <jsp:include page="/views/common/header.jsp"/>
    <jsp:include page="/views/common/menu.jsp"/>

    <div class="container my-5 main-content">
        <c:if test="${not empty view}">
            <jsp:include page="${view}"/>
        </c:if>
    </div>

    <jsp:include page="/views/common/footer.jsp"/>
</body>
</html>