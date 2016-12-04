package com.gtemate.petiteannoncekmer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Localisation.
 */
@Entity
@Table(name = "localisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Localisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Column(name = "region", nullable = false)
    private String region;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "village")
    private String village;

    @Column(name = "area")
    private String area;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "special_adress")
    private String specialAdress;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public Localisation country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public Localisation region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public Localisation city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVillage() {
        return village;
    }

    public Localisation village(String village) {
        this.village = village;
        return this;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getArea() {
        return area;
    }

    public Localisation area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreetName() {
        return streetName;
    }

    public Localisation streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public Localisation streetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Localisation postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSpecialAdress() {
        return specialAdress;
    }

    public Localisation specialAdress(String specialAdress) {
        this.specialAdress = specialAdress;
        return this;
    }

    public void setSpecialAdress(String specialAdress) {
        this.specialAdress = specialAdress;
    }

    public User getUser() {
        return user;
    }

    public Localisation user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Localisation localisation = (Localisation) o;
        if (localisation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, localisation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Localisation{" +
            "id=" + id +
            ", country='" + country + "'" +
            ", region='" + region + "'" +
            ", city='" + city + "'" +
            ", village='" + village + "'" +
            ", area='" + area + "'" +
            ", streetName='" + streetName + "'" +
            ", streetNumber='" + streetNumber + "'" +
            ", postalCode='" + postalCode + "'" +
            ", specialAdress='" + specialAdress + "'" +
            '}';
    }
}
