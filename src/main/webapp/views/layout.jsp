<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PolyCafe</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/views/common/header.jsp"/>
    <jsp:include page="/views/common/menu.jsp"/>

    <div class="container mt-4">
        <c:if test="${not empty view}">
            <jsp:include page="${view}"/>
        </c:if>
    </div>

    <jsp:include page="/views/common/footer.jsp"/>
</body>
</html>