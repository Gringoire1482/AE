package com.zapichnyi.oleksii.ae_test_task.service.util.entity;

import com.zapichnyi.oleksii.ae_test_task.data.model.Image;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageListWrapper {
   private List<Image> pictures = new ArrayList<>();
   private boolean hasMore;
}
