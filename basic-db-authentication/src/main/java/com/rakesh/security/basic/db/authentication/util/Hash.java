package com.rakesh.security.basic.db.authentication.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by rakeshcherukuri on 16/04/16.
 */
public class Hash {

  public static String createWildFlyPasswordHash(String userName, String password) {
    try {
      String str = String.join("", userName, ":ApplicationRealm:", password);

      MessageDigest digest = MessageDigest.getInstance("MD5");

      return String.format("%x", new BigInteger(1, digest.digest(str.getBytes())));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
