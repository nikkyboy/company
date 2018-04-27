package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Product_MasterDTO;
import java.util.List;

/**
 * Service Interface for managing Product_Master.
 */
public interface Product_MasterService {

    /**
     * Save a product_Master.
     *
     * @param product_MasterDTO the entity to save
     * @return the persisted entity
     */
    Product_MasterDTO save(Product_MasterDTO product_MasterDTO);

    /**
     * Get all the product_Masters.
     *
     * @return the list of entities
     */
    List<Product_MasterDTO> findAll();

    /**
     * Get the "id" product_Master.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Product_MasterDTO findOne(String id);

    /**
     * Delete the "id" product_Master.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
