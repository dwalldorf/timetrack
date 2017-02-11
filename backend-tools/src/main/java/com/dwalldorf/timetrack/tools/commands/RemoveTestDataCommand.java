package com.dwalldorf.timetrack.tools.commands;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.repository.dao.UserDao;
import com.dwalldorf.timetrack.repository.dao.WorklogEntryDao;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoveTestDataCommand extends AbstractCommand {

    public static final String CMD_NAME = "removeTestData";

    private static final Logger logger = LoggerFactory.getLogger(RemoveTestDataCommand.class);

    private final UserDao userDao;

    private final WorklogEntryDao worklogDao;

    @Inject
    public RemoveTestDataCommand(UserDao userDao, WorklogEntryDao worklogDao) {
        this.userDao = userDao;
        this.worklogDao = worklogDao;
    }

    @Override
    public void run(CommandLine cmd) {
        logger.info("Wiping test data...");
        List<UserModel> testUsers = userDao.findTestUsers();

        int deletedUsers = 0;
        int deletedWorklogEntries = 0;

        for (UserModel testUser : testUsers) {
            List<WorklogEntryModel> worklogEntries = worklogDao.findByUser(testUser);
            worklogDao.delete(worklogEntries);

            userDao.delete(testUser);

            deletedUsers++;
            deletedWorklogEntries += worklogEntries.size();
        }

        logger.info("Removed {} worklog entries", deletedWorklogEntries);
        logger.info("Removed {} test users", deletedUsers);
    }

    @Override
    protected String getCmdName() {
        return CMD_NAME;
    }
}