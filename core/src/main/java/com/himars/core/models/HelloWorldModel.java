/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.himars.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.Optional;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = Resource.class)
public class HelloWorldModel {


    protected String resourceType;
    private final Resource currentResource;
    private final ResourceResolver resourceResolver;

    @Getter
    private String message;

    @Inject
    public HelloWorldModel(@ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
                            @Default(values="No resourceType") final String resourceType,
                            @SlingObject final Resource currentResource,
                            @SlingObject final ResourceResolver resourceResolver) {
        this.resourceType = resourceType;
        this.currentResource = currentResource;
        this.resourceResolver = resourceResolver;
        this.message = message();
    }

    private String message() {
        return "Hello World!\n"
                + "Resource type is: " + resourceType + "\n"
                + "Current page is:  " + currentPagePath() + "\n";
    }

    private String currentPagePath() {
        return Optional
                .ofNullable(resourceResolver.adaptTo(PageManager.class))
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse(StringUtils.EMPTY);
    }

}
