package de.htwg.smartcity.termcounterbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class NonTerm {

    @Id
    private String word;

    public NonTerm(){}

    public NonTerm(String word) {
        this.word = word;
    }
}
