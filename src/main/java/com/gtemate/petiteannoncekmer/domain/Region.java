package com.gtemate.petiteannoncekmer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 04/12/2016.
 */
@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Region extends BaseEntity {


    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @ManyToOne
    private Country country;

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Region{" +
            "name='" + name + '\'' +
            ", country=" + country +
            '}';
    }
}
