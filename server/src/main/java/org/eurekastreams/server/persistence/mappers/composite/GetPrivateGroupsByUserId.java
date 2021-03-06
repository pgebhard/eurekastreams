/*
 * Copyright (c) 2009-2011 Lockheed Martin Corporation
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
package org.eurekastreams.server.persistence.mappers.composite;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eurekastreams.server.persistence.mappers.DomainMapper;
import org.eurekastreams.server.persistence.mappers.GetPrivateGroupIdsCoordinatedByPerson;
import org.eurekastreams.server.persistence.mappers.ReadMapper;
import org.eurekastreams.server.persistence.mappers.cache.OrganizationHierarchyCache;
import org.eurekastreams.server.persistence.mappers.db.GetPrivateGroupIdsUnderOrganizations;

/**
 * This class retrieves a set of private group ids from cache that a user has access to view activities for through
 * either a direct group coordinator role or a parent org tree coordinator role.
 *
 */
public class GetPrivateGroupsByUserId extends ReadMapper<Long, Set<Long>>
{
    /**
     * Local instance of mapper to retrieve the private group ids from the db.
     */
    private final GetPrivateGroupIdsCoordinatedByPerson privateGroupIdsMapper;

    /**
     * Mapper to retrieve the org ids that the user is a coordinator of.
     */
    private final DomainMapper<Long, Set<Long>> orgCoordMapper;

    /**
     * Local instance of mapper to retrieve recursive child orgs under a give org.
     */
    private final OrganizationHierarchyCache orgHierarchyCacheMapper;

    /**
     * Local instance of mapper to retrieve the private group ids under an org.
     */
    private final GetPrivateGroupIdsUnderOrganizations orgPrivateGroupIdsMapper;

    /**
     * Constructor.
     *
     * @param inPrivateGroupIdsMapper
     *            - instance of the {@link GetPrivateGroupIdsCoordinatedByPerson} mapper.
     * @param inOrgCoordMapper
     *            Mapper to retrieve the org ids that the user is a coordinator of.
     *            (GetOrgIdsDirectlyCoordinatedByPerson)
     * @param inOrgHierarchyCacheMapper
     *            - instance of the {@link OrganizationHierarchyCache} mapper.
     * @param inOrgPrivateGroupIdsMapper
     *            - instance of the {@link GetPrivateGroupIdsUnderOrganizations} mapper.
     */
    public GetPrivateGroupsByUserId(final GetPrivateGroupIdsCoordinatedByPerson inPrivateGroupIdsMapper,
            final DomainMapper<Long, Set<Long>> inOrgCoordMapper,
            final OrganizationHierarchyCache inOrgHierarchyCacheMapper,
            final GetPrivateGroupIdsUnderOrganizations inOrgPrivateGroupIdsMapper)
    {
        privateGroupIdsMapper = inPrivateGroupIdsMapper;
        orgCoordMapper = inOrgCoordMapper;
        orgHierarchyCacheMapper = inOrgHierarchyCacheMapper;
        orgPrivateGroupIdsMapper = inOrgPrivateGroupIdsMapper;
    }

    /**
     * Retrieve the Set of ids for the private groups that the supplied user has the ability to view either through
     * group/org coordinator access.
     *
     * @param inUserId
     *            - user id of the context to bring back private group ids.
     * @return - Set of private group ids based on the user id context.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<Long> execute(final Long inUserId)
    {
        // Retrieve direct private orgs coordinated.
        List<Long> results = privateGroupIdsMapper.execute(inUserId);
        Set<Long> groupIds = new HashSet<Long>(results);

        // Retrieve org ids direct coordinator of.
        Set<Long> orgCoordResults = orgCoordMapper.execute(inUserId);

        // Retrieve recursive child org ids of orgs direct coordinator of.
        Set<Long> orgHierarchyResults = new HashSet<Long>();
        for (Long currentOrgId : orgCoordResults)
        {
            orgHierarchyResults.addAll(orgHierarchyCacheMapper.getSelfAndRecursiveChildOrganizations(currentOrgId));
        }
        orgCoordResults.addAll(orgHierarchyResults);

        // Retrieve all private groups beneath orgs.
        groupIds.addAll(orgPrivateGroupIdsMapper.execute(orgCoordResults));

        return groupIds;
    }
}
