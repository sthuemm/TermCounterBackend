package de.htwg.smartcity.termcounterbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class World {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany
    private List<UnionOfStates> unionOfStates;

    public double getAverageTermCount(){
        double terms = unionOfStates.stream().mapToDouble(UnionOfStates::getAverageTermCount).sum();
        int numberPersons = unionOfStates.size();
        return terms / numberPersons;
    }

    public World(){
        this.unionOfStates = new ArrayList<>();
    }

}
