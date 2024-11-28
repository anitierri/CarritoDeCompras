package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ContrasenaHash {

	public static String encriptar(String contrasena) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = digest.digest(contrasena.getBytes());
		StringBuilder hexString = new StringBuilder();

		for (byte b : hashBytes) {
			hexString.append(String.format("%02x", b));
		}

		return hexString.toString();
	}

	public static boolean chequearContrasena(String contrasena, String hash) throws NoSuchAlgorithmException {
		return encriptar(contrasena).equals(hash);
	}
}