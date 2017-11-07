package lebed.ecommerce.validator;

import lebed.ecommerce.model.product.ProductGroup;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GroupValidator implements Validator {

    private static final String GROUP_NAME = "groupName";
    private static final String NAME_REQUIRED = "name.required";

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductGroup.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, GROUP_NAME, NAME_REQUIRED);
        ProductGroup group = (ProductGroup) o;
        if (group.getGroupName().length() < 2) {
            errors.rejectValue(GROUP_NAME, NAME_REQUIRED);
        }
    }
}
