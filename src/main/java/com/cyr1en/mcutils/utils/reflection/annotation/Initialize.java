package com.cyr1en.mcutils.utils.reflection.annotation;

import xdean.deannotation.checker.CheckMethod;
import xdean.deannotation.checker.CheckType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@CheckMethod(argCount = 0, returnType = @CheckType(void.class))
public @interface Initialize {
    int priority();
}
