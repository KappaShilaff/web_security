package com.webapp.users;

import com.webapp.users.controllers.TestController;
import com.webapp.users.dao.TestDao;
import com.webapp.users.dto.TestDto;
import com.webapp.users.exception.TestNotFoundException;
import com.webapp.users.model.Test;
import com.webapp.users.service.TestService;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @MockBean
    TestDao testDao;

    @Autowired
    TestService testService;

    @Autowired
    TestController testController;

    @org.junit.Test
    public void saveTest() throws TestNotFoundException {
        String text = UUID.randomUUID().toString();

        TestDto testDto = new TestDto(text);
        Test test = new Test(testDto.getTest());
        Assert.assertEquals(test, testService.saveTest(testDto));
        Mockito.verify(testDao, Mockito.times(1)).save(test);
    }

    @org.junit.Test(expected = TestNotFoundException.class)
    public void failSaveTest() throws TestNotFoundException {
        String text = UUID.randomUUID().toString();

        TestDto testDto = new TestDto(text);
        Mockito.doReturn(new Test())
                .when(testDao)
                .findByTest(testDto.getTest());
        testService.saveTest(testDto);
    }

    @org.junit.Test
    public void deleteTest() throws TestNotFoundException {
        Long id = 123L;
        String text = UUID.randomUUID().toString();

        TestDto testDto = new TestDto(text);
        Test test = new Test(testDto.getTest());
        Mockito.doReturn(Optional.of(test))
                .when(testDao)
                .findById(id);
        Assert.assertEquals(test, testService.deleteTest(id));
        Mockito.verify(testDao, Mockito.times(1)).deleteById(id);
    }

    @org.junit.Test(expected = TestNotFoundException.class)
    public void failDeleteTest() throws TestNotFoundException {
        Long id = 123L;
        String text = UUID.randomUUID().toString();

        TestDto testDto = new TestDto(text);
        Test test = new Test(testDto.getTest());
        Assert.assertEquals(test, testService.deleteTest(id));
        Mockito.verify(testDao, Mockito.times(0)).deleteById(id);
    }

    @org.junit.Test
    @WithMockUser(authorities = {"user:write", "user:read"})
    public void getAllTests() {
        Mockito.doReturn(null)
                .when(testDao)
                .findAll();
        Assert.assertNull(testController.getAllTests());
        Mockito.verify(testDao, Mockito.times(1)).findAll();
    }

    @org.junit.Test(expected = AccessDeniedException.class)
    @WithMockUser(authorities = {"user:read"})
    public void failGetAllTests() {
        testController.getAllTests();
        Mockito.verify(testDao, Mockito.times(0)).findAll();
    }
}
