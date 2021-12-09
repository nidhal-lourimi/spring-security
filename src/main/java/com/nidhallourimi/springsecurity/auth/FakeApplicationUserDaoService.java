package com.nidhallourimi.springsecurity.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nidhallourimi.springsecurity.security.ApplicationUserRole.*;


@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUser().stream().filter(applicationUser -> username.equals(applicationUser.getUsername())).findFirst();
    }

    private List<ApplicationUser> getApplicationUser() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        STUDENT.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "anna",
                        true,
                        true,
                        true,
                        true

                ),
                new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "ahmed",
                        true,
                        true,
                        true,
                        true

                ),
                new ApplicationUser(
                        ADMINTRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "lolita",
                        true,
                        true,
                        true,
                        true


                ));
        return applicationUsers;
    }


}
