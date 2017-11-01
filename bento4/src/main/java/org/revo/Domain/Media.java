package org.revo.Domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by ashraf on 15/04/17.
 */
@Getter
@Setter
public class Media {
    private String id;
    private String m3u8;
    private String image;
    private double time;
    private String meta;
    private byte[] secret;
    private Status status = Status.BINDING;
    private String user;
    private Date createdDate = new Date();
}