package brainacadorg.lab_4.Task_5;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "newsServlet", value = "/company-news")
public class NewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String companyId = request.getParameter("company");
        Company company = DataLoader.getCompanyById(companyId);

        request.setAttribute("companies", DataLoader.getCompanies());
        request.setAttribute("selectedCompany", company);
        request.getRequestDispatcher("/Task_5/news.jsp").forward(request, response);
    }
}