package com.nvfredy.testapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@IdClass(HistoryId.class)
public class History {

    @Id
    private String secId;
    @Id
    private LocalDate tradeDate;
    private Double numTrades;
    private Double open;
    private Double close;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secId", insertable = false, updatable = false)
    private Security security;


}
