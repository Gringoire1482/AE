package com.zapichnyi.oleksii.ae_test_task.data.repository;

import com.zapichnyi.oleksii.ae_test_task.data.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findAllByPageNum(int pageNum);

    @Query("select i" +
            " from Image i" +
            " where lower(i.author) like lower( CONCAT('%',?1,'%'))" +
            " or  lower( i.camera) like lower( CONCAT('%',?1,'%'))" +
            " or  lower(i.tags) like lower( CONCAT('%',?1,'%'))")
    List<Image> findByTerm(String term);
}
