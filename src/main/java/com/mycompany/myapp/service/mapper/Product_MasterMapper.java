package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Product_MasterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product_Master and its DTO Product_MasterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Product_MasterMapper extends EntityMapper<Product_MasterDTO, Product_Master> {


}
