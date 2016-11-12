package com.navispeed.greg.requieris;

import com.google.common.primitives.Longs;
import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * Created by grimaceplume on 26/10/2016.
 */
public class GoogleAuthenticator {

  private final String secret;
  private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";


  public GoogleAuthenticator(String secret) {
    this.secret = secret;
  }

  private byte[] calculateRFC2104HMAC(Long data, byte[] key)
      throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
  {
    Mac mac = Mac.getInstance("HmacSHA1");
    SecretKeySpec signingKey = new SecretKeySpec(key, "");
    mac.init(signingKey);
    return mac.doFinal(Longs.toByteArray(data));
  }

  public Integer getToken() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    byte[] key = new Base32().decode(secret.toUpperCase().getBytes());
    Long Epoch = (System.currentTimeMillis() / 1000L) / 30L;
    try {
      byte[] hash = calculateRFC2104HMAC(Epoch, key);
      char offset = (char)(hash[hash.length - 1] & 0x0f);
      byte truncatedHash2[] = {0, 0, 0, 0};
      take4Bytes(hash, offset, truncatedHash2);
      ByteBuffer wrap = ByteBuffer.wrap(truncatedHash2);
      Integer wrapLong = wrap.getInt();
      return (wrapLong) % 1000000;
    } catch (NoSuchAlgorithmException | InvalidKeyException ignored) {
    }
    return -1;
  }

  private void take4Bytes(byte[] hash, char offset, byte[] truncatedHash2) {
    truncatedHash2[0] = (byte)(hash[offset] & 0b01111111);
    truncatedHash2[1] = hash[offset + 1];
    truncatedHash2[2] = hash[offset + 2];
    truncatedHash2[3] = hash[offset + 3];
  }
}
