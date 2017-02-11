package com.dwalldorf.timetrack.tools.commands;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.stub.UserStub;
import com.dwalldorf.timetrack.model.stub.WorklogStub;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.repository.dao.UserDao;
import com.dwalldorf.timetrack.repository.dao.WorklogEntryDao;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateTestDataCommand extends AbstractCommand {

    public static final String CMD_NAME = "testData";
    public static final String CMD_USER_COUNT_OPT_NAME = "userCount";
    public static final Integer CMD_USER_COUNT_OPT_DEFAULT = 10;

    public static final String CMD_WORKLOG_COUNT_OPT_NAME = "worklogCount";
    public static final Integer CMD_WORKLOG_COUNT_OPT_DEFAULT = 50;

    private static final Logger logger = LoggerFactory.getLogger(CreateTestDataCommand.class);

    private final RandomUtil randomUtil;

    private final UserStub userStub;

    private final WorklogStub worklogStub;

    private final UserDao userDao;

    private final WorklogEntryDao worklogDao;

    @Inject
    public CreateTestDataCommand(RandomUtil randomUtil, UserStub userStub, WorklogStub worklogStub, UserDao userDao, WorklogEntryDao worklogDao) {
        this.randomUtil = randomUtil;
        this.userStub = userStub;
        this.worklogStub = worklogStub;
        this.userDao = userDao;
        this.worklogDao = worklogDao;
    }

    @Override
    public void run(CommandLine cmd) {
        if (!invoked(CMD_NAME)) {
            return;
        }

        final Integer userCount = getOptionValueInt(CMD_USER_COUNT_OPT_NAME, CMD_USER_COUNT_OPT_DEFAULT);
        final Integer worklogCount = getOptionValueInt(CMD_WORKLOG_COUNT_OPT_NAME, CMD_WORKLOG_COUNT_OPT_DEFAULT);

        logger.info("Creating {} user(s)", userCount);
        logger.info("Creating a max of {} worklog entries per user", worklogCount);

        // create users
        List<UserModel> users = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            UserModel user = userStub.createUser()
                                     .setId(null);

            user = userDao.register(user);

            if (logger.isDebugEnabled()) {
                logger.debug("Created user {}", user);
            }
            users.add(user);
        }

        // create worklog entries
        for (UserModel user : users) {
            int entriesToCreate = randomUtil.randomInt(worklogCount);
            logger.info("Creating {} worklog entries for user {}", entriesToCreate, user.getId());

            List<WorklogEntryModel> entries = worklogStub.createWorklogEntrySeries(user, entriesToCreate);
            worklogDao.save(entries);
        }
    }
}