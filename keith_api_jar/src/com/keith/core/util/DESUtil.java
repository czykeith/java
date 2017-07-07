package com.keith.core.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
 
public class DESUtil {
	private final String DES="DES";
    private final String CHARSETNAME = "UTF-8";// 编码
    private final String ALGORITHM = "DES/CBC/PKCS5Padding";
    private static DESUtil util=null;
    private DESUtil(){}
    public static DESUtil getInstances(){
    	if(util==null){
    		util=new DESUtil();
    	}
    	return util;
    }
 
    /**
     * 对str进行DES加密
     * 
     * @param str
     * @return
     */
    public  String getEncryptString(String str,byte [] keyBytes) {
        try {
        	DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			//密钥
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// 偏移量
            byte[] bytes = str.getBytes(CHARSETNAME);
            // 偏移量
            IvParameterSpec iv = new IvParameterSpec(keyBytes);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,iv);
            byte[] doFinal = cipher.doFinal(bytes);
            return hexEncode(doFinal);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
    	System.out.println(DESUtil.getInstances().getEncryptString("18801042496", "27185419".getBytes()));
		System.out.println(DESUtil.getInstances().getDecryptString("8300f8a8576cc8e6d87167912ece17f5", "27185419".getBytes()));
	}
 
    /**
     * 对str进行DES解密
     * 
     * @param str
     * @return
     */
    public  String getDecryptString(String str,byte [] keyBytes) {
    	String res=null;
        try {
        	DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			//密钥
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// 偏移量
            byte[] bytes =hexDecode(str);
            // 偏移量
            IvParameterSpec iv = new IvParameterSpec(keyBytes);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey,iv);
            byte[] doFinal = cipher.doFinal(bytes);
            res=new String(doFinal, CHARSETNAME);
        } catch (Exception e) {
         e.printStackTrace();
        }
        return res;
        
    }
    /**
	 * Hex编码, byte[]->String.
	 */
	public String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码, String->byte[].
	 */
	public byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}	
}