package vn.edu.hcmuaf.fit.DAO;

import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import vn.edu.hcmuaf.fit.bean.PublicKey;
import vn.edu.hcmuaf.fit.db.JDBIConnector;

import java.util.List;
import java.util.stream.Collectors;

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
            return handle.createQuery("SELECT public_key.public_id,public_key.User_id,public_key.p_key,public_key.date_create,public_key.status FROM public_key WHERE \n" +
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
            return handle.createQuery("SELECT public_key.public_id,public_key.User_id,public_key.p_key,public_key.date_create,public_key.status FROM public_key WHERE \n" +
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
            return handle.createQuery("SELECT public_key.public_id,public_key.User_id,public_key.p_key,public_key.date_create,public_key.status FROM public_key WHERE \n" +
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
            return handle.createQuery("SELECT public_key.public_id,public_key.User_id,public_key.p_key,public_key.date_create,public_key.status FROM public_key WHERE \n" +
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
    public static void main(String[] args) {
        KeyDAO dao = KeyDAO.getInstance();

        List<PublicKey> list = dao.getPublicKeyByUserId("User34567");

        System.out.println(list);

//        List<PublicKey> users = JDBIConnector.get().withHandle(handle -> {
//            return handle.createQuery("select * from public_key")
//                    .registerRowMapper(ConstructorMapper.factory(PublicKey.class))
//                    .mapTo(PublicKey.class)
//                    .list();
//        });
//        System.out.println(users);
    }
}
