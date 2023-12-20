package vn.edu.hcmuaf.fit.controller;


import vn.edu.hcmuaf.fit.bean.PublicKey;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.KeyService;
import vn.edu.hcmuaf.fit.services.RSAService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;

@WebServlet(name = "CreateByPrivate", value = "/user/views/CreateByPrivate")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50)
public class CreateByPrivate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        int message = 1;


        String data = req.getParameter("data");


        System.out.println(data);

        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession(true);
        User user = (User) (User) session.getAttribute("auth");
        KeyService keyService = new KeyService();
        List<PublicKey> list = keyService.getPublicKeyByStatus(user.getUser_Id(),KeyService.ENABLE);

        if(list.size() > 0){
            message = 2;

        }else {
            try (PrintWriter out = resp.getWriter()) {
                Part filePart = req.getPart("file"); // "file" là tên của input file trong form
                InputStream fileContent = filePart.getInputStream();

                System.out.println("OK");

                System.out.println(fileContent.available());

                byte[] bytes = fileContent.readAllBytes();

                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);

                RSAService rsa = new RSAService();
//                KeyService keyService = KeyService.getInstance();
//                HttpSession session = req.getSession();
//                User user = (User)session.getAttribute("auth");

                try {
                    rsa.generatePublicKeyFromPrivateKey(spec);

                    keyService.insertKey(user.getUser_Id(),rsa.exportPublicKey(), KeyService.ENABLE);
                    message = 1;

                    System.out.println(rsa.exportPublicKey());
                } catch (NoSuchAlgorithmException e) {
                    message = 0;

                    System.out.println("error");
                } catch (InvalidKeySpecException e) {
                    message = 0;
                    System.out.println("error");
                }

//            resp.getWriter().println(message);

//                out.println("File uploaded successfully!");
            } catch (Exception e) {
                message = 0;

            }
        }
        System.out.println(message);
        resp.getWriter().println(message);

    }
}
