package vn.edu.hcmuaf.fit.services;

import vn.edu.hcmuaf.fit.bean.Booking;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class WriteBookingSevice {
    private static WriteBookingSevice intance;

    private WriteBookingSevice() {
    }

    public static WriteBookingSevice getInstance() {
        if (intance == null) intance = new WriteBookingSevice();
        return intance;
    }

    public String writeBooking(Booking booking, HttpServletRequest request) {
        String dirUrl =File.separator + "sign";
        String absolutePath = request.getServletContext().getRealPath(dirUrl);

        File dir = new File(absolutePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String fileBooking = dir.getAbsolutePath() + File.separator + booking.getBOOKING_ID() + ".txt";
        try {
            PrintWriter print = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fileBooking)), true);
            print.println(booking.getBOOKING_ID());
            print.println(booking.getTOUR_ID());
            print.println(booking.getUSER_ID());
            print.println(booking.getHoTen());
            print.println(booking.getEmail());
            print.println(booking.getPhone());
            print.println(booking.getDiaChi());
            print.println(booking.getSOLUONG_VENGUOILON());
            print.println(booking.getSOLUONG_VETREEM());
            print.println(booking.getSOLUONG());
            print.println(booking.getNgayTao());
            print.println(booking.getTongTien());
            print.println(booking.getDescription());

            if (print != null) {
                print.close();
            }
            return fileBooking;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
