package cz.atlascon.profidata.app;

import com.google.common.io.BaseEncoding;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

@Named
public class CryptoService {

    private final SecureRandom sr;
    private final String key;

    @Inject
    public CryptoService(@Value("${key:secretKey}") final String key) {
        this.key = key;
        try {
            this.sr = SecureRandom.getInstanceStrong();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String create(final String msg) throws Exception {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        bos.write(salt);

        final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        final KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 256);
        final SecretKey secretKey = new SecretKeySpec(secretKeyFactory.generateSecret(spec).getEncoded(), "AES");

        final Cipher aes = Cipher.getInstance("AES");
        aes.init(Cipher.ENCRYPT_MODE, secretKey);
        bos.write(aes.update(msg.getBytes(StandardCharsets.UTF_8)));
        bos.write(aes.doFinal());
        return BaseEncoding.base64Url().encode(bos.toByteArray());
    }

    public String decode(final String msg) throws Exception {
        final byte[] all = BaseEncoding.base64Url().decode(msg);
        final byte[] salt = Arrays.copyOfRange(all, 0, 16);
        final byte[] data = Arrays.copyOfRange(all, 16, all.length);

        final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        final KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 256);
        final SecretKey secretKey = new SecretKeySpec(secretKeyFactory.generateSecret(spec).getEncoded(), "AES");

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final Cipher aes = Cipher.getInstance("AES");
        aes.init(Cipher.DECRYPT_MODE, secretKey);
        bos.write(aes.update(data));
        bos.write(aes.doFinal());
        return bos.toString(StandardCharsets.UTF_8);
    }


}
