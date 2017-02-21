package com.dwalldorf.timetrack.repository.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.stub.UserStub;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.repository.document.UserDocument;
import com.dwalldorf.timetrack.repository.document.UserProperties;
import com.dwalldorf.timetrack.repository.repository.UserRepository;
import com.dwalldorf.timetrack.repository.service.PasswordService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    private static final byte[] SALT = "salt".getBytes();

    private static final UserStub userStub = new UserStub(new RandomUtil());

    private PasswordService passwordServiceMock;

    private UserRepository userRepository;

    private UserDao userDao;

    @Before
    public void beforeSetUp() throws Exception {
        passwordServiceMock = mock(PasswordService.class);
        userRepository = mock(UserRepository.class);

        userDao = new UserDao(passwordServiceMock, userRepository);
    }

    @Test
    public void testRegister_HashesCorrectly() {
        UserModel user = userStub.createUser();
        UserDocument userDocument = new UserDocument()
                .setUserProperties(new UserProperties());
        when(userRepository.save(any(UserDocument.class))).thenReturn(userDocument);

        userDao.register(user);

        Mockito.verify(passwordServiceMock).createSalt();
        Mockito.verify(passwordServiceMock).hash(any(), any());
    }

    @Test
    public void testRegister_ReturnSecureUserCopy() {
        UserModel user = userStub.createUser();
        UserDocument userDocument = new UserDocument()
                .setUserProperties(new UserProperties());
        when(userRepository.save(any(UserDocument.class))).thenReturn(userDocument);
        when(passwordServiceMock.createSalt()).thenReturn(SALT);

        UserModel registeredUser = userDao.register(user);

        assertNull(registeredUser.getPassword());
    }

    @Test
    public void testToModel_WithNull() {
        UserModel userModel = userDao.toModel(null);
        assertNull(userModel);
    }

    @Test
    public void testToModel() {
        final String userId = "someId";
        final String username = "testUser";
        final String email = "name@host.tld";
        final boolean confirmedEmail = true;
        final DateTime registrationDate = new DateTime().minusDays(5);
        final DateTime firstLoginDate = registrationDate.plusMinutes(30);
        final DateTime lastLoginDate = new DateTime().minusHours(1);


        UserDocument userDocument = new UserDocument()
                .setId(userId)
                .setUserProperties(
                        new UserProperties()
                                .setUsername(username)
                                .setEmail(email)
                                .setConfirmedEmail(confirmedEmail)
                                .setRegistration(registrationDate)
                                .setFirstLogin(firstLoginDate)
                                .setLastLogin(lastLoginDate)
                );
        UserModel userModel = userDao.toModel(userDocument);

        assertEquals(userId, userModel.getId());
        assertEquals(username, userModel.getUsername());
        assertEquals(email, userModel.getEmail());
        assertEquals(confirmedEmail, userModel.isConfirmedEmail());
        assertEquals(registrationDate, userModel.getRegistration());
        assertEquals(firstLoginDate, userModel.getFirstLogin());
        assertEquals(lastLoginDate, userModel.getLastLogin());
    }

    @Test
    public void testToDocument_WithNull() {
        UserDocument document = userDao.toDocument(null);
        assertNull(document);
    }

    @Test
    public void testToDocument() {
        final String userId = "someId";
        final String username = "testUser";
        final String email = "name@host.tld";
        final boolean confirmedEmail = true;
        final DateTime registrationDate = new DateTime().minusDays(5);
        final DateTime firstLoginDate = registrationDate.plusMinutes(30);
        final DateTime lastLoginDate = new DateTime().minusHours(1);

        UserModel model = new UserModel()
                .setId(userId)
                .setUsername(username)
                .setEmail(email)
                .setConfirmedEmail(confirmedEmail)
                .setRegistration(registrationDate)
                .setFirstLogin(firstLoginDate)
                .setLastLogin(lastLoginDate);

        UserDocument document = userDao.toDocument(model);
        UserProperties userProps = document.getUserProperties();

        assertEquals(userId, document.getId());
        assertEquals(username, userProps.getUsername());
        assertEquals(email, userProps.getEmail());
        assertEquals(confirmedEmail, userProps.isConfirmedEmail());
        assertEquals(registrationDate, userProps.getRegistration());
        assertEquals(firstLoginDate, userProps.getFirstLogin());
        assertEquals(lastLoginDate, userProps.getLastLogin());
    }
}