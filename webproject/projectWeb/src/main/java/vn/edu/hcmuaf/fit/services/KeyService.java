package vn.edu.hcmuaf.fit.services;

import vn.edu.hcmuaf.fit.DAO.KeyDAO;
import vn.edu.hcmuaf.fit.bean.PublicKey;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

public class KeyService {
    private static KeyService instance;
    public static final  int DISABLE = 0;
    public static final  int ENABLE = 1;
    public static final  int WARNING = -1;

    public KeyService() {

    }

    public List<PublicKey> getPublicKeyByStatus(String userId,int status){
        return KeyDAO.getInstance().getPublicKeyByStatus(userId,status);
    }

    public List<PublicKey> getPublicKeyByUserId(String userId){
        return KeyDAO.getInstance().getPublicKeyByUserId(userId);
    }

    public void insertKey(String userId,String p_key,int status){
        KeyDAO.getInstance().insertKey(userId,p_key,status);
    }

    public int lostKey(int publicId){
        return KeyDAO.getInstance().lostKey(publicId, WARNING);
    }

    public String getOnePublicKeyBySatus(String userId){
        return KeyDAO.getInstance().getOnePublicKeyByStatus(userId, ENABLE);
    }

    public String getOnePublicKeyIdBySatus(String userId){
        return KeyDAO.getInstance().getOnePublicKeyIdByStatus(userId, ENABLE);
    }

    public boolean isContantKey(String key){
        return KeyDAO.getInstance().isContantKey(key);
    }

    public List<PublicKey> getAllPublicKey() {
        // Triển khai phương thức này để lấy tất cả khóa công khai từ DAO
        return KeyDAO.getInstance().getAllPublicKey();
    }

    public String getKeyById(String id){
        return KeyDAO.getInstance().getOnePublicKeyById(id);
    }

    public static KeyService getInstance() {
        if (instance == null) instance = new KeyService();
        return instance;
    }


    public void disableKey(int publicKeyId) {
        KeyDAO.getInstance().disableKey(publicKeyId);
    }

    public int getPublicIdByStatus(String userId){
        return KeyDAO.getInstance().getPublicIdByStatus(userId, ENABLE);
    }
    public java.security.PublicKey convertStringToPublicKey(String publicKeyString) {
        if (publicKeyString != null && !publicKeyString.isEmpty()) {
            try {
                byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);

                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return keyFactory.generatePublic(keySpec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public PrivateKey convertFileToPrivateKey(InputStream fileContent) {
        try{
            byte[] bytes = fileContent.readAllBytes();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
