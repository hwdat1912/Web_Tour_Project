package vn.edu.hcmuaf.fit.services;

import vn.edu.hcmuaf.fit.DAO.BookingDAO;

import vn.edu.hcmuaf.fit.DAO.VerifyDAO;
import vn.edu.hcmuaf.fit.bean.Booking;
import vn.edu.hcmuaf.fit.bean.User;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class VerifyService {
    private static VerifyService instance;

    public  static final int  VERIFY_SUCCESS = 1;
    public  static final int  NONE_VERIFY = 0;

    public  static final int  VERIFY_CHANGE = -1;



    private VerifyService() {
    }

    public boolean changeVerifyInDb(Booking booking,String fileBooking){
        BufferedReader read = null;
        try {
            read = new BufferedReader(new FileReader(fileBooking));
            String bookindID = read.readLine();
            String tourID =read.readLine().trim();
            String userID =read.readLine().trim();
            String hoTen =read.readLine().trim();
            String email = read.readLine().trim();
            String phone =read.readLine().trim();
            String diachi =read.readLine().trim();
            int slVeNguoiLon =Integer.parseInt(read.readLine());
            int slVeTreEm = Integer.parseInt(read.readLine());
            String soLuong =read.readLine();
            String ngayTao =read.readLine();
////            print.println(booking.getTongTien());
            String descreption =read.readLine().trim();
            System.out.println(bookindID +"-" +tourID +"-" + userID +"-" + hoTen +"-" +email +"-" +phone+"-" +diachi+"-" +
                    slVeNguoiLon +"-" +slVeTreEm +"-" +descreption);

            return  (bookindID.equals(booking.getBOOKING_ID()) && tourID.equals(booking.getTOUR_ID()) &&
                    userID.equals(booking.getUSER_ID()) && hoTen.equals(booking.getHoTen().trim())
                    && email.equals(booking.getEmail().trim()) && phone.equals(booking.getPhone().trim())
                    && diachi.equals(booking.getDiaChi().trim()) && (slVeNguoiLon ==booking.getSOLUONG_VENGUOILON())
                    && (slVeTreEm == booking.getSOLUONG_VETREEM() && descreption.equals(booking.getDescription().trim()))
                    );


        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
            return false;
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return  false;
        }
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

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    //Phuong thuc ki hoa don
    //fileStoreVerify la thu muc chua file sinh ra khi ki
    //filename la file can ki
    public void sign(String filename, String fileStoreVerify, PrivateKey privateKey) {
        try {


            FileInputStream fis = null;
            // ********************************
            // Ký số (Sign)***************************
            // Tạo đối tượng signer
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(privateKey, new SecureRandom());
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

    public void insertVerify(String public_id,String booking_id	){
         VerifyDAO.getInstance().insertVerify(public_id,booking_id);
    }

//    public boolean isVerify(String booking_id){
//        return  VerifyDAO.getInstance().isVerify(booking_id);
//    }

    public static VerifyService getInstance() {
        if (instance == null) instance = new VerifyService();
        return instance;
    }

    public  String getKeyIdByBookingId(String booking){
        return VerifyDAO.getInstance().getKeyIdByBookingId(booking);
    }

    public  void updateVerify(String public_key,String booking_id){
        VerifyDAO.getInstance().update(public_key,booking_id);
    }

    public static void main(String[] args) throws FileNotFoundException {
        VerifyService verifyService = VerifyService.getInstance();
        KeyService keyService = KeyService.getInstance();
        String publicKeyString = keyService.getOnePublicKeyBySatus("User54926");
        PublicKey publicKey = keyService.convertStringToPublicKey(publicKeyString);

        InputStream fileContent = new FileInputStream("/Users/hidroxit/Downloads/privateKey.bin");
        PrivateKey privateKey = keyService.convertFileToPrivateKey(fileContent);
        boolean keyCompatibility = verifyService.check(publicKey,privateKey);
        System.out.println(publicKey);
        System.out.println(privateKey);
        BookingDAO bookingDAO = BookingDAO.getInstance();
        Booking booking = bookingDAO.getBookingById("BOOKING-1289403724");
        System.out.println(booking);

        if (keyCompatibility) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
        }

    }




}
