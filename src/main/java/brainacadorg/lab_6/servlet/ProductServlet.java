package brainacadorg.lab_6.servlet;

import brainacadorg.lab_6.dao.ProductDao;
import brainacadorg.lab_6.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() {
        productDao = new ProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> products = productDao.findAll();
        Product topProduct = productDao.getMostPopularProduct();

        request.setAttribute("products", products);
        request.setAttribute("topProduct", topProduct);

        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Product product = productDao.findById(id);
            if (product != null) {
                productDao.delete(product);
            }
        } else {
            String name = request.getParameter("name");
            BigDecimal price = new BigDecimal(request.getParameter("price"));

            Product product = Product.builder()
                    .name(name)
                    .price(price)
                    .build();

            productDao.save(product);
        }

        response.sendRedirect(request.getContextPath() + "/products");
    }
}