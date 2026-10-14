<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Завдання 4 - Обчислення трьох чисел</title>
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
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="number"] {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .radio-group {
            margin: 15px 0;
        }
        .radio-option {
            margin-bottom: 8px;
            display: flex;
            align-items: center;
        }
        .radio-option input {
            margin-right: 8px;
        }
        .btn {
            padding: 10px 15px;
            font-size: 15px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
            color: white;
            background-color: #007bff;
            width: 100%;
        }
        .btn:hover { background-color: #0056b3; }
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .result-box {
            background-color: #e2e3e5;
            border: 1px solid #d6d8db;
            padding: 15px;
            border-radius: 4px;
            margin-top: 15px;
        }
    </style>
</head>
<body>

<h2>Завдання 4: Обчислення (Min, Max, Avg)</h2>

<div class="card">
    <%
        String error = (String) request.getAttribute("error");
        Double result = (Double) request.getAttribute("result");
        String operationName = (String) request.getAttribute("operationName");

        String num1 = request.getAttribute("num1") != null ? (String) request.getAttribute("num1") : "";
        String num2 = request.getAttribute("num2") != null ? (String) request.getAttribute("num2") : "";
        String num3 = request.getAttribute("num3") != null ? (String) request.getAttribute("num3") : "";
        String selectedOp = request.getAttribute("selectedOp") != null ? (String) request.getAttribute("selectedOp") : "min";
    %>

    <% if (error != null) { %>
    <div class="alert-error">
        <strong>Помилка:</strong> <%= error %>
    </div>
    <% } %>

    <form action="radio" method="POST">
        <div class="form-group">
            <label for="num1">Перше число:</label>
            <input type="number" step="any" id="num1" name="num1" value="<%= num1 %>" required />
        </div>

        <div class="form-group">
            <label for="num2">Друге число:</label>
            <input type="number" step="any" id="num2" name="num2" value="<%= num2 %>" required />
        </div>

        <div class="form-group">
            <label for="num3">Третє число:</label>
            <input type="number" step="any" id="num3" name="num3" value="<%= num3 %>" required />
        </div>

        <div class="radio-group">
            <label>Оберіть операцію:</label>

            <div class="radio-option">
                <input type="radio" id="min" name="operation" value="min" <%= "min".equals(selectedOp) ? "checked" : "" %>>
                <label for="min" style="font-weight: normal; margin: 0;">Мінімум (Min)</label>
            </div>

            <div class="radio-option">
                <input type="radio" id="max" name="operation" value="max" <%= "max".equals(selectedOp) ? "checked" : "" %>>
                <label for="max" style="font-weight: normal; margin: 0;">Максимум (Max)</label>
            </div>

            <div class="radio-option">
                <input type="radio" id="avg" name="operation" value="avg" <%= "avg".equals(selectedOp) ? "checked" : "" %>>
                <label for="avg" style="font-weight: normal; margin: 0;">Середнє арифметичне (Avg)</label>
            </div>
        </div>

        <button type="submit" class="btn">Обчислити</button>
    </form>

    <% if (result != null) { %>
    <div class="result-box">
        <p style="margin: 0;"><strong><%= operationName %>:</strong></p>
        <h3 style="margin: 5px 0 0 0; color: #28a745;">
            <%= String.format("%.2f", result) %>
        </h3>
    </div>
    <% } %>
</div>

<br/>
<a href="index.jsp">&larr; На головну</a>

</body>
</html>