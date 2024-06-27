package com.kakinohana.deliveryapi.core.validation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {}
)
@PositiveOrZero
public @interface DeliveryTax {

    //Override para mostrar essa mensagem ao inves de mostrar a mensagem padrão do positiveorzero
    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    String message() default "{DeliveryTax.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
