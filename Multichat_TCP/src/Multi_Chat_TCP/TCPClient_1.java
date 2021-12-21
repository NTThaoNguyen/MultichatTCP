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

public class TCPClient_1 {
    private InetAddress host;
    private int port;

    public TCPClient_1(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }
    
    private void ThucThi() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ten: ");
        String name = sc.nextLine();
        System.out.println("Bắt đầu chat");
        Socket client = new Socket(host, port);
        ReadClient1 read = new ReadClient1(client);
        read.start();
        WriteClient1 write = new WriteClient1(client, name);
        write.start();
    }
    
    public static void main(String[] args) throws IOException {
        TCPClient_1 client  = new TCPClient_1(InetAddress.getLocalHost(), 12345);
        client.ThucThi();
    }
}

class ReadClient1 extends Thread{
    private Socket client;

    public ReadClient1(Socket client) {
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

class WriteClient1 extends Thread{
    private Socket client;
    private String name;

    public WriteClient1(Socket client, String name) {
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
        