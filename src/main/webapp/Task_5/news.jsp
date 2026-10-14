<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="brainacadorg.lab_4.Task_5.Company" %>
<%@ page import="brainacadorg.lab_4.Task_5.NewsItem" %>
<!DOCTYPE html>
<html>
<head>
    <title>Новини - Виробник ноутбуків</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .news-grid { display: flex; gap: 20px; flex-wrap: wrap; }
        .news-card { border: 1px solid #ddd; padding: 15px; border-radius: 8px; width: 300px; background: #fff; }
        .news-img { width: 100%; height: 180px; object-fit: cover; border-radius: 6px; }
    </style>
</head>
<body>

<jsp:include page="navbar.jsp" />

<% Company company = (Company) request.getAttribute("selectedCompany"); %>

<h2>Останні новини компанії <%= company != null ? company.getName() : "" %></h2>

<div class="news-grid">
    <% if (company != null && company.getNews() != null) {
        for (NewsItem item : company.getNews()) { %>
    <div class="news-card">
        <img src="<%= item.getImage() %>" alt="<%= item.getTitle() %>" class="news-img" onerror="this.src='https://via.placeholder.com/300x180?text=News+Photo';" />
        <h3><%= item.getTitle() %></h3>
        <p><%= item.getDescription() %></p>
    </div>
    <%  }
    } %>
</div>

</body>
</html>