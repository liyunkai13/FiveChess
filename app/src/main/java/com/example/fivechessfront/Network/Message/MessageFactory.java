package com.example.fivechessfront.Network.Message;

import com.example.fivechessfront.Network.Message.Implement.HeartBeatMessage;
import com.example.fivechessfront.Network.Message.Implement.PositionMessage;
import com.example.fivechessfront.Network.Message.Implement.RoomOperationMessage;

public class MessageFactory {
    public PositionMessage GetTextMessage(String content, long targetID, boolean isGroup, String sourceToken) {
        return null;
    }

    public HeartBeatMessage GetHeartBeatMessage(String sourceName) {
        return new HeartBeatMessage(sourceName);
    }

    /// <summary>
    /// 自动解析bytes为Message
    /// </summary>
    /// <param name="bytes">字节数组</param>
    /// <returns>返回消息接口,可自己转换成对应消息类型</returns>
    public IMessage Parse(byte[] bytes) {
        MessageType type = MessageType.values()[bytes[0]];
        switch (type) {
            case None:
                break;
            case Position:
                return PositionMessage.Parse(bytes);
            case HeartBeat:
                return HeartBeatMessage.Parse(bytes);
            case RoomOperation:
                return RoomOperationMessage.Parse(bytes);
            default:
                break;
        }
        return null;
    }
}
