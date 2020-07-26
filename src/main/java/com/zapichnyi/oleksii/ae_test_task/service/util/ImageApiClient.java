package com.zapichnyi.oleksii.ae_test_task.service.util;

import com.zapichnyi.oleksii.ae_test_task.data.model.Image;
import com.zapichnyi.oleksii.ae_test_task.data.repository.ImageRepository;
import com.zapichnyi.oleksii.ae_test_task.service.util.entity.ApiKeyContainer;
import com.zapichnyi.oleksii.ae_test_task.service.util.entity.AuthToken;
import com.zapichnyi.oleksii.ae_test_task.service.util.entity.ImageListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ImageApiClient {
    private final RestTemplate restTemplate;
    private final String GET_IMAGES_URL
            = "http://interview.agileengine.com:80/images?limit=20&page=";
    private final String GET_IMAGE_URL
            = "http://interview.agileengine.com:80/images/";
    private final String AUTH_URL =
            "http://interview.agileengine.com:80/auth";

    private String VALID_TOKEN;
    private final ImageRepository imageRepository;

    private class AuthErrorHandler extends DefaultResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                executeAuth();
            } else throw new RuntimeException();
        }
    }

    @Autowired
    public ImageApiClient(RestTemplate restTemplate, ImageRepository imageRepository) {
        this.restTemplate = restTemplate;
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    private void init() {
        executeAuth();
        restTemplate.setErrorHandler(new AuthErrorHandler());
        refreshCache();
    }

    @Scheduled(cron = "${cron.string}")
    public void refreshCache() {
        Set<Image> images = executeImagesRequest();
        imageRepository.saveAll(images);
    }

    private void executeAuth() {


        HttpEntity<ApiKeyContainer> request = new HttpEntity<>(new ApiKeyContainer());

        AuthToken authToken = restTemplate.postForObject(AUTH_URL, request, AuthToken.class);
        if (authToken == null || !authToken.isAuth()) {
            throw new RuntimeException();
        } else {
            VALID_TOKEN = authToken.getToken();

        }

    }

    private Set<Image> executeImagesRequest() {
        Set<Image> images = new HashSet<>();
        boolean hasMore = true;
        int pageNum = 1;
        while (hasMore) {
            ResponseEntity<ImageListWrapper> response = restTemplate.exchange(
                    GET_IMAGES_URL + pageNum, HttpMethod.GET, getEntityWithHeaders(), ImageListWrapper.class);
            Optional<ImageListWrapper> listWrapper = Optional.ofNullable(response.getBody());
            ImageListWrapper imageListWrapper = listWrapper.orElseThrow();
            int finalPageNum = pageNum;
            List<Image> page = imageListWrapper.getPictures().stream()
                    .map(this::adjustImage)
                    .peek(image -> image.setPageNum(finalPageNum))
                    .collect(Collectors.toList());

            images.addAll(page);

            if (response.getStatusCode().is2xxSuccessful()) {
                pageNum++;
                hasMore = imageListWrapper.isHasMore();
            }
        }
        return images;


    }

    private Image adjustImage(Image image) {
        ResponseEntity<Image> response = restTemplate.exchange(
                GET_IMAGE_URL + image.getId(), HttpMethod.GET, getEntityWithHeaders(), Image.class);
        Optional<Image> imageWrapper = Optional.ofNullable(response.getBody());
        Image detailed = imageWrapper.orElseThrow();
        image.setAuthor(detailed.getAuthor());
        image.setCamera(detailed.getCamera());
        image.setCropped_picture(detailed.getCropped_picture());
        image.setFull_picture(detailed.getFull_picture());
        image.setTags(detailed.getTags());
        return image;

    }

    private HttpEntity getEntityWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + VALID_TOKEN);
        return new HttpEntity(headers);
    }
}
