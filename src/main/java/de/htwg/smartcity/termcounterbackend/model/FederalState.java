package de.htwg.smartcity.termcounterbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class FederalState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany
    private List<District> districts;

    public double getAverageTermCount(){
        double terms = districts.stream().mapToDouble(District::getAverageTermCount).sum();
        int numberPersons = districts.size();
        return terms / numberPersons;
    }

    public FederalState(){
        this.districts = new ArrayList<>();
    }

    public FederalState(String name){
        this.districts = new ArrayList<>();
        this.name = name;
    }

}
