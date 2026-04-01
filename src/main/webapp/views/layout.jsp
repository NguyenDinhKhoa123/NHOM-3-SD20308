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
        :root {
            --pc-green:        #27ae60;
            --pc-green-dark:   #219150;
            --pc-green-light:  #eafaf1;
            --pc-mint:         #2ecc71;
            --pc-red:          #e74c3c;
            --pc-orange:       #f39c12;
            --pc-bg:           #f8f9fa;
            --pc-white:        #ffffff;
            --pc-border:       #dee2e6;
            --pc-text:         #2c3e50;
            --pc-muted:        #7f8c8d;
        }
        body { background-color: var(--pc-bg); color: var(--pc-text); }
        .main-content { min-height: 70vh; }
        .btn-primary {
            background-color: var(--pc-green);
            border-color: var(--pc-green);
        }
        .btn-primary:hover {
            background-color: var(--pc-green-dark);
            border-color: var(--pc-green-dark);
        }
        .text-primary { color: var(--pc-green) !important; }
        a { color: var(--pc-green); }
        a:hover { color: var(--pc-green-dark); }
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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>