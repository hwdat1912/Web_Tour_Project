package vn.edu.hcmuaf.fit.DAO;

import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import vn.edu.hcmuaf.fit.bean.PublicKey;
import vn.edu.hcmuaf.fit.db.JDBIConnector;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static vn.edu.hcmuaf.fit.services.KeyService.DISABLE;
import static vn.edu.hcmuaf.fit.services.KeyService.ENABLE;

public class KeyDAO {
    private static KeyDAO instance;
    private KeyDAO(){}

    public static KeyDAO getInstance(){
        if (instance ==null) instance = new KeyDAO();
        return instance;
    }

    public List<PublicKey> getAllPublicKey(){
//        List<PublicKey> list = JDBIConnector.get().withHandle(handle ->
//                handle.createQuery("select * from public_key")
//
//                        .mapToBean(PublicKey.class)
//                        .stream()
//                        .collect(Collectors.toList())
//        );

        List<PublicKey> list = JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("select * from public_key ORDER BY  public_key.date_create DESC")
                    .registerRowMapper(ConstructorMapper.factory(PublicKey.class))
                    .mapTo(PublicKey.class)
                    .list();
        });
        return list;
    }

    public List<PublicKey> getPublicKeyByStatus(int status){
        List<PublicKey> list = JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT public_key.public_id,public_key.User_id,public_key.p_key,public_key.date_create,public_key.date_report,public_key.status FROM public_key WHERE \n" +
                            "status = ? ORDER BY  public_key.date_create DESC")
                    .bind(0,status)
                    .registerRowMapper(ConstructorMapper.factory(PublicKey.class))
                    .mapTo(PublicKey.class)
                    .list();
        });
        return list;
    }

    public List<PublicKey> getPublicKeyByStatus(String userId,int status){
//        List<PublicKey> list = JDBIConnector.get().withHandle(handle ->
//                handle.createQuery("select * from public_key")
//
//                        .mapToBean(PublicKey.class)
//                        .stream()
//                        .collect(Collectors.toList())
//        );

        List<PublicKey> list = JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT public_key.public_id,public_key.User_id,public_key.p_key,public_key.date_create,public_key.date_report,public_key.status FROM public_key WHERE \n" +
                            "status = ? and User_id = ? ORDER BY  public_key.date_create DESC")
                    .bind(0,status)
                    .bind(1,userId)
                    .registerRowMapper(ConstructorMapper.factory(PublicKey.class))
                    .mapTo(PublicKey.class)
                    .list();
        });
        return list;
    }


    public List<PublicKey> getPublicKeyByUserId(String userId){


        List<PublicKey> list = JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT public_key.public_id,public_key.User_id,public_key.p_key,public_key.date_create,public_key.date_report,public_key.status FROM public_key WHERE \n" +
                            "User_id = ? ORDER BY  public_key.date_create DESC")
                    .bind(0,userId)
                    .registerRowMapper(ConstructorMapper.factory(PublicKey.class))
                    .mapTo(PublicKey.class)
                    .list();
        });
        return list;
    }

    public boolean isContantKey(String key){
        List<PublicKey> list = JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT public_key.public_id,public_key.User_id,public_key.p_key,public_key.date_create,public_key.date_report,public_key.status FROM public_key WHERE \n" +
                            "p_key = ? ")
                    .bind(0,key)
                    .registerRowMapper(ConstructorMapper.factory(PublicKey.class))
                    .mapTo(PublicKey.class)
                    .list();
        });
        return list.size() > 0 ? true :false;
    }

    public void insertKey(String userId,String p_key,int status){
        JDBIConnector.get().withHandle(handle -> {
            return handle.createUpdate("INSERT INTO public_key(User_id,p_key,status) VALUES (?,?,?)")
                    .bind(0,userId)
                    .bind(1,p_key)
                    .bind(2,status)
                    .execute();
        });

    }


    public int lostKey(int publicId, int status) {
        return JDBIConnector.get().withHandle(handle -> {
            int currentStatus = handle.createQuery("SELECT status FROM public_key WHERE public_id = ?")
                    .bind(0, publicId)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0); // Assuming default value is 0 if not found

            if (currentStatus == -1) {
                return -1;
            } else {
                // Status is 1, update the key
                return handle.createUpdate("UPDATE public_key " +
                                "SET status = ?, date_report = NOW() " +
                                "WHERE public_id = ?")
                        .bind(0, status)
                        .bind(1, publicId)
                        .execute();
            }
        });
    }

    public String getOnePublicKeyByStatus(String userId, int status){
        return JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT public_key.p_key FROM public_key " +
                            "WHERE status = ? AND User_id = ? ORDER BY  public_key.date_create DESC LIMIT 1")
                    .bind(0, status)
                    .bind(1, userId)
                    .mapTo(String.class)
                    .findOne()
                    .orElse(null);
        });
    }

    public String getOnePublicKeyById(String id){
        return JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT public_key.p_key FROM public_key " +
                            "WHERE public_id =? ORDER BY  public_key.date_create DESC LIMIT 1")
                    .bind(0, id)
                    .mapTo(String.class)
                    .findOne()
                    .orElse(null);
        });
    }

    public String getOnePublicKeyIdByStatus(String userId, int status){
        return JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT public_key.public_id FROM public_key " +
                            "WHERE status = ? AND User_id = ? ORDER BY  public_key.date_create DESC LIMIT 1")
                    .bind(0, status)
                    .bind(1, userId)
                    .mapTo(String.class)
                    .findOne()
                    .orElse(null);
        });
    }

    public int getPublicIdByStatus(String userId, int status){
        return JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT public_id FROM public_key " +
                            "WHERE status = ? AND User_id = ? ORDER BY date_create DESC LIMIT 1;")
                    .bind(0, status)
                    .bind(1, userId)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(-1);
        });
    }

    public static void main(String[] args) {
        KeyDAO dao = KeyDAO.getInstance();

//        List<PublicKey> list = dao.getPublicKeyByUserId("User34567");
//
//        System.out.println(list);

//        List<PublicKey> users = JDBIConnector.get().withHandle(handle -> {
//            return handle.createQuery("select * from public_key")
//                    .registerRowMapper(ConstructorMapper.factory(PublicKey.class))
//                    .mapTo(PublicKey.class)
//                    .list();
//        });
//        System.out.println(users);

        System.out.println(KeyDAO.getInstance().getOnePublicKeyById("75"));
    }


    public void disableKey(int publicKeyId) {
        JDBIConnector.get().withHandle(handle -> {
            return handle.createUpdate("UPDATE public_key SET status = ? WHERE public_id = ?")
                    .bind(0, DISABLE)
                    .bind(1, publicKeyId)
                    .execute();
        });
    }
    public void enableKey(int publicKeyId) {
        JDBIConnector.get().withHandle(handle -> {
            return handle.createUpdate("UPDATE public_key SET status = ? WHERE public_id = ?")
                    .bind(0, ENABLE)
                    .bind(1, publicKeyId)
                    .execute();
        });
    }

    public LocalDateTime getDateReportById(int publicId) {
        try {
            Optional<LocalDateTime> dateReport = JDBIConnector.get().withHandle(handle -> {
                return handle.createQuery("SELECT date_report FROM public_key WHERE public_id = ?")
                        .bind(0, publicId)
                        .mapTo(LocalDateTime.class)
                        .findOne();
            });

            return dateReport.orElse(null);
        } catch (JdbiException e) {
            // Handle the exception, log or throw a custom exception as needed
            e.printStackTrace();
            return null;
        }
    }

}
