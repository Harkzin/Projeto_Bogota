package web.support.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class CryptographySHA256 {
    private MessageDigest messageDigest;
    private Base64 encoder;
    private static CryptographySHA256 instance;
    private Logger logger = Logger.getLogger(this.getClass());

    private CryptographySHA256() {
        try {
            this.messageDigest = MessageDigest.getInstance("SHA-256");
            this.encoder = new Base64();
        } catch (NoSuchAlgorithmException var2) {
            this.logger.error(var2);
        }

    }

    public static CryptographySHA256 getInstance() {
        if (instance == null) {
            instance = new CryptographySHA256();
        }

        return instance;
    }

    public String encrypt(String str) {
        if (str == null) {
            return null;
        } else {
            byte[] hash = this.messageDigest.digest(str.getBytes());
            return new String(this.encoder.encode(hash));
        }
    }


    public static String decrypt(String str) {
        CryptographySHA256 cpto = new CryptographySHA256();
        String retorno = "";


        for(Integer i=0;i<999999;i++){
            String token = cpto.encrypt(i.toString());
            if (token.equals(str)){
                retorno = i.toString();
                break;
            }
        }
        return retorno;

    }

    public static void main(String[] args) {

    }
}