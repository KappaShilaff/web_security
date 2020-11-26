package com.webapp.users.service;

import com.webapp.users.dto.TestDto;
import com.webapp.users.exception.TestNotFoundException;
import com.webapp.users.model.Test;

public interface TestService {
    public Test saveTest(TestDto testDto) throws TestNotFoundException;
    public Iterable<Test> getAllTests();
    public Test deleteTest(Long id) throws TestNotFoundException;
}
