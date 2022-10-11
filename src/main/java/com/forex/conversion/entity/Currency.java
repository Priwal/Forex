package com.forex.conversion.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "currency")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String currencyName;
    private String currencySymbol;

    private double exchangeRate;


}
