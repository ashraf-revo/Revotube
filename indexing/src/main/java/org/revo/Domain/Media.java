package org.revo.Domain;

import io.searchbox.annotations.JestId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by ashraf on 15/04/17.
 */
@Getter
@Setter
@Builder
@ToString
public class Media {
    @JestId
    private String id;
    private String image;
    private String user;
    private String title;
    private String meta;
    private Date createdDate = new Date();
}