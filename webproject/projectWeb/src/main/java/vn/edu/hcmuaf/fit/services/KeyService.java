package vn.edu.hcmuaf.fit.services;

import vn.edu.hcmuaf.fit.DAO.KeyDAO;
import vn.edu.hcmuaf.fit.bean.PublicKey;

import java.io.FileOutputStream;
import java.security.*;
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

    public boolean lostKey(int publicId){
        try {
            KeyDAO.getInstance().lostKey(publicId, WARNING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isContantKey(String key){
        return KeyDAO.getInstance().isContantKey(key);
    }
    public static KeyService getInstance() {
        if (instance == null) instance = new KeyService();
        return instance;
    }
}
