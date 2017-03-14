package com.dwalldorf.timetrack.backend.service;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogDayMetricModel;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import com.dwalldorf.timetrack.repository.dao.WorklogDayMetricDao;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class WorklogMetricDayService {

    private final WorklogDayMetricDao worklogDayMetricDao;

    @Inject
    public WorklogMetricDayService(WorklogDayMetricDao worklogDayMetricDao) {
        this.worklogDayMetricDao = worklogDayMetricDao;
    }

    public List<WorklogDayMetricModel> getByGraphConfig(UserModel user, GraphConfig graphConfig) {
        return worklogDayMetricDao.findByUserAndGraphConfig(user, graphConfig);
    }
}