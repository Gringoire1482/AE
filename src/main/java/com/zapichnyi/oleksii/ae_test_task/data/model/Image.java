package com.zapichnyi.oleksii.ae_test_task.data.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "images")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicInsert
@DynamicUpdate
public class Image {
    @EqualsAndHashCode.Include
    @Id
    private String id;

    private String author;

    private String camera;

    @Column(name = "tag_content")
    private String tags;

    private String cropped_picture;

    private String full_picture;

    private int pageNum;
}
