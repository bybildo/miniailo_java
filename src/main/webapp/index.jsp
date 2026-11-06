<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Система Управління Продажами</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="p-5 mb-4 bg-white rounded-3 shadow-sm text-center">
        <h1 class="display-5 fw-bold text-primary">Система Управління «Продажі»</h1>
        <p class="fs-5 text-muted">Лабораторна робота №6 — Jakarta EE + Hibernate ORM</p>
        <hr class="my-4">

        <div class="row g-4 mt-2">
            <div class="col-md-3">
                <div class="card h-100 shadow-sm border-0">
                    <div class="card-body">
                        <h5 class="card-title text-success">Продавці</h5>
                        <p class="card-text text-muted">Управління продавцями та перегляд аналітики продажів.</p>
                        <a href="${pageContext.request.contextPath}/sellers" class="btn btn-outline-success w-100">Перейти</a>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card h-100 shadow-sm border-0">
                    <div class="card-body">
                        <h5 class="card-title text-info">Покупці</h5>
                        <p class="card-text text-muted">База покупців та аналіз найбільш активних клієнтів.</p>
                        <a href="${pageContext.request.contextPath}/customers" class="btn btn-outline-info w-100">Перейти</a>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card h-100 shadow-sm border-0">
                    <div class="card-body">
                        <h5 class="card-title text-warning">Товари</h5>
                        <p class="card-text text-muted">Каталог товарів та перегляд найбільш популярних позицій.</p>
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-warning w-100">Перейти</a>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card h-100 shadow-sm border-0">
                    <div class="card-body">
                        <h5 class="card-title text-primary">Угоди</h5>
                        <p class="card-text text-muted">Журнал угод, фільтрація за датами, покупцями та продавцями.</p>
                        <a href="${pageContext.request.contextPath}/sales" class="btn btn-outline-primary w-100">Перейти</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>