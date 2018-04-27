package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Order_DetailsService;
import com.mycompany.myapp.domain.Order_Details;
import com.mycompany.myapp.repository.Order_DetailsRepository;
import com.mycompany.myapp.service.dto.Order_DetailsDTO;
import com.mycompany.myapp.service.mapper.Order_DetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Order_Details.
 */
@Service
public class Order_DetailsServiceImpl implements Order_DetailsService {

    private final Logger log = LoggerFactory.getLogger(Order_DetailsServiceImpl.class);

    private final Order_DetailsRepository order_DetailsRepository;

    private final Order_DetailsMapper order_DetailsMapper;

    public Order_DetailsServiceImpl(Order_DetailsRepository order_DetailsRepository, Order_DetailsMapper order_DetailsMapper) {
        this.order_DetailsRepository = order_DetailsRepository;
        this.order_DetailsMapper = order_DetailsMapper;
    }

    /**
     * Save a order_Details.
     *
     * @param order_DetailsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Order_DetailsDTO save(Order_DetailsDTO order_DetailsDTO) {
        log.debug("Request to save Order_Details : {}", order_DetailsDTO);
        Order_Details order_Details = order_DetailsMapper.toEntity(order_DetailsDTO);
        order_Details = order_DetailsRepository.save(order_Details);
        return order_DetailsMapper.toDto(order_Details);
    }

    /**
     * Get all the order_Details.
     *
     * @return the list of entities
     */
    @Override
    public List<Order_DetailsDTO> findAll() {
        log.debug("Request to get all Order_Details");
        return order_DetailsRepository.findAll().stream()
            .map(order_DetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one order_Details by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Order_DetailsDTO findOne(String id) {
        log.debug("Request to get Order_Details : {}", id);
        Order_Details order_Details = order_DetailsRepository.findOne(UUID.fromString(id));
        return order_DetailsMapper.toDto(order_Details);
    }

    /**
     * Delete the order_Details by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Order_Details : {}", id);
        order_DetailsRepository.delete(UUID.fromString(id));
    }
}
