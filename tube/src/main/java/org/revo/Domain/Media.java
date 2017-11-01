package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.revo.Util.UtilView;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Created by ashraf on 15/04/17.
 */
@Getter
@Setter
@Document
public class Media {
    @Id
    @JsonView(UtilView.Media.class)
    private String id;
    private String m3u8;
    @JsonView(UtilView.Media.class)
    private double time;
    @JsonView(UtilView.Media.class)
    private String image;
    @JsonView(UtilView.Media.class)
    private String meta;
    private byte[] secret;
    @JsonView(UtilView.Media.class)
    private Status status = Status.BINDING;
    @CreatedBy
    @JsonView(UtilView.Media.class)
    private String user;
    @CreatedDate
    @JsonView(UtilView.Media.class)
    private Date createdDate = new Date();
    @JsonView(UtilView.Media.class)
    private String title;
    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile file;
}