package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.KeyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LostKey", value = "/user/views/LostKey")
public class LostKey extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(true);
        User user = (User) (User) session.getAttribute("auth");

        String option = request.getParameter("option");
        if("warning".equals(option)){
            String publicIdParam = request.getParameter("publicId").trim();
            if(publicIdParam != null && !publicIdParam.isEmpty()){
                try{
                int publicId = Integer.parseInt(publicIdParam);
                KeyService keyService = KeyService.getInstance();
                boolean success = keyService.lostKey(publicId);

                if (success) {
                        response.getWriter().println("success");
                    } else {
                        response.getWriter().println("error");
                    }
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Log the exception or handle it as needed
                }
            }
        }
    }
}
