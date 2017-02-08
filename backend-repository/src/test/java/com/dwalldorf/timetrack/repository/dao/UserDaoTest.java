package com.dwalldorf.timetrack.repository.dao;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.stub.UserStub;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.repository.UserRepository;
import com.dwalldorf.timetrack.repository.document.UserDocument;
import com.dwalldorf.timetrack.repository.document.UserProperties;
import com.dwalldorf.timetrack.repository.document.UserSettings;
import com.dwalldorf.timetrack.repository.service.PasswordService;
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
                .setUserProperties(
                        new UserProperties()
                                .setUserSettings(new UserSettings())
                );
        when(userRepository.save(any(UserDocument.class))).thenReturn(userDocument);

        userDao.register(user);

        Mockito.verify(passwordServiceMock).createSalt();
        Mockito.verify(passwordServiceMock).hash(any(), any());
    }

    @Test
    public void testRegister_ReturnSecureUserCopy() {
        UserModel user = userStub.createUser();
        UserDocument userDocument = new UserDocument()
                .setUserProperties(
                        new UserProperties()
                                .setPassword("somePassword")
                                .setUserSettings(new UserSettings())
                );
        when(userRepository.save(any(UserDocument.class))).thenReturn(userDocument);
        when(passwordServiceMock.createSalt()).thenReturn(SALT);

        UserModel registeredUser = userDao.register(user);

        assertNull(registeredUser.getPassword());
    }
}