package br.com.bsbapps.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Andre Becklas on 30/11/2016.
 *
 * Retorna um token para validação de autenticidade da aplicação junto aos serviços
 */

public class SecurityToken {
    private String key;

    public SecurityToken() {
        // Captura data atual e gera hash de autenticidade
        DateHandler date = new DateHandler();
        key = md5(md5(("dormammu".concat(date.getStringDate("ddMMyyyy")))));
        if (key==null) key="";
    }

    public String getKey(){
        return key;
    }

    // Método md5
    // converte a string in em um hash MD5
    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }
}
