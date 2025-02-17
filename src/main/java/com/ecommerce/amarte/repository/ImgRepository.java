package com.ecommerce.amarte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.amarte.entity.Img;

@Repository
public interface ImgRepository extends JpaRepository <Img, Long> {
       List<Img> findByProductId(Long userId);

    
}
