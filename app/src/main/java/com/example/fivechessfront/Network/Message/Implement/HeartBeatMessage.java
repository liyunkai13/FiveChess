package com.example.fivechessfront.Network.Message.Implement;

import com.example.fivechessfront.Network.Message.IMessage;
import com.example.fivechessfront.Network.Message.MessageType;
import com.example.fivechessfront.Network.Modify.ModifyReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HeartBeatMessage implements IMessage {
    private MessageType messageType;
    private int contentLength;
    private String sourceName;

    public HeartBeatMessage(String sourceName) {
        this.messageType = MessageType.HeartBeat;
        this.sourceName = sourceName;
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
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(stream);
        try {
            writer.writeByte(messageType.ordinal());
            writer.writeUTF(sourceName);
            contentLength = (stream.size() + Integer.BYTES);
            writer.writeInt(contentLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    public static HeartBeatMessage Parse(byte[] bytes) {
        HeartBeatMessage message = null;
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        ModifyReader reader = new ModifyReader(stream);
        try {
            MessageType type = MessageType.values()[reader.readByte()];
            String sn = reader.readUTF();
            int length = reader.readCSharpInt();
            message = new HeartBeatMessage(sn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
