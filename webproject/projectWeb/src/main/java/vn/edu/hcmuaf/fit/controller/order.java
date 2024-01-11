package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.Booking;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.services.BookingService;
import vn.edu.hcmuaf.fit.services.KeyService;
import vn.edu.hcmuaf.fit.services.VerifyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "order", value = "/user/views/order")
public class order extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("auth");
        String cancel = request.getParameter("cancel");
        if (cancel !=null) {
            boolean b = BookingService.getInstance().cancelBooking(cancel,user.getUser_Id());

        }
        System.out.println("Vao order");
        List<Booking> listBooking = BookingService.getInstance().getListBookingByUserId(user.getUser_Id());
        System.out.println("Da qua list");

        Map<String,Integer> verfiyOrder = new HashMap<>();
        VerifyService verifyService = VerifyService.getInstance();

        int status;
        for (Booking booking:listBooking
             ) {
            System.out.println("Vao for");
            if (VerifyService.getInstance().getKeyIdByBookingId(booking.getBOOKING_ID()) != null){
                String dirUrl = File.separator + "booking";
                String absolutePath = request.getServletContext().getRealPath(dirUrl);
                String fileBooking = absolutePath + File.separator + booking.getBOOKING_ID() + ".txt";

                String dirVerify = File.separator + "sign";
                String absoluteVerify = request.getServletContext().getRealPath(dirVerify);
                String fileVerify = absoluteVerify+File.separator + booking.getBOOKING_ID();

                File fileBook = new File(fileBooking);
                File fileVeri = new File(fileVerify);

                System.out.println("File Book:" + fileBook.exists());
                System.out.println("File Verify:" + fileVeri.exists());

                System.out.println("Path:" + fileBook.getAbsolutePath());
                System.out.println("Path:" + fileVeri.getAbsolutePath());


                String publicKey = KeyService.getInstance().getKeyById(VerifyService.getInstance().getKeyIdByBookingId(booking.getBOOKING_ID()));

                System.out.println(verifyService.changeVerifyInDb(booking,fileBooking));

                if(verifyService.verify(fileBooking,fileVerify,publicKey) && verifyService.changeVerifyInDb(booking,fileBooking)){
                    status = VerifyService.VERIFY_SUCCESS;
                }else {
                    status = VerifyService.VERIFY_CHANGE;
                }

            }else {
                status = VerifyService.NONE_VERIFY;
            }

            verfiyOrder.put(booking.getBOOKING_ID(), status);
        }

        request.setAttribute("listVerify",verfiyOrder);
        request.setAttribute("listBooking",listBooking);
        request.getRequestDispatcher("Order.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
