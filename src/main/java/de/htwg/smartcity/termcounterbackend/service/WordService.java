package de.htwg.smartcity.termcounterbackend.service;

import de.htwg.smartcity.termcounterbackend.model.PendingWord;
import org.springframework.stereotype.Service;

@Service
public interface WordService {

    void checkSentencesForNewTerms(String sentence, Long personId);

    Iterable<PendingWord> getPendingWords();

    void declarePendingWordToTerm(String word);

    void declarePendingWordToNonTerm(String word);


}
