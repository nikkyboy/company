package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Order_Details;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Order_Details entity.
 */
@Repository
public class Order_DetailsRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Order_Details> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Order_DetailsRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Order_Details.class);
        this.findAllStmt = session.prepare("SELECT * FROM order_Details");
        this.truncateStmt = session.prepare("TRUNCATE order_Details");
    }

    public List<Order_Details> findAll() {
        List<Order_Details> order_DetailsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Order_Details order_Details = new Order_Details();
                order_Details.setId(row.getUUID("id"));
                order_Details.setOrder_id(row.getInt("order_id"));
                order_Details.setOrder_Date(row.get("order_Date", LocalDate.class));
                order_Details.setCompany_id(row.getInt("company_id"));
                order_Details.setProduct_id(row.getInt("product_id"));
                order_Details.setQuantity(row.getInt("quantity"));
                order_Details.setTotal(row.getDecimal("total"));
                order_Details.setVat(row.getDecimal("vat"));
                order_Details.setOrder_value(row.getDecimal("order_value"));
                return order_Details;
            }
        ).forEach(order_DetailsList::add);
        return order_DetailsList;
    }

    public Order_Details findOne(UUID id) {
        return mapper.get(id);
    }

    public Order_Details save(Order_Details order_Details) {
        if (order_Details.getId() == null) {
            order_Details.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Order_Details>> violations = validator.validate(order_Details);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(order_Details);
        return order_Details;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
