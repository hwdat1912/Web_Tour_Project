package vn.edu.hcmuaf.fit.controller.admin;

import vn.edu.hcmuaf.fit.services.KeyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
