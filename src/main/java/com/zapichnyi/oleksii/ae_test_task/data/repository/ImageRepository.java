package com.zapichnyi.oleksii.ae_test_task.data.repository;

import com.zapichnyi.oleksii.ae_test_task.data.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository< Image,String> {
}
