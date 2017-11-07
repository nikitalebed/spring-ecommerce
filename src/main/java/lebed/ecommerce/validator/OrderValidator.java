package lebed.ecommerce.validator;

import lebed.ecommerce.model.order.Order;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class OrderValidator implements Validator {

    private static final String NAME = "name";
    private static final String ITEMS = "items";
    private static final String ITEMS_REQUIRED = "items.required";
    private static final String NAME_REQUIRED = "name.required";

    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, NAME, NAME_REQUIRED);
        Order order = (Order) o;
        if (order.getName().length() < 2) {
            errors.rejectValue(NAME, NAME_REQUIRED);
        }
        if (order.getItems() == null || order.getItems().size() < 1) {
            errors.rejectValue(ITEMS, ITEMS_REQUIRED);
        }
    }
}

