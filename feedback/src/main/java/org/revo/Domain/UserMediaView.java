package org.revo.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@Document
public class UserMediaView {
    @Id
    private String id;
    @CreatedBy
    private String user;
    private String media;
    @CreatedDate
    private Date createdDate = new Date();
}
