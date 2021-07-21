package com.nvfredy.testapp.service;

import com.nvfredy.testapp.entity.Security;
import com.nvfredy.testapp.repository.SecurityRepository;
import com.nvfredy.testapp.util.SecurityXMLParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class SecurityService {

    private final SecurityRepository repository;

    public SecurityService(SecurityRepository repository) {
        this.repository = repository;
    }

    public void saveAll(File fileName) {
        List<Security> list = SecurityXMLParser.parseFile(fileName);
        repository.saveAll(list);
    }

    public List<Security> findAll() {
        return repository.findAll();
    }
}
