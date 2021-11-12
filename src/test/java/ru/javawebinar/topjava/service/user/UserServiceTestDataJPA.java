package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"datajpa", "postgres"})
public class UserServiceTestDataJPA extends AbstractUserServiceTest{
}
