/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author dimitris
 */
public class Server implements Runnable {

    private int port;
    private boolean isRunning;
    private MultiThreadServer server;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        try {
            ServerSocket ss = new ServerSocket(port);
            isRunning = true;

            while (true) {
                Socket s = ss.accept();
                //Creates new server thread.
                server = new MultiThreadServer(s);
                new Thread(server).start();
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public String getName() {
        return server.getFileName();
    }

    public String getExt() {
        return server.getFileExt();
    }

    public String getRemoteAddr() {
        return server.getRemoteAddr();
    }

    public int getLength() {
        return server.getLength();
    }

}
