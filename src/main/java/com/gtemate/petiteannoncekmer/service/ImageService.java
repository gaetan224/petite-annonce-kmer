package com.gtemate.petiteannoncekmer.service;

import com.gtemate.petiteannoncekmer.domain.Image;
import com.gtemate.petiteannoncekmer.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Image.
 */
@Service
@Transactional
public class ImageService extends BaseEntityService<Image> {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Inject
    private ImageRepository imageRepository;

    @Override
    public ImageRepository getRepository() {
        return imageRepository;
    }

    @Override
    public JpaSpecificationExecutor<Image> getPagineableRepository() {
        return  getRepository();
    }

    /**
     *  Get all the images.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Image> getByDeclaration(Long id) {
        log.debug("Request to get Declaration");
        List<Image> result = imageRepository.findByDeclaration(id);
        return result;
    }
}
