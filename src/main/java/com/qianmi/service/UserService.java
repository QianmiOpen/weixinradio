package com.qianmi.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.directory.Attributes;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import com.qianmi.domain.User;
import com.qianmi.repository.UserRepository;

/**
 * 用户
 */
@Service
public class UserService {

    @Value("${root.username}")
    private String root;

    @Value("${root.password}")
    private String rootPwd;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private UserRepository userRepository;

    public Page<User> list(Pageable pageable) {
        Iterable<User>  users = userRepository.findAll();

        System.out.println(users);

        return userRepository.findAll(pageable);
    }

    public User get(String id) {
        return userRepository.findOne(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User createIfNotExists(String workNo){
        // 校验通过后，如果用户不存在，则保存用户信息
        User userSetting = userRepository.findOne(workNo);
        if(userSetting == null) {
            userSetting = userRepository.save(new User(workNo));
        }

        return userSetting;
    }

    public boolean authenticate(String username, String password) {
        if (StringUtils.equalsIgnoreCase(root, username)) {
            return DigestUtils.sha1Hex(password).equals(rootPwd);
        }
        // 校验
        return ldapAuthentication(username, password);
    }

    /**
     * 忽略大小写,依赖层级查找,举例:
     * 1.配置`根`路径
     *   BaseDN = "dc=com1000,dc=local"
     * 2.用户路径
     *   UserBase = "cn=OF049,ou=欧飞网,dc=com1000,dc=local"
     * 3.用户和`根`之间路径
     *   FilterBase = "ou=欧飞网"
     * @param user 账号
     * @param secret 密码
     * @return 账号密码是否匹配
     */
    private boolean ldapAuthentication(String user, String secret) {
        AndFilter filter = new AndFilter().
                and(new EqualsFilter("objectClass", "person")).
                and(new EqualsFilter("cn", user));
        return ldapTemplate.authenticate("ou=欧飞网", filter.encode(), secret);
    }

    private List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectClass").is("person"), (Attributes attrs) -> {
                    return attrs.get("cn").get().toString();
                });
    }

}
