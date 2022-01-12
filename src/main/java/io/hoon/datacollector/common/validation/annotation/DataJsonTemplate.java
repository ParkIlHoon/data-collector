package io.hoon.datacollector.common.validation.annotation;

import io.hoon.datacollector.common.validation.validator.DataJsonTemplateValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

/**
 * 수집 요청 데이터가 템플릿에 맞는 지 여부를 체크합니다
 */
@Constraint(validatedBy = DataJsonTemplateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataJsonTemplate {
    String message() default "유효하지 않은 형태의 데이터입니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
