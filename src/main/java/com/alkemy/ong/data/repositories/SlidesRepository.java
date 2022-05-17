package com.alkemy.ong.data.repositories;


import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.domain.organizations.Organization;
import com.alkemy.ong.domain.slide.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlidesRepository extends JpaRepository<SlidesEntity,Long> {

    List<SlidesEntity> findByOrganizationOrderBySlideOrderAsc(OrganizationEntity organization);

    @Query(value="SELECT max(slide_order) FROM slides ", nativeQuery=true)
    Integer findOrder();

}
