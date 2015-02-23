package com.ofs.srv.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import org.apache.commons.codec.binary.Base64;

import com.ofs.srv.config.Constants;

public class SecurityKeyGen {

	public static void main(String[] args) throws Exception {

	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		 // Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
	    KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Constants.SECURITY_ALGORITHM.toString(), Constants.SECURITY_PROVIDER.toString());
	    keyGen.initialize(1024, new SecureRandom());
	    KeyPair keypair = keyGen.generateKeyPair();
	    PrivateKey privateKey = keypair.getPrivate();
	    PublicKey publicKey = keypair.getPublic();

	    // Get the bytes of the public and private keys (these go in the database with API Key)
	    byte[] privateKeyEncoded = privateKey.getEncoded();
	    byte[] publicKeyEncoded = publicKey.getEncoded();
	    
	    String privateK = Base64.encodeBase64URLSafeString(privateKeyEncoded);
	    String publicK = Base64.encodeBase64URLSafeString(publicKeyEncoded);

	    System.out.println("Private Key: " + privateK);
	    System.out.println("Public Key: " + publicK);
	}
}