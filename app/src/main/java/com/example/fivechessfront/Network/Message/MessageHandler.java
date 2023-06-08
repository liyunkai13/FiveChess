package com.example.fivechessfront.Network.Message;

import com.example.fivechessfront.Network.Udp.UdpHandler;

import java.util.function.Consumer;

public class MessageHandler {
    UdpHandler udpHandler;
    Thread receiveTask;
    Consumer<IMessage> onReceive;
    public MessageHandler(UdpHandler udpHandler, Consumer<IMessage> onReceive){
        this.udpHandler = udpHandler;
        this.onReceive = onReceive;
        class TaskThread extends Thread{
            @Override
            public void run(){
                while (true)
                {
                    IMessage message = udpHandler.ReceiveMessage();
                    onReceive.accept(message);
                }
            }
        }
        receiveTask = new TaskThread();
    }
    public void Start(){
        receiveTask.start();
    }
}
