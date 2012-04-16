/*
 *   This file is part of Funky Domino.
 *
 *   Funky Domino is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Funky Domino is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Funky Domino.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gmxteam.funkydomino.xmlencryptor;

import java.math.BigInteger;

/**
 * Implémentation RSA en Java.
 *
 * @author Guillaume Poirier-Morency
 */
/**
 * ***********************************************************************
 * Compilation: javac RSA.java Execution: java RSA N
 *
 * Generate an N-bit public and private RSA key and use to encrypt and decrypt a
 * random message.
 *
 * % java RSA 50 public = 65537 private = 553699199426609 modulus =
 * 825641896390631 message = 48194775244950 encrpyted = 321340212160104
 * decrypted = 48194775244950
 *
 * Known bugs (not addressed for simplicity)
 * ----------------------------------------- - It could be the case that the
 * message >= modulus. To avoid, use a do-while loop to generate key until
 * modulus happen to be exactly N bits.
 *
 * - It's possible that gcd(phi, publicKey) != 1 in which case the key
 * generation fails. This will only happen if phi is a multiple of 65537. To
 * avoid, use a do-while loop to generate keys until the gcd is 1.
 *
 ************************************************************************
 */
import java.math.BigInteger;
import java.security.SecureRandom;

class RSA {

    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();
    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;
    

    // generate an N-bit (roughly) public and private key
    RSA(int N) {
        BigInteger p = BigInteger.probablePrime(N / 2, random);
        BigInteger q = BigInteger.probablePrime(N / 2, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger("65537");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
    }
    
    /**
     * Constructeur pour encrypter seulement.
     * @param N
     * @param privateKey 
     */
    RSA(int N, BigInteger privateKey) {
        this(N);
        this.privateKey = privateKey;
    }
    
    

    /**
     * Constructeur avec des valeur pré-existantes.
     * @param modulus
     * @param publicKey
     * @param privateKey 
     */
    RSA(BigInteger modulus, BigInteger publicKey, BigInteger privateKey) {
        this.modulus = modulus;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }    

    BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }
    
    private BigInteger byteConverter = BigInteger.valueOf(5);
    
    byte encrypt (byte b) {
        return encrypt(BigInteger.valueOf(b)).byteValue();    
    }
    
    

    BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RSA key = new RSA(N);
        System.out.println(key);

        // create random message, encrypt and decrypt
        BigInteger message = new BigInteger(N - 1, random);

        //// create message by converting string to integer
        // String s = "test";
        // byte[] bytes = s.getBytes();
        // BigInteger message = new BigInteger(s);

        BigInteger encrypt = key.encrypt(message);
        BigInteger decrypt = key.decrypt(encrypt);
        System.out.println("message   = " + message);
        System.out.println("encrpyted = " + encrypt);
        System.out.println("decrypted = " + decrypt);
    }
    
    
}
// Copyright © 2000–2011, Robert Sedgewick and Kevin Wayne. 
// Last updated: Wed Feb 9 09:20:16 EST 2011.

