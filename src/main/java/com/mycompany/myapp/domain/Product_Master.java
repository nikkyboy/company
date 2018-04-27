package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A Product_Master.
 */
@Table(name = "product_Master")
public class Product_Master implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private Integer product_id;

    private String product_Name;

    private Integer company_id;

    private BigDecimal unit_Price;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public Product_Master product_id(Integer product_id) {
        this.product_id = product_id;
        return this;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public Product_Master product_Name(String product_Name) {
        this.product_Name = product_Name;
        return this;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public Product_Master company_id(Integer company_id) {
        this.company_id = company_id;
        return this;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public BigDecimal getUnit_Price() {
        return unit_Price;
    }

    public Product_Master unit_Price(BigDecimal unit_Price) {
        this.unit_Price = unit_Price;
        return this;
    }

    public void setUnit_Price(BigDecimal unit_Price) {
        this.unit_Price = unit_Price;
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
        Product_Master product_Master = (Product_Master) o;
        if (product_Master.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product_Master.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product_Master{" +
            "id=" + getId() +
            ", product_id=" + getProduct_id() +
            ", product_Name='" + getProduct_Name() + "'" +
            ", company_id=" + getCompany_id() +
            ", unit_Price=" + getUnit_Price() +
            "}";
    }
}
