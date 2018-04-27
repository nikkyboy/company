package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Company_MasterService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.Company_MasterDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Company_Master.
 */
@RestController
@RequestMapping("/api")
public class Company_MasterResource {

    private final Logger log = LoggerFactory.getLogger(Company_MasterResource.class);

    private static final String ENTITY_NAME = "company_Master";

    private final Company_MasterService company_MasterService;

    public Company_MasterResource(Company_MasterService company_MasterService) {
        this.company_MasterService = company_MasterService;
    }

    /**
     * POST  /company-masters : Create a new company_Master.
     *
     * @param company_MasterDTO the company_MasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new company_MasterDTO, or with status 400 (Bad Request) if the company_Master has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-masters")
    @Timed
    public ResponseEntity<Company_MasterDTO> createCompany_Master(@RequestBody Company_MasterDTO company_MasterDTO) throws URISyntaxException {
        log.debug("REST request to save Company_Master : {}", company_MasterDTO);
        if (company_MasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new company_Master cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Company_MasterDTO result = company_MasterService.save(company_MasterDTO);
        return ResponseEntity.created(new URI("/api/company-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-masters : Updates an existing company_Master.
     *
     * @param company_MasterDTO the company_MasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated company_MasterDTO,
     * or with status 400 (Bad Request) if the company_MasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the company_MasterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-masters")
    @Timed
    public ResponseEntity<Company_MasterDTO> updateCompany_Master(@RequestBody Company_MasterDTO company_MasterDTO) throws URISyntaxException {
        log.debug("REST request to update Company_Master : {}", company_MasterDTO);
        if (company_MasterDTO.getId() == null) {
            return createCompany_Master(company_MasterDTO);
        }
        Company_MasterDTO result = company_MasterService.save(company_MasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, company_MasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-masters : get all the company_Masters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of company_Masters in body
     */
    @GetMapping("/company-masters")
    @Timed
    public List<Company_MasterDTO> getAllCompany_Masters() {
        log.debug("REST request to get all Company_Masters");
        return company_MasterService.findAll();
        }

    /**
     * GET  /company-masters/:id : get the "id" company_Master.
     *
     * @param id the id of the company_MasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the company_MasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-masters/{id}")
    @Timed
    public ResponseEntity<Company_MasterDTO> getCompany_Master(@PathVariable String id) {
        log.debug("REST request to get Company_Master : {}", id);
        Company_MasterDTO company_MasterDTO = company_MasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(company_MasterDTO));
    }

    /**
     * DELETE  /company-masters/:id : delete the "id" company_Master.
     *
     * @param id the id of the company_MasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompany_Master(@PathVariable String id) {
        log.debug("REST request to delete Company_Master : {}", id);
        company_MasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
