package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.Cart;
import vn.edu.hcmuaf.fit.bean.TourCart;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.security.PublicKey;


/* Servlet đăng nhập
Được tạo bởi Bùi Thanh Đảm - 20130217

 */
@WebServlet(name = "Login", value = "/user/views/doLogin")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng đến Servlet xem Public Key

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = UserService.getInstance().checkLogin(username, password);

        if (user == null) {
            request.setAttribute("error", "Tên người dùng hoặc mật khẩu không chính xác");
            request.setAttribute("route", "sign");
            request.getRequestDispatcher("sign.jsp").forward(request, response);
        } else {
            Map<String, TourCart> listTourCart = new LinkedHashMap<>();
            Cart cart = new Cart(user.getUser_Id(), listTourCart);
            HttpSession session = request.getSession(true);


            // Các dòng mã tiếp theo không thay đổi từ mã ban đầu của bạn
            session.setAttribute("auth", user);
            session.setAttribute("cart", cart);

            if (user.getUser_role() > 0) {
                response.sendRedirect("/projectWeb_war/redirect.jsp");
            } else {
                response.sendRedirect("/projectWeb_war/user/views/home");
            }
        }
    }
}