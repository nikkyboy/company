package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Company_Master entity.
 */
public class Company_MasterDTO implements Serializable {

    private UUID id;

    private Integer company_id;

    private String company_Name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getCompany_Name() {
        return company_Name;
    }

    public void setCompany_Name(String company_Name) {
        this.company_Name = company_Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company_MasterDTO company_MasterDTO = (Company_MasterDTO) o;
        if(company_MasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company_MasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company_MasterDTO{" +
            "id=" + getId() +
            ", company_id=" + getCompany_id() +
            ", company_Name='" + getCompany_Name() + "'" +
            "}";
    }
}
