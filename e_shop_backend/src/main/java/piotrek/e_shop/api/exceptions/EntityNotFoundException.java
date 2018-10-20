package piotrek.e_shop.api.exceptions;

import java.math.BigDecimal;

public class EntityNotFoundException extends RuntimeException {

    private Class resourceClass;
    private BigDecimal resourceId;

    public EntityNotFoundException(Class resourceClass, BigDecimal resourceId) {
        super(String.format("Not found %s with id %s", resourceClass.getSimpleName(), String.valueOf(resourceId)));
        this.resourceClass = resourceClass;
        this.resourceId = resourceId;
    }

    public Class getResourceClass() {
        return resourceClass;
    }

    public BigDecimal getResourceId() {
        return resourceId;
    }

}
