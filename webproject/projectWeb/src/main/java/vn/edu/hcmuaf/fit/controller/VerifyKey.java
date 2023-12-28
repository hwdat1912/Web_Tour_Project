package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.DAO.BookingDAO;
import vn.edu.hcmuaf.fit.bean.Booking;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.KeyService;
import vn.edu.hcmuaf.fit.services.VerifyService;
import vn.edu.hcmuaf.fit.services.WriteBookingSevice;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;

@WebServlet(name = "VerifyKey", value = "/user/views/VerifyKey")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50)
public class VerifyKey extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try{
            HttpSession session = request.getSession(true);
            User user = (User) (User) session.getAttribute("auth");

            KeyService keyService = KeyService.getInstance();

            String bookingId = request.getParameter("bookingId");
            System.out.println(bookingId);

            String publicKeyString = keyService.getOnePublicKeyBySatus(user.getUser_Id());
            PublicKey publicKey = keyService.convertStringToPublicKey(publicKeyString);

            Part filePart = request.getPart("file");
            InputStream fileContent = filePart.getInputStream();
            PrivateKey privateKey = keyService.convertFileToPrivateKey(fileContent);

            VerifyService verifyService = VerifyService.getInstance();
            boolean keyCompatibility = verifyService.check(publicKey,privateKey);

            String jsonResponse;
            if (keyCompatibility) {

                BookingDAO bookingDAO = BookingDAO.getInstance();
                Booking booking = bookingDAO.getBookingById(bookingId);

                System.out.println(booking);

                WriteBookingSevice writeBookingSevice = WriteBookingSevice.getInstance();
                String writeBooking = writeBookingSevice.writeBooking(booking, request);
                System.out.println(writeBooking);

                String dirUrl =File.separator + "sign";
                String absolutePath = request.getServletContext().getRealPath(dirUrl);
                File dir = new File(absolutePath);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                String fileStoreVerify = dir.getAbsolutePath() + File.separator + bookingId;

                verifyService.sign(writeBooking, fileStoreVerify, privateKey);

                boolean verify = verifyService.verify(writeBooking, fileStoreVerify, publicKeyString);

                if(verify){
                    System.out.println("Signature verified successfully");
                    jsonResponse = "{\"status\": \"success\"}";
                }else{
                    System.out.println("Signature not verified");
                    jsonResponse = "{\"status\": \"error\"}";
                }
            } else {
                jsonResponse = "{\"status\": \"error\"}";
            }
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
            return;
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
