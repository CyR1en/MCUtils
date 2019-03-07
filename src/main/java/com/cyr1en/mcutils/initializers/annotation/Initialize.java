package com.cyr1en.mcutils.initializers.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Initialize {
    int priority() default -1;
    //Condition conditional() default Condition.IGNORE;
}
