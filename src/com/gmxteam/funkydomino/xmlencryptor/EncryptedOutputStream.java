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
 * Utilisé pour encrypter les fichiers XMLs.
 *
 * @author guillaume
 */
public class EncryptedOutputStream extends OutputStream {

    private final OutputStream stdout;
    private final String privatekey;

    /**
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
        privatekey = tempKey;
        stdout = resourceStream;

    }

    /**
     *
     * @param resourceStream
     * @param publicKey
     */
    EncryptedOutputStream(OutputStream resourceStream, char[] privateKey) {
        this.privatekey = new String(privateKey);
        stdout = resourceStream;
    }

    @Override
    public void write(int oneByte) throws IOException {
        stdout.write(oneByte);
    }
}
