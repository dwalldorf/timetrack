package com.dwalldorf.timetrack.backend.rest.dto;

import static org.junit.Assert.assertEquals;

import com.dwalldorf.timetrack.backend.BaseTest;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ListDtoTest extends BaseTest {

    @Test
    public void testGetCount() {
        List<String> strings = Arrays.asList("", "");
        ListDto<String> listDto = new ListDto<>(strings);

        assertEquals(strings.size(), listDto.getCount());
    }

    @Test
    public void testCount_Null() {
        ListDto<String> listDto = new ListDto<>(null);

        assertEquals(0, listDto.getCount());
    }
}