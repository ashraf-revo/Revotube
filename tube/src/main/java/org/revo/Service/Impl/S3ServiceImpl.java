package org.revo.Service.Impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.FrameGrab;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.NIOUtils;
import org.revo.Domain.Bucket;
import org.revo.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by ashraf on 15/04/17.
 */
@Service
public class S3ServiceImpl implements S3Service {
    @Autowired
    private AmazonS3Client amazonS3Client;

    @Override
    public String generateTsUrl(String key) {
        return getUrlExpired(Bucket.Ts, key);
    }

    private String getUrlExpired(Bucket bucket, String key) {
        java.util.Date expiration = new java.util.Date();
        long msec = expiration.getTime();
        msec += 1000 * 60 * 60;
        expiration.setTime(msec);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket.toString(), key);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expiration);
        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    @Override
    public void pushMedia(String key, File file) {
            this.amazonS3Client.putObject(Bucket.Videos.toString(), key, file);
    }

    @Override
    public String pushImage(String key, File file) {
        this.amazonS3Client.putObject(Bucket.Thumb.toString(), key, file);
        return getUrl(Bucket.Thumb, key);
    }

    @Override
    public String getUrl(Bucket bucket, String key) {
        return amazonS3Client.getUrl(bucket.toString(), key).toString();
    }


}
