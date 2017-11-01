package org.revo.Util;

import com.comcast.viper.hlsparserj.tags.UnparsedTag;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Created by ashraf on 16/04/17.
 */
public class Utils {
    public static String TOString(List<UnparsedTag> tags, Function<String, String> function) {
        StringBuilder builder = new StringBuilder();
        for (Iterator i$ = tags.iterator(); i$.hasNext(); builder.append("\n")) {
            UnparsedTag tag = (UnparsedTag) i$.next();
            builder.append(tag.getRawTag());
            if (tag.getURI() != null && tag.getTagName().equals("EXTINF"))
                builder.append("\n").append(function.apply(tag.getURI()));
        }
        return builder.toString();
    }

    public static void copyRecursively(File src, File dest) throws IOException {
        doCopyRecursively(src, dest);
    }

    private static void doCopyRecursively(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            dest.mkdir();
            File[] ex = src.listFiles();
            if (ex == null) {
                throw new IOException("Could not list files in directory: " + src);
            }

            File[] ioex = ex;
            int var4 = ex.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                File entry = ioex[var5];
                doCopyRecursively(entry, new File(dest, entry.getName()));
            }
        } else if (src.isFile()) {
            try {
                dest.createNewFile();
            } catch (IOException var7) {
                IOException var8 = new IOException("Failed to create file: " + dest);
                var8.initCause(var7);
                throw var8;
            }

            FileCopyUtils.copy(src, dest);
        }

    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        } else {
            int index = indexOfExtension(filename);
            return index == -1 ? "" : filename.substring(index + 1);
        }
    }

    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int extensionPos = filename.lastIndexOf(46);
            int lastSeparator = indexOfLastSeparator(filename);
            return lastSeparator > extensionPos ? -1 : extensionPos;
        }
    }

    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int lastUnixPos = filename.lastIndexOf(47);
            int lastWindowsPos = filename.lastIndexOf(92);
            return Math.max(lastUnixPos, lastWindowsPos);
        }
    }

    public static boolean deleteRecursively(File root) {
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

    public static String getFilename(String path) {
        if (path == null) {
            return null;
        } else {
            int separatorIndex = path.lastIndexOf("/");
            return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
        }
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

    public static byte[] key(String encryption_key) {
        final int len = encryption_key.length();
        byte[] out = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(encryption_key.charAt(i));
            int l = hexToBin(encryption_key.charAt(i + 1));

            out[i / 2] = (byte) (h * 16 + l);
        }
        return out;
    }

    public static String readFileToString(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        String collect = "";
        while (scanner.hasNext()) {
            collect += scanner.nextLine();
            if (scanner.hasNext()) {
                collect += "\n";
            }
        }
        return collect;
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File \'" + file + "\' exists but is a directory");
            } else if (!file.canRead()) {
                throw new IOException("File \'" + file + "\' cannot be read");
            } else {
                return new FileInputStream(file);
            }
        } else {
            throw new FileNotFoundException("File \'" + file + "\' does not exist");
        }
    }
}
