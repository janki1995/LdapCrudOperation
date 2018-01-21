
// Source : http://www.includehelp.com/java-programs/encrypt-decrypt-string-using-aes-128-bits-encryption-algorithm.aspx

package basicoperations;

import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	private static final Logger logger = Logger.getLogger(Encryption.class.getName());

	public String encryptString(String originalString)
	{
		String encryptedString="";
		try
		{
			// Forms the core of Java Cryptographic Extension
			Cipher cipher = Cipher.getInstance(Constants.cipherTransformation); 
			byte[] key = Constants.encryptionKey.getBytes(Constants.characterEncoding);
			SecretKeySpec secretKey = new SecretKeySpec(key, Constants.aesEncryptionAlgorithem);
			IvParameterSpec ivparameterspec = new IvParameterSpec(key);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
			byte[] cipherText = cipher.doFinal(originalString.getBytes("UTF8"));
			Base64.Encoder encoder = Base64.getEncoder();
			encryptedString = encoder.encodeToString(cipherText);
		}
		catch(Exception exception)
		{
			logger.info("Classname: Encryption, Method: encryptString, Exception:" + exception);
		}

		return encryptedString;		
	}	

	/*String encryptString(String password)
	{
		try 
		{

			MessageDigest digest = MessageDigest.getInstance("ldap");
			digest.update(password.getBytes("UTF8"));
			Base64.Encoder encoder = Base64.getEncoder();
			String encryptedString = encoder.encodeToString(digest.digest());
			System.out.println("ldap" + encryptedString);
			return "ldap" + encryptedString;
		} 
		catch (Exception exception) 
		{
			logger.info("Classname: Encryption, Method: encryptString, Exception:" + exception);
			return null;
		}
		// String md5Password = Base64.encode(digest.digest());

	}*/
}
