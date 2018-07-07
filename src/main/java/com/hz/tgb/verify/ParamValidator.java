package com.hz.tgb.verify;

import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 参数验证器
 * 
 * @author Yaphis 2017年11月27日 下午3:06:52
 */
public class ParamValidator {

    private static final Logger LOG = LoggerFactory.getLogger(ParamValidator.class);

    private static Validator validator;

    static {
        try {
            ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
            validator = factory.getValidator();
        } catch (Exception e) {
            LOG.error("", e);
        }
    }

    /**
     * 参数验证
     * 
     * @param object
     * @return
     */
    public static <T> ValidateResult validate(T object) {
        long startTime = System.currentTimeMillis();
        ValidateResult validateResult = new ValidateResult();
        if (null != validator) {
            if (null == object) {
                LOG.info("param:{} is null!", object);
                validateResult.fail("参数为空");
            } else {
                Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
                if (null != constraintViolations && !constraintViolations.isEmpty()) {
                    for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                        // 只循环一个参数验证结果 其实初始化的时候已经设置了快速失败
                        validateResult.fail("参数【" + constraintViolation.getPropertyPath() + "】" + constraintViolation.getMessage());
                        break;
                    }
                }
            }
        } else {
            LOG.warn("validator:{} is null! please check static init method!", validator);
        }
        LOG.debug("ParamValidator.validate() cost:{} ms!", System.currentTimeMillis() - startTime);
        return validateResult;
    }

}
