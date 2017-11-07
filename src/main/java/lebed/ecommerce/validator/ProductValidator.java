package lebed.ecommerce.validator;


import lebed.ecommerce.model.product.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {

    private static final String GROUP = "group";
    private static final String USER_ID = "userId";
    private static final String NAME = "name";
    private static final String NAME_REQUIRED = "name.required";
    private static final String GROUP_REQUIRED = "group.required";
    private static final String USER_REQUIRED = "user.required";

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, NAME, NAME_REQUIRED);
        Product product = (Product) o;
        if (product.getGroup() == null) {
            errors.rejectValue(GROUP, GROUP_REQUIRED);
        }
        if (product.getUserId() == null) {
            errors.rejectValue(USER_ID, USER_REQUIRED);
        }
    }
}

