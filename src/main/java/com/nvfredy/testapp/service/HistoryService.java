package com.nvfredy.testapp.service;

import com.nvfredy.testapp.entity.History;
import com.nvfredy.testapp.repository.HistoryRepository;
import com.nvfredy.testapp.repository.SecurityRepository;
import com.nvfredy.testapp.util.HistoryXMLParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final SecurityRepository securityRepository;

    public HistoryService(HistoryRepository repository, SecurityRepository securityRepository) {
        this.historyRepository = repository;
        this.securityRepository = securityRepository;
    }

    public void saveAll(File fileName) {
        List<History> list = HistoryXMLParser.parseFile(fileName);
        for (History h : list) {
            if (securityRepository.findBySecId(h.getSecId()) != null) {
                historyRepository.save(h);
            }
        }
    }

    public List<History> findAll() {
        return historyRepository.findAll();
    }

    public void deleteById(String id) {
        historyRepository.deleteBySecId(id);
    }
}
