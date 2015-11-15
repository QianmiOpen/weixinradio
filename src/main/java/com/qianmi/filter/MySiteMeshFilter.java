package com.qianmi.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.stereotype.Component;

/**
 * Sitemesh3
 */
@Component
public class MySiteMeshFilter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
//        builder.addExcludedPath("/event/show");
//        builder.addDecoratorPath("/*", "/WEB-INF/decorator.jsp");
    }

}

