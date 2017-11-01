package org.revo.Service;

import org.revo.Domain.Media;
import org.revo.Util.HlsResult;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by ashraf on 15/04/17.
 */
public interface S3Service {


    void saveTs(Path path, String key);

    String pull(Media media) throws IOException;

    void push(HlsResult hlsResult);
}
