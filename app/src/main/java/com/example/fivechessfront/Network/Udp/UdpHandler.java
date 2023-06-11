package com.example.fivechessfront.Network.Udp;

import com.example.fivechessfront.Network.Message.IMessage;
import com.example.fivechessfront.Network.Message.MessageFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpHandler {
    public DatagramSocket socket;
    public InetAddress address;
    public MessageFactory factory;

    public UdpHandler() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("8.130.104.12");
            factory = new MessageFactory();
        } catch (Exception ignored) {

        }
    }

    public void SendMessage(IMessage message) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(message.ToBytes(), message.ToBytes().length, address, 6000);
            Thread thread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        socket.send(sendPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        } catch (Exception ignored) {

        }
    }

    public IMessage ReceiveMessage() {
        byte[] responseBytes = new byte[4096];
        DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
        try {
            socket.receive(responsePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory.Parse(responsePacket.getData());
    }
}

