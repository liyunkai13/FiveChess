package com.example.fivechessfront.Network.Message;

public interface IPassedMessage extends IMessage {
    /// <summary>
    /// 房间号
    /// </summary>
    int getRoomID();
    void setRoomID(int roomID);
}
