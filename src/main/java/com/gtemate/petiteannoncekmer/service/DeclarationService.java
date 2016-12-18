package com.gtemate.petiteannoncekmer.service;

import com.gtemate.petiteannoncekmer.domain.Declaration;
import com.gtemate.petiteannoncekmer.domain.Localisation;
import com.gtemate.petiteannoncekmer.domain.User;
import com.gtemate.petiteannoncekmer.repository.DeclarationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;

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
    @Transactional(readOnly = true)
    public Page<Declaration> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all Declarations");
        Page<Declaration>  result = declarationRepository.findByOwnerIsCurrentUser(pageable);
        return result;
    }
    /**
     *  Get all the declarations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Declaration> findAll(Pageable pageable) {
        log.debug("Request to get all Declarations");
        Page<Declaration> result = declarationRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Declaration> getAllDeclarationsByRegion(Pageable pageable,String IdRegion) {
        log.debug("Request to get all Declarations");
        Page<Declaration> result = declarationRepository.findAllDeclarationsByRegion(pageable,IdRegion);
        return result;
    }

    @Transactional
    public Declaration saveDeclarationUser(Declaration declaration,
                                    User user,
                                    Localisation localisation,
                                    MultipartFile[] images) {
        User newUser = userService.getByLogin(user.getLogin());
        declaration.setOwner(newUser);
        declaration.setLocalisation(localisation);
        declaration.setLastModifiedDate(ZonedDateTime.now());
        declaration.setIsPublished(false);
        declaration.setCreationDate(ZonedDateTime.now());

        localisationService.save(localisation);
        declarationRepository.save(declaration);

        return declaration;
    }
}
