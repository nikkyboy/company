package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Company_MasterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Company_Master and its DTO Company_MasterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Company_MasterMapper extends EntityMapper<Company_MasterDTO, Company_Master> {


}
