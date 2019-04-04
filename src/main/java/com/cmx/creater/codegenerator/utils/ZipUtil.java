package com.cmx.creater.codegenerator.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author cmx  2018-01-13
 * 压缩处理工具
 */
public final class ZipUtil {

    private final static Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 解压
     *
     * @param inputStream
     * @param fileName
     * @param charset
     * @return
     * @throws IOException
     */
    public static InputStream unZip(InputStream inputStream, String fileName, Charset charset) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream, charset);
        ZipEntry zipEntry;
        while (null != (zipEntry = zipInputStream.getNextEntry())) {
            String name = zipEntry.getName();
            if (name.contains(fileName)) {
                return zipInputStream;
            }
        }
        return null;
    }


    /**
     * 压缩多个文件
     *
     * @param map
     * @return
     * @throws IOException
     */
    public static InputStream zipOutputStreams(Map<String, ByteArrayOutputStream> map) throws IOException {

        ByteArrayOutputStream zipBuffer = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(zipBuffer, CHARSET);

        byte[] buff = new byte[1024];

        try {
            for (Map.Entry<String, ByteArrayOutputStream> entry : map.entrySet()) {
                zipOutputStream.putNextEntry(new ZipEntry(entry.getKey()));
                ByteArrayInputStream byteArrayInputStream = null;
                try {
                    byteArrayInputStream = new ByteArrayInputStream(map.get(entry.getKey()).toByteArray());
                    int size;
                    while ((size = byteArrayInputStream.read(buff, 0, buff.length)) != -1) {
                        zipOutputStream.write(buff, 0, size);
                    }
                } finally {
                    if (byteArrayInputStream != null) {
                        byteArrayInputStream.close();
                    }
                }
            }
        } finally {
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            zipBuffer.close();
        }
        return new ByteArrayInputStream(zipBuffer.toByteArray());
    }


    public static void main(String[] args){
        ByteArrayOutputStream zipBuffer = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(zipBuffer);
        try {

            Map<String, ByteArrayOutputStream> streamMap = new HashMap<>(16);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write("test".getBytes());
            streamMap.put("textMapper.xml",byteArrayOutputStream);

            zipOutputStream.putNextEntry(new ZipEntry("com/code/generate/"));

            InputStream inputStream = zipOutputStreams(streamMap);
            File file = new File("C:\\Users\\Dell\\Desktop\\testZ.zip");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buff = new byte[1024 * 256];
            int length;
            while((length = inputStream.read(buff))!= -1){
                fileOutputStream.write(buff, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
