package com.zapichnyi.oleksii.ae_test_task.web.controller;

import com.zapichnyi.oleksii.ae_test_task.service.ImageService;
import com.zapichnyi.oleksii.ae_test_task.service.dto.ImageDTO;
import com.zapichnyi.oleksii.ae_test_task.service.dto.ImagePreview;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{imageId}")
    public ImageDTO getById(@PathVariable String imageId) {
        return imageService.getById(imageId);
    }

    @GetMapping()
    public List<ImagePreview> getById(@RequestParam(required = false,defaultValue = "1") Integer pageNum) {
        return imageService.getAll(pageNum);
    }
}
