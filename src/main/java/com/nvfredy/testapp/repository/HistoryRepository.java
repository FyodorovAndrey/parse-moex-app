package com.nvfredy.testapp.repository;

import com.nvfredy.testapp.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
    void deleteBySecId(String secId);
}
