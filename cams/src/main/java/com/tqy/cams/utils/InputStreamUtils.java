package com.tqy.cams.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream、String互相转的工具类
*/ 
public class InputStreamUtils {
 
    final static int BUFFER_SIZE = 4096;

    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static final byte[] input2byte(InputStream inStream)throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
 
    /**
     * @param in
     * @return String
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
 
        data = null;
        return new String(outStream.toByteArray());
    }
 
    /**
     * @param in
     * @param encoding
     * @return String
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in, String encoding) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
 
        data = null;
        return new String(outStream.toByteArray(), encoding);
    }
 
    /**
     * @param in
     * @param encoding
     * @return InputStream
     * @throws Exception
     */
    public static InputStream StringTOInputStream(String in, String encoding) throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes(encoding));
        return is;
    }
     
    /**
     * @param in
     * @return InputStream
     * @throws Exception
     */
    public static InputStream StringTOInputStream(String in) throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes());
        return is;
    }
}