package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.services.KeyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LostKey", value = "/user/views/LostKey")
public class LostKey extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String option = request.getParameter("option");
        if ("warning".equals(option)) {
            String publicIdParam = request.getParameter("publicId");
            if (publicIdParam != null && !publicIdParam.isEmpty()) {
                int publicId = Integer.parseInt(publicIdParam);
                KeyService keyService = KeyService.getInstance();
                int status = keyService.lostKey(publicId);

                String jsonResponse;
                if (status == KeyService.WARNING) {
                    jsonResponse = "{\"status\": \"warning\"}";
                } else {
                    jsonResponse = "{\"status\": \"success\"}";
                }

                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
                return;
            }
        }
        doGet(request, response);
    }
}
