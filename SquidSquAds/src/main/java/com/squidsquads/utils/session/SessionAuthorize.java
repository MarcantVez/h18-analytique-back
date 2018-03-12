package com.squidsquads.utils.session;

import com.squidsquads.model.AdminType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionAuthorize {
    AdminType[] value();
}
