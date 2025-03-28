package com.sparta.rooibos.user.application.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MasterOnlyCheck {
}