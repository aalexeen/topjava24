package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"jdbc", "postgres"})
public class UserServiceTestJDBC extends AbstractUserServiceTest{
}
