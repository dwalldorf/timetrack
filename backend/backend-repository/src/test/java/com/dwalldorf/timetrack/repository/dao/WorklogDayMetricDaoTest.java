package com.dwalldorf.timetrack.repository.dao;

import com.dwalldorf.timetrack.model.WorklogDayMetricModel;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.repository.document.WorklogDayMetricDocument;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WorklogDayMetricDaoTest {

    private static final RandomUtil randomUtil = new RandomUtil();

    @Test
    public void findByUserAndGraphConfig() {

    }

    @Test
    public void toModel_WithNull() {
    }

    @Test
    public void toModel() {
    }

    @Test
    public void toModelList_WithNull() {
    }

    @Test
    public void toModelList() {
    }

    private WorklogDayMetricModel getModel() {
        return new WorklogDayMetricModel()
                .setId(randomUtil.mongoId())
                .setUserId(randomUtil.mongoId())
                .setDay(new DateTime())
                .setDuration(randomUtil.randomInt(360, 540));
    }

    private WorklogDayMetricDocument getDocument() {
        return new WorklogDayMetricDocument()
                .setId(randomUtil.mongoId())
                .setUserId(randomUtil.mongoId())
                .setDay(new DateTime())
                .setDuration(randomUtil.randomInt(360, 540));
    }
}