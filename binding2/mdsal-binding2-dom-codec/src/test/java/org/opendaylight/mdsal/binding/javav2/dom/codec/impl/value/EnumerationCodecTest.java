/*
 * Copyright (c) 2017 Pantheon Technologies s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.binding.javav2.dom.codec.impl.value;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.opendaylight.yangtools.yang.model.api.type.EnumTypeDefinition;
import org.opendaylight.yangtools.yang.model.api.type.EnumTypeDefinition.EnumPair;

public class EnumerationCodecTest {

    private enum TestEnum {
        TEST
    }

    @Test
    public void basicTest() throws Exception {
        final EnumPair pair = mock(EnumPair.class);
        doReturn(TestEnum.TEST.name()).when(pair).getName();
        doReturn(0).when(pair).getValue();
        final EnumTypeDefinition definition = mock(EnumTypeDefinition.class);
        doReturn(ImmutableList.of(pair)).when(definition).getValues();

        final EnumerationCodec codec = EnumerationCodec.loader(TestEnum.class, definition).call();
        assertEquals(codec.deserialize(codec.serialize(TestEnum.TEST)), TestEnum.TEST);
        assertEquals(codec.serialize(codec.deserialize(TestEnum.TEST.name())), TestEnum.TEST.name());
    }
}
