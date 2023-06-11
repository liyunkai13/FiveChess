package com.example.fivechessfront.Network.Modify;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ModifyReader extends DataInputStream {
    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public ModifyReader(InputStream in) {
        super(in);
    }
    public int readCSharpInt() throws IOException {
        byte[] bytes = new byte[4];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        byte ch1 = readByte();
        bytes[0] = ch1;
        byte ch2 = readByte();
        bytes[1] = ch2;
        byte ch3 = readByte();
        bytes[2] = ch3;
        byte ch4 = readByte();
        bytes[3] = ch4;
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getInt();
    }
}
