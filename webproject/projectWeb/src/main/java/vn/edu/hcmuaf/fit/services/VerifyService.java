package vn.edu.hcmuaf.fit.services;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class VerifyService {
    private static VerifyService instance;

    private VerifyService() {
    }

    private String encrypt(String input,PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] ouptut = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(ouptut);
    }

    private String decrypt(String input,PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IllegalArgumentException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] ouptut = cipher.doFinal(Base64.getDecoder().decode(input));
        return new String(ouptut, StandardCharsets.UTF_8);
    }

    // Phuong thuc kiem tra xem private key va public co phu hop voi nhau hay khong
    public boolean check(PublicKey publicKey,PrivateKey privateKey) {
        String messageTest = "Messsgae Check Private Key String";

        try {
            String encrypt = encrypt(messageTest, publicKey);

            String deString = decrypt(encrypt, privateKey);

            return messageTest.equals(deString);

        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            return false;

        }

    }

    //Phuong thuc ki hoa don
    //fileStoreVerify la thu muc chua file sinh ra khi ki
    //filename la file can ki
    public void sign(String filename, String fileStoreVerify, PrivateKey priKey) {
        try {


            FileInputStream fis = null;
            // ********************************
            // Ký số (Sign)***************************
            // Tạo đối tượng signer
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(priKey, new SecureRandom());
            // Chọn file để thực hiện ký số


            fis = new FileInputStream(filename);
//			byte byteFile[] = new byte[fis.available()];
            byte byteFile[] = fis.readAllBytes();


            // Chèn message vào đối tượng signer
            signer.update(byteFile);
            byte[] bsign = signer.sign();
            // Lưu chữ ký số
            FileOutputStream fos = new FileOutputStream(fileStoreVerify);
            fos.write(bsign);
            // *******************************
            fis.close();
            fos.close();
            System.out.println("Sign document successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Phuong thuc verify hoa don
    //Verify la file sinh ra khi ki
    //fileName la file can kiem tra
    public boolean verify(String fileName, String verify, String publicKey) {
        try {
            // Nạp public key

            FileInputStream fis = null;
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            // Tạo public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);

            // Khởi tạo đối tượng Signature
            Signature s = Signature.getInstance("SHA1withRSA");
            s.initVerify(pubKey);
            // Chọn file để kiểm chứng
//			String filename = "c:/file/test.doc";
            fis = new FileInputStream(fileName);
//			byte byteFile[] = new byte[fis.available()];
            byte[] byteFile = fis.readAllBytes();


            fis.close();
            // Nạp message vào đối tượng Signuture
            s.update(byteFile);
            // Kiểm chứng chữ ký trên Message
            // Nạp chữ ký signature từ file
            fis = new FileInputStream(verify);
//			byte[] bsign = new byte[fis.available()];

            byte[] bsign = fis.readAllBytes();


            fis.read(bsign);
            fis.close();
            // Kết quả kiểm chứng
            boolean result = s.verify(bsign);

            return result;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static VerifyService getInstance() {
        if (instance == null) instance = new VerifyService();
        return instance;
    }




}
