package vn.edu.hcmuaf.fit.services;

import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAService {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public void generate() {
        try {
            SecureRandom sr = new SecureRandom();

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024, sr);
            KeyPair keys = kpg.generateKeyPair();
            // Save private key
            privateKey = keys.getPrivate();
            // Save public key
            publicKey = keys.getPublic();
            System.out.println("\"Register key successfully\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void generatePublicKeyFromPrivateKey(PKCS8EncodedKeySpec spec) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = factory.generatePrivate(spec);

        RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey)privateKey;
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent()));

        this.publicKey = publicKey;
    }


    public PublicKey importPublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        setPublicKey(publicKey);

        return publicKey;
    }

    public String exportPublicKey() {
        return Base64.getEncoder().encodeToString(this.publicKey.getEncoded());
    }

    public static void main(String[] args) {
        RSAService rsa = new RSAService();
        rsa.generate();

        System.out.println(Base64.getEncoder().encodeToString(rsa.getPublicKey().getEncoded()));
    }

}
