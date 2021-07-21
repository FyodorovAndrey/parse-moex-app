package com.nvfredy.testapp.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Security {

    private Long id;
    @Id
    private String secId;
    private String regNumber;
    private String name;
    private String emitentTitle;

    @OneToMany(mappedBy = "security")
    private List<History> historyList;
}
