package com.webapp.users.service;

import com.webapp.users.dao.TestDao;
import com.webapp.users.dto.TestDto;
import com.webapp.users.exception.TestNotFoundException;
import com.webapp.users.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;

    public Test saveTest(TestDto testDto) throws TestNotFoundException{

        Test testFromBd = testDao.findByTest(testDto.getTest());
        if (testFromBd != null) {
            throw new TestNotFoundException("Test " + testDto.getTest() + " already exist");
        }
        Test test = new Test(testDto);
        testDao.save(test);
        return test;
    }

    public Iterable<Test> getAllTests() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return testDao.findAll();
    }

    public Test deleteTest(Long id) throws TestNotFoundException {
         Test test = testDao.findById(id).orElseThrow(()-> new TestNotFoundException("Test id " + id + " not found"));
         testDao.deleteById(id);
         return test;
    }
}
