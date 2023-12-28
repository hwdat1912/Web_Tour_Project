package vn.edu.hcmuaf.fit.controller;

import com.mysql.cj.Session;
import netscape.javascript.JSObject;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.KeyService;
import vn.edu.hcmuaf.fit.services.RSAService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ImportKey", value = "/user/views/ImportKey")
public class ImportKey extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RSAService rsa = new RSAService();
        KeyService keyService = KeyService.getInstance();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("auth");


        if(keyService.getPublicKeyByStatus(user.getUser_Id(),KeyService.ENABLE).size() == 0){
//            String message ="" ;
            int message;
            String key = request.getParameter("key");
            if(key == null || key.isEmpty() || keyService.isContantKey(key)){
//                message = "error";
                message = 0 ;
                response.getWriter().println(message);
                return;
            }
            try {
                rsa.importPublicKey(key);
                keyService.insertKey(user.getUser_Id(),rsa.exportPublicKey(),KeyService.ENABLE);
//                message = "success";
                message = 1 ;


            } catch (NoSuchAlgorithmException e) {
//                message = "error";
                message = 0 ;
            } catch (InvalidKeySpecException e) {
//                message = "error";
                message = 0 ;
            }
            System.out.println("Print Message:" +message);
            response.getWriter().println(message);

        }else {
            response.getWriter().println(0);
        }
    }
}
