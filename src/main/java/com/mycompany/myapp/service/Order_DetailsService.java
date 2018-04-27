package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Order_DetailsDTO;
import java.util.List;

/**
 * Service Interface for managing Order_Details.
 */
public interface Order_DetailsService {

    /**
     * Save a order_Details.
     *
     * @param order_DetailsDTO the entity to save
     * @return the persisted entity
     */
    Order_DetailsDTO save(Order_DetailsDTO order_DetailsDTO);

    /**
     * Get all the order_Details.
     *
     * @return the list of entities
     */
    List<Order_DetailsDTO> findAll();

    /**
     * Get the "id" order_Details.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Order_DetailsDTO findOne(String id);

    /**
     * Delete the "id" order_Details.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
