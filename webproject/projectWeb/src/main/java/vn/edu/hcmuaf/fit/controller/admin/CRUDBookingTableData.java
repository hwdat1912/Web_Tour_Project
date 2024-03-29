package vn.edu.hcmuaf.fit.controller.admin;

import vn.edu.hcmuaf.fit.bean.*;
import vn.edu.hcmuaf.fit.services.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "CRUDBookingTableData", value = "/admin/CRUDBookingTableData")
public class CRUDBookingTableData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> listKH = UserService.getInstance().getListKhachHang();
        List<Tour> listTour = TourService.getInstance().getListTour();
        request.setAttribute("listKH",listKH);
        request.setAttribute("listTour",listTour);
        request.getRequestDispatcher("form-add-don-hang.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String bookingUserId = request.getParameter("bookingUserId");
        String bookingTourId = request.getParameter("bookingTourId");
        String bookingDate = request.getParameter("bookingDate");
        String bookingHoTen = request.getParameter("bookingHoTen");
        String bookingEmail = request.getParameter("bookingEmail");
        String bookingPhone = request.getParameter("bookingPhone");
        String bookingDiaChi = request.getParameter("bookingDiaChi");
        String bookingSLVNL = request.getParameter("bookingSLVNL");
        String bookingSLVTE = request.getParameter("bookingSLVTE");
        String bookingTrangThai = request.getParameter("bookingTrangThai");
        String bookingDescription = request.getParameter("bookingDescription");

        System.out.println("User Id:"+bookingUserId);

        Booking tc = new Booking();
        tc.setBOOKING_ID(bookingId);
        tc.setUSER_ID(bookingUserId);
        tc.setTOUR_ID(bookingTourId);
        tc.setNgayTao(Date.valueOf(bookingDate));
        tc.setHoTen(bookingHoTen);
        tc.setEmail(bookingEmail);
        tc.setPhone(bookingPhone);
        tc.setDiaChi(bookingDiaChi);
        tc.setSOLUONG_VENGUOILON(Integer.parseInt(bookingSLVNL));
        tc.setSOLUONG_VETREEM(Integer.parseInt(bookingSLVTE));
        tc.setTRANGTHAI(Integer.parseInt(bookingTrangThai));
        tc.setDescription(bookingDescription);
        tc.setSOLUONG(Integer.parseInt(bookingSLVNL)+Integer.parseInt(bookingSLVTE));

        System.out.println(tc.toString());

        System.out.println("Đã vào booking table data");
        if (bookingId.equals("")){
            System.out.println("Vào thêm mới");

            boolean b = BookingService.getInstance().createBookingAdmin(tc);
            if (b){

                response.sendRedirect(request.getContextPath()+"/admin/BookingTableData");
            }else {
                String text = "Tạo mới không thành công vì số lượng còn lại của Tour: "+tc.getTOUR_ID()+" bé hơn số lượng vé yêu cầu "+tc.getSOLUONG();
                request.setAttribute("error",text);

                List<User> listKH = UserService.getInstance().getListKhachHang();
                List<Tour> listTour = TourService.getInstance().getListTour();
                request.setAttribute("listKH",listKH);
                request.setAttribute("listTour",listTour);
                request.getRequestDispatcher("form-add-don-hang.jsp").forward(request, response);
            }
        }else{

            System.out.println("Vào thêm update");
            boolean b = BookingService.getInstance().updateBookingAdmin(tc);
            boolean checkVerify = false;
            System.out.println("Check:" + b);
            if (b){

//               System.out.println("Write:"+ WriteBookingSevice.getInstance().writeBooking(tc,request));
                WriteBookingSevice.getInstance().writeBooking(tc,request);
                System.out.println("ghi thành công");
                response.sendRedirect(request.getContextPath()+"/admin/BookingTableData");
            }else {
                String text = "Sửa không thành công vì số lượng còn lại của Tour: "+tc.getTOUR_ID()+" bé hơn số lượng vé yêu cầu "+tc.getSOLUONG();
                request.setAttribute("error",text);

                List<User> listKH = UserService.getInstance().getListKhachHang();
                List<Tour> listTour = TourService.getInstance().getListTour();
                request.setAttribute("listKH",listKH);
                request.setAttribute("listTour",listTour);
                request.getRequestDispatcher("form-add-don-hang.jsp").forward(request, response);
            }
        }


    }
}
