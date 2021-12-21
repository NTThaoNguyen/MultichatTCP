/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multi_Chat_TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;



public class TCPServer {
    private int port;
    public static ArrayList<Socket> dsSK;

    public TCPServer(int port) {
        this.port = port;
    }
    
    public void ThucThi() throws IOException{
        ServerSocket server = new ServerSocket(port);
        WriteServer write = new WriteServer();
        write.start();
        System.out.println("Server listening");
        while (true) {            
            Socket socket = server.accept();
            System.out.println("Da ket noi voi " + socket);
            TCPServer.dsSK.add(socket);
            ReadServer read = new ReadServer(socket);
            read.start();
        }
    }
    
    public static void main(String[] args) throws IOException {
        TCPServer.dsSK = new ArrayList<>();
        TCPServer server = new TCPServer(12345);
        server.ThucThi();
    }
}

class WriteServer extends Thread{

    @Override
    public void run() {
        DataOutputStream dout = null;
        Scanner sc = new Scanner(System.in);
        while (true) {
            String guiTN = sc.nextLine();
            for(Socket x: TCPServer.dsSK){
                try {
                    dout = new DataOutputStream(x.getOutputStream());
                    dout.writeUTF("Server chat: " + guiTN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}

class ReadServer extends Thread{
    private Socket server;

    public ReadServer(Socket server) {
        this.server = server;
    }    
    
    @Override
    public void run() {
        DataInputStream din = null;
        try {
            din = new DataInputStream(server.getInputStream());
            while (true) {                
                String nhanTN = din.readUTF();
                for(Socket x: TCPServer.dsSK){
                    if(x.getPort() != server.getPort()){
                        DataOutputStream dout = new DataOutputStream(x.getOutputStream());
                        dout.writeUTF(nhanTN);
                    }
                }
                System.out.println(nhanTN);
            }
        } catch (Exception e) {
            try {
                din.close();
                server.close();
            } catch (Exception ex) {
                System.out.println("Ngat ket noi");
            }
        }
    }
    
}
        