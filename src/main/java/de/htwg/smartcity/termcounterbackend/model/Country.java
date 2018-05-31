package de.htwg.smartcity.termcounterbackend.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany
    private List<FederalState> federalStates;



    public Country(){
        this.federalStates = new ArrayList<>();
    }

    public Country(String name){
        this.federalStates = new ArrayList<>();
        this.name = name;
    }

}
