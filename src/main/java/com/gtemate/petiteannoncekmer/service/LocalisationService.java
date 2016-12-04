package com.gtemate.petiteannoncekmer.service;

import com.gtemate.petiteannoncekmer.domain.Localisation;
import com.gtemate.petiteannoncekmer.repository.LocalisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class LocalisationService {

    private final Logger log = LoggerFactory.getLogger(LocalisationService.class);

    @Inject
    LocalisationRepository  localisationRepository;

    /**
     * Save a localisation.
     *
     * @param localisation the entity to save
     * @return the persisted entity
     */
    public Localisation save(Localisation localisation) {
        log.debug("Request to save localisation : {}", localisation);
        Localisation result = localisationRepository.save(localisation);
        return result;
    }

    /**
     *  Get all the localisation.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Localisation> findAll() {
        log.debug("Request to get all localisations");
        List<Localisation> result = localisationRepository.findAll();
        return result;
    }

    /**
     *  Get one localisation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Localisation findOne(Long id) {
        log.debug("Request to get localisation : {}", id);
        Localisation localisation = localisationRepository.findOne(id);
        return localisation;
    }

    /**
     *  Delete the localisation by id.
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete localisation : {}", id);
        localisationRepository.delete(id);
    }

    /**
     *  Get all the Localisation.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Localisation> findByUserIsCurrentUser() {
        log.debug("Request to get all Localisation");
        List<Localisation>  result = localisationRepository.findByUserIsCurrentUser();
        return result;
    }
}
