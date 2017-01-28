package com.gtemate.petiteannoncekmer.service;

import com.gtemate.petiteannoncekmer.domain.*;
import com.gtemate.petiteannoncekmer.domain.Image;
import com.gtemate.petiteannoncekmer.domain.Localisation;
import com.gtemate.petiteannoncekmer.domain.User;
import com.gtemate.petiteannoncekmer.repository.DeclarationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Declaration.
 */
@Service
@Transactional
public class DeclarationService extends BaseEntityService<Declaration> {

    private final Logger log = LoggerFactory.getLogger(DeclarationService.class);

    @Inject
    private DeclarationRepository declarationRepository;

    @Inject
    private UserService userService;

    @Inject
    private LocalisationService localisationService;

    @Inject
    private ImageService imageService;

    @Override
    public DeclarationRepository getRepository() {
        return declarationRepository;
    }

    @Override
    public JpaSpecificationExecutor<Declaration> getPagineableRepository() {
        return  getRepository();
    }
    /**
     *  Get all the declarations.
     *
     *  @return the list of entities
     */

    public Page<Declaration> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all Declarations");
        return declarationRepository.findByOwnerIsCurrentUser(pageable);
    }
    /**
     *  Get all the declarations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */

    public Page<Declaration> findAll(Pageable pageable) {
        log.debug("Request to get all Declarations");
        return declarationRepository.findAll(pageable);
    }


    /**
     *  Get number per region.
     *
     *  @param IdRegion code of region
     *  @return number of declaration
     */
    public Long countAllPerRegion(String IdRegion) {
        log.debug("Request to get all Declarations");
       Long result = declarationRepository.count((root, criteriaQuery, criteriaBuilder) ->
               criteriaBuilder.and(
           criteriaBuilder.equal(root.get(Declaration_.isPublished),true), // count only published declarations
           criteriaBuilder.equal(root.get(Declaration_.localisation)  // and only declarations of the specified region
               .get(Localisation_.region)
               .get(Region_.code),IdRegion)));
        return result;
    }


    public Page<Declaration> getAllDeclarationsByRegion(Pageable pageable,String IdRegion) {
        log.debug("Request to get all Declarations By Region");
        return declarationRepository.findAllDeclarationsByRegion(pageable,IdRegion);
    }



    public Declaration saveDeclarationUser(Declaration declaration,
                                    Localisation localisation,
                                    String login,
                                    MultipartFile[] images) {

        User currentUser = userService.getByLogin(login); // get declaration user
        if(currentUser != null)
        declaration.setOwner(currentUser); // define declaration user

        declaration.setLocalisation(localisation);  // set localisation
        declaration.setLastModifiedDate(ZonedDateTime.now());
        declaration.setIsPublished(false);
        declaration.setCreationDate(ZonedDateTime.now());
        Optional<Image> image = null;

        if(images.length > 0) {
              //generate thumbnail from first image
            Image min = imageService.getThumbNail(images[0]);
            declaration.setMiniature(min);
        }

        // add every images to the declaration
        for (MultipartFile multipartFile : images) {
            // save  image
            image = imageService.createImageFromMultipartFile(multipartFile);
            // attach image to declaration
            image.ifPresent(declaration::addImages);
        }

        localisationService.save(localisation);
        declarationRepository.save(declaration);

        return declaration;
    }
}
