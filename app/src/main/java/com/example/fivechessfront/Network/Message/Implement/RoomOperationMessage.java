package com.example.fivechessfront.Network.Message.Implement;

import com.example.fivechessfront.Network.Message.IMessage;
import com.example.fivechessfront.Network.Message.MessageType;
import com.example.fivechessfront.Network.Modify.ModifyReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RoomOperationMessage implements IMessage {
    private String sourceName;
    private MessageType messageType;
    private int contentLength;
    public int roomID;
    public String operation;

    public RoomOperationMessage(String sourceName, int roomID, String operation) {
        this.sourceName = sourceName;
        messageType = MessageType.RoomOperation;
        this.roomID = roomID;
        this.operation = operation;
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

    public static RoomOperationMessage Parse(byte[] bytes){
        RoomOperationMessage message = null;
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        ModifyReader reader = new ModifyReader(stream);
        try {
            MessageType type = MessageType.values()[reader.readByte()];
            String sn = reader.readUTF();
            int rid = reader.readCSharpInt();
            String op = reader.readUTF();
            int length = reader.readCSharpInt();
            message = new RoomOperationMessage(sn,rid,op);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public byte[] ToBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(stream);
        try {
            writer.writeByte(messageType.ordinal());
            writer.writeUTF(sourceName);
            writer.writeInt(roomID);
            writer.writeUTF(operation);
            contentLength = (stream.size() + Integer.BYTES);
            writer.writeInt(contentLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }
}
