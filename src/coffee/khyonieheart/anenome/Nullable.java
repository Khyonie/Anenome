/*
 * Anenome ~ Shared code for my projects
 * Copyright (C) 2024 Hailey-Jane "Khyonie" Garrett (www.khyonieheart.coffee)
 */

package coffee.khyonieheart.anenome;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Denotes that a symbol with this annotation can be/return null, and extra caution should be exercised when handling such elements.
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Documented
public @interface Nullable {}
