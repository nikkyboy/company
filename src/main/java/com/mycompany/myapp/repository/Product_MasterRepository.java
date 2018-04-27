package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Product_Master;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Product_Master entity.
 */
@Repository
public class Product_MasterRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Product_Master> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Product_MasterRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Product_Master.class);
        this.findAllStmt = session.prepare("SELECT * FROM product_Master");
        this.truncateStmt = session.prepare("TRUNCATE product_Master");
    }

    public List<Product_Master> findAll() {
        List<Product_Master> product_MastersList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Product_Master product_Master = new Product_Master();
                product_Master.setId(row.getUUID("id"));
                product_Master.setProduct_id(row.getInt("product_id"));
                product_Master.setProduct_Name(row.getString("product_Name"));
                product_Master.setCompany_id(row.getInt("company_id"));
                product_Master.setUnit_Price(row.getDecimal("unit_Price"));
                return product_Master;
            }
        ).forEach(product_MastersList::add);
        return product_MastersList;
    }

    public Product_Master findOne(UUID id) {
        return mapper.get(id);
    }

    public Product_Master save(Product_Master product_Master) {
        if (product_Master.getId() == null) {
            product_Master.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Product_Master>> violations = validator.validate(product_Master);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(product_Master);
        return product_Master;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
