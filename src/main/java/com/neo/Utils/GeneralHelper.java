package com.neo.Utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by Praveen Gupta on 5/13/2017.
 */
public class GeneralHelper {

    public static boolean isNotEmpty(String string) {
        if (string == null) return false;
        else if (string.isEmpty()) return false;
        return true;
    }
    /*public static String urlEncode(String param){
        URLEncoder.encode()
    }*/

    public static String encodeString(String s) {
        byte[] encodedBytes = Base64.encodeBase64(s.getBytes());
        return new String(encodedBytes);
    }

    public static int OTPGenerator() {
        Random rand = new Random();
        int value = 0, x;
        for (int i = 0; i < 4; i++) {
            x = rand.nextInt(9) + 1;
            value = value * 10 + x;
        }
        return value;
    }

    public static float round2(double num) {
        return ((int) (num * 100)) / 100f;
    }

    public static String url(String scheme, String authority, String path, HashMap<String, String> query) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(authority);
        if (isNotEmpty(path))
            uriBuilder.setPath(path);
        if (query != null)
            for (Map.Entry<String, String> e : query.entrySet())
                uriBuilder.addParameter(e.getKey(), e.getValue());

        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String randomToken() {
        return UUID.randomUUID().toString();
    }

    public static int sizeOf(List list){
        if(list==null) return 0;
        return list.size();
    }
}
