package vn.edu.hcmuaf.fit.controller.admin;

import vn.edu.hcmuaf.fit.bean.PublicKey;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.KeyService;
import vn.edu.hcmuaf.fit.services.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DisableKeyServlet", value = "/admin/DisableKeyServlet")
public class DisableKeyServlet extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int publicKeyId = Integer.parseInt(request.getParameter("publicId"));

        KeyService.getInstance().disableKey(publicKeyId);

        // You can send a response if needed
        response.getWriter().write("Key disabled successfully");
    }
}
