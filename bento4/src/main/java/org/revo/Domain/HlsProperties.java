package org.revo.Domain;

import lombok.Getter;

import java.io.File;
import java.util.UUID;

@Getter
public class HlsProperties {
    private String id;
    private String source;

    private int version = 3;
    private int duration = 10;
    private int threshold = 15;
    private String mode = "random";

    private String key;
    private String executionDir;
    private String executable;
    private String indexFilename;
    private String keyFilename;
    private String segmentFilenameTemplate;
    private String segmentUrlTemplate;
    private String prefix;

    public HlsProperties(String source, String id, String prefix) {
        this.id = id;
        this.source = source;
        this.prefix = prefix;
        init();
    }

    private void init() {
        this.key = generateKey();
        this.executionDir = concatDir(new File(source).getParent(), id, this.prefix);
        this.executable = concatDir(System.getProperty("user.home"), "Bento4", "bin", "mp42hls");
        this.indexFilename = concatDir(executionDir, id + ".m3u8");
        this.keyFilename = id + ".key/";
        this.segmentFilenameTemplate = id + "-%d.ts";
        this.segmentFilenameTemplate = concatDir(executionDir, id + "-%d.ts");
        this.segmentUrlTemplate = id + "/" + this.prefix + "/" + id + "-%d.ts";
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    @Override
    public String toString() {
        return executable
                + " --hls-version " + version
                + " --segment-duration " + duration
                + " --segment-duration-threshold " + threshold
                + " --index-filename " + indexFilename
                + " --segment-filename-template " + segmentFilenameTemplate
                + " --segment-url-template " + segmentUrlTemplate
                + " --encryption-key " + key
                + " --encryption-iv-mode " + mode
                + " --encryption-key-uri " + keyFilename
                + " " + source;
    }

    private static String generateKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static String concatDir(String... l) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < l.length - 1; i++) {
            result.append(l[i]).append(File.separator);
        }
        return result.append(l[l.length - 1]).toString();
    }
}
