package de.htwg.smartcity.termcounterbackend.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany
    private List<City> cities;

    public double getAverageTermCount(){
        double terms = cities.stream().mapToDouble(City::getAverageTermCount).sum();
        int numberPersons = cities.size();
        return terms / numberPersons;
    }

    public District(){
        this.cities = new ArrayList<>();
    }

    public District(String name){
        this.cities = new ArrayList<>();
        this.name = name;
    }

}
