package com.zapichnyi.oleksii.ae_test_task.service.impl;

import com.zapichnyi.oleksii.ae_test_task.data.model.Image;
import com.zapichnyi.oleksii.ae_test_task.data.repository.ImageRepository;
import com.zapichnyi.oleksii.ae_test_task.service.ImageService;
import com.zapichnyi.oleksii.ae_test_task.service.dto.ImageDTO;
import com.zapichnyi.oleksii.ae_test_task.service.dto.ImagePreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public ImageDTO getById(String id) {
        return mapToDto(imageRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ImagePreview> getAll(int page) {
        return imageRepository.findAllByPageNum(page).stream()
                .map(this::mapToPreview)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> getBySearchTerm(String serachTerm) {
        return imageRepository.findByTerm(serachTerm).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ImageDTO mapToDto(Image image) {
        ImageDTO imageDto = new ImageDTO();
        imageDto.setPreview(mapToPreview(image));
        imageDto.setAuthor(image.getAuthor());
        imageDto.setCamera(image.getCamera());
        imageDto.setFull_picture(image.getFull_picture());
        imageDto.setTagContent(image.getTags());
        return  imageDto;
    }

    private ImagePreview mapToPreview(Image image) {
        ImagePreview preview = new ImagePreview();
        preview.setCropped_picture(image.getCropped_picture());
        preview.setId(image.getId());
        return preview;
    }
}
