

package com.test.brewery.events;

import org.springframework.context.ApplicationEvent;

import com.test.brewery.domain.BeerOrder;
import com.test.brewery.domain.OrderStatusEnum;


public class BeerOrderStatusChangeEvent extends ApplicationEvent {

    private final OrderStatusEnum previousStatus;

    public BeerOrderStatusChangeEvent(BeerOrder source, OrderStatusEnum previousStatus) {
        super(source);
        this.previousStatus = previousStatus;
    }

    public OrderStatusEnum getPreviousStatus() {
        return previousStatus;
    }
}
