package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Company_Master;
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
 * Cassandra repository for the Company_Master entity.
 */
@Repository
public class Company_MasterRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Company_Master> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Company_MasterRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Company_Master.class);
        this.findAllStmt = session.prepare("SELECT * FROM company_Master");
        this.truncateStmt = session.prepare("TRUNCATE company_Master");
    }

    public List<Company_Master> findAll() {
        List<Company_Master> company_MastersList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Company_Master company_Master = new Company_Master();
                company_Master.setId(row.getUUID("id"));
                company_Master.setCompany_id(row.getInt("company_id"));
                company_Master.setCompany_Name(row.getString("company_Name"));
                return company_Master;
            }
        ).forEach(company_MastersList::add);
        return company_MastersList;
    }

    public Company_Master findOne(UUID id) {
        return mapper.get(id);
    }

    public Company_Master save(Company_Master company_Master) {
        if (company_Master.getId() == null) {
            company_Master.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Company_Master>> violations = validator.validate(company_Master);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(company_Master);
        return company_Master;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
