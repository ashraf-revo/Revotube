package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
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
    private String id;
    private String m3u8;
    private double time;
    private String image;
    private String meta;
    private byte[] secret;
    private Status status = Status.BINDING;
    @CreatedBy
    private String userId;
    private User user;
    @CreatedDate
    private Date createdDate = new Date();
    private String title;
    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile file;
}