package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.PublicKey;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.KeyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerKey", value = "/user/views/ManagerKey")
public class ManagerKey extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        User user = (User) (User) session.getAttribute("auth");

        KeyService keyService = KeyService.getInstance();
        List<PublicKey> list =null ;
        if(user != null){
            list = keyService.getPublicKeyByUserId(user.getUser_Id());
        }
        request.setAttribute("listKey",list);

        request.getRequestDispatcher("ManagerKey.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
