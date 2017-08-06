package de.totalsurprise.userlogin.persistence;


import de.totalsurprise.userlogin.services.AuthService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.security.SecureRandom;

/**
 * This class generates test data that are inserted in the database.
 * setupTestData() method gets executed when the application is started.
 */
@Singleton
@Startup
public class TestDataGenerator {

    @Inject
    UserDAO userDAO;

    @Inject
    AuthService authService;

    @PostConstruct
    public void setupTestData() {
        UserEntity user1 = new UserEntity();
        user1.setMailAdress("c.norris@foo.bar");
        byte[] salt1 = generateSalt();
        user1.setSalt(salt1);
        user1.setEncryptedPassword(authService.encrypt("1234".toCharArray(), salt1));
        userDAO.persist(user1);


        UserEntity user2 = new UserEntity();
        user2.setMailAdress("s.stallone@foo.bar");
        byte[] salt2 = generateSalt();
        user2.setSalt(salt2);
        user2.setEncryptedPassword(authService.encrypt("1234".toCharArray(), salt2));
        userDAO.persist(user2);

    }

    private byte[] generateSalt() {
        byte[] salt = new byte[64];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

}
