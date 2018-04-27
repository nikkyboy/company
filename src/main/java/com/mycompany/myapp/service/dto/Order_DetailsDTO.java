package com.mycompany.myapp.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Order_Details entity.
 */
public class Order_DetailsDTO implements Serializable {

    private UUID id;

    private Integer order_id;

    private LocalDate order_Date;

    private Integer company_id;

    private Integer product_id;

    private Integer quantity;

    private BigDecimal total;

    private BigDecimal vat;

    private BigDecimal order_value;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public LocalDate getOrder_Date() {
        return order_Date;
    }

    public void setOrder_Date(LocalDate order_Date) {
        this.order_Date = order_Date;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getOrder_value() {
        return order_value;
    }

    public void setOrder_value(BigDecimal order_value) {
        this.order_value = order_value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order_DetailsDTO order_DetailsDTO = (Order_DetailsDTO) o;
        if(order_DetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), order_DetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Order_DetailsDTO{" +
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
