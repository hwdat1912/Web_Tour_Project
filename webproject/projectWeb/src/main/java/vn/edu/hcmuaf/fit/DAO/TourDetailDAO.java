package vn.edu.hcmuaf.fit.DAO;



import vn.edu.hcmuaf.fit.bean.*;
import vn.edu.hcmuaf.fit.db.JDBIConnector;
import vn.edu.hcmuaf.fit.services.VoucherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/*
Lớp TourPackageDAO dùng để bơm dữ liệu từ csdl lên chuyển qua TourPackageService để Servlet gọi lấy dữ liệu
được tạo bởi Bùi Thanh Đảm 20130217
cập nhật bởi Bùi Thanh Đảm 20130217
 */
public class TourDetailDAO {

    private static TourDetailDAO instance;

    //non constructor
    private TourDetailDAO(){
    }

    public static TourDetailDAO getInstance(){
        if (instance == null) instance = new TourDetailDAO();
        return  instance;
    }

    public List<TourDetailDays> getListDay(String tour_id){
        List<TourDetailDays> list = JDBIConnector.get().withHandle(h ->
                h.createQuery("select * from tour_detail_per_day where tour_detail_per_day.TOUR_ID  = ?")
                        .bind(0, tour_id)
                        .mapToBean(TourDetailDays.class)
                        .stream()
                        .collect(Collectors.toList())
        );

        return list ;
    }
    public List<TourDetailImages> getListImage(String tour_id){
        List<TourDetailImages> list = JDBIConnector.get().withHandle(h ->
                h.createQuery("select * from tour_detail_image where tour_detail_image.TOUR_ID  = ?")
                        .bind(0, tour_id)
                        .mapToBean(TourDetailImages.class)
                        .stream()
                        .collect(Collectors.toList())
        );

        return list ;
    }
    public List<TourDetailType> getListType(String tour_id){
        List<TourDetailType> list = JDBIConnector.get().withHandle(h ->
                h.createQuery("select * from tour_type where tour_type.TOUR_ID  = ?")
                        .bind(0, tour_id)
                        .mapToBean(TourDetailType.class)
                        .stream()
                        .collect(Collectors.toList())
        );

        return list ;
    }

    public List<TourGuide> getListGuide(String tour_id){
        List<TourGuide> list = JDBIConnector.get().withHandle(h ->
                h.createQuery("select tour_guide.*,user.FullName ,user.email ,user.phone ,user.ImageURL from tour_guide inner join user on user.USER_ID =tour_guide.USER_ID where tour_guide.TOUR_ID  = ? and user.USER_Role =1")
                        .bind(0, tour_id)
                        .mapToBean(TourGuide.class)
                        .stream()
                        .collect(Collectors.toList())
        );

        return list ;
    }

    public List<Voucher> getListVoucher(String tour_id){
        VoucherDAO.getInstance().updateStatusVoucher();
        List<Voucher> list = JDBIConnector.get().withHandle(h ->
                h.createQuery("select voucher.* from voucher inner join tour_voucher on tour_voucher.VOUCHER_ID =voucher.VOUCHER_ID where tour_voucher.TOUR_ID  = ? and voucher.TRANGTHAI = 1")
                        .bind(0, tour_id)
                        .mapToBean(Voucher.class)
                        .stream()
                        .collect(Collectors.toList())
        );

        return list ;
    }

    public boolean likeTour(String user_id,String tourId){
        List<TourDetail> td = JDBIConnector.get().withHandle(handle ->
                handle.createQuery("select tour.* from like_tour inner join tour on tour.TOUR_ID = like_tour.TOUR_ID where like_tour.TOUR_ID =? and like_tour.USER_ID = ?")
                        .bind(0,tourId)
                        .bind(1,user_id)
                        .mapToBean(TourDetail.class)
                        .stream()
                        .collect(Collectors.toList())
                );
        if (td.size() > 0) return false;

         JDBIConnector.get().withHandle(handle ->

                handle.createUpdate("insert into like_tour values (?,?)")
                        .bind(0,tourId)
                        .bind(1,user_id)
                        .execute()
        );
        return true;

    }

    public boolean unLikeTour(String user_id,String tourId){

        JDBIConnector.get().withHandle(handle ->

                handle.createUpdate("delete from like_tour where like_tour.TOUR_ID =? and like_tour.USER_ID = ?")
                        .bind(0,tourId)
                        .bind(1,user_id)
                        .execute()
        );
        return true;

    }

    public List<TourDetail> getListLikedTour(String user_id){
        List<TourDetail> llt = JDBIConnector.get().withHandle(handle ->
                handle.createQuery("select tour.* from like_tour inner join tour on tour.TOUR_ID = like_tour.TOUR_ID where like_tour.USER_ID = ?")
                        .bind(0,user_id)
                        .mapToBean(TourDetail.class)
                        .stream()
                        .collect(Collectors.toList())
        );
        return llt;
    }

    public boolean getLikedTourDetail(String user_id,String tourId){
        List<TourDetail> llt = JDBIConnector.get().withHandle(handle ->
                handle.createQuery("select tour.* from like_tour inner join tour on tour.TOUR_ID = like_tour.TOUR_ID where like_tour.TOUR_ID =? and like_tour.USER_ID = ?")
                        .bind(0,tourId)
                        .bind(1,user_id)
                        .mapToBean(TourDetail.class)
                        .stream()
                        .collect(Collectors.toList())
        );
        if (llt.size()!=1) return false;
        return true;
    }

    public List<TourDetail> getListIncomingSoldOutTour(){
        List<TourDetail> llt = JDBIConnector.get().withHandle(handle ->
                handle.createQuery("select * from tour  where tour.SoLuong < 10")

                        .mapToBean(TourDetail.class)
                        .stream()
                        .collect(Collectors.toList())
        );
        return llt;
    }

    public List<TourDetail> getListTourGuideCalendar(String guideId){
        List<TourDetail> llt = JDBIConnector.get().withHandle(handle ->
                handle.createQuery("select tour.* from tour inner join tour_guide on tour_guide.TOUR_ID = tour.TOUR_ID where NgayKhoiHanh > CURRENT_DATE and tour_guide.USER_ID = ?")
                        .bind(0,guideId)
                        .mapToBean(TourDetail.class)
                        .stream()
                        .collect(Collectors.toList())
        );
        llt.sort((o1, o2) -> o1.getNgayKhoiHanh().getTime() >=o2.getNgayKhoiHanh().getTime() ?-1:1);
        return llt;
    }
    public boolean adminCreateTour(Map<String,String > map){
        Random random = new Random();
        String id ="Tour"+ (random.nextInt(90000000) );
        int countGuide = Integer.parseInt(map.get("tourDetailCountGuide"));
        List<String> guidesId = new ArrayList<String>();
        for (int i = 1; i <= countGuide; i++) {
            String g = "tourDetailGuide"+i;
           if (map.containsKey(g)){
               String gid = map.get(g);
               guidesId.add(gid);
           }
        }



        List<String[]> listType = new ArrayList<String[]>();
        String[] NLtype = new String[3];
        NLtype[0] = id;
        NLtype[1] = "1";
        NLtype[2] = map.get("tourDetailSLVNL");
        listType.add(NLtype);
        String[] TEtype = new String[3];
        TEtype[0] = id;
        TEtype[1] = "0";
        TEtype[2] = map.get("tourDetailSLVTE");
        listType.add(TEtype);


       int countDay = Integer.parseInt(map.get("tourDetailCountDay")) ;
        List<String[]> listDay = new ArrayList<String[]>();
        for (int i = 1; i <= countDay ; i++) {
            String[] day = new String[3];
            day[0] = map.get("tourDetailDay"+i);
            day[1] = map.get("tourDetailDayTitle"+i);
            day[2] = map.get("tourDetailDayMoTa"+i);
            listDay.add(day);
        }

       if (createTourDetail(map,id)==true){
           createTourDetailGuide(guidesId,id);
           createTourDetailType(listType,id);
           createTourDetailDay(listDay,id);
           createTourDetailVoucher(map.get("tourDetailVoucher"),id);
           return true;
       }else{
           return false;
       }

    }

    public boolean adminUpdateTour(Map<String,String > map){

        String id = map.get("tourDetailId");
        int countGuide = Integer.parseInt(map.get("tourDetailCountGuide"));
        List<String> guidesId = new ArrayList<String>();
        for (int i = 1; i <= countGuide; i++) {
            String g = "tourDetailGuide"+i;
            if (map.containsKey(g)){
                String gid = map.get(g);
                guidesId.add(gid);
            }
        }



        List<String[]> listType = new ArrayList<String[]>();
        String[] NLtype = new String[3];
        NLtype[0] = id;
        NLtype[1] = "1";
        NLtype[2] = map.get("tourDetailSLVNL");
        listType.add(NLtype);
        String[] TEtype = new String[3];
        TEtype[0] = id;
        TEtype[1] = "0";
        TEtype[2] = map.get("tourDetailSLVTE");
        listType.add(TEtype);


        int countDay = Integer.parseInt(map.get("tourDetailCountDay")) ;
        List<String[]> listDay = new ArrayList<String[]>();
        for (int i = 1; i <= countDay ; i++) {
            String[] day = new String[3];
            day[0] = map.get("tourDetailDay"+i);
            day[1] = map.get("tourDetailDayTitle"+i);
            day[2] = map.get("tourDetailDayMoTa"+i);
            listDay.add(day);
        }

        if (updateTourDetail(map,id)==true){
            deleteTourDetailAll(id);
            createTourDetailGuide(guidesId,id);
            createTourDetailType(listType,id);
            createTourDetailDay(listDay,id);
            createTourDetailVoucher(map.get("tourDetailVoucher"),id);
            return true;
        }else{
            return false;
        }

    }

    public boolean createTourDetail(Map<String,String > map , String id){
      int row =  JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("insert into tour values(?,?,?,?,?,?,?,?,?,?,?,?,?)")
                        .bind(0,id)
                        .bind(1,map.get("tourDetailName"))
                        .bind(2,map.get("tourDetailDiaDiem"))
                        .bind(3,Integer.parseInt(map.get("tourDetailTrangThai")))
                        .bind(4,map.get("tourDetailCreateDate"))
                        .bind(5,map.get("tourDetailStartDate"))
                        .bind(6,map.get("tourDetailEndDate"))
                        .bind(7,map.get("tourDetailStartDiaDiem"))
                        .bind(8,Integer.parseInt(map.get("tourDetailSoLuong")))
                        .bind(9,map.get("tourDetailVehicle"))
                        .bind(10,map.get("ImageUpload"))
                        .bind(11,map.get("tourDetailDescription"))
                        .bind(12,map.get("tourDetailCategory"))
                        .execute()

        );
        return  row!=1?false:true;
    }

    public boolean updateTourDetail(Map<String,String > map , String id){
        int row = 0;
        if (map.get("ImageUpload")==null){
            row = JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate("update tour" +
                                    " set TourName=?,DiaDiem_ID=?,TrangThai=?,NgayTao=?,NgayKhoiHanh=?,NgayKetThuc=?,NoiKhoiHanh=?,SoLuong=?,PhuongTienDiChuyen=?,Description=?,TOUR_CATEGORY=? " +
                                    "where TOUR_ID= ?")

                            .bind(0, map.get("tourDetailName"))
                            .bind(1, map.get("tourDetailDiaDiem"))
                            .bind(2, Integer.parseInt(map.get("tourDetailTrangThai")))
                            .bind(3, map.get("tourDetailCreateDate"))
                            .bind(4, map.get("tourDetailStartDate"))
                            .bind(5, map.get("tourDetailEndDate"))
                            .bind(6, map.get("tourDetailStartDiaDiem"))
                            .bind(7, Integer.parseInt(map.get("tourDetailSoLuong")))
                            .bind(8, map.get("tourDetailVehicle"))

                            .bind(9, map.get("tourDetailDescription"))
                            .bind(10, map.get("tourDetailCategory"))
                            .bind(11, id)
                            .execute()

            );
        }else {
            row = JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate("update tour" +
                                    " set TourName=?,DiaDiem_ID=?,TrangThai=?,NgayTao=?,NgayKhoiHanh=?,NgayKetThuc=?,NoiKhoiHanh=?,SoLuong=?,PhuongTienDiChuyen=?,ImageURL=?,Description=?,TOUR_CATEGORY=? " +
                                    "where TOUR_ID= ?")

                            .bind(0, map.get("tourDetailName"))
                            .bind(1, map.get("tourDetailDiaDiem"))
                            .bind(2, Integer.parseInt(map.get("tourDetailTrangThai")))
                            .bind(3, map.get("tourDetailCreateDate"))
                            .bind(4, map.get("tourDetailStartDate"))
                            .bind(5, map.get("tourDetailEndDate"))
                            .bind(6, map.get("tourDetailStartDiaDiem"))
                            .bind(7, Integer.parseInt(map.get("tourDetailSoLuong")))
                            .bind(8, map.get("tourDetailVehicle"))
                            .bind(9, map.get("ImageUpload"))
                            .bind(10, map.get("tourDetailDescription"))
                            .bind(11, map.get("tourDetailCategory"))
                            .bind(12, id)
                            .execute()

            );
        }
        return  row!=1?false:true;
    }

    public boolean deleteTourDetailAll(String id){
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour_guide where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour_type where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour_detail_per_day where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour_voucher where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        return  true;
    }

    public boolean deleteTour(String id){
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour_guide where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour_type where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour_detail_per_day where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("delete from tour_voucher where TOUR_ID =?")
                        .bind(0, id)

                        .execute()
        );
        return  true;
    }

    public boolean createTourDetailGuide(List<String> list, String id){
        for (String st:
             list) {


            JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate("insert into tour_guide values(?,?)")
                            .bind(0, id)
                            .bind(1, st)
                            .execute()
            );
        }
        return true;
    }

    public boolean createTourDetailType(List<String[]> list, String id){
        for (String[] st:
                list) {


            JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate("insert into tour_type values(?,?,?)")
                            .bind(0, id)
                            .bind(1,Integer.parseInt(st[1]) )
                            .bind(2,Float.parseFloat(st[2] ))
                            .execute()
            );
        }
        return true;
    }

    public boolean createTourDetailDay(List<String[]> list, String id){
        for (String[] st:
                list) {


            JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate("insert into tour_detail_per_day values(?,?,?,?)")
                            .bind(0, id)
                            .bind(1,st[1])
                            .bind(2,Integer.parseInt(st[0]))
                            .bind(3,st[2])
                            .execute()
            );
        }
        return true;
    }

    public boolean createTourDetailVoucher(String voucherid, String id){


if (!voucherid.equals("none")) {
    JDBIConnector.get().withHandle(handle ->
            handle.createUpdate("insert into tour_voucher values(?,?)")
                    .bind(0, id)
                    .bind(1, voucherid)

                    .execute()
    );
}
        return true;
    }
}
