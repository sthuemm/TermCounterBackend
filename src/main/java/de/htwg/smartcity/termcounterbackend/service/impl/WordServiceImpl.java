package de.htwg.smartcity.termcounterbackend.service.impl;

import de.htwg.smartcity.termcounterbackend.dao.NonTermRepository;
import de.htwg.smartcity.termcounterbackend.dao.PendingWordRepository;
import de.htwg.smartcity.termcounterbackend.dao.PersonRepository;
import de.htwg.smartcity.termcounterbackend.dao.TermRepository;
import de.htwg.smartcity.termcounterbackend.model.NonTerm;
import de.htwg.smartcity.termcounterbackend.model.PendingWord;
import de.htwg.smartcity.termcounterbackend.model.Person;
import de.htwg.smartcity.termcounterbackend.model.Term;
import de.htwg.smartcity.termcounterbackend.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class WordServiceImpl implements WordService {

    @Resource
    private TermRepository termRepository;

    @Resource
    private NonTermRepository nonTermRepository;

    @Resource
    private PendingWordRepository pendingWordRepository;

    @Resource
    private PersonRepository personRepository;

    @Override
    public void checkSentencesForNewTerms(String sentence, Long personId) {
        Optional<Person> person = personRepository.findById(personId);

        List<String> words = Arrays.asList(sentence.split(" "));
        System.out.println(words);
        if(person.isPresent()){
            List<Term> personsTerms = person.get().getTerms();
            words.forEach(word -> {

                Optional<Term> term = termRepository.findByWord(word);

                //Wort ist ein Begriff
                if(term.isPresent()){
                    //Wort gehört noch nicht zum Begriffsschatz der Person
                    if(!personsTerms.contains(term)){
                        personsTerms.add(term.get());
                        log.info("added '"+ word + "' to person");
                    }
                    return;
                }
                //Wort ist kein Begriff
                Optional<NonTerm> nonTerm = nonTermRepository.findByWord(word);
                if(nonTerm.isPresent()){
                    log.info("' "+word + "' is not a term");
                //Noch unklar ob 'word' ein Begriff ist oder nicht
                } else if(!pendingWordRepository.findById(word).isPresent()) {
                    PendingWord pendingWord = new PendingWord(word);
                    pendingWord.setPersonId(personId);
                    pendingWordRepository.save(pendingWord);
                    log.info("' "+word + "' added in pending words");
                }
            });
            personRepository.save(person.get());
        } else {
            Person person1 = new Person();
            person1.setId(1L);
            personRepository.save(person1);
        }

    }

    @Override
    public Iterable<PendingWord> getPendingWords() {
        return pendingWordRepository.findAll();
    }

    @Override
    public void declarePendingWordToTerm(String word) {
        Optional<PendingWord> pendingWordOptional = pendingWordRepository.findById(word);
        if(pendingWordOptional.isPresent()){
            PendingWord pendingWord = pendingWordOptional.get();
            removePendingWordFromTable(pendingWord);
            Term newTerm = new Term(pendingWord.getWord());
            termRepository.save(newTerm);
            log.info("Added '"+word+"' to Term table ");
            Optional<Person> personOptional = personRepository.findById(pendingWord.getPersonId());
            if(personOptional.isPresent()){
                Person person = personOptional.get();
                person.getTerms().add(newTerm);
                personRepository.save(person);
                newTerm.getPersons().add(person);
                termRepository.save(newTerm);
                log.info("Assigned '"+person.getFirstName()+" "+person.getLastName()+"' to '"+word+"'");
            }
        }


    }

    @Override
    public void declarePendingWordToNonTerm(String word) {
        Optional<PendingWord> pendingWordOptional = pendingWordRepository.findById(word);
        if(pendingWordOptional.isPresent()){
            PendingWord pendingWord = pendingWordOptional.get();
            removePendingWordFromTable(pendingWord);
            NonTerm nonTerm = new NonTerm(pendingWord.getWord());
            nonTermRepository.save(nonTerm);
            log.info("Added '"+word+"' to Non-Term table");
        }

    }

    private void removePendingWordFromTable(PendingWord word){
        pendingWordRepository.delete(word);
        log.info("Removed '"+word+"' from pendingTable ");
    }

    public void initializeTestData(){
        log.info("Initializing Test Data");
    }
}
