package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Company_Master.
 */
@Table(name = "company_Master")
public class Company_Master implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private Integer company_id;

    private String company_Name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public Company_Master company_id(Integer company_id) {
        this.company_id = company_id;
        return this;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getCompany_Name() {
        return company_Name;
    }

    public Company_Master company_Name(String company_Name) {
        this.company_Name = company_Name;
        return this;
    }

    public void setCompany_Name(String company_Name) {
        this.company_Name = company_Name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company_Master company_Master = (Company_Master) o;
        if (company_Master.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company_Master.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company_Master{" +
            "id=" + getId() +
            ", company_id=" + getCompany_id() +
            ", company_Name='" + getCompany_Name() + "'" +
            "}";
    }
}
