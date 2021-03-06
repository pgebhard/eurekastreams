/*
 * Copyright (c) 2010-2011 Lockheed Martin Corporation
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
package org.eurekastreams.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity representing a usage metric generated by page views and stream views.
 */
@Entity
public class UsageMetric implements Serializable
{
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3418914129954861130L;

    /**
     * Primary key ID field for ORM.
     * 
     * Where you set the @Id on entities tells the ORM if you're using field or property-based entity mapping. if you
     * set it on a private variable, then the ORM will not use getters/setters at all. If you set it on getId(), then
     * you need to have getters/setters on everything.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The id of the Person registering the metric.
     */
    private long actorPersonId;

    /**
     * Whether the metric registers a page view.
     */
    private boolean isPageView;

    /**
     * Whether this metric registers a stream view.
     */
    private boolean isStreamView;

    /**
     * The created date.
     */
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    /**
     * Empty constructor for the framework.
     */
    public UsageMetric()
    {
    }

    /**
     * Constructor.
     * 
     * @param inActorPersonId
     *            the id of the Person registering the metric
     * @param inIsPageView
     *            whether the metric registers a page view.
     * @param inIsStreamView
     *            whether this metric registers a stream view.
     * @param inCreated
     *            the date created
     */
    public UsageMetric(final long inActorPersonId, final boolean inIsPageView, final boolean inIsStreamView,
            final Date inCreated)
    {
        actorPersonId = inActorPersonId;
        isPageView = inIsPageView;
        isStreamView = inIsStreamView;
        created = inCreated;
    }

    /**
     * @return the actorPersonId
     */
    public long getActorPersonId()
    {
        return actorPersonId;
    }

    /**
     * @param inActorPersonId
     *            the actorPersonId to set
     */
    public void setActorPersonId(final long inActorPersonId)
    {
        actorPersonId = inActorPersonId;
    }

    /**
     * @return the isPageView
     */
    public boolean isPageView()
    {
        return isPageView;
    }

    /**
     * @param inIsPageView
     *            the isPageView to set
     */
    public void setPageView(final boolean inIsPageView)
    {
        isPageView = inIsPageView;
    }

    /**
     * @return the isStreamView
     */
    public boolean isStreamView()
    {
        return isStreamView;
    }

    /**
     * @param inIsStreamView
     *            the isStreamView to set
     */
    public void setStreamView(final boolean inIsStreamView)
    {
        isStreamView = inIsStreamView;
    }

    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param inId
     *            the id to set
     */
    public void setId(final long inId)
    {
        id = inId;
    }

    /**
     * @return the created
     */
    public Date getCreated()
    {
        return created;
    }

    /**
     * @param inCreated
     *            the created to set
     */
    public void setCreated(final Date inCreated)
    {
        created = inCreated;
    }

}
