package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Company_MasterService;
import com.mycompany.myapp.domain.Company_Master;
import com.mycompany.myapp.repository.Company_MasterRepository;
import com.mycompany.myapp.service.dto.Company_MasterDTO;
import com.mycompany.myapp.service.mapper.Company_MasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Company_Master.
 */
@Service
public class Company_MasterServiceImpl implements Company_MasterService {

    private final Logger log = LoggerFactory.getLogger(Company_MasterServiceImpl.class);

    private final Company_MasterRepository company_MasterRepository;

    private final Company_MasterMapper company_MasterMapper;

    public Company_MasterServiceImpl(Company_MasterRepository company_MasterRepository, Company_MasterMapper company_MasterMapper) {
        this.company_MasterRepository = company_MasterRepository;
        this.company_MasterMapper = company_MasterMapper;
    }

    /**
     * Save a company_Master.
     *
     * @param company_MasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Company_MasterDTO save(Company_MasterDTO company_MasterDTO) {
        log.debug("Request to save Company_Master : {}", company_MasterDTO);
        Company_Master company_Master = company_MasterMapper.toEntity(company_MasterDTO);
        company_Master = company_MasterRepository.save(company_Master);
        return company_MasterMapper.toDto(company_Master);
    }

    /**
     * Get all the company_Masters.
     *
     * @return the list of entities
     */
    @Override
    public List<Company_MasterDTO> findAll() {
        log.debug("Request to get all Company_Masters");
        return company_MasterRepository.findAll().stream()
            .map(company_MasterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one company_Master by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Company_MasterDTO findOne(String id) {
        log.debug("Request to get Company_Master : {}", id);
        Company_Master company_Master = company_MasterRepository.findOne(UUID.fromString(id));
        return company_MasterMapper.toDto(company_Master);
    }

    /**
     * Delete the company_Master by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Company_Master : {}", id);
        company_MasterRepository.delete(UUID.fromString(id));
    }
}
