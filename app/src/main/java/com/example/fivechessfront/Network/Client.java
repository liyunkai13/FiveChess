package com.example.fivechessfront.Network;

import android.util.Log;

import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.Impl.CyberHuman;
import com.example.fivechessfront.Entity.Position;
import com.example.fivechessfront.Network.Message.IMessage;
import com.example.fivechessfront.Network.Message.Implement.PositionMessage;
import com.example.fivechessfront.Network.Message.Implement.RoomOperationMessage;
import com.example.fivechessfront.Network.Message.Implement.RoomStateMessage;
import com.example.fivechessfront.Network.Message.MessageFactory;
import com.example.fivechessfront.Network.Message.MessageHandler;
import com.example.fivechessfront.Network.Udp.UdpHandler;
import com.example.fivechessfront.utils.AccountManager;

import java.util.Timer;
import java.util.TimerTask;

public class Client {
    UdpHandler udpHandler;
    MessageFactory messageFactory;
    MessageHandler messageHandler;
    CyberHuman cyberHuman;
    Timer timer;
    private static Client mInstance;
    public static Client getInstance(){
        if(mInstance == null) mInstance = new Client();
        return mInstance;
    }
    private Client(){
        udpHandler = new UdpHandler();
        messageFactory = new MessageFactory();
        timer = new Timer();
        messageHandler = new MessageHandler(udpHandler, message -> {
            switch (message.getMessageType()){
                case Position : {
                    setCyberHumanMove(message);
                    break;
                }
                case RoomOperation:{
                    System.out.println(message.getSourceName());
                    break;
                }
                case RoomState:{
                    SetRoomInfo(message);
                }
            }
        });
    }
    public void SetRoomInfo(IMessage message){
        if(cyberHuman == null) return;
        RoomStateMessage roomStateMessage = (RoomStateMessage) message;
        Log.d("Client",roomStateMessage.toString());
        Game game = cyberHuman.game;
        game.SetInternetRoomInfo(roomStateMessage.playerAName,roomStateMessage.playerBName,roomStateMessage.firstIsBlack);
    }
    public void setCyberHuman(CyberHuman cyberHuman){
        this.cyberHuman = cyberHuman;
    }
    private void setCyberHumanMove(IMessage message){
        if(cyberHuman==null) return;
        PositionMessage positionMessage = (PositionMessage) message;
        cyberHuman.setIntention(new Position(positionMessage.positionX,positionMessage.positionY));
        cyberHuman.game.RunATurn();
    }
    public void Start(){
        String name = AccountManager.getInstance().getAccount().getName();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                udpHandler.SendMessage(messageFactory.GetHeartBeatMessage(name));
                System.out.println("正在发送心跳");
            }
        }, 0, 1000);
        messageHandler.Start();
        udpHandler.SendMessage(messageFactory.GetHeartBeatMessage(name));
        RoomOperationMessage joinRoomMessage = new RoomOperationMessage(name,12,"Join");
        udpHandler.SendMessage(joinRoomMessage);
    }
    public void SendPosition(Position position){
        String name = AccountManager.getInstance().getAccount().getName();
        udpHandler.SendMessage(new PositionMessage(12,name,position.col,position.row));
    }
}
