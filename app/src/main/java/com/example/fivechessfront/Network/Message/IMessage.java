package com.example.fivechessfront.Network.Message;

public interface IMessage {
    /// <summary>
    /// 消息种类
    /// </summary>
    MessageType getMessageType();
    void setMessageType(MessageType messageType);
    /// <summary>
    /// 消息长度
    /// </summary>
    int getContentLength();
    void setContentLength(int contentLength);
    /// <summary>
    /// 发送者名称
    /// </summary>
    String getSourceName();
    void setSourceName(String sourceName);
    /// <summary>
    /// 转换为byte数组,用于信息传递
    /// </summary>
    /// <returns>byte数组</returns>
    public byte[] ToBytes();
}
