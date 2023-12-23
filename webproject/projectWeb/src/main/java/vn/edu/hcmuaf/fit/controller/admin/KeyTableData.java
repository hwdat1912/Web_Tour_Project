package vn.edu.hcmuaf.fit.controller.admin;

import vn.edu.hcmuaf.fit.bean.PublicKey;
import vn.edu.hcmuaf.fit.services.KeyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "KeyTableData", value = "/admin/KeyTableData")
public class KeyTableData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<PublicKey> listPublicKeys = KeyService.getInstance().getAllPublicKey();
        // Set danh sách khóa vào request để sử dụng trong JSP
        request.setAttribute("listKeys", listPublicKeys);

        request.getRequestDispatcher("QuanLyKhoa.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
