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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Utilisé pour encrypter les fichiers XMLs. Seulement la méthode write est
 * recodée.
 *
 * @author guillaume
 */
public class EncryptedOutputStream extends OutputStream {

    private final OutputStream stdout;
    private final char[] privatekey;

    /**
     * Constructeur pour le flux encrypté. Il demande d'entrer la clé privée en
     * entrée standard.
     *
     * @param resourceStream
     * @param publicKey
     */
    EncryptedOutputStream(OutputStream resourceStream) {
        String tempKey = "";
        System.out.println("Please enter the private key in order to encrypt this stream");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        try {
            tempKey = stdin.readLine();
        } catch (IOException ex) {
            System.exit(0);

        }
        privatekey = tempKey.toCharArray();
        tempKey = null;
        stdout = resourceStream;

    }

    /**
     * Constructeur pour le flux encrypté. La clé privée est spécifier en input.
     *
     * @param resourceStream
     * @param publicKey
     */
    EncryptedOutputStream(OutputStream resourceStream, char[] privateKey) {
        this.privatekey = privateKey;
        stdout = resourceStream;
    }

    @Override
    public void write(int oneByte) throws IOException {
        stdout.write(encryptOneInteger(oneByte));
    }

    @Override
    public void write(byte[] oneArrayByte) throws IOException {
        for (int i = 0; i < oneArrayByte.length; i++) {
            oneArrayByte[i] = encryptOneByte(oneArrayByte[i]);
        }
        stdout.write(oneArrayByte);
                System.out.println("used");

    }

    @Override
    public void write(byte[] oneArrayByte, int i, int j) throws IOException {
        // On convertit seulement les portions de l'array qui est spécifiée
        for (int iCounter = i; iCounter < j; iCounter++) {
            oneArrayByte[iCounter] = encryptOneByte(oneArrayByte[iCounter]);
        }
        stdout.write(oneArrayByte, i, j);
        System.out.println("used offset");
    }

    @Override
    public void close() throws IOException {
        // On vide la clé et on ferme les flux.
        for (char c : this.privatekey) {
            c = 0;
        }
        stdout.close();
        super.close();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes pour l'encryptage.
    /**
     *
     * @param b
     * @return
     */
    private byte encryptOneByte(byte b) {
        return b;
    }

    /**
     *
     * @param i
     * @return
     */
    private int encryptOneInteger(int i) {
        return i;
    }
}
