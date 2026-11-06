package brainacadorg.lab_6.servlet;

import brainacadorg.lab_6.dao.SellerDao;
import brainacadorg.lab_6.entity.Seller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/sellers")
public class SellerServlet extends HttpServlet {

    private SellerDao sellerDao;

    @Override
    public void init() {
        sellerDao = new SellerDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Seller> sellers = sellerDao.findAll();
        Seller topSeller = sellerDao.getMostSuccessfulSeller();

        request.setAttribute("sellers", sellers);
        request.setAttribute("topSeller", topSeller);

        request.getRequestDispatcher("/WEB-INF/views/sellers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Seller seller = sellerDao.findById(id);
            if (seller != null) {
                sellerDao.delete(seller);
            }
        } else {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            Seller seller = Seller.builder()
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .build();

            sellerDao.save(seller);
        }

        response.sendRedirect(request.getContextPath() + "/sellers");
    }
}