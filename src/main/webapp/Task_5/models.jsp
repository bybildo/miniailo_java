<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="brainacadorg.lab_4.Task_5.Company" %>
<%@ page import="brainacadorg.lab_4.Task_5.LaptopModel" %>
<!DOCTYPE html>
<html>
<head>
    <title>Моделі ноутбуків - Виробник</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .models-grid { display: flex; gap: 20px; flex-wrap: wrap; }
        .model-card { border: 1px solid #ddd; padding: 15px; border-radius: 8px; width: 280px; background: #fff; }
        .model-img { width: 100%; height: 180px; object-fit: cover; border-radius: 6px; }
    </style>
</head>
<body>

<jsp:include page="navbar.jsp" />

<% Company company = (Company) request.getAttribute("selectedCompany"); %>

<h2>Моделі ноутбуків <%= company != null ? company.getName() : "" %></h2>

<div class="models-grid">
    <% if (company != null && company.getModels() != null) {
        for (LaptopModel model : company.getModels()) { %>
    <div class="model-card">
        <img src="<%= model.getImage() %>" alt="<%= model.getName() %>" class="model-img" onerror="this.src='https://via.placeholder.com/280x180?text=Laptop+Photo';" />
        <h3><%= model.getName() %></h3>
        <p><%= model.getDescription() %></p>
    </div>
    <%  }
    } %>
</div>

</body>
</html>