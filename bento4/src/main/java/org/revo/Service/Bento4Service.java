package org.revo.Service;

import org.revo.Domain.Media;

import java.io.IOException;

/**
 * Created by ashraf on 15/04/17.
 */
public interface Bento4Service {
    Media convertHls(Media media) throws IOException;
}
