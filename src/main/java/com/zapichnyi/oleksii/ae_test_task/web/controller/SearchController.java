package com.zapichnyi.oleksii.ae_test_task.web.controller;

import com.zapichnyi.oleksii.ae_test_task.service.ImageService;
import com.zapichnyi.oleksii.ae_test_task.service.dto.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final ImageService imageService;
    @GetMapping("/{searchTerm}")
    public List<ImageDTO> getById(@PathVariable String searchTerm) {
        return imageService.getBySearchTerm(searchTerm);
    }
}
