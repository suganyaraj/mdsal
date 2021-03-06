/*
 * Copyright (c) 2017 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.binding.javav2.java.api.generator.rangeGenerators;

import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import org.opendaylight.mdsal.binding.javav2.model.api.ConcreteType;
import org.opendaylight.mdsal.binding.javav2.model.api.GeneratedProperty;
import org.opendaylight.mdsal.binding.javav2.model.api.GeneratedTransferObject;
import org.opendaylight.mdsal.binding.javav2.model.api.Type;

/**
 * Random utility methods for dealing with {@link Type} objects.
 */
final class TypeUtils {
    private static final String VALUE_PROP = "value";

    private TypeUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Given a {@link Type} object lookup the base Java type which sits at the top
     * of its type hierarchy.
     *
     * @param type Input Type object
     * @return Resolved {@link ConcreteType} instance.
     */
    static ConcreteType getBaseYangType(@Nonnull final Type type) {
        // Already the correct type
        if (type instanceof ConcreteType) {
            return (ConcreteType) type;
        }

        Preconditions.checkArgument(type instanceof GeneratedTransferObject, "Unsupported type %s", type);

        // Need to walk up the GTO chain to the root
        GeneratedTransferObject rootGto = (GeneratedTransferObject) type;
        while (rootGto.getSuperType() != null) {
            rootGto = rootGto.getSuperType();
        }

        // Look for the 'value' property and return its type
        for (GeneratedProperty s : rootGto.getProperties()) {
            if (VALUE_PROP.equals(s.getName())) {
                return (ConcreteType) s.getReturnType();
            }
        }

        // Should never happen
        throw new IllegalArgumentException(String.format("Type %s root %s properties %s do not include \"%s\"",
            type, rootGto, rootGto.getProperties(), VALUE_PROP));
    }
}