package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Company_MasterDTO;
import java.util.List;

/**
 * Service Interface for managing Company_Master.
 */
public interface Company_MasterService {

    /**
     * Save a company_Master.
     *
     * @param company_MasterDTO the entity to save
     * @return the persisted entity
     */
    Company_MasterDTO save(Company_MasterDTO company_MasterDTO);

    /**
     * Get all the company_Masters.
     *
     * @return the list of entities
     */
    List<Company_MasterDTO> findAll();

    /**
     * Get the "id" company_Master.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Company_MasterDTO findOne(String id);

    /**
     * Delete the "id" company_Master.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
