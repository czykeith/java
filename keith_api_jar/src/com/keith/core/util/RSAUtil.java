package com.keith.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;






import sun.misc.BASE64Decoder;

/**
 * rsa加密
 * @author keith
 * 2015年12月19日 20:31:35
 */
public class RSAUtil {
   private  final String SIGNATURE_ALGORITHM = "SHA1withRSA";
   private  final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
   private  final int KEYBIT = 2048;
   private  final int RESERVEBYTES = 11;
   private static PrivateKey localkey;
   private static RSAUtil util;
   private RSAUtil(String privateKeyStr){
	    try {
		localkey=getPrivateKey(privateKeyStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
   public static RSAUtil getInstances(String privateKeyStr){
	   if(util==null){
		   util=new RSAUtil(privateKeyStr);
	   }
	   return util;
   }
   /**
	 * 从字符串中加载私钥
	 * 
	 * @param privateKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载私钥时产生的异常
	 */
	private RSAPrivateKey getPrivateKey(String privateKeyStr) throws Exception {
		try {
				
				BASE64Decoder base64Decoder = new BASE64Decoder();
				byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory
						.generatePrivate(keySpec);
				return privateKey;
			
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (IOException e) {
			throw new Exception("私钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}
	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	private RSAPublicKey getPublicKey(String publicKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory
					.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (IOException e) {
			throw new Exception("公钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}
	/**
	 * rsa 加密  
	 * @param plainBytes
	 * @param publicKeyStr  加密公钥
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptRSA(byte[] plainBytes,String publicKeyStr)
			throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		int decryptBlock = KEYBIT / 8; // 256 bytes
		int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
		// 计算分段加密的block数 (向上取整)
		int nBlock = (plainBytes.length / encryptBlock);
		if ((plainBytes.length % encryptBlock) != 0) { // 余数非0，block数再加1
			nBlock += 1;
		}
		PublicKey pubKey=getPublicKey(publicKeyStr);
		// 输出buffer, 大小为nBlock个decryptBlock
		ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock
				* decryptBlock);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		// cryptedBase64Str =
		// Base64.encodeBase64String(cipher.doFinal(plaintext.getBytes()));
		// 分段加密
		for (int offset = 0; offset < plainBytes.length; offset += encryptBlock) {
			// block大小: encryptBlock 或 剩余字节数
			int inputLen = (plainBytes.length - offset);
			if (inputLen > encryptBlock) {
				inputLen = encryptBlock;
			}
			// 得到分段加密结果
			byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
			// 追加结果到输出buffer中
			outbuf.write(encryptedBlock);
		}
		return outbuf.toByteArray(); // ciphertext
	}
	/**
	 * rsa解密 
	 * @param cryptedBytes
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptRSA(byte[] cryptedBytes) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		int decryptBlock = KEYBIT / 8; // 256 bytes
		int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
		// 计算分段解密的block数 (理论上应该能整除)
		int nBlock = (cryptedBytes.length / decryptBlock);
		// 输出buffer, , 大小为nBlock个encryptBlock
		ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock
				* encryptBlock);
		cipher.init(Cipher.DECRYPT_MODE, localkey);

		for (int offset = 0; offset < cryptedBytes.length; offset += decryptBlock) {
			// block大小: decryptBlock 或 剩余字节数
			int inputLen = (cryptedBytes.length - offset);
			if (inputLen > decryptBlock) {
				inputLen = decryptBlock;
			}

			// 得到分段解密结果
			byte[] decryptedBlock = cipher.doFinal(cryptedBytes, offset, inputLen);
			// 追加结果到输出buffer中
			outbuf.write(decryptedBlock);
		}
		outbuf.flush();
		outbuf.close();
		return outbuf.toByteArray();
	}
	/**
	 * RSA签名
	 * 
	 * @param localPrivKey
	 *            私钥
	 * @param plaintext
	 *            需要签名的信息
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] signRSA(byte[] bodyBytes) throws Exception {
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(localkey);
		signature.update(bodyBytes);
		return signature.sign();
	}
	/**
	 * 验签操作
	 * 
	 * @param peerPubKey
	 *            公钥
	 * @param plainBytes
	 *            需要验签的信息
	 * @param signBytes
	 *            签名信息
	 * @return boolean
	 */
	public boolean verifyRSA(byte[] plainBytes, byte[] signBytes,String publicKeyStr
			) throws Exception {
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		PublicKey pubKey=getPublicKey(publicKeyStr);
		signature.initVerify(pubKey);
		signature.update(plainBytes);
		return signature.verify(signBytes);
	}
}
