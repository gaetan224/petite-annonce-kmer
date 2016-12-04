package com.gtemate.petiteannoncekmer.web.rest;

import com.gtemate.petiteannoncekmer.PetiteAnnonceKmerApp;

import com.gtemate.petiteannoncekmer.domain.Localisation;
import com.gtemate.petiteannoncekmer.repository.LocalisationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LocalisationResource REST controller.
 *
 * @see LocalisationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PetiteAnnonceKmerApp.class)
public class LocalisationResourceIntTest {

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_ADRESS = "BBBBBBBBBB";

    @Inject
    private LocalisationRepository localisationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLocalisationMockMvc;

    private Localisation localisation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocalisationResource localisationResource = new LocalisationResource();
        ReflectionTestUtils.setField(localisationResource, "localisationRepository", localisationRepository);
        this.restLocalisationMockMvc = MockMvcBuilders.standaloneSetup(localisationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createEntity(EntityManager em) {
        Localisation localisation = new Localisation()
                .country(DEFAULT_COUNTRY)
                .region(DEFAULT_REGION)
                .city(DEFAULT_CITY)
                .village(DEFAULT_VILLAGE)
                .area(DEFAULT_AREA)
                .streetName(DEFAULT_STREET_NAME)
                .streetNumber(DEFAULT_STREET_NUMBER)
                .postalCode(DEFAULT_POSTAL_CODE)
                .specialAdress(DEFAULT_SPECIAL_ADRESS);
        return localisation;
    }

    @Before
    public void initTest() {
        localisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalisation() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // Create the Localisation

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isCreated());

        // Validate the Localisation in the database
        List<Localisation> localisations = localisationRepository.findAll();
        assertThat(localisations).hasSize(databaseSizeBeforeCreate + 1);
        Localisation testLocalisation = localisations.get(localisations.size() - 1);
        assertThat(testLocalisation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testLocalisation.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testLocalisation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testLocalisation.getVillage()).isEqualTo(DEFAULT_VILLAGE);
        assertThat(testLocalisation.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testLocalisation.getStreetName()).isEqualTo(DEFAULT_STREET_NAME);
        assertThat(testLocalisation.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
        assertThat(testLocalisation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testLocalisation.getSpecialAdress()).isEqualTo(DEFAULT_SPECIAL_ADRESS);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setCountry(null);

        // Create the Localisation, which fails.

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisations = localisationRepository.findAll();
        assertThat(localisations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setRegion(null);

        // Create the Localisation, which fails.

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisations = localisationRepository.findAll();
        assertThat(localisations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setCity(null);

        // Create the Localisation, which fails.

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisations = localisationRepository.findAll();
        assertThat(localisations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalisations() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get all the localisations
        restLocalisationMockMvc.perform(get("/api/localisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].village").value(hasItem(DEFAULT_VILLAGE.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())))
            .andExpect(jsonPath("$.[*].streetName").value(hasItem(DEFAULT_STREET_NAME.toString())))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].specialAdress").value(hasItem(DEFAULT_SPECIAL_ADRESS.toString())));
    }

    @Test
    @Transactional
    public void getLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localisation.getId().intValue()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.village").value(DEFAULT_VILLAGE.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()))
            .andExpect(jsonPath("$.streetName").value(DEFAULT_STREET_NAME.toString()))
            .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.specialAdress").value(DEFAULT_SPECIAL_ADRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalisation() throws Exception {
        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findOne(localisation.getId());
        updatedLocalisation
                .country(UPDATED_COUNTRY)
                .region(UPDATED_REGION)
                .city(UPDATED_CITY)
                .village(UPDATED_VILLAGE)
                .area(UPDATED_AREA)
                .streetName(UPDATED_STREET_NAME)
                .streetNumber(UPDATED_STREET_NUMBER)
                .postalCode(UPDATED_POSTAL_CODE)
                .specialAdress(UPDATED_SPECIAL_ADRESS);

        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalisation)))
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisations = localisationRepository.findAll();
        assertThat(localisations).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisations.get(localisations.size() - 1);
        assertThat(testLocalisation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testLocalisation.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testLocalisation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testLocalisation.getVillage()).isEqualTo(UPDATED_VILLAGE);
        assertThat(testLocalisation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLocalisation.getStreetName()).isEqualTo(UPDATED_STREET_NAME);
        assertThat(testLocalisation.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testLocalisation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testLocalisation.getSpecialAdress()).isEqualTo(UPDATED_SPECIAL_ADRESS);
    }

    @Test
    @Transactional
    public void deleteLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);
        int databaseSizeBeforeDelete = localisationRepository.findAll().size();

        // Get the localisation
        restLocalisationMockMvc.perform(delete("/api/localisations/{id}", localisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Localisation> localisations = localisationRepository.findAll();
        assertThat(localisations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
