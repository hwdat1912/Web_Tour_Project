package vn.edu.hcmuaf.fit.DAO;

import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import vn.edu.hcmuaf.fit.bean.PublicKey;
import vn.edu.hcmuaf.fit.bean.Verify;
import vn.edu.hcmuaf.fit.db.JDBIConnector;

import java.util.List;

public class VerifyDAO {

    private static VerifyDAO instance;
    private VerifyDAO(){}

    public static VerifyDAO getInstance(){
        if (instance ==null) instance = new VerifyDAO();
        return instance;
    }

    public void insertVerify(String public_id,String booking_id	){
        JDBIConnector.get().withHandle(handle -> {
            return handle.createUpdate("INSERT INTO verify(public_id,booking_id) VALUES (?,?)")
                    .bind(0,public_id)
                    .bind(1,booking_id	)
                    .execute();
        });

    }

    public void update(String public_id,String booking_id){
        JDBIConnector.get().withHandle(handle -> {
            return handle.createUpdate("UPDATE verify set verify.public_id = ? where verify.booking_id = ?")
                    .bind(0,public_id)
                    .bind(1,booking_id	)
                    .execute();
        });

    }

    public String getKeyIdByBookingId(String bookingId){
        return JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT verify.public_id FROM verify WHERE booking_id = ? ")
                    .bind(0, bookingId)
                    .mapTo(String.class)
                    .findOne()
                    .orElse(null);
        });
    }

//    public boolean isVerify(String booking_id){
//        List<Verify> list = JDBIConnector.get().withHandle(handle -> {
//            return handle.createQuery("SELECT verify.public_id FROM verify WHERE \n" +
//                            "booking_id = ? ")
//                    .bind(0,booking_id)
//                    .registerRowMapper(ConstructorMapper.factory(Verify.class))
//                    .mapTo(Verify.class)
//                    .list();
//        });
//        return list.size() > 0 ? true :false;
//    }

    public static void main(String[] args) {
        System.out.println(VerifyDAO.getInstance().getKeyIdByBookingId("BOOKING-287308338"));
    }
}
