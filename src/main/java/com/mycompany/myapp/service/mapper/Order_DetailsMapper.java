package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Order_DetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Order_Details and its DTO Order_DetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Order_DetailsMapper extends EntityMapper<Order_DetailsDTO, Order_Details> {


}
