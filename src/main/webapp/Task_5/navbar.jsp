<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="brainacadorg.lab_4.Task_5.Company" %>
<%@ page import="java.util.List" %>

<%
    Company currentCompany = (Company) request.getAttribute("selectedCompany");
    List<Company> allCompanies = (List<Company>) request.getAttribute("companies");
    String currentCompanyId = (currentCompany != null) ? currentCompany.getId() : "asus";
%>

<style>
    .nav-container { background-color: #333; padding: 12px 20px; border-radius: 6px; margin-bottom: 20px; }
    .nav-links { display: flex; gap: 15px; align-items: center; }
    .nav-links a { color: white; text-decoration: none; font-weight: bold; padding: 6px 12px; border-radius: 4px; }
    .nav-links a:hover { background-color: #555; }
    .company-select { margin-left: auto; color: white; font-weight: bold; }
    .company-select select { padding: 5px; font-size: 14px; border-radius: 4px; }
</style>

<div class="nav-container">
    <div class="nav-links">
        <a href="<%= request.getContextPath() %>/index.jsp" style="background-color: #007bff;">&larr; На головну</a>
        <a href="<%= request.getContextPath() %>/laptop-manufacturer?company=<%= currentCompanyId %>">Загальна інформація</a>
        <a href="<%= request.getContextPath() %>/company-news?company=<%= currentCompanyId %>">Новини</a>
        <a href="<%= request.getContextPath() %>/laptop-models?company=<%= currentCompanyId %>">Моделі ноутбуків</a>

        <div class="company-select">
            <label for="companySelect">Виробник: </label>
            <select id="companySelect" onchange="location = '<%= request.getContextPath() %>/laptop-manufacturer?company=' + this.value;">
                <% if (allCompanies != null) {
                    for (Company c : allCompanies) { %>
                <option value="<%= c.getId() %>" <%= c.getId().equalsIgnoreCase(currentCompanyId) ? "selected" : "" %>>
                    <%= c.getName() %>
                </option>
                <%  }
                } %>
            </select>
        </div>
    </div>
</div>