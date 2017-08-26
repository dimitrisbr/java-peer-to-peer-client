/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author dimitris
 */
public class Client implements Runnable {

    private String ip;
    private int port;
    private String fileExt;
    private String fileName;
    private File file;

    public Client(String ip, int port, String fileExt, String fileName, File file) {
        this.ip = ip;
        this.port = port;
        this.fileExt = fileExt;
        this.fileName = fileName;
        this.file=file;
    }

    @Override
    public void run() {
        try (Socket clientsocket = new Socket(ip, port)) {
            DataOutputStream out = new DataOutputStream(clientsocket.getOutputStream());

            out.writeUTF(fileName); // Sending file name.
            out.writeUTF(fileExt); // Sending file extension
            out.writeInt((int) file.length()); //Sending byte array.

            InputStream fIn = new FileInputStream(file);
            BufferedInputStream bIn = new BufferedInputStream(fIn);

            int n = 0;
            byte[] buffer = new byte[8192];
            while ((n = bIn.read(buffer)) > 0) {
                out.write(buffer, 0, n);
                out.flush();
            }
            out.close();
            fIn.close();
            bIn.close();
            clientsocket.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
}
