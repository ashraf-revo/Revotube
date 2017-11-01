package org.revo.Util;


import org.revo.Domain.HlsProperties;

import java.io.File;
import java.io.IOException;

import static org.revo.Util.HlsResult.BadResult;
import static org.revo.Util.Utils.copyRecursively;
import static org.springframework.util.FileSystemUtils.deleteRecursively;


/**
 * Created by ashraf on 14/04/17.
 */
public class Hls {
    private HlsProperties hlsProperties;

    public Hls(String source, String id) {
        this.hlsProperties = new HlsProperties(source, id, "exc");
    }

    @Override
    public String toString() {
        return this.hlsProperties.toString();
    }

    public HlsResult execute() {
        try {
            String[] CmdBento4 = {"/bin/sh", "-c", this.toString()};
            ProcessBuilder processBuilder = new ProcessBuilder(CmdBento4);
            new File(this.hlsProperties.getExecutionDir()).getParentFile().mkdirs();
            new File(this.hlsProperties.getExecutionDir()).mkdirs();
            new File(this.hlsProperties.getIndexFilename()).createNewFile();

            Process start = processBuilder.start();
            int result = start.waitFor();
            return new HlsResult(hlsProperties, result == 0);
        } catch (IOException | InterruptedException e) {
            System.out.println("oooooo"+e.getMessage());
        }
        return BadResult();
    }

    public static void init(File bento4) {
        try {
            String Bento4 = System.getProperty("user.home") + File.separator + "Bento4";
            File path = new File(Bento4);
            deleteRecursively(path);
            path.mkdir();
            copyRecursively(bento4, path);

            String s = Bento4 + File.separator + "bin" + File.separator;
            String[] CmdChmod = {"chmod", "-R", "+xr", s};
            new ProcessBuilder(CmdChmod).start();
        } catch (IOException e) {
            System.out.println("***********************************"+e.getMessage());
        }
}}
