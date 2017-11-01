package org.revo.Util;

import org.revo.Domain.HlsProperties;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by ashraf on 15/04/17.
 */
public class HlsResult {
    private HlsProperties hlsProperties;
    private String m3u8;
    private byte[] key;
    private boolean status;
    private String data;
    private String file;

    public static HlsResult BadResult() {
        return new HlsResult("", new byte[]{}, false);
    }

    public HlsResult(HlsProperties hlsProperties, boolean status) throws IOException {
        this.hlsProperties = hlsProperties;
        if (status) init(readFileToString(hlsProperties.getIndexFilename()), key(hlsProperties.getKey()), status);
        else init("", new byte[]{}, false);
    }

    private HlsResult(String m3u8, byte[] key, boolean status) {
        init(m3u8, key, status);
    }

    private void init(String m3u8, byte[] key, boolean status) {
        this.m3u8 = m3u8;
        this.key = key;
        this.status = status;
        this.data = this.hlsProperties.getExecutionDir();
        this.file = this.hlsProperties.getSource();
    }

    public String getM3u8() {
        return m3u8;
    }

    public byte[] getKey() {
        return key;
    }

    public boolean isStatus() {
        return status;
    }


    public void freeSpace() {
        deleteRecursively(new File(hlsProperties.getExecutionDir()).getParentFile());
    }

    private static byte[] key(String encryption_key) {
        final int len = encryption_key.length();
        byte[] out = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(encryption_key.charAt(i));
            int l = hexToBin(encryption_key.charAt(i + 1));

            out[i / 2] = (byte) (h * 16 + l);
        }
        return out;
    }

    private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        return -1;
    }

    public String getData() {
        return data;
    }

    public String getFile() {
        return file;
    }

    private static boolean deleteRecursively(File root) {
        if (root != null && root.exists()) {
            if (root.isDirectory()) {
                File[] children = root.listFiles();
                if (children != null) {
                    File[] var2 = children;
                    int var3 = children.length;

                    for (int var4 = 0; var4 < var3; ++var4) {
                        File child = var2[var4];
                        deleteRecursively(child);
                    }
                }
            }

            return root.delete();
        } else {
            return false;
        }
    }

    private static String readFileToString(String file) throws IOException {
        Scanner scanner = new Scanner(new File(file));
        String collect = "";
        while (scanner.hasNext()) {
            collect += scanner.nextLine();
            if (scanner.hasNext()) {
                collect += "\n";
            }
        }
        return collect;
    }
}
