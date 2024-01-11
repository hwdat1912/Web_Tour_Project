package vn.edu.hcmuaf.fit.DAO;

import vn.edu.hcmuaf.fit.bean.Review;
import vn.edu.hcmuaf.fit.db.JDBIConnector;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewDAO {
    private static ReviewDAO instance;

    private ReviewDAO(){

    }

    public static ReviewDAO getInstance(){
        if (instance == null) instance = new ReviewDAO();
        return instance;
    }

    public List<Review> getListReview(String tour_id){
        List<Review> list = JDBIConnector.get().withHandle(h ->
                h.createQuery("select review.TOUR_ID,user.USER_ID ,user.FullName ,user.ImageURL ,review.Comment , review.NgayTao ,review.Category,review.Stars  from review inner join user on user.USER_ID =review.USER_ID where review.TOUR_ID  = ?")
                        .bind(0, tour_id)
                        .mapToBean(Review.class)
                        .stream()
                        .collect(Collectors.toList())
        );

        list.sort((o1, o2) -> o1.getNgayTao().getTime() >= o2.getNgayTao().getTime()?-1:1);
        return list ;
    }

    public boolean createReview(String tour_id,String user_id,String comment,String ngayTao,String category,int star){
       Object o = JDBIConnector.get().withHandle(h ->
                h.createUpdate("insert into review values (?,?,?,?,?,?)")
                        .bind(0,tour_id)
                        .bind(1,user_id)
                        .bind(2,comment)
                        .bind(3,ngayTao)
                        .bind(4,category)
                        .bind(5,star)
                        .execute()
        );
      return o==null?false:true;
    }

    public static void main(String[] args) {
       boolean b =  ReviewDAO.getInstance().createReview("TOUR0001","guide1","abc","2022-12-05","all",4);
       System.out.println(b);
    }
}
