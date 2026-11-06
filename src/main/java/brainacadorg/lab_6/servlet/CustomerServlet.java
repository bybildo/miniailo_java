package brainacadorg.lab_6.servlet;

import brainacadorg.lab_6.dao.CustomerDao;
import brainacadorg.lab_6.entity.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerDao customerDao;

    @Override
    public void init() {
        customerDao = new CustomerDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Customer> customers = customerDao.findAll();
        Customer topCustomer = customerDao.getMostSuccessfulCustomer();

        request.setAttribute("customers", customers);
        request.setAttribute("topCustomer", topCustomer);

        request.getRequestDispatcher("/WEB-INF/views/customers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Customer customer = customerDao.findById(id);
            if (customer != null) {
                customerDao.delete(customer);
            }
        } else {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            Customer customer = Customer.builder()
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .build();

            customerDao.save(customer);
        }

        response.sendRedirect(request.getContextPath() + "/customers");
    }
}