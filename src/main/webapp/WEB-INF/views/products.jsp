<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Каталог Товарів</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Товари</h2>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">На головну</a>
    </div>

    <c:if test="${not empty topProduct}">
        <div class="alert alert-warning shadow-sm mb-4">
            <strong>Товар, що найбільше користується попитом:</strong>
                ${topProduct.name} (Ціна: ${topProduct.price} грн)
        </div>
    </c:if>

    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <table class="table table-hover align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Назва товару</th>
                    <th>Ціна (грн)</th>
                    <th class="text-end">Дії</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.id}</td>
                        <td><strong>${product.name}</strong></td>
                        <td>${product.price}</td>
                        <td class="text-end">
                            <form action="${pageContext.request.contextPath}/products" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${product.id}">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Видалити товар?')">Видалити</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty products}">
                    <tr>
                        <td colspan="4" class="text-center text-muted">Список порожній</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-warning text-dark">
            <h5 class="card-title mb-0">Додати новий товар</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/products" method="post" class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">Назва товару</label>
                    <input type="text" name="name" class="form-control" required placeholder="Ноутбук">
                </div>
                <div class="col-md-6">
                    <label class="form-label">Ціна (грн)</label>
                    <input type="number" step="0.01" name="price" class="form-control" required placeholder="25000.00">
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-warning">Зберегти</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>