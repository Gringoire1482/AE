package com.zapichnyi.oleksii.ae_test_task.service.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private ImagePreview preview;

    private String author;

    private String camera;

    private String tagContent;

    private String full_picture;
}
