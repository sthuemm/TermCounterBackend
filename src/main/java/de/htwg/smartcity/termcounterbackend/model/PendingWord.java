package de.htwg.smartcity.termcounterbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PendingWord {

    @Id
    private String word;

    private Long personId;

    public PendingWord(){}

    public PendingWord(String word) {
        this.word = word;
    }
}
