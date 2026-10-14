package brainacadorg.lab_4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "guessNumberServlet", value = "/guess-number")
public class GuessNumberServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("min") == null) {
            initGame(session);
        }

        request.getRequestDispatcher("/guess.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("reset".equals(action) || session.getAttribute("min") == null) {
            initGame(session);
        } else {
            int min = (Integer) session.getAttribute("min");
            int max = (Integer) session.getAttribute("max");
            int guess = (Integer) session.getAttribute("guess");
            int attempts = (Integer) session.getAttribute("attempts");

            switch (action) {
                case "greater":
                    min = guess + 1;
                    break;
                case "less":
                    max = guess - 1;
                    break;
                case "equals":
                    session.setAttribute("isGameOver", true);
                    session.setAttribute("isWon", true);
                    break;
            }

            if (min > max && !"equals".equals(action)) {
                session.setAttribute("isGameOver", true);
                session.setAttribute("isWon", false);
                session.setAttribute("errorMsg", "Некоректні підказки. Межі чисел перетинулися!");
            } else if (!"equals".equals(action)) {
                guess = min + (max - min) / 2;
                attempts++;

                session.setAttribute("min", min);
                session.setAttribute("max", max);
                session.setAttribute("guess", guess);
                session.setAttribute("attempts", attempts);
            }
        }

        response.sendRedirect(request.getContextPath() + "/guess-number");
    }

    private void initGame(HttpSession session) {
        session.setAttribute("min", 0);
        session.setAttribute("max", 100);
        session.setAttribute("guess", 50);
        session.setAttribute("attempts", 1);
        session.setAttribute("isGameOver", false);
        session.setAttribute("isWon", false);
        session.removeAttribute("errorMsg");
    }
}
