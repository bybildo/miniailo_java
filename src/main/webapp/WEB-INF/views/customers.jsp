<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Управління Покупцями</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Покупці</h2>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">На головну</a>
    </div>

    <c:if test="${not empty topCustomer}">
        <div class="alert alert-info shadow-sm mb-4">
            <strong>Найуспішніший покупець (макс. сума покупок):</strong>
                ${topCustomer.name} (${topCustomer.email})
        </div>
    </c:if>

    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <table class="table table-hover align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>ПІБ / Назва</th>
                    <th>Телефон</th>
                    <th>Email</th>
                    <th class="text-end">Дії</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="customer" items="${customers}">
                    <tr>
                        <td>${customer.id}</td>
                        <td><strong>${customer.name}</strong></td>
                        <td>${customer.phone}</td>
                        <td>${customer.email}</td>
                        <td class="text-end">
                            <form action="${pageContext.request.contextPath}/customers" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${customer.id}">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Видалити покупця?')">Видалити</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty customers}">
                    <tr>
                        <td colspan="5" class="text-center text-muted">Список порожній</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-info text-white">
            <h5 class="card-title mb-0">Додати нового покупця</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/customers" method="post" class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Ім'я</label>
                    <input type="text" name="name" class="form-control" required placeholder="Олена Петрівна">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Телефон</label>
                    <input type="text" name="phone" class="form-control" placeholder="+380500000000">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" placeholder="customer@mail.com">
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-info text-white">Зберегти</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>