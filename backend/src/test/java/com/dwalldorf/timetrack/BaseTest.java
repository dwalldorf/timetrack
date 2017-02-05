package com.dwalldorf.timetrack;

import static org.mockito.Mockito.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

    protected JoinPoint createJoinPointMock() {
        JoinPoint joinPointMock = Mockito.mock(JoinPoint.class);
        Signature signatureMock = Mockito.mock(Signature.class);

        when(signatureMock.toString()).thenReturn("SomeBean#someMethod");
        when(joinPointMock.getSignature()).thenReturn(signatureMock);

        return joinPointMock;
    }
}