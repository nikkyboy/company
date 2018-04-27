package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Product_MasterService;
import com.mycompany.myapp.domain.Product_Master;
import com.mycompany.myapp.repository.Product_MasterRepository;
import com.mycompany.myapp.service.dto.Product_MasterDTO;
import com.mycompany.myapp.service.mapper.Product_MasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Product_Master.
 */
@Service
public class Product_MasterServiceImpl implements Product_MasterService {

    private final Logger log = LoggerFactory.getLogger(Product_MasterServiceImpl.class);

    private final Product_MasterRepository product_MasterRepository;

    private final Product_MasterMapper product_MasterMapper;

    public Product_MasterServiceImpl(Product_MasterRepository product_MasterRepository, Product_MasterMapper product_MasterMapper) {
        this.product_MasterRepository = product_MasterRepository;
        this.product_MasterMapper = product_MasterMapper;
    }

    /**
     * Save a product_Master.
     *
     * @param product_MasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Product_MasterDTO save(Product_MasterDTO product_MasterDTO) {
        log.debug("Request to save Product_Master : {}", product_MasterDTO);
        Product_Master product_Master = product_MasterMapper.toEntity(product_MasterDTO);
        product_Master = product_MasterRepository.save(product_Master);
        return product_MasterMapper.toDto(product_Master);
    }

    /**
     * Get all the product_Masters.
     *
     * @return the list of entities
     */
    @Override
    public List<Product_MasterDTO> findAll() {
        log.debug("Request to get all Product_Masters");
        return product_MasterRepository.findAll().stream()
            .map(product_MasterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one product_Master by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Product_MasterDTO findOne(String id) {
        log.debug("Request to get Product_Master : {}", id);
        Product_Master product_Master = product_MasterRepository.findOne(UUID.fromString(id));
        return product_MasterMapper.toDto(product_Master);
    }

    /**
     * Delete the product_Master by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Product_Master : {}", id);
        product_MasterRepository.delete(UUID.fromString(id));
    }
}
