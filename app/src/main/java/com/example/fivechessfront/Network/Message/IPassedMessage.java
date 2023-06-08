package com.example.fivechessfront.Network.Message;

public interface IPassedMessage extends IMessage {
    /// <summary>
    /// 发送目标名称
    /// </summary>
    String getTargetName();
    void setTargetName(String targetName);
    /// <summary>
    /// 房间号
    /// </summary>
    int getRoomID();
    void setRoomID(int roomID);
}
