/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author dimitris
 */
public class MultiThreadServer implements Runnable {

    private Socket socket;
    private String remoteAddr;
    private String fileExt;
    private String fileName;
    private int length;

    public MultiThreadServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        remoteAddr = socket.getRemoteSocketAddress().toString();

        try {
            DataInputStream in = new DataInputStream(socket.getInputStream()); // Receives the filename , extension and size.
            fileName = in.readUTF();
            fileExt = in.readUTF();
            length = in.readInt();

            BufferedInputStream bin = new BufferedInputStream(socket.getInputStream());
            FileOutputStream fout = new FileOutputStream(fileName + "." + fileExt);
            int n;
            byte[] buffer = new byte[8192]; // buffer size 8kb
            while ((n = bin.read(buffer)) > -1) { // sends file into small chuncks
                fout.write(buffer, 0, n);  // writes to file.
            }
            fout.flush();
            fout.close();

            JOptionPane.showMessageDialog(null, "File Received!");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public String getFileExt() {
        return fileExt;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLength() {
        return length;
    }

}
