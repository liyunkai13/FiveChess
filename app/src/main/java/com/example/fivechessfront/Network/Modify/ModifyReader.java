package com.example.fivechessfront.Network.Modify;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        int ch1 = readByte();
        int ch2 = readByte();
        int ch3 = readByte();
        int ch4 = readByte();
        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1));
    }
}
