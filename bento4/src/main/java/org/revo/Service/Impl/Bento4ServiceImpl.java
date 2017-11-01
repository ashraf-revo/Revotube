package org.revo.Service.Impl;

import org.revo.Domain.Media;
import org.revo.Domain.Status;
import org.revo.Service.Bento4Service;
import org.revo.Service.S3Service;
import org.revo.Util.Hls;
import org.revo.Util.HlsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ashraf on 15/04/17.
 */
@Service
public class Bento4ServiceImpl implements Bento4Service {
    @Autowired
    private S3Service s3Service;


    @Override
    public Media convertHls(Media media) throws IOException {
        String source = s3Service.pull(media);
        HlsResult hlsResult = new Hls(source, media.getId()).execute();
        if (hlsResult.isStatus()) {
            s3Service.push(hlsResult);
            media.setM3u8(hlsResult.getM3u8());
            media.setSecret(hlsResult.getKey());
            media.setStatus(Status.SUCCESS);
            hlsResult.freeSpace();
        } else {
            media.setStatus(Status.FAIL);
            hlsResult.freeSpace();
        }
        return media;
    }

}
