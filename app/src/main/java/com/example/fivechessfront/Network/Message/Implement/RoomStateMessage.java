package com.example.fivechessfront.Network.Message.Implement;


import com.example.fivechessfront.Network.Message.IMessage;
import com.example.fivechessfront.Network.Message.MessageType;
import com.example.fivechessfront.Network.Modify.ModifyReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RoomStateMessage implements IMessage {
    private MessageType messageType;
    private int contentLength;
    private String sourceName;
    public String playerAName;
    public String playerBName;
    public boolean firstIsBlack;
    public int nowPlayers;
    public int roomID;
    public RoomStateMessage(String sourceName,String playerAName, String playerBName, int nowPlayers, int roomID, boolean firstIsBlack) {
        this.messageType = MessageType.RoomState;
        this.sourceName = sourceName;
        this.playerAName = playerAName;
        this.playerBName = playerBName;
        this.nowPlayers = nowPlayers;
        this.roomID = roomID;
        this.firstIsBlack = firstIsBlack;
    }
    @Override
    public MessageType getMessageType() {
        return messageType;
    }
    @Override
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
    @Override
    public int getContentLength() {
        return contentLength;
    }
    @Override
    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }
    @Override
    public String getSourceName() {
        return sourceName;
    }
    @Override
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public byte[] ToBytes() {
        return null;
    }

    public static RoomStateMessage Parse(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            sb.append(bytes[i]+",");
        }
        System.out.println(sb.toString());
        RoomStateMessage message = null;
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        ModifyReader reader = new ModifyReader(stream);
        try {
            MessageType type = MessageType.values()[reader.readByte()];
            String sn = reader.readUTF();
            int rid = reader.readCSharpInt();
            int nplrs = reader.readCSharpInt();
            String plaName = reader.readUTF();
            String plbName = reader.readUTF();
            boolean fib = reader.readBoolean();
            int length = reader.readCSharpInt();
            message = new RoomStateMessage(sn,plaName,plbName,nplrs,rid,fib);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public String toString() {
        return "RoomStateMessage{" +
                "messageType=" + messageType +
                ", contentLength=" + contentLength +
                ", sourceName='" + sourceName + '\'' +
                ", playerAName='" + playerAName + '\'' +
                ", playerBName='" + playerBName + '\'' +
                ", firstIsBlack=" + firstIsBlack +
                ", nowPlayers=" + nowPlayers +
                ", roomID=" + roomID +
                '}';
    }
}
