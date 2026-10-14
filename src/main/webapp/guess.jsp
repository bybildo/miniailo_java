<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Завдання 3 - Вгадай число</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            max-width: 450px;
            background-color: #f9f9f9;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .btn {
            padding: 10px 15px;
            font-size: 15px;
            margin: 5px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
            color: white;
        }
        .btn-blue { background-color: #007bff; }
        .btn-orange { background-color: #fd7e14; }
        .btn-green { background-color: #28a745; }
        .btn-gray { background-color: #6c757d; }
        .btn:hover { opacity: 0.9; }
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .actions-form {
            display: inline-block;
        }
    </style>
</head>
<body>

<h2>Завдання 3: гра "Вгадай число"</h2>

<div class="card">
    <p>Загадайте число в діапазоні від <strong>0 до 100</strong> у голові, а комп'ютер спробує його відгадати!</p>

    <%
        Boolean isGameOver = (Boolean) session.getAttribute("isGameOver");
        Boolean isWon = (Boolean) session.getAttribute("isWon");
        String errorMsg = (String) session.getAttribute("errorMsg");
        Integer guess = (Integer) session.getAttribute("guess");
        Integer attempts = (Integer) session.getAttribute("attempts");
        Integer min = (Integer) session.getAttribute("min");
        Integer max = (Integer) session.getAttribute("max");
    %>

    <% if (Boolean.TRUE.equals(isGameOver)) { %>
    <% if (Boolean.TRUE.equals(isWon)) { %>
    <div class="alert-success">
        <h3>Ура! Комп'ютер вгадав ваше число: <strong><%= guess %></strong>!</h3>
        <p>Кількість спроб: <strong><%= attempts %></strong></p>
    </div>
    <% } else { %>
    <div class="alert-error">
        <h3>Помилка</h3>
        <p><%= errorMsg %></p>
    </div>
    <% } %>

    <form action="guess-number" method="POST">
        <input type="hidden" name="action" value="reset">
        <button type="submit" class="btn btn-blue">Зіграти ще раз</button>
    </form>

    <% } else { %>

    <h3>Спроба №<%= attempts %></h3>
    <p style="font-size: 1.3em;">Ваше число <strong><%= guess %></strong>?</p>
    <p style="color: #666; font-size: 0.9em;">(Поточний діапазон пошуку: від <%= min %> до <%= max %>)</p>

    <div style="margin-top: 20px;">
        <form action="guess-number" method="POST" class="actions-form">
            <input type="hidden" name="action" value="greater">
            <button type="submit" class="btn btn-orange">Число більше &gt;</button>
        </form>

        <form action="guess-number" method="POST" class="actions-form">
            <input type="hidden" name="action" value="less">
            <button type="submit" class="btn btn-blue">Число менше &lt;</button>
        </form>

        <form action="guess-number" method="POST" class="actions-form">
            <input type="hidden" name="action" value="equals">
            <button type="submit" class="btn btn-green">Вгадав!=</button>
        </form>
    </div>

    <div style="margin-top: 15px;">
        <form action="guess-number" method="POST">
            <input type="hidden" name="action" value="reset">
            <button type="submit" class="btn btn-gray" style="font-size: 12px;">Скинути / Почати спочатку</button>
        </form>
    </div>

    <% } %>
</div>

<br/>
<a href="index.jsp">&larr; На головну</a>

</body>
</html>