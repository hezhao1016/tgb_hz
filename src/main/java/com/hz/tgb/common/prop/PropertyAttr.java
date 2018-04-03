package com.hz.tgb.common.prop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实际使用中,键名可能包含点,对于这样的键名我们可以通过注解来控制.
 * 以下注解实现了键名可配置和键值不存时给初始配置.
 * Created by hezhao on 2015/12/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface PropertyAttr {
    /** 
     * 键名 
     * 
     * @return 
     */  
    public abstract String key();  
  
    /** 
     * 默认键值 
     * @return 
     */  
    public abstract String defalutValue() default "";  
  
}  