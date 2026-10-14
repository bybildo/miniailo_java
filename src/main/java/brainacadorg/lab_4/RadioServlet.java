package brainacadorg.lab_4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "radioServlet", value = "/radio")
public class RadioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/radio.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");
        String num3Str = request.getParameter("num3");
        String operation = request.getParameter("operation");

        if (num1Str != null && num2Str != null && num3Str != null && operation != null) {
            try {
                double num1 = Double.parseDouble(num1Str);
                double num2 = Double.parseDouble(num2Str);
                double num3 = Double.parseDouble(num3Str);

                double result = 0;
                String operationName = "";

                switch (operation) {
                    case "min":
                        result = Math.min(num1, Math.min(num2, num3));
                        operationName = "Мінімальне значення";
                        break;
                    case "max":
                        result = Math.max(num1, Math.max(num2, num3));
                        operationName = "Максимальне значення";
                        break;
                    case "avg":
                        result = (num1 + num2 + num3) / 3.0;
                        operationName = "Середнє арифметичне";
                        break;
                }

                request.setAttribute("num1", num1Str);
                request.setAttribute("num2", num2Str);
                request.setAttribute("num3", num3Str);
                request.setAttribute("selectedOp", operation);
                request.setAttribute("result", result);
                request.setAttribute("operationName", operationName);

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Введіть коректні числові значення");
            }
        } else {
            request.setAttribute("error", "Усі поля та вибір операції є обов'язковими");
        }

        request.getRequestDispatcher("/radio.jsp").forward(request, response);
    }
}
