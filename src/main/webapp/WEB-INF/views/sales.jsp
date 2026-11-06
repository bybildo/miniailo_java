<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Журнал Угод</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-4 mb-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Журнал Угод</h2>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">На головну</a>
    </div>

    <c:if test="${not empty avgAmount}">
        <div class="alert alert-primary shadow-sm mb-4">
            <strong>Середня сума покупки:</strong> ${avgAmount} грн
        </div>
    </c:if>

    <div class="card shadow-sm mb-4">
        <div class="card-header bg-secondary text-white">
            <h6 class="mb-0">Фільтрація угод</h6>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/sales" method="get" class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">За датою</label>
                    <input type="date" name="saleDate" class="form-control">
                    <input type="hidden" name="filter" value="date">
                </div>
                <div class="col-md-3 align-self-end">
                    <button type="submit" class="btn btn-outline-secondary w-100">Фільтрувати за датою</button>
                </div>
                <div class="col-md-6 align-self-end text-end">
                    <a href="${pageContext.request.contextPath}/sales" class="btn btn-link text-decoration-none">Скинути всі фільтри</a>
                </div>
            </form>

            <hr>

            <form action="${pageContext.request.contextPath}/sales" method="get" class="row g-3">
                <input type="hidden" name="filter" value="range">
                <div class="col-md-3">
                    <label class="form-label">З дати</label>
                    <input type="date" name="startDate" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label">По дату</label>
                    <input type="date" name="endDate" class="form-control" required>
                </div>
                <div class="col-md-3 align-self-end">
                    <button type="submit" class="btn btn-outline-secondary w-100">За діапазоном</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <table class="table table-hover align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Дата</th>
                    <th>Продавець</th>
                    <th>Покупець</th>
                    <th>Товар</th>
                    <th>Сума (грн)</th>
                    <th class="text-end">Дії</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sale" items="${sales}">
                    <tr>
                        <td>${sale.id}</td>
                        <td>${sale.saleDate}</td>
                        <td>${sale.seller.name}</td>
                        <td>${sale.customer.name}</td>
                        <td>${sale.product.name}</td>
                        <td><strong>${sale.salePrice}</strong></td>
                        <td class="text-end">
                            <form action="${pageContext.request.contextPath}/sales" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${sale.id}">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Видалити угоду?')">Видалити</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty sales}">
                    <tr>
                        <td colspan="7" class="text-center text-muted">Угод не знайдено</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h5 class="card-title mb-0">Оформити нову угоду</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/sales" method="post" class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Продавець</label>
                    <select name="sellerId" class="form-select" required>
                        <c:forEach var="s" items="${sellers}">
                            <option value="${s.id}">${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Покупець</label>
                    <select name="customerId" class="form-select" required>
                        <c:forEach var="c" items="${customers}">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Товар</label>
                    <select name="productId" class="form-select" required>
                        <c:forEach var="p" items="${products}">
                            <option value="${p.id}">${p.name} (${p.price} грн)</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Сума продажу (грн)</label>
                    <input type="number" step="0.01" name="salePrice" class="form-control" required placeholder="1500.00">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Дата продажу</label>
                    <input type="date" name="saleDate" class="form-control" required>
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-primary">Зберегти угоду</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>