package com.dwalldorf.timetrack.repository.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.stub.UserStub;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.repository.document.UserDocument;
import com.dwalldorf.timetrack.repository.document.UserProperties;
import com.dwalldorf.timetrack.repository.repository.UserRepository;
import com.dwalldorf.timetrack.repository.service.PasswordService;
import java.util.Arrays;
import java.util.List;
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

    private PasswordService mockPasswordService;

    private UserRepository mockUserRepository;

    private UserDao userDao;

    @Before
    public void beforeSetUp() throws Exception {
        mockPasswordService = mock(PasswordService.class);
        mockUserRepository = mock(UserRepository.class);

        userDao = new UserDao(mockPasswordService, mockUserRepository);
    }

    @Test
    public void testRegister_HashesCorrectly() {
        UserModel user = userStub.createUser();
        UserDocument userDocument = new UserDocument()
                .setUserProperties(new UserProperties());
        when(mockUserRepository.save(any(UserDocument.class))).thenReturn(userDocument);

        userDao.register(user);

        Mockito.verify(mockPasswordService).createSalt();
        Mockito.verify(mockPasswordService).hash(any(), any());
    }

    @Test
    public void testRegister_ReturnSecureUserCopy() {
        UserModel user = userStub.createUser();
        UserDocument userDocument = new UserDocument()
                .setUserProperties(new UserProperties());
        when(mockUserRepository.save(any(UserDocument.class))).thenReturn(userDocument);
        when(mockPasswordService.createSalt()).thenReturn(SALT);

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
        final Float workingHoursWeek = 37.2F;

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
                                .setWorkingHoursWeek(workingHoursWeek)
                );
        UserModel userModel = userDao.toModel(userDocument);

        assertEquals(userId, userModel.getId());
        assertEquals(username, userModel.getUsername());
        assertEquals(email, userModel.getEmail());
        assertEquals(confirmedEmail, userModel.isConfirmedEmail());
        assertEquals(registrationDate, userModel.getRegistration());
        assertEquals(firstLoginDate, userModel.getFirstLogin());
        assertEquals(lastLoginDate, userModel.getLastLogin());
        assertEquals(workingHoursWeek, userModel.getWorkingHoursWeek());
    }

    @Test
    public void testToModelList_WithNull() throws Exception {
        List<UserDocument> documents = Arrays.asList(null, null);
        List<UserModel> modelList = userDao.toModelList(documents);

        assertEquals(0, modelList.size());
    }

    @Test
    public void testToModelList() throws Exception {
        UserDocument userDocument1 = userDao.toDocument(userStub.createUser());
        UserDocument userDocument2 = userDao.toDocument(userStub.createUser());
        List<UserDocument> documents = Arrays.asList(userDocument1, userDocument2);

        List<UserModel> models = userDao.toModelList(documents);

        assertEquals(2, models.size());

        UserModel model2 = models.get(1);
        assertEquals(userDocument2.getId(), model2.getId());
        assertEquals(userDocument2.getUserProperties().getUsername(), model2.getUsername());
        assertEquals(userDocument2.getUserProperties().getEmail(), model2.getEmail());
        assertEquals(userDocument2.getUserProperties().getRegistration(), model2.getRegistration());
        assertEquals(userDocument2.getUserProperties().getFirstLogin(), model2.getFirstLogin());
        assertEquals(userDocument2.getUserProperties().getLastLogin(), model2.getLastLogin());
        assertEquals(userDocument2.getUserProperties().getWorkingHoursWeek(), model2.getWorkingHoursWeek());
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
        final Float workingHoursWeek = 40F;

        UserModel model = new UserModel()
                .setId(userId)
                .setUsername(username)
                .setEmail(email)
                .setConfirmedEmail(confirmedEmail)
                .setRegistration(registrationDate)
                .setFirstLogin(firstLoginDate)
                .setLastLogin(lastLoginDate)
                .setWorkingHoursWeek(workingHoursWeek);

        UserDocument document = userDao.toDocument(model);
        UserProperties userProps = document.getUserProperties();

        assertEquals(userId, document.getId());
        assertEquals(username, userProps.getUsername());
        assertEquals(email, userProps.getEmail());
        assertEquals(confirmedEmail, userProps.isConfirmedEmail());
        assertEquals(registrationDate, userProps.getRegistration());
        assertEquals(firstLoginDate, userProps.getFirstLogin());
        assertEquals(lastLoginDate, userProps.getLastLogin());
        assertEquals(workingHoursWeek, userProps.getWorkingHoursWeek());
    }
}