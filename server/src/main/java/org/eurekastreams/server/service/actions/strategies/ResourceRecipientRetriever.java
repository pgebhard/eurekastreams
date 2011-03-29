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
package org.eurekastreams.server.service.actions.strategies;

import org.eurekastreams.server.domain.Organization;
import org.eurekastreams.server.domain.stream.ActivityDTO;
import org.eurekastreams.server.domain.stream.StreamScope;
import org.eurekastreams.server.persistence.mappers.DomainMapper;
import org.eurekastreams.server.persistence.mappers.FindByIdMapper;
import org.eurekastreams.server.persistence.mappers.GetRootOrganizationIdAndShortName;
import org.eurekastreams.server.persistence.mappers.requests.FindByIdRequest;

/**
 * RecipientRetriever for activities posted to resources.
 */
public class ResourceRecipientRetriever implements RecipientRetriever
{
    /**
     * Root organization id DAO.
     */
    private GetRootOrganizationIdAndShortName rootOrgIdDAO;

    /**
     * Mapper to get organization entity.
     */
    @SuppressWarnings("unchecked")
    private FindByIdMapper findByIdMapper;

    /**
     * Mapper to get Resource stream scope id.
     */
    private DomainMapper<String, Long> streamScopeIdByUniqueKeyMapper;

    /**
     * Constructor.
     * 
     * @param inRootOrgIdDAO
     *            Root org id DAO.
     * @param inFindByIdMapper
     *            Mapper to get Org entity (not dto).
     * @param inStreamScopeIdByUniqueKeyMapper
     *            Mapper to get Resource stream scope id.
     */
    public ResourceRecipientRetriever(final GetRootOrganizationIdAndShortName inRootOrgIdDAO,
            final FindByIdMapper inFindByIdMapper, final DomainMapper<String, Long> inStreamScopeIdByUniqueKeyMapper)
    {
        rootOrgIdDAO = inRootOrgIdDAO;
        findByIdMapper = inFindByIdMapper;
        streamScopeIdByUniqueKeyMapper = inStreamScopeIdByUniqueKeyMapper;
    }

    @Override
    public Organization getParentOrganization(final ActivityDTO inActivityDTO)
    {
        return (Organization) findByIdMapper.execute(new FindByIdRequest("Organization", rootOrgIdDAO
                .getRootOrganizationId()));
    }

    @Override
    public StreamScope getStreamScope(final ActivityDTO inActivityDTO)
    {
        // TODO: this could be faster with new mapper to return stream scope entity by unique key, rather than find
        // id the get the entity by id.
        return (StreamScope) findByIdMapper.execute(new FindByIdRequest("StreamScope", streamScopeIdByUniqueKeyMapper
                .execute(inActivityDTO.getDestinationStream().getUniqueIdentifier())));
    }

    @Override
    public Boolean isDestinationStreamPublic(final ActivityDTO inActivityDTO)
    {
        return true;
    }
}
