package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Order_DetailsService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.Order_DetailsDTO;
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
 * REST controller for managing Order_Details.
 */
@RestController
@RequestMapping("/api")
public class Order_DetailsResource {

    private final Logger log = LoggerFactory.getLogger(Order_DetailsResource.class);

    private static final String ENTITY_NAME = "order_Details";

    private final Order_DetailsService order_DetailsService;

    public Order_DetailsResource(Order_DetailsService order_DetailsService) {
        this.order_DetailsService = order_DetailsService;
    }

    /**
     * POST  /order-details : Create a new order_Details.
     *
     * @param order_DetailsDTO the order_DetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new order_DetailsDTO, or with status 400 (Bad Request) if the order_Details has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-details")
    @Timed
    public ResponseEntity<Order_DetailsDTO> createOrder_Details(@RequestBody Order_DetailsDTO order_DetailsDTO) throws URISyntaxException {
        log.debug("REST request to save Order_Details : {}", order_DetailsDTO);
        if (order_DetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new order_Details cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Order_DetailsDTO result = order_DetailsService.save(order_DetailsDTO);
        return ResponseEntity.created(new URI("/api/order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-details : Updates an existing order_Details.
     *
     * @param order_DetailsDTO the order_DetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated order_DetailsDTO,
     * or with status 400 (Bad Request) if the order_DetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the order_DetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-details")
    @Timed
    public ResponseEntity<Order_DetailsDTO> updateOrder_Details(@RequestBody Order_DetailsDTO order_DetailsDTO) throws URISyntaxException {
        log.debug("REST request to update Order_Details : {}", order_DetailsDTO);
        if (order_DetailsDTO.getId() == null) {
            return createOrder_Details(order_DetailsDTO);
        }
        Order_DetailsDTO result = order_DetailsService.save(order_DetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, order_DetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-details : get all the order_Details.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of order_Details in body
     */
    @GetMapping("/order-details")
    @Timed
    public List<Order_DetailsDTO> getAllOrder_Details() {
        log.debug("REST request to get all Order_Details");
        return order_DetailsService.findAll();
        }

    /**
     * GET  /order-details/:id : get the "id" order_Details.
     *
     * @param id the id of the order_DetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the order_DetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-details/{id}")
    @Timed
    public ResponseEntity<Order_DetailsDTO> getOrder_Details(@PathVariable String id) {
        log.debug("REST request to get Order_Details : {}", id);
        Order_DetailsDTO order_DetailsDTO = order_DetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(order_DetailsDTO));
    }

    /**
     * DELETE  /order-details/:id : delete the "id" order_Details.
     *
     * @param id the id of the order_DetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrder_Details(@PathVariable String id) {
        log.debug("REST request to delete Order_Details : {}", id);
        order_DetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
