package brainacadorg.lab_4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "quoteServlet", value = "/quote")
public class QuoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Завдання 1 - Цитата</title></head>");
        out.println("<body style='font-family: Arial, sans-serif; padding: 20px;'>");
        out.println("<h2>Завдання 1: цитата Мартіна Фаулера</h2>");
        out.println("<blockquote style='font-size: 1.2em; font-style: italic; background-color: #f9f9f9; padding: 15px; border-left: 5px solid #ccc;'>");
        out.println("\"Any fool can write code that a computer can understand. Good programmers write code that humans can understand.\"");
        out.println("</blockquote>");
        out.println("<p>— Martin Fowler</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
