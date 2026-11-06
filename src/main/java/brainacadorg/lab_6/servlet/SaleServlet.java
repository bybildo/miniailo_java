package brainacadorg.lab_6.servlet;

import brainacadorg.lab_6.dao.CustomerDao;
import brainacadorg.lab_6.dao.ProductDao;
import brainacadorg.lab_6.dao.SaleDao;
import brainacadorg.lab_6.dao.SellerDao;
import brainacadorg.lab_6.entity.Customer;
import brainacadorg.lab_6.entity.Product;
import brainacadorg.lab_6.entity.Sale;
import brainacadorg.lab_6.entity.Seller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/sales")
public class SaleServlet extends HttpServlet {

    private SaleDao saleDao;
    private SellerDao sellerDao;
    private CustomerDao customerDao;
    private ProductDao productDao;

    @Override
    public void init() {
        saleDao = new SaleDao();
        sellerDao = new SellerDao();
        customerDao = new CustomerDao();
        productDao = new ProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filter = request.getParameter("filter");
        List<Sale> sales;

        if ("date".equals(filter) && request.getParameter("saleDate") != null && !request.getParameter("saleDate").isEmpty()) {
            LocalDate date = LocalDate.parse(request.getParameter("saleDate"));
            sales = saleDao.findByDate(date);
        } else if ("range".equals(filter) && request.getParameter("startDate") != null && request.getParameter("endDate") != null) {
            LocalDate start = LocalDate.parse(request.getParameter("startDate"));
            LocalDate end = LocalDate.parse(request.getParameter("endDate"));
            sales = saleDao.findByDateRange(start, end);
        } else if ("seller".equals(filter) && request.getParameter("sellerId") != null) {
            Long sellerId = Long.parseLong(request.getParameter("sellerId"));
            sales = saleDao.findBySellerId(sellerId);
        } else if ("customer".equals(filter) && request.getParameter("customerId") != null) {
            Long customerId = Long.parseLong(request.getParameter("customerId"));
            sales = saleDao.findByCustomerId(customerId);
        } else {
            sales = saleDao.findAll();
        }

        request.setAttribute("sales", sales);
        request.setAttribute("sellers", sellerDao.findAll());
        request.setAttribute("customers", customerDao.findAll());
        request.setAttribute("products", productDao.findAll());
        request.setAttribute("avgAmount", saleDao.getAverageSaleAmount());
        request.getRequestDispatcher("/WEB-INF/views/sales.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Sale sale = saleDao.findById(id);
            if (sale != null) {
                saleDao.delete(sale);
            }
        } else {
            Long sellerId = Long.parseLong(request.getParameter("sellerId"));
            Long customerId = Long.parseLong(request.getParameter("customerId"));
            Long productId = Long.parseLong(request.getParameter("productId"));
            BigDecimal salePrice = new BigDecimal(request.getParameter("salePrice"));
            LocalDate saleDate = LocalDate.parse(request.getParameter("saleDate"));

            Seller seller = sellerDao.findById(sellerId);
            Customer customer = customerDao.findById(customerId);
            Product product = productDao.findById(productId);

            Sale sale = Sale.builder()
                    .seller(seller)
                    .customer(customer)
                    .product(product)
                    .salePrice(salePrice)
                    .saleDate(saleDate)
                    .build();

            saleDao.save(sale);
        }

        response.sendRedirect(request.getContextPath() + "/sales");
    }
}