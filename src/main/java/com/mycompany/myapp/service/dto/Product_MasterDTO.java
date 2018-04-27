package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Product_Master entity.
 */
public class Product_MasterDTO implements Serializable {

    private UUID id;

    private Integer product_id;

    private String product_Name;

    private Integer company_id;

    private BigDecimal unit_Price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public BigDecimal getUnit_Price() {
        return unit_Price;
    }

    public void setUnit_Price(BigDecimal unit_Price) {
        this.unit_Price = unit_Price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product_MasterDTO product_MasterDTO = (Product_MasterDTO) o;
        if(product_MasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product_MasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product_MasterDTO{" +
            "id=" + getId() +
            ", product_id=" + getProduct_id() +
            ", product_Name='" + getProduct_Name() + "'" +
            ", company_id=" + getCompany_id() +
            ", unit_Price=" + getUnit_Price() +
            "}";
    }
}
