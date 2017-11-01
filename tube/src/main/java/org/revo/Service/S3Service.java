package org.revo.Service;

import org.revo.Domain.Bucket;

import java.io.File;

/**
 * Created by ashraf on 15/04/17.
 */
public interface S3Service {
    String generateTsUrl(String key);

    void pushMedia(String key, File file);

    String getUrl(Bucket bucket, String key);

    String pushImage(String key, File file);
}
