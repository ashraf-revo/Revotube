package org.revo.Service.Impl;

import com.comcast.viper.hlsparserj.PlaylistFactory;
import com.comcast.viper.hlsparserj.PlaylistVersion;
import com.comcast.viper.hlsparserj.tags.UnparsedTag;
import org.revo.Domain.Media;
import org.revo.Domain.Status;
import org.revo.Service.Bento4Service;
import org.revo.Service.S3Service;
import org.revo.Util.Hls;
import org.revo.Util.HlsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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
            List<UnparsedTag> tags = PlaylistFactory.parsePlaylist(PlaylistVersion.TWELVE, hlsResult.getM3u8()).getTags();
            double sum = tags
                    .stream().filter(it -> it.getTagName().equalsIgnoreCase("EXTINF"))
                    .map(it -> it.getAttributes().get("NONAME0"))
                    .mapToDouble(Double::parseDouble).sum();
            media.setTime(sum);
            media.setStatus(Status.SUCCESS);
            hlsResult.freeSpace();
        } else {
            media.setStatus(Status.FAIL);
            hlsResult.freeSpace();
        }
        return media;
    }

}
