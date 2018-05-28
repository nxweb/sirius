package com.bcyj99.sirius.core.security.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;

public class RsaUtils {
	private static String src = "imooc security rsa";  
	  
    public static void main(String[] args) {  
        try {
			String pubfile = "E:\\app\\pub.key";
			String prifile = "E:\\app\\pri.key";
			
//			makekeyfile(pubfile, prifile);
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pubfile));
			RSAPublicKey pubkey = (RSAPublicKey) ois.readObject();
			ois.close();
			ois = new ObjectInputStream(new FileInputStream(prifile));
			RSAPrivateKey prikey = (RSAPrivateKey) ois.readObject();
			ois.close();
			// 使用公钥加密  
			String msg = "~O(∩_∩)O哈哈~";
			String enc = "UTF-8";
			// 使用公钥加密私钥解密  
			System.out.println("原文: " + msg);
			byte[] result = handleData(pubkey, msg.getBytes(enc), 1);
			System.out.println("加密: " + new String(result, enc));
			byte[] deresult = handleData(prikey, result, 0);
			System.out.println("解密: " + new String(deresult, enc));
			msg = "嚯嚯";
			// 使用私钥加密公钥解密  
			System.out.println("原文: " + msg);
			byte[] result2 = handleData(prikey, msg.getBytes(enc), 1);
			System.out.println("加密: " + new String(result2, enc));
			byte[] deresult2 = handleData(pubkey, result2, 0);
			System.out.println("解密: " + new String(deresult2, enc));
		} catch (Exception e) {
			e.printStackTrace();
		} 
        
    }  
    
    public static void makekeyfile(String pubkeyfile, String privatekeyfile)  
            throws Exception {  
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        // 初始化密钥对生成器，密钥大小为1024位  
        keyPairGen.initialize(1024);  
        // 生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
  
        // 得到私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
  
        // 得到公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
  
        // 生成私钥  
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(  
                privatekeyfile));  
        oos.writeObject(privateKey);  
        oos.flush();  
        oos.close();  
  
        oos = new ObjectOutputStream(new FileOutputStream(pubkeyfile));  
        oos.writeObject(publicKey);  
        oos.flush();  
        oos.close();  
  
        System.out.println("make file ok!");  
    }
    
    /** 
     *  
     * @param k 
     * @param data 
     * @param encrypt 
     *            1 加密 0解密 
     * @return 
     * @throws NoSuchPaddingException 
     * @throws Exception 
     */  
    public static byte[] handleData(Key k, byte[] data, int encrypt)  
            throws Exception {  
  
        if (k != null) {  
  
            Cipher cipher = Cipher.getInstance("RSA");  
  
            if (encrypt == 1) {  
                cipher.init(Cipher.ENCRYPT_MODE, k);  
                byte[] resultBytes = cipher.doFinal(data);  
                return resultBytes;  
            } else if (encrypt == 0) {  
                cipher.init(Cipher.DECRYPT_MODE, k);  
                byte[] resultBytes = cipher.doFinal(data);  
                return resultBytes;  
            } else {  
                System.out.println("参数必须为: 1 加密 0解密");  
            }  
        }  
        return null;  
    }  
      
    public static void jdkRSA() {  
        try {  
            //1.初始化密钥  
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");  
            keyPairGenerator.initialize(512);  
            KeyPair keyPair = keyPairGenerator.generateKeyPair();  
            RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();  
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
            System.out.println("rsaPublicKey:" + rsaPublicKey.getPublicExponent());
            System.out.println("rsaPrivateKey:" + rsaPrivateKey.getPrivateExponent());
              
            //2.执行签名  
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);  
            Signature signature = Signature.getInstance("MD5withRSA");  
            signature.initSign(privateKey);  
            signature.update(src.getBytes());  
            byte[] result = signature.sign();  
            System.out.println("jdk rsa sign : " + Hex.encodeHexString(result));  
              
            //3.验证签名  
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());  
            keyFactory = KeyFactory.getInstance("RSA");  
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);  
            signature = Signature.getInstance("MD5withRSA");  
            signature.initVerify(publicKey);  
            signature.update(src.getBytes());  
            boolean bool = signature.verify(result);  
            System.out.println("jdk rsa verify : " + bool);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
    /**
     * 使用私钥签名 
     */
    public static void sign() {  
        try {  
            //1.初始化密钥  
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");  
            keyPairGenerator.initialize(512);  
            KeyPair keyPair = keyPairGenerator.generateKeyPair();  
            PublicKey publicKey = keyPair.getPublic();  
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println("rsaPublicKey:" + publicKey.getFormat());
            System.out.println("rsaPrivateKey:" + privateKey.getFormat());
            
            //2.执行签名  
            Signature signature = Signature.getInstance("MD5withRSA");  
            signature.initSign(privateKey);  
            signature.update("这里签名".getBytes());  
            byte[] sign = signature.sign();  
            System.out.println("jdk rsa sign : " + Hex.encodeHexString(sign));
            
            //保存公钥并写入文件中   
            saveKey(publicKey,"E:\\app\\zxx_private.key");    
            //将签名后的数据写入到文件     
            saveData(sign,"E:\\app\\public_encryt.dat");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
    /**
     * 公钥解密
     */
    public static void verify() {  
        try {   
        	Signature signture =Signature.getInstance("MD5withRSA");  
            //获取到公钥  
            PublicKey publicKey=(PublicKey)readKey("/app/zxx_private.key");  
            //初始化校验  
            signture.initVerify(publicKey);  
            //初始化签名对象  
            signture.update("这里签名".getBytes());  
            //读数据源     
            byte [] sign =readData("/app/public_encryt.dat");    
            //返回匹配结果  
            boolean isYouSigned=signture.verify(sign);  
            //如果返回数据为true则数据没有发生修改，否则发生修改  
            System.out.println(isYouSigned);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
    public static void saveKey(Key key,String fileName)throws Exception{  
        FileOutputStream fosKey=new FileOutputStream(fileName);  
        ObjectOutputStream oosSecretKey =new ObjectOutputStream(fosKey);  
        oosSecretKey.writeObject(key);  
        oosSecretKey.close();  
        fosKey.close();  
    }  
    
    private static void saveData(byte[] result, String fileName) throws Exception {  
        // TODO Auto-generated method stub  
        FileOutputStream fosData=new FileOutputStream(fileName);  
        fosData.write(result);  
        fosData.close();  
    } 
    
    private static Key readKey(String fileName) throws Exception {  
        FileInputStream fisKey=new FileInputStream(fileName);  
        ObjectInputStream oisKey =new ObjectInputStream(fisKey);  
        Key key=(Key)oisKey.readObject();  
        oisKey.close();  
        fisKey.close();  
        return key;  
    } 
    
    private static byte[] readData(String filename) throws Exception {  
        FileInputStream fisDat=new FileInputStream(filename);  
        byte [] src=new byte [fisDat.available()];  
        int len =fisDat.read(src);  
        int total =0;  
        while(total<src.length){  
            total +=len;  
            len=fisDat.read(src,total,src.length-total);  
        }  
        fisDat.close();  
        return src;  
    }  
}
