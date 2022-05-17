package com.alkemy.ong.domain.slide;

import java.util.List;

public interface SlideGateway {

    Slide findById(Long id);

    void delete(Long id);

    List <Slide> findAll();

    Slide save(Slide slide, Long organizationId);

    Slide toUpdate(Long id, Slide slide,Long organizationId);
}
