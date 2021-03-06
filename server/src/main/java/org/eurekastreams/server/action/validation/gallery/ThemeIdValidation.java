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
package org.eurekastreams.server.action.validation.gallery;

import org.eurekastreams.commons.actions.ValidationStrategy;
import org.eurekastreams.commons.actions.context.service.ServiceActionContext;
import org.eurekastreams.commons.exceptions.ValidationException;
import org.eurekastreams.server.domain.Theme;
import org.eurekastreams.server.persistence.ThemeMapper;

/**
 * Validation strategy to make sure a theme exists given its id.
 */
public class ThemeIdValidation implements ValidationStrategy<ServiceActionContext>
{
    /**
     * Mapper used to look up the theme.
     */
    // TODO: This could be faster by using cached mappers for themecss or version by uuid or url.
    // then it would only hit the db when needed.
    private ThemeMapper themeMapper = null;

    /**
     * Constructor.
     * 
     * @param inThemeMapper
     *            the theme mapper
     */
    public ThemeIdValidation(final ThemeMapper inThemeMapper)
    {
        themeMapper = inThemeMapper;
    }

    /**
     * Make sure a theme exists given its id.
     * 
     * @param inActionContext
     *            the context
     */
    @Override
    public void validate(final ServiceActionContext inActionContext)
    {
        String themeId = (String) inActionContext.getParams();
        Theme theme;
        // UUID identified by starting with { and ending with }
        if (themeId.startsWith("{") && themeId.substring(themeId.length() - 1).equals("}"))
        {
            // theme is UUID
            theme = themeMapper.findByUUID(themeId.substring(1, themeId.length() - 1));
        }
        else
        {
            // Theme is a URL, find or create.
            theme = themeMapper.findByUrl(themeId);
        }

        /*
         * If theme is still null, throw an exception, something went wrong, most likely a bad UUID.
         */
        if (null == theme)
        {
            throw new ValidationException("Unable to instantiate theme.");
        }

        inActionContext.getState().put("THEME", theme);
    }

}
