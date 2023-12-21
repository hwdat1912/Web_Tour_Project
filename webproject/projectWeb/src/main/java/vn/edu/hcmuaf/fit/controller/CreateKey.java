package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.PublicKey;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.KeyService;
import vn.edu.hcmuaf.fit.services.RSAService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@WebServlet(name = "CreateKey", value = "/user/views/CreateKey")
public class CreateKey extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath()+"/user/views/ManagerKey");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(true);
        User user = (User) (User) session.getAttribute("auth");
        KeyService keyService = new KeyService();
        List<PublicKey> list = keyService.getPublicKeyByStatus(user.getUser_Id(),KeyService.ENABLE);
        System.out.println(user);


        if(list.size() == 0){
            RSAService rsaService = new RSAService();
            rsaService.generate();

            String fileNameOut = "privateKey.bin";
            byte[] data = rsaService.getPrivateKey().getEncoded();

            System.out.println(rsaService.getPrivateKey().getEncoded());


            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename="+fileNameOut);
            response.setContentLength(data.length);


            OutputStream outStream = new BufferedOutputStream(response.getOutputStream());

            request.setAttribute("success","Thành Công");
            outStream.write(data);
            keyService.insertKey(user.getUser_Id(),rsaService.exportPublicKey(),KeyService.ENABLE);
            outStream.flush();
            outStream.close();
//            response.setHeader("Refresh", "0");


//            request.getRequestDispatcher("/user/views/ManagerKey").forward(request,response);

        }else if(list.size() > 0) {
//            response.sendRedirect(request.getContextPath()+"/user/views/ManagerKey");
            request.setAttribute("error","Lỗi có public key đang được sử dụng");
            request.getRequestDispatcher("/user/views/ManagerKey").forward(request,response);

        }else {
            response.sendRedirect(request.getContextPath()+"/user/views/ManagerKey");
        }

//        response.sendRedirect(request.getContextPath()+"/user/views/ManagerKey");
    }
}
