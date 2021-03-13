

package com.test.brewery.web.mappers;


import com.test.brewery.domain.Beer;
import com.test.brewery.domain.BeerOrder;
import com.test.brewery.domain.BeerOrderLine;
import com.test.brewery.web.model.BeerOrderDto;
import com.test.brewery.web.model.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);

    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    default BeerOrderLine dtoToBeerOrder(BeerOrderLineDto dto) {
        return BeerOrderLine.builder()
            .orderQuantity(dto.getOrderQuantity())
            .beer(Beer.builder().id(dto.getBeerId()).build())
            .build();
    }
}
