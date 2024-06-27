package com.kakinohana.deliveryapi.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ZeroValueIncludesDescriptionValidator implements ConstraintValidator<ZeroValueIncludesDescription, Object> {

    private String fieldValue;
    private String fieldDescription;
    private String requiredDescription;

    @Override
    public void initialize(ZeroValueIncludesDescription constraintAnnotation) {
        this.fieldValue = constraintAnnotation.fieldValue();
        this.fieldDescription = constraintAnnotation.fieldDescription();
        this.requiredDescription = constraintAnnotation.requiredDescription();
    }

    @Override
    public boolean isValid(Object validationObject, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(validationObject.getClass(), fieldValue)
                    .getReadMethod().invoke(validationObject);

            String description = (String) BeanUtils.getPropertyDescriptor(validationObject.getClass(), fieldDescription)
                    .getReadMethod().invoke(validationObject);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null){
                valid = description.toLowerCase().contains(this.requiredDescription.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return valid;
    }
}
