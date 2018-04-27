package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Product_MasterService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.Product_MasterDTO;
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
 * REST controller for managing Product_Master.
 */
@RestController
@RequestMapping("/api")
public class Product_MasterResource {

    private final Logger log = LoggerFactory.getLogger(Product_MasterResource.class);

    private static final String ENTITY_NAME = "product_Master";

    private final Product_MasterService product_MasterService;

    public Product_MasterResource(Product_MasterService product_MasterService) {
        this.product_MasterService = product_MasterService;
    }

    /**
     * POST  /product-masters : Create a new product_Master.
     *
     * @param product_MasterDTO the product_MasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new product_MasterDTO, or with status 400 (Bad Request) if the product_Master has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-masters")
    @Timed
    public ResponseEntity<Product_MasterDTO> createProduct_Master(@RequestBody Product_MasterDTO product_MasterDTO) throws URISyntaxException {
        log.debug("REST request to save Product_Master : {}", product_MasterDTO);
        if (product_MasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new product_Master cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Product_MasterDTO result = product_MasterService.save(product_MasterDTO);
        return ResponseEntity.created(new URI("/api/product-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-masters : Updates an existing product_Master.
     *
     * @param product_MasterDTO the product_MasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated product_MasterDTO,
     * or with status 400 (Bad Request) if the product_MasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the product_MasterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-masters")
    @Timed
    public ResponseEntity<Product_MasterDTO> updateProduct_Master(@RequestBody Product_MasterDTO product_MasterDTO) throws URISyntaxException {
        log.debug("REST request to update Product_Master : {}", product_MasterDTO);
        if (product_MasterDTO.getId() == null) {
            return createProduct_Master(product_MasterDTO);
        }
        Product_MasterDTO result = product_MasterService.save(product_MasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, product_MasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-masters : get all the product_Masters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of product_Masters in body
     */
    @GetMapping("/product-masters")
    @Timed
    public List<Product_MasterDTO> getAllProduct_Masters() {
        log.debug("REST request to get all Product_Masters");
        return product_MasterService.findAll();
        }

    /**
     * GET  /product-masters/:id : get the "id" product_Master.
     *
     * @param id the id of the product_MasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the product_MasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-masters/{id}")
    @Timed
    public ResponseEntity<Product_MasterDTO> getProduct_Master(@PathVariable String id) {
        log.debug("REST request to get Product_Master : {}", id);
        Product_MasterDTO product_MasterDTO = product_MasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(product_MasterDTO));
    }

    /**
     * DELETE  /product-masters/:id : delete the "id" product_Master.
     *
     * @param id the id of the product_MasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduct_Master(@PathVariable String id) {
        log.debug("REST request to delete Product_Master : {}", id);
        product_MasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
