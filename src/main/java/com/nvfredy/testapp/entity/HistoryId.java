package com.nvfredy.testapp.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class HistoryId implements Serializable {
    private String secId;
    private LocalDate tradeDate;
}
