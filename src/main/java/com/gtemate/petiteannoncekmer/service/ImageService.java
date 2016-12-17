package com.gtemate.petiteannoncekmer.service;

import com.gtemate.petiteannoncekmer.domain.Image;
import com.gtemate.petiteannoncekmer.repository.ImageRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
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

    /**
     *  Get all the images.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Image getThumbNail(Long id) {
        Image image = imageRepository.findOne(id);
        File tempFile = null;
        Image res = null;
        try {
            tempFile = File.createTempFile("", "", null);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(image.getContent());

            Thumbnails.of(tempFile).size(100,100).toFile(tempFile);
            res = new Image();
            byte[] array = Files.readAllBytes(tempFile.toPath());
            res.setContent(array);

        } catch (Exception e) {
            e.printStackTrace();
        }



        return res;
    }
}
