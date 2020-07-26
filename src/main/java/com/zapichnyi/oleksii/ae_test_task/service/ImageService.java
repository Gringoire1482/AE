package com.zapichnyi.oleksii.ae_test_task.service;

import com.zapichnyi.oleksii.ae_test_task.service.dto.ImageDTO;
import com.zapichnyi.oleksii.ae_test_task.service.dto.ImagePreview;

import java.util.List;

public interface ImageService {
    ImageDTO getById(String id);

    List<ImagePreview> getAll(int page);

    List<ImageDTO> getBySearchTerm(String serachTerm);
}
