<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<div class="container-fluid mt-4 px-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="mb-0 fw-bold text-dark"><i class="bi bi-speedometer2 me-2"></i>BẢNG ĐIỀU KHIỂN HỆ THỐNG</h3>
    </div>

    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card text-white bg-success border-0 shadow-sm h-100">
                <div class="card-body text-center py-4">
                    <h5 class="card-title fw-normal mb-3"><i class="bi bi-cash-stack me-2"></i>Tổng Doanh Thu</h5>
                    <h2 class="card-text fw-bold"><fmt:formatNumber value="${totalRevenue}" type="number"/> đ</h2>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white bg-primary border-0 shadow-sm h-100">
                <div class="card-body text-center py-4">
                    <h5 class="card-title fw-normal mb-3"><i class="bi bi-receipt me-2"></i>Tổng Đơn Hàng</h5>
                    <h2 class="card-text fw-bold">${totalOrders}</h2>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white bg-info border-0 shadow-sm h-100">
                <div class="card-body text-center py-4">
                    <h5 class="card-title fw-normal mb-3"><i class="bi bi-cup-straw me-2"></i>Món Trong Thực Đơn</h5>
                    <h2 class="card-text fw-bold">${totalDrinks}</h2>
                </div>
            </div>
        </div>
    </div>

    <div class="card shadow-sm border-0 mb-4 p-3 bg-white">
        <form action="${pageContext.request.contextPath}/admin/dashboard" method="get" class="row g-3 align-items-end">
            <div class="col-md-4">
                <label class="form-label small fw-bold text-muted">Từ ngày</label>
                <input type="date" name="startDate" class="form-control" value="${startDate}" required>
            </div>
            <div class="col-md-4">
                <label class="form-label small fw-bold text-muted">Đến ngày</label>
                <input type="date" name="endDate" class="form-control" value="${endDate}" required>
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-primary w-100 fw-bold">CẬP NHẬT BIỂU ĐỒ</button>
            </div>
        </form>
    </div>

    <div class="row">
        <div class="col-md-8 mb-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header bg-white fw-bold py-3 border-bottom-0">BIỂU ĐỒ DOANH THU THEO NGÀY</div>
                <div class="card-body"><canvas id="revenueChart" height="130"></canvas></div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header bg-white fw-bold py-3 border-bottom-0 text-success">TOP 5 BÁN CHẠY NHẤT</div>
                <div class="card-body p-0">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light small"><tr><th class="ps-3">TÊN MÓN</th><th class="text-center">ĐÃ BÁN</th></tr></thead>
                        <tbody>
                            <c:forEach var="item" items="${topDrinks}">
                                <tr>
                                    <td class="ps-3 fw-bold">${item[0]}</td>
                                    <td class="text-center"><span class="badge bg-success-subtle text-success rounded-pill px-3">${item[1]}</span></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty topDrinks}">
                                <tr><td colspan="2" class="text-center py-4 text-muted small">Không có dữ liệu trong khoảng thời gian này</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    const labels = [];
    const dataPoints = [];
    <c:forEach var="rev" items="${revenues}">
        labels.push('${rev[0]}');
        dataPoints.push(${rev[1]});
    </c:forEach>

    new Chart(document.getElementById('revenueChart').getContext('2d'), {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu', data: dataPoints, borderColor: '#0d6efd', backgroundColor: 'rgba(13,110,253,0.1)', fill: true, tension: 0.4
            }]
        }
    });
</script>