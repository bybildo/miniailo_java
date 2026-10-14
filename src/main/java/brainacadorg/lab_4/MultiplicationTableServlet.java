package brainacadorg.lab_4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "multiplicationTableServlet", value = "/multiplication-table")
public class MultiplicationTableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numberParam = request.getParameter("number");

        if (numberParam != null && !numberParam.trim().isEmpty()) {
            try {
                int number = Integer.parseInt(numberParam);
                request.setAttribute("number", number);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Некоректне число");
            }
        }
        request.getRequestDispatcher("/multiplication.jsp").forward(request, response);
    }
}
