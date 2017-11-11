package org.revo.Service;

import org.revo.Domain.Media;
import org.revo.Domain.Status;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ashraf on 15/04/17.
 */
public interface MediaService {

    File saveInFileSystem(Media media) throws IOException;

    Media saveFile(Media media);

    Media save(Media media);

    List<Media> findAll(Status status);

    Media findOne(String id);

    String findOneParsed(String id);

    String lastId();

    List<Media> findByUser(String id, Status status);

    /*
        Media publish(Media media, User user);
    */
    File convertImage(File video);

    Media process(Media media) throws IOException;
}
