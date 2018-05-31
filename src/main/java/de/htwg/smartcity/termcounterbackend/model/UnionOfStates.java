package de.htwg.smartcity.termcounterbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UnionOfStates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany
    private List<Country> countries;

    public UnionOfStates(){
        this.countries = new ArrayList<>();
    }

    public UnionOfStates(String name){
        this.countries = new ArrayList<>();
        this.name = name;
    }

}
