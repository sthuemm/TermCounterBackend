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

    public double getAverageTermCount(){
        double terms = federalStates.stream().mapToDouble(FederalState::getAverageTermCount).sum();
        int numberPersons = federalStates.size();
        return terms / numberPersons;
    }

    public Country(){
        this.federalStates = new ArrayList<>();
    }

    public Country(String name){
        this.federalStates = new ArrayList<>();
        this.name = name;
    }

}
