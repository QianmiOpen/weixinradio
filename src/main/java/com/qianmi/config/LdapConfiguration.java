package com.qianmi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * ldap配置
 */
@Configuration
public class LdapConfiguration {

    @Value("${ldap.url}")
    private String url;
    @Value("${ldap.base_dn}")
    private String baseDN;
    @Value("${ldap.bind_dn}")
    private String bindDN;
    @Value("${ldap.bind_password}")
    private String bindPassword;

    @Bean
    public LdapContextSource contextSource () {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(url);
        contextSource.setBase(baseDN);
        contextSource.setUserDn(bindDN);
        contextSource.setPassword(bindPassword);
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

}