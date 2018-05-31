package de.htwg.smartcity.termcounterbackend.service.impl;

import de.htwg.smartcity.termcounterbackend.dao.*;
import de.htwg.smartcity.termcounterbackend.model.*;
import de.htwg.smartcity.termcounterbackend.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
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

    @Resource
    private SchoolRepository schoolRepository;

    @Resource
    private GraduationRepository graduationRepository;

    @Resource
    private CityRepository cityRepository;

    @Resource
    private DistrictRepository districtRepository;

    @Resource
    private FederalStateRepository federalStateRepository;

    @Resource
    private CountyRepository countyRepository;

    @Resource
    private UnionOfStatesRepository unionOfStatesRepository;

    @Resource
    private WorldRepository worldRepository;

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
            log.error("No database entry for id "+personId+" found");
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
                log.info("Assigned '"+person.getFirstname()+" "+person.getLastname()+"' to '"+word+"'");
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
        initTerms();
        initPersons();
        assignTermsToPersons();
        initSchools();
        initGraduations();

    }

    private void initCities(){

    }

    private void initGraduations(){
        Graduation graduation = new Graduation("Hauptschule");
        graduation.getSchools().add(findSchool("Geschwister Scholl Schule"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Realschule");
        graduation.getSchools().add(findSchool("Wessenbergschule"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Gymnasium");
        graduation.getSchools().add(findSchool("Humboldgymnasium"));
        graduation.getSchools().add(findSchool("Mettnau Schule"));
        graduation.getSchools().add(findSchool("Merz Schule"));
        graduationRepository.save(graduation);

        graduationRepository.findAll().forEach(graduation1 -> log.info("Added '"+graduation1.getName()+"' into Table 'Graduation'"));
    }

    private School findSchool(String name){
        School school = schoolRepository.findByName(name);
        if(school == null){
            log.error("School '"+name+"' could not be found" );
        }
        return school;
    }

    private void initSchools(){
        School newSchool = new School("Geschwister Scholl Schule");
        newSchool.getPersons().add(personRepository.findByFirstnameAndLastname("Hans", "Kohlhorst"));
        schoolRepository.save(newSchool);
        newSchool = new School("Mettnau Schule");
        newSchool.getPersons().add(personRepository.findByFirstnameAndLastname("Mario", "Gruenkamp"));
        schoolRepository.save(newSchool);
        newSchool = new School("Merz Schule");
        newSchool.getPersons().add(personRepository.findByFirstnameAndLastname("Niklas", "Kohlfeld"));
        schoolRepository.save(newSchool);
        newSchool = new School("Wessenbergschule");
        newSchool.getPersons().add(personRepository.findByFirstnameAndLastname("Jennifer", "Schröder"));
        schoolRepository.save(newSchool);
        newSchool = new School("Humboldgymnasium");
        newSchool.getPersons().add(personRepository.findByFirstnameAndLastname("Mike", "Zimmermann"));
        schoolRepository.save(newSchool);

        //print new schools
        schoolRepository.findAll().forEach(school -> log.info("Added '"+school.getName()+"' into table 'School'"));
    }

    private void initPersons(){
        personRepository.save(new Person("Hans", "Kohlhorst"));
        personRepository.save(new Person("Mario", "Gruenkamp"));
        personRepository.save(new Person("Niklas", "Kohlfeld"));
        personRepository.save(new Person("Jennifer", "Schröder"));
        personRepository.save(new Person("Mike", "Zimmermann"));

        //print new persons
        personRepository.findAll().forEach(person -> log.info("Added '"+person.getFirstname()+" "+person.getLastname()+"' into table 'Person'"));
    }

    private void initTerms(){
        termRepository.save(new Term("Begriff"));
        termRepository.save(new Term("Haus"));
        termRepository.save(new Term("Bank"));
        termRepository.save(new Term("Auto"));
        termRepository.save(new Term("Smartphone"));
        termRepository.save(new Term("Schulabschluss"));
        termRepository.save(new Term("Familie"));
        termRepository.save(new Term("Arbeitsplatz"));
        termRepository.save(new Term("Präsentation"));
        termRepository.save(new Term("Software"));

        //print new terms
        termRepository.findAll().forEach(term -> log.info("Added '"+term.getWord()+"' into table 'Term'"));
    }

    private Person findByFirstAndLastNameAndAddTermToPerson(Term term, String firstname, String lastname){
        Person person = personRepository.findByFirstnameAndLastname(firstname, lastname);
        person.getTerms().add(term);
        return person;
    }

    private void assignTermsToPersons(){
        Optional<Term> optionalTerm = termRepository.findByWord("Begriff");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Jennifer", "Schröder"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mike", "Zimmermann"));
        }
        optionalTerm = termRepository.findByWord("Haus");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }
        optionalTerm = termRepository.findByWord("Bank");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }
        optionalTerm = termRepository.findByWord("Auto");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }
        optionalTerm = termRepository.findByWord("Smartphone");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }
        optionalTerm = termRepository.findByWord("Schulabschluss");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }
        optionalTerm = termRepository.findByWord("Familie");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }
        optionalTerm = termRepository.findByWord("Arbeitsplatz");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }
        optionalTerm = termRepository.findByWord("Präsentation");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }
        optionalTerm = termRepository.findByWord("Software");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
        }

    }
}
