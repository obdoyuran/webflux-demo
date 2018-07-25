package com.trendyol.webFluxDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class County {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @ManyToOne
    private City city;
}
