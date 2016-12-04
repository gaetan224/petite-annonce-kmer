package com.gtemate.petiteannoncekmer.service;

import com.gtemate.petiteannoncekmer.domain.Declaration;
import com.gtemate.petiteannoncekmer.repository.DeclarationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Declaration.
 */
@Service
@Transactional
public class DeclarationService {

    private final Logger log = LoggerFactory.getLogger(DeclarationService.class);

    @Inject
    private DeclarationRepository declarationRepository;

    /**
     * Save a declaration.
     *
     * @param declaration the entity to save
     * @return the persisted entity
     */
    public Declaration save(Declaration declaration) {
        log.debug("Request to save Declaration : {}", declaration);
        Declaration result = declarationRepository.save(declaration);
        return result;
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


    /**
     *  Get one declaration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Declaration findOne(Long id) {
        log.debug("Request to get Declaration : {}", id);
        Declaration declaration = declarationRepository.findOne(id);
        return declaration;
    }

    /**
     *  Delete the  declaration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Declaration : {}", id);
        declarationRepository.delete(id);
    }
}
