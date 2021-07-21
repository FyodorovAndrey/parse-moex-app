package com.nvfredy.testapp.repository;

import com.nvfredy.testapp.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<Security, String> {

    Security findBySecId(String secId);
}
