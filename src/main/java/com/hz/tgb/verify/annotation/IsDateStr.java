package com.hz.tgb.verify.annotation;

import com.hz.tgb.verify.annotation.validator.DateStrValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否日期
 * 
 * @author Yaphis 2017年1月22日 下午7:16:30
 */
@Inherited
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = DateStrValidator.class)
@Documented
public @interface IsDateStr {

    public static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT2 = "yyyyMMddHHmmss";
    public static final String FORMAT3 = "yyyy-MM-dd";

    /**
     * 日期格式
     * 
     * @return
     */
    public String format() default FORMAT1;

    /**
     * 是否比当前时间晚
     * 
     * @return
     */
    public boolean future() default false;

    /**
     * 是否比当前时间早
     * 
     * @return
     */
    public boolean past() default false;

    /**
     * 验证失败返回信息
     * 
     * @return
     */
    public String message() default "必须为日期字符串,格式:{format}";

    /**
     * 验证分组
     * 
     * @return
     */
    public Class<?>[] groups() default {};

    /**
     * @return
     */
    Class<? extends Payload>[] payload() default {};

}
