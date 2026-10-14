<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="brainacadorg.lab_4.Task_5.Company" %>
<!DOCTYPE html>
<html>
<head>
    <title>Загальна інформація - Виробник ноутбуків</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .card { border: 1px solid #ddd; padding: 20px; border-radius: 8px; max-width: 600px; background: #f9f9f9; }
        .logo { max-width: 150px; height: auto; margin-bottom: 15px; display: block; }
    </style>
</head>
<body>

<jsp:include page="navbar.jsp" />

<%
    Company company = (Company) request.getAttribute("selectedCompany");
%>

<h2>Загальна інформація про виробника</h2>

<% if (company != null) { %>
<div class="card">
    <img src="<%= company.getLogo() %>" alt="Logo <%= company.getName() %>" class="logo" onerror="this.src='https://via.placeholder.com/150?text=Logo';" />
    <h3>Назва: <%= company.getName() %></h3>
    <p><strong>Країна штаб-квартири:</strong> <%= company.getCountry() %></p>
    <p><strong>Кількість співробітників:</strong> <%= String.format("%,d", company.getEmployees()) %></p>
    <p><strong>Коротка інформація:</strong> <%= company.getDescription() %></p>
</div>
<% } else { %>
<p>Інформація про компанію відсутня.</p>
<% } %>

</body>
</html>