/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multi_Chat_TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient_2 {
    private InetAddress host;
    private int port;

    public TCPClient_2(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }
    
    private void ThucThi() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ten: ");
        String name = sc.nextLine();
        System.out.println("Bắt đầu chat");
        Socket client = new Socket(host, port);
        ReadClient2 read = new ReadClient2(client);
        read.start();
        WriteClient2 write = new WriteClient2(client, name);
        write.start();
    }
    
    public static void main(String[] args) throws IOException {
        TCPClient_2 client  = new TCPClient_2(InetAddress.getLocalHost(), 12345);
        client.ThucThi();
    }
}

class ReadClient2 extends Thread{
    private Socket client;

    public ReadClient2(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream din = null;
        try {
            din = new DataInputStream(client.getInputStream());
            while (true) {                
               String nhanTN = din.readUTF();
                System.out.println(nhanTN);
            }
        } catch (Exception e) {
            try {
                din.close();
                client.close();
            } catch (Exception ex) {
                System.out.println("Ngat ket noi");
            }
        }
    }
    
    
}

class WriteClient2 extends Thread{
    private Socket client;
    private String name;

    public WriteClient2(Socket client, String name) {
        this.client = client;
        this.name = name;
    }

    @Override
    public void run() {
        DataOutputStream dout = null;
        Scanner sc = null;
        try {
            dout = new DataOutputStream(client.getOutputStream());
            sc = new  Scanner(System.in);
            while (true) {
                String guiTN = sc.nextLine();
                dout.writeUTF("Client " + name + " chat: " + guiTN);
            }
        } catch (Exception e) {
            try {
                dout.close();
                client.close();
            } catch (Exception ex) {
                System.out.println("Ngat ket noi voi Server");
            }
        }
    }
    
    
}
        