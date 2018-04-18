package com.yin.myapp;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 key= serialNumber and value= 716419760
 key= SHA1         and value= 5E:E8:F3:30:21:24:5C:19:B2:FF:1E:09:E4:B5:A3:1C:14:BA:42:F3
 key= signName     and value= SHA256WithRSA
 key= MD5          and value= 95:DA:61:D9:FA:1F:98:EE:E9:18:0F:06:F0:02:7C:2B
 key= endTime      and value= 2042-11-14 21:54:26
 key= startTime    and value= 2017-11-20 21:54:26
 key= subjectDN    and value= CN=zhijieYin, OU=owner, O=owner, L=chengdu, ST=sichuan, C=86
 key= sigAlgOID    and value= 1.2.840.113549.1.1.11
 key= pubKey       and value= OpenSSLRSAPublicKey{modulus=842f40bd87cca95ef019df1518aece9f6853fdf04f9d1c84eb58c225b20fd492658d40e8b728109b90151314467354c583ff61855ecdb2c4b42741ff326e549c54d9a16d150a88e4de5c4083cfc00897b65bfbe979cd4fcf34c7e99c600ba63e6a5667bf86f1832cee8cc9372dd42a3cdda99f61b472a28c3262131461bc90c7f8fe3f865a32aba63f5eb27c167f02869b2b52f1d13d55be6af4a7d45b6eb8d783d48d8cbc58182fd82183bf45cf6a63eef9a4de84d8101746dde139272f7ef630fc882e39d339b3f0510debb2b4382f07fb3da387e80237731c359ae2ef599422ddbf04ea8e723c9a11e6f30ce97bdf8092c537cbc49244e11721b364ce7f13,publicExponent=10001}
 key= SHA256       and value= 22:04:2A:B6:EF:5E:39:F0:D7:4C:37:CF:EF:26:FD:24:A0:6B:5B:56:10:41:1A:25:F5:3F:FC:46:38:CC:09:8D
 */

public class Signature {

    public static Map<String, String> getSignatureInfo(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            android.content.pm.Signature[] signs = packageInfo.signatures;
            android.content.pm.Signature sign = signs[0];
            byte[] signature = sign.toByteArray();

            X509Certificate cert = parseSignature(signature);

            map.put("signName", cert.getSigAlgName());

            map.put("pubKey", cert.getPublicKey().toString());

            map.put("serialNumber", cert.getSerialNumber().toString());

            map.put("sigAlgOID", cert.getSigAlgOID());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("startTime", sdf.format(cert.getNotBefore()));
            map.put("endTime", sdf.format(cert.getNotAfter()));

            map.put("subjectDN", cert.getSubjectDN().toString());

            map.put("MD5", getMessageDigest("MD5", signature));

            map.put("SHA1", getMessageDigest("SHA1", signature));

            map.put("SHA256", getMessageDigest("SHA256", signature));

            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                Log.i("",String.format("%s=%s", entry.getKey(), entry.getValue()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static String getMessageDigest(String instance, byte[] signature) {
        String sinfo = null;
        try {
            MessageDigest md = MessageDigest.getInstance(instance);

            md.update(signature);

            byte[] digest = md.digest();

            sinfo = toHexString(digest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sinfo;
    }

    public static X509Certificate parseSignature(byte[] signature) {
        X509Certificate cert = null;
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return cert;
    }

    private static void byte2hex(byte b, StringBuffer buf) {

        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',

                '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        int high = ((b & 0xf0) >> 4);

        int low = (b & 0x0f);

        buf.append(hexChars[high]);

        buf.append(hexChars[low]);

    }


    /**
     * Converts a byte array to hex string
     */
    private static String toHexString(byte[] block) {

        StringBuffer buf = new StringBuffer();


        int len = block.length;


        for (int i = 0; i < len; i++) {

            byte2hex(block[i], buf);

            if (i < len - 1) {

                buf.append(":");

            }

        }

        return buf.toString();

    }
}
