package com.qst.dms.entity;

import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class DmsNetServer {
    public DmsNetServer() {
        new AcceptLogThread(6666).start();
        new AcceptTranThread(6668).start();
        System.out.println("网络服务器已开启");
    }
    private  class AcceptLogThread extends Thread {
        private ServerSocket serverSocket;
        private Socket socket;
        private LogRecService logRecService;
        private ObjectInputStream ois;

        public AcceptLogThread(int port) {
            logRecService = new LogRecService();
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (this.isAlive()) {
                try {
                    socket = serverSocket.accept();
                    if(socket != null) {
                        ois = new ObjectInputStream(socket.getInputStream());
                        try {
                            ArrayList<MatchedLogRec> matchedLogs = (ArrayList<MatchedLogRec>) ois.readObject();
                            logRecService.saveMatchLogToDB(matchedLogs);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                ois.close();
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }
    private  class AcceptTranThread extends Thread {
        private ServerSocket serverSocket;
        private Socket socket;
        private TransportService transportService;
        private ObjectInputStream ois;

        public AcceptTranThread(int port) {
            transportService = new TransportService();
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (this.isAlive()) {
                try {
                    socket = serverSocket.accept();
                    if(socket != null) {
                        ois = new ObjectInputStream(socket.getInputStream());
                        try {
                            ArrayList<MatchedTransport> tranLogs = (ArrayList<MatchedTransport>) ois.readObject();
                            transportService.saveMatchTransportToDB(tranLogs);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                ois.close();
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new DmsNetServer();
    }

}
