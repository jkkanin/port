package com.ofs.srv.security;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ofs.srv.config.Constants;

@Component("securityHelper")
public class SecurityHelper {
	
	@Value("${security.private.key}")
	public String privateKey;
	
	private final String algorithm = Constants.SECURITY_ALGORITHM.toString();
	
	private final String provider = Constants.SECURITY_PROVIDER.toString();

	public boolean validateSignature(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchProviderException {

		if (publicKey == null || publicKey.isEmpty()) {
			return false;
		}

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

	    KeyFactory keyFactory = KeyFactory.getInstance(algorithm, provider);

	    // Private key encoding
		byte[] privateKeyBytes = Base64.decodeBase64(privateKey);
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

		// Private key signature creation
		Signature privateSignature = Signature.getInstance(algorithm, provider);
		privateSignature.initSign(keyFactory.generatePrivate(privateKeySpec), new SecureRandom());

		// Public key encoding 
		byte[] publicKeyBytes = Base64.decodeBase64(publicKey);
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

		// Public key signature creation
		Signature publicSignature = Signature.getInstance(algorithm, provider);
		publicSignature.initVerify(keyFactory.generatePublic(publicKeySpec));

		try {
			return publicSignature.verify(privateSignature.sign());
		} catch (SignatureException e) {
			return false;
		}
	}
}