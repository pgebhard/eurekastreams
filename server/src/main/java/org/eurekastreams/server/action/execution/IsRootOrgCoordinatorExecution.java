/*
 * Copyright (c) 2010 Lockheed Martin Corporation
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
package org.eurekastreams.server.action.execution;

import java.io.Serializable;

import org.eurekastreams.commons.actions.ExecutionStrategy;
import org.eurekastreams.commons.actions.context.PrincipalActionContext;
import org.eurekastreams.server.action.authorization.IsRootOrganizationCoordinator;

/**
 * Execution strategy for GetIsRootOrgCoordinator action.
 * 
 */
public class IsRootOrgCoordinatorExecution implements ExecutionStrategy<PrincipalActionContext>
{
    /**
     * The IsRootOrganizationCoordinatorStrategy.
     */
    private IsRootOrganizationCoordinator strategy;

    /**
     * Constructor.
     * 
     * @param inStrategy
     *            the IsRootOrganizationCoordinatorStrategy
     */
    public IsRootOrgCoordinatorExecution(final IsRootOrganizationCoordinator inStrategy)
    {
        strategy = inStrategy;
    }

    /**
     * Return true if current user is root org coordinator, false otherwise.
     * 
     * @param inActionContext
     *            Action context.
     * @return true if current user is root org coordinator, false otherwise.
     */
    @Override
    public Serializable execute(final PrincipalActionContext inActionContext)
    {
        return strategy.isRootOrganizationCoordinator(inActionContext.getPrincipal().getId());
    }
}
