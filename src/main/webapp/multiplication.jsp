<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Завдання 2 - таблиця множення</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        form { margin-bottom: 20px; }
        input[type='number'] { padding: 6px; font-size: 16px; width: 120px; }
        input[type='submit'] { padding: 6px 12px; font-size: 16px; cursor: pointer; }
        table { border-collapse: collapse; width: 250px; margin-top: 15px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f4f4f4; }
    </style>
</head>
<body>

<h2>Завдання 2: таблиця множення</h2>

<form action="multiplication-table" method="GET">
    <label for="number">Введіть число: </label>
    <input type="number" id="number" name="number" value="${param.number}" required />
    <input type="submit" value="Обчислити" />
</form>

<%
    String errorMessage = (String) request.getAttribute("error");
    if (errorMessage != null) {
%>
        <p style="color: red;"><%= errorMessage %></p>
<%
    }
%>

<%
    Integer number = (Integer) request.getAttribute("number");
    if (number != null) {
%>
<h3>Таблиця множення для числа <%= number %>:</h3>
<table>
    <thead>
    <tr><th>Вираз</th><th>Результат</th></tr>
    </thead>
    <tbody>
    <% for (int i = 1; i <= 10; i++) { %>
    <tr>
        <td><%= number %> &times; <%= i %></td>
        <td><%= number * i %></td>
    </tr>
    <% } %>
    </tbody>
</table>
<% } %>

<br/>
<a href="index.jsp">&larr; На головну</a>

</body>
</html>