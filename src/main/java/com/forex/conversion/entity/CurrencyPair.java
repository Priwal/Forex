package com.forex.conversion.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency_pairs")
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="currency_1")
    String currency1;

    @Column(name="currency_2")
    String currency2;

    @Column(name="count")
    Integer count;

}
