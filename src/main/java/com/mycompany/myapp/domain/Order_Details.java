package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A Order_Details.
 */
@Table(name = "order_Details")
public class Order_Details implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private Integer order_id;

    private LocalDate order_Date;

    private Integer company_id;

    private Integer product_id;

    private Integer quantity;

    private BigDecimal total;

    private BigDecimal vat;

    private BigDecimal order_value;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public Order_Details order_id(Integer order_id) {
        this.order_id = order_id;
        return this;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public LocalDate getOrder_Date() {
        return order_Date;
    }

    public Order_Details order_Date(LocalDate order_Date) {
        this.order_Date = order_Date;
        return this;
    }

    public void setOrder_Date(LocalDate order_Date) {
        this.order_Date = order_Date;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public Order_Details company_id(Integer company_id) {
        this.company_id = company_id;
        return this;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public Order_Details product_id(Integer product_id) {
        this.product_id = product_id;
        return this;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order_Details quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Order_Details total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public Order_Details vat(BigDecimal vat) {
        this.vat = vat;
        return this;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getOrder_value() {
        return order_value;
    }

    public Order_Details order_value(BigDecimal order_value) {
        this.order_value = order_value;
        return this;
    }

    public void setOrder_value(BigDecimal order_value) {
        this.order_value = order_value;
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
        Order_Details order_Details = (Order_Details) o;
        if (order_Details.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), order_Details.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Order_Details{" +
            "id=" + getId() +
            ", order_id=" + getOrder_id() +
            ", order_Date='" + getOrder_Date() + "'" +
            ", company_id=" + getCompany_id() +
            ", product_id=" + getProduct_id() +
            ", quantity=" + getQuantity() +
            ", total=" + getTotal() +
            ", vat=" + getVat() +
            ", order_value=" + getOrder_value() +
            "}";
    }
}
