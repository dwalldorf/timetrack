package com.dwalldorf.timetrack;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest {

    @Before
    public void beforeSetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.setUp();
    }

    protected void setUp() {
    }
}