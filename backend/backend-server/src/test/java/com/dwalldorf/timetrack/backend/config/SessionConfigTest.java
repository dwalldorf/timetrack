package com.dwalldorf.timetrack.backend.config;

import static org.junit.Assert.assertEquals;

import com.dwalldorf.timetrack.backend.BaseTest;
import org.junit.Test;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.test.util.ReflectionTestUtils;

public class SessionConfigTest extends BaseTest {

    private SessionConfig sessionConfig;

    @Override
    protected void setUp() {
        this.sessionConfig = new SessionConfig();
    }

    @Test
    public void testCookieSerializer() throws Exception {
        CookieSerializer cookieSerializer = sessionConfig.cookieSerializer();

        String cookieName = String.valueOf(ReflectionTestUtils.getField(cookieSerializer, "cookieName"));
        assertEquals("TIMETRACK", cookieName);
    }
}