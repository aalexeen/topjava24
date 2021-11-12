package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"jpa", "postgres"})
public class UserServiceTestJPA extends AbstractUserServiceTest{
}
