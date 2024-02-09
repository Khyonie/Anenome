/*
 * Anenome ~ Shared code for my projects
 * Copyright (C) 2024 Hailey-Jane "Khyonie" Garrett (www.khyonieheart.coffee)
 */

package coffee.khyonieheart.anenome;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Denotes that an array argument passed to this parameter should not have zero elements.
 */
@Target({ ElementType.PARAMETER })
@Documented
public @interface NotEmpty {}
