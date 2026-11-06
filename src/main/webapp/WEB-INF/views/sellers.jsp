<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Управління Продавцями</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Продавці</h2>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">На головну</a>
    </div>

    <c:if test="${not empty topSeller}">
        <div class="alert alert-success shadow-sm mb-4">
            <strong>Найуспішніший продавець (макс. сума продажів):</strong>
                ${topSeller.name} (${topSeller.email})
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
                <c:forEach var="seller" items="${sellers}">
                    <tr>
                        <td>${seller.id}</td>
                        <td><strong>${seller.name}</strong></td>
                        <td>${seller.phone}</td>
                        <td>${seller.email}</td>
                        <td class="text-end">
                            <form action="${pageContext.request.contextPath}/sellers" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${seller.id}">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Видалити продавця?')">Видалити</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty sellers}">
                    <tr>
                        <td colspan="5" class="text-center text-muted">Список порожній</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-success text-white">
            <h5 class="card-title mb-0">Додати нового продавця</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/sellers" method="post" class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Ім'я</label>
                    <input type="text" name="name" class="form-control" required placeholder="Іван Іванов">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Телефон</label>
                    <input type="text" name="phone" class="form-control" placeholder="+380970000000">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" placeholder="seller@mail.com">
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-success">Зберегти</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>