package vn.edu.hcmuaf.fit.controller.mod;

import vn.edu.hcmuaf.fit.bean.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Mod", value = "/mod/mod")
public class Mod extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User auth= (User) session.getAttribute("auth");
        if(auth==null || !auth.checkRole(1)){
            request.setAttribute("error","Bạn không có quyền truy cập");
            response.sendRedirect("../login.jsp");
            return;
        }
        response.getWriter().println("Xin chào Mod");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
