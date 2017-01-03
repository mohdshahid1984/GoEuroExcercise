package com.goeuro.dataproviders.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mshahid on 02/01/17.
 */
@Retention(RetentionPolicy.RUNTIME) public @interface MethodArguments
{
    String[] value();
}
