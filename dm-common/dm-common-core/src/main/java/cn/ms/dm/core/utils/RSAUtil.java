package cn.ms.dm.core.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {

    public static final String KEY_ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    // 1024 bits 的 RSA 密钥对，最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;

    // 1024 bits 的 RSA 密钥对，最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;

    // 生成密钥对
    public static Map<String, Object> initKey(int keysize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 设置密钥对的 bit 数，越大越安全
        keyPairGen.initialize(keysize);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 获取公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    // 获取公钥字符串
    public static String getPublicKeyStr(Map<String, Object> keyMap) {
        // 获得 map 中的公钥对象，转为 key 对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    // 获取私钥字符串
    public static String getPrivateKeyStr(Map<String, Object> keyMap) {
        // 获得 map 中的私钥对象，转为 key 对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    // 获取公钥
    public static PublicKey getPublicKey(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyByte = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    // 获取私钥
    public static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        byte[] privateKeyByte = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * BASE64 编码返回加密字符串
     *
     * @param key 需要编码的字节数组
     * @return 编码后的字符串
     */
    public static String encryptBASE64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }

    /**
     * BASE64 解码，返回字节数组
     *
     * @param key 待解码的字符串
     * @return 解码后的字节数组
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * 公钥加密
     *
     * @param text         待加密的明文字符串
     * @param publicKeyStr 公钥
     * @return 加密后的密文
     */
    public static String encrypt(String text, String publicKeyStr) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyStr));
            byte[] tempBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(tempBytes);
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + text + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param secretText    待解密的密文字符串
     * @param privateKeyStr 私钥
     * @return 解密后的明文
     */
    public static String decrypt(String secretText, String privateKeyStr) {
        try {
            // 生成私钥
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyStr));
            // 密文解码
            byte[] secretTextDecoded = Base64.getDecoder().decode(secretText.getBytes(StandardCharsets.UTF_8));
            byte[] tempBytes = cipher.doFinal(secretTextDecoded);
            return new String(tempBytes);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + secretText + "]时遇到异常", e);
        }
    }


    public static void main(String[] args) throws Exception {
//        Map<String, Object> stringObjectMap = initKey(2048);
//        String publicKeyStr = getPublicKeyStr(stringObjectMap);
//        String privateKeyStr = getPrivateKeyStr(stringObjectMap);
//        System.out.println(publicKeyStr);
//        System.out.println(privateKeyStr);
//        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAifik7jGu9gVU+xZXF7VlffZ+h9RgEccGrHEiQ+tDFjTbNBg9ou8PCq2sBkshFr3qIkd4SLJooXymjff97OgBamjKD01ZG3GOn9TVe7jdV3r0hDXofIOUso22p9DXI5NQnNv9WRmMF/dwlBh679GSrt3sCyNJExPSzBCqVb5e19UH/tDJwRPsgHII+I+Yg73gD790OCMkbLWD3484+17vje/LZfKY6MiA11tMo1aeaOd2ZGAvRpuOdPZF8WBFnKcIjBUaFhYHhrkxvDNWs8rXuB/gGZkhoujSnem1hERWnogX6IFe7EStD5xJt0NqTmwG8XFnyCF3SAD3XxAYV9CAHwIDAQAB";
//        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJ+KTuMa72BVT7FlcXtWV99n6H1GARxwascSJD60MWNNs0GD2i7w8KrawGSyEWveoiR3hIsmihfKaN9/3s6AFqaMoPTVkbcY6f1NV7uN1XevSENeh8g5Syjban0Ncjk1Cc2/1ZGYwX93CUGHrv0ZKu3ewLI0kTE9LMEKpVvl7X1Qf+0MnBE+yAcgj4j5iDveAPv3Q4IyRstYPfjzj7Xu+N78tl8pjoyIDXW0yjVp5o53ZkYC9Gm4509kXxYEWcpwiMFRoWFgeGuTG8M1azyte4H+AZmSGi6NKd6bWERFaeiBfogV7sRK0PnEm3Q2pObAbxcWfIIXdIAPdfEBhX0IAfAgMBAAECggEAEjqE+pNwh5XyFwUUl0/4C3RjCJRz55AMFFcTgosKtLG3Dw2aLHS5NV1MumgirwaDGz77aAoU7iux4FVAzz6329SMcwkdGhXJai83JWl/P5eXHACjVBAZDUjRNnV5IdIiIcH5malZFlwyxjOVyn7oildNCwurvpbX5ZjYvbYGgdgJ1MPgBgZQ6UbEoPs+nbmbFtaUQEsEg6m+pDMQ8ZwWBd5S4dolBIuw2h4+vvJc3Nc8sE1qpFPAu/fFwHT3jTyuX7g/0HahElXt6OIgUmMiAHY+EyhZujmEPy0IMhIbtASlEmwJsIdnxmsDECpFy29lucSaF1EmkkPkUt3EmJIjwQKBgQC6b5mitSEmQcUXKWWIOGnEhzn9+w2K5/C1c87p9MyY+91ajTnL2u1aodBi08fDvItRFyhsWmBjKZ/GE/DfcuZK+BSl+71LK4UnAzANashBV67O+OnO3yrQv22z/O+0bfTt5PVKBc5BpPWgZoMqf93bDDp6JnQGK1RyNdh+feRFXQKBgQC9c7BFG6/edhxpBpgPZfh4FKcxmb7PaB8rg+e8PcUfj1d79UWRQHOgty+CiCxYw+Quii3l4If4IlM3UtRRDA/euCbxNEC48AH4TwxRRcEY/ZEKitpOMcng+LNzUv2ABxf7DTZCQc7fEDc2vi8A9W27qzJmJNqF53YpMGioF4knqwKBgGMBbvI0BDWfyb2CE1QiuTBPq6FP2CJDB9YkeOZPRPOdFTDIPMAgM9nkgpho5PXyQ329sY7Q88zVRu7WmhzflKKNWdHHfP/VTbGpBLSrV0sz+DYbg3aDluYiMmzZzqXfmqlW5Ff5+01q/7G9nrxMCRIRTWCe2RCJpB++roiF7FpVAoGAaiDpyP0Gm1+c8LTR8CZWd6ZSNvYCrxuMAouFAK21xMU+4+D1lLX/UDPueHgiJ6OtKukbZyBN6xIHfgF3kONp7IFKx2pXK4LAB8L4inrB/mblnntEafN4PP/1PxBplBPlOi0fjSRiaBEkbRVDLY1PaIcx14ijpcmRJufIaQmuOrsCgYB9KxBuoKq7YqFcPZEWWVHopQ4s6XTq7/GxbdgKMZmpxOOUq1HY4v1F+qfv6BiMogZL1tkJZg999WAhfMplC+uLoXAXEGWpBXNdROWBnzhm8nl/gaI4WsbmtXbT/s2JYi/uPzq+/s23CpROAHTlWHdv4i/24AHfnTMyUWhIaC5Wng==";
//        String encrypt = encrypt("admin@123", publicKey);
//        System.out.println(encrypt);
//        String decrypt = decrypt(encrypt, privateKey);
//        System.out.println(decrypt);
    }
}

