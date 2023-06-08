package com.example.fivechessfront.Network.Message.Implement;

import com.example.fivechessfront.Network.Message.IPassedMessage;
import com.example.fivechessfront.Network.Message.MessageType;
import com.example.fivechessfront.Network.Modify.ModifyReader;


import java.io.*;

public class PositionMessage implements IPassedMessage {
    public PositionMessage(String targetName, int roomID, String sourceName, int positionX,int positionY) {
        this.targetName = targetName;
        this.roomID = roomID;
        messageType = MessageType.Position;
        this.sourceName = sourceName;
        this.positionX = positionX;
        this.positionY = positionY;
    }
    private String targetName;
    private String sourceName;
    private MessageType messageType;
    private int contentLength;
    private int roomID;
    public int positionX;
    public int positionY;

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
    @Override
    public String getTargetName() {
        return targetName;
    }
    @Override
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
    @Override
    public int getRoomID() {
        return roomID;
    }
    @Override
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public byte[] ToBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(stream);
        try {
            writer.writeByte(messageType.ordinal());
            writer.writeUTF(sourceName);
            writer.writeUTF(targetName);
            writer.writeInt(roomID);
            writer.writeInt(positionX);
            writer.writeInt(positionY);
            contentLength = (stream.size() + Integer.BYTES);
            writer.writeInt(contentLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    public static PositionMessage Parse(byte[] bytes) {
        PositionMessage message = null;
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        ModifyReader reader = new ModifyReader(stream);
        try {
            MessageType type = MessageType.values()[reader.readByte()];
            String sn = reader.readUTF();
            String tn = reader.readUTF();
            int rid = reader.readCSharpInt();
            int px = reader.readCSharpInt();
            int py = reader.readCSharpInt();
            int length = reader.readCSharpInt();
            message = new PositionMessage(tn,rid,sn,px,py);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }




}
