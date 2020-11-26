package com.webapp.users.controllers;

import com.webapp.users.dto.TestDto;
import com.webapp.users.exception.TestNotFoundException;
import com.webapp.users.model.Test;
import com.webapp.users.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/test")
@Api(tags = {"Администрирование тестовых записей"})
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "Добавить тестовую запись через TestDto.")
    @PostMapping
    public Test postTest(@RequestBody TestDto testDto) throws TestNotFoundException {
        return testService.saveTest(testDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Возвращает Iterable<Test>.")
    @GetMapping
    public Iterable<Test> getAllTests() {
            return testService.getAllTests();
    }
}



