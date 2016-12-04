package com.gtemate.petiteannoncekmer.service;

import com.gtemate.petiteannoncekmer.domain.Image;
import com.gtemate.petiteannoncekmer.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Image.
 */
@Service
@Transactional
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Inject
    private ImageRepository imageRepository;

    /**
     * Save a image.
     *
     * @param image the entity to save
     * @return the persisted entity
     */
    public Image save(Image image) {
        log.debug("Request to save Image : {}", image);
        Image result = imageRepository.save(image);
        return result;
    }

    /**
     *  Get all the images.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Image> findAll() {
        log.debug("Request to get all Images");
        List<Image> result = imageRepository.findAll();
        return result;
    }

    /**
     *  Get one image by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Image findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        Image image = imageRepository.findOne(id);
        return image;
    }

    /**
     *  Delete the  image by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.delete(id);
    }
}
