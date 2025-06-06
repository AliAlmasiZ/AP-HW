package common.utils;

import common.models.Message;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {
	public static String HashFile(String filePath) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		try(FileInputStream inputStream = new FileInputStream(filePath);
			DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest)) {

			var	buffer = new byte[8 * 1024];
			while (digestInputStream.read(buffer) != -1){}

		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] digestBytes = messageDigest.digest();

		StringBuilder hexString = new StringBuilder(2 * digestBytes.length);
		for (byte digestByte : digestBytes) {
			String hex = Integer.toHexString((digestByte & 0xFF));
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
