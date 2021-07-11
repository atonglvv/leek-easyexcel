package cn.atong.leek.easyexcel.leekeasyexcel.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * @program: leek-easyexcel
 * @description: Stream 转换Util
 * @author: atong
 * @create: 2021-07-11 12:54
 */
public class StreamConvertUtil {
    // inputStream转outputStream
    public static ByteArrayOutputStream parse(final InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }

    // outputStream转inputStream
    public static ByteArrayInputStream parse(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }

    // inputStream转String
    public static String parseString(final InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream.toString();
    }

    // OutputStream 转String
    public static String parseString(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream.toString();
    }

    // String转inputStream
    public static ByteArrayInputStream parse_inputStream(final String in) throws Exception {
        final ByteArrayInputStream input = new ByteArrayInputStream(in.getBytes());
        return input;
    }

    // String 转outputStream
    public static ByteArrayOutputStream parse_outputStream(final String in) throws Exception {
        return parse(parse_inputStream(in));
    }
}
