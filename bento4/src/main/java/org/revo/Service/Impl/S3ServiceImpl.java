package org.revo.Service.Impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import org.revo.Domain.Bucket;
import org.revo.Domain.Media;
import org.revo.Service.S3Service;
import org.revo.Util.HlsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by ashraf on 15/04/17.
 */
@Service
public class S3ServiceImpl implements S3Service {
    @Autowired
    private AmazonS3Client amazonS3Client;

    @Override
    public void saveTs(Path path, String key) {
        this.amazonS3Client.putObject(Bucket.Ts.toString(), key, path.toFile());
    }

    @Override
    public String pull(Media media) throws IOException {
        S3Object object = this.amazonS3Client.getObject(Bucket.Videos.toString(), media.getId() + "");
        Path temp = Files.createTempDirectory("temp");
        Path f = Paths.get(temp.toString(), UUID.randomUUID().toString().replace("-", "") + media.getId() + "");
        Files.copy(object.getObjectContent(), f);
        return f.toString();
    }

    @Override
    public void push(HlsResult hlsResult) {
        try {
            Files.walk(Paths.get(hlsResult.getData()))
                    .filter(Files::isRegularFile)
                    .filter(it -> it.toString().endsWith("ts"))
                    .peek(System.out::println)
                    .forEach(path -> {
                        String key = path.toString().substring(new File(hlsResult.getFile()).getParent().length() + 1, path.toString().length());
                        saveTs(path, key);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
