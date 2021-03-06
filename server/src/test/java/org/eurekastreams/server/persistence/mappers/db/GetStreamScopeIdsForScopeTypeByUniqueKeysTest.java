/*
 * Copyright (c) 2011 Lockheed Martin Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eurekastreams.server.persistence.mappers.db;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eurekastreams.server.domain.stream.StreamScope.ScopeType;
import org.eurekastreams.server.persistence.mappers.MapperTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for GetStreamScopeIdsForScopeTypeByUniqueKeys.
 * 
 */
public class GetStreamScopeIdsForScopeTypeByUniqueKeysTest extends MapperTest
{
    /**
     * System under test.
     */
    private GetStreamScopeIdsForScopeTypeByUniqueKeys sut;

    /**
     * Setup method.
     */
    @Before
    public void setup()
    {
        sut = new GetStreamScopeIdsForScopeTypeByUniqueKeys(ScopeType.PERSON);
        sut.setEntityManager(getEntityManager());
    }

    /**
     * Test execute when the scope exists.
     */
    @Test
    public void testWhenExists()
    {
        final Long expectedId = 3L;
        List<Long> results = sut.execute(Arrays.asList("csagan"));
        assertEquals(1, results.size());
        assertEquals(expectedId, results.get(0));
    }

    /**
     * Test execute when the scope does not exists.
     */
    @Test
    public void testEmptyWhenDoesNotExist()
    {
        // this is a valid group ScopeType, so exercise that ScopeType is obeyed.
        assertEquals(0, sut.execute(Arrays.asList("group1")).size());
    }

    /**
     * Test execute when the scope does not exists.
     */
    @Test
    public void testEmptyParams()
    {
        // this is a valid group ScopeType, so exercise that ScopeType is obeyed.
        assertEquals(0, sut.execute(new ArrayList<String>()).size());
    }

    /**
     * Test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConfiguredNullScopeType()
    {
        GetStreamScopeIdsForScopeTypeByUniqueKeys testSut = new GetStreamScopeIdsForScopeTypeByUniqueKeys(null);
    }
}
