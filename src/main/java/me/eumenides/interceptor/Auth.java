package me.eumenides.interceptor;

import java.lang.annotation.*;

/**
 * Created by Eumenides on 2017/11/30.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}
