package org.revo.Service;

import org.revo.Domain.Media;
import org.revo.Domain.Search;

import java.io.IOException;
import java.util.List;

public interface MediaService {
    void index(Media media) throws IOException;

    List<Media> search(Search search) throws IOException;

    void delete(String id) throws IOException;

    Media findOne(String id) throws IOException;
}
