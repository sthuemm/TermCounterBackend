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
        initGraduations();
        initSchools();
        initCities();
        initDistricts();
        initFederalStates();
        initCountries();
        initUnionOfStates();

    }

    private void initUnionOfStates(){
        UnionOfStates unionOfStates = new UnionOfStates("Europäische Union");
        unionOfStates.getCountries().add(findCountry("Deutschland"));
        unionOfStates.getCountries().add(findCountry("Frankreich"));
        unionOfStatesRepository.save(unionOfStates);

        unionOfStates = new UnionOfStates("Organisation Amerikanischer Staaten");
        unionOfStates.getCountries().add(findCountry("Kanada"));
        unionOfStatesRepository.save(unionOfStates);

        unionOfStatesRepository.findAll().forEach(unionOfStates1 -> log.info("Added '"+unionOfStates1.getName()+"' into Table 'UnionOfStates'"));
    }

    private Country findCountry(String name){
        Country country = countyRepository.findByName(name);
        if(country == null){
            log.error("Country '"+name+"' could not be found");
        }
        return country;
    }

    private void initCountries(){
        Country country = new Country("Deutschland");
        country.getFederalStates().add(findFederalState("Bayern"));
        country.getFederalStates().add(findFederalState("Baden Wuerttemberg"));
        countyRepository.save(country);

        country = new Country("Frankreich");
        country.getFederalStates().add(findFederalState("Alsace"));
        countyRepository.save(country);

        country = new Country("Kanada");
        country.getFederalStates().add(findFederalState("Ontario"));
        countyRepository.save(country);

        //print new districts
        countyRepository.findAll().forEach(country1 -> log.info("Added '"+country1.getName()+"' into Table 'Country'"));
    }

    private FederalState findFederalState(String name){
        FederalState federalState = federalStateRepository.findByName(name);
        if(federalState == null){
            log.error("FederalState '"+name+"' could not be found");
        }
        return federalState;
    }

    private void initFederalStates(){
        FederalState federalState = new FederalState("Baden Wuerttemberg");
        federalState.getDistricts().add(findDistrict("Konstanz"));
        federalState.getDistricts().add(findDistrict("Stuttgart"));
        federalStateRepository.save(federalState);

        federalState = new FederalState("Bayern");
        federalState.getDistricts().add(findDistrict("Muenchen"));
        federalStateRepository.save(federalState);

        federalState = new FederalState("Alsace");
        federalState.getDistricts().add(findDistrict("Colmar"));
        federalStateRepository.save(federalState);

        federalState = new FederalState("Ontario");
        federalState.getDistricts().add(findDistrict("Toronto"));
        federalStateRepository.save(federalState);

        //print new districts
        federalStateRepository.findAll().forEach(federalState1 -> log.info("Added '"+federalState1.getName()+"' into Table 'Federal State'"));
    }

    private District findDistrict(String name){
        District district = districtRepository.findByName(name);
        if(district == null){
            log.error("District '"+name+"' could not be found");
        }
        return district;
    }

    private void initDistricts(){
        District district = new District("Konstanz");
        district.getCities().add(findCity("Konstanz"));
        district.getCities().add(findCity("Radolfzell"));
        districtRepository.save(district);
        district = new District("Stuttgart");
        district.getCities().add(findCity("Stuttgart"));
        districtRepository.save(district);
        district = new District("Muenchen");
        district.getCities().add(findCity("Muenchen"));
        districtRepository.save(district);

        district = new District("Colmar");
        district.getCities().add(findCity("Colmar"));
        districtRepository.save(district);

        district = new District("Toronto");
        district.getCities().add(findCity("Toronto"));
        districtRepository.save(district);


        //print new districts
        districtRepository.findAll().forEach(district1 -> log.info("Added '"+district1.getName()+"' into Table 'District'"));
    }

    private City findCity(String name){
        City city = cityRepository.findByName(name);
        if(city == null){
            log.error("City '"+name+"' could not be found");
        }
        return city;
    }

    private void initCities(){
        City city = new City("Konstanz");
        city.getSchools().add(findSchool("Geschwister Scholl Schule"));
        city.getSchools().add(findSchool("Wessenbergschule"));
        city.getSchools().add(findSchool("Humboldgymnasium"));
        cityRepository.save(city);
        city = new City("Radolfzell");
        city.getSchools().add(findSchool("Mettnau Schule"));
        cityRepository.save(city);
        city = new City("Stuttgart");
        city.getSchools().add(findSchool("Merz Schule"));
        cityRepository.save(city);
        city = new City("Muenchen");
        city.getSchools().add(findSchool("Lukas-Schule"));
        cityRepository.save(city);

        city = new City("Colmar");
        city.getSchools().add(findSchool("School Bartholdi"));
        cityRepository.save(city);

        city = new City("Toronto");
        city.getSchools().add(findSchool("Toronto Public School"));
        cityRepository.save(city);

        //print new cities
        cityRepository.findAll().forEach(city1 -> log.info("Added '"+city1.getName()+"' into Table 'City'"));
    }

    private Graduation findGraduation(String name){
        Graduation graduation = graduationRepository.findByName(name);
        if(graduation == null){
            log.error("Graduation '"+name+"' could not be found");
        }

        return graduation;
    }

    private void initGraduations(){
        Graduation graduation = new Graduation("Hauptschule_GSS");
        graduation.getPersons().add(findByFirstAndLastName("Hans", "Kohlhorst"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Realschule_Wessenberg");
        graduation.getPersons().add(findByFirstAndLastName("Jennifer", "Schröder"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Gymnasium_Wessenberg");
        graduation.getPersons().add(findByFirstAndLastName("Mario", "Gruenkamp"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Gymnasium_MettnauSchule");
        graduation.getPersons().add(findByFirstAndLastName("Niklas", "Kohlfeld"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Gymnasium_MerzSchule");
        graduation.getPersons().add(findByFirstAndLastName("Mike", "Zimmermann"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Gymnasium_Humboldgymnasium");
        graduation.getPersons().add(findByFirstAndLastName("Carl", "Friedenshorst"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Gymnasium_Lukas-Schule");
        graduation.getPersons().add(findByFirstAndLastName("Daniel", "Köster"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Gymnasium_SchoolBartholdi");
        graduation.getPersons().add(findByFirstAndLastName("Jaqueline", "Jaunefevre"));
        graduationRepository.save(graduation);
        graduation = new Graduation("Gymnasium_TorontoPublicSchool");
        graduation.getPersons().add(findByFirstAndLastName("Cookie", "Ashton"));
        graduationRepository.save(graduation);

        //print new graduations
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
        newSchool.getGraduations().add(findGraduation("Hauptschule_GSS"));
        schoolRepository.save(newSchool);

        newSchool = new School("Wessenbergschule");
        newSchool.getGraduations().add(findGraduation("Gymnasium_Wessenberg"));
        newSchool.getGraduations().add(findGraduation("Realschule_Wessenberg"));
        schoolRepository.save(newSchool);

        newSchool = new School("Mettnau Schule");
        newSchool.getGraduations().add(findGraduation("Gymnasium_MettnauSchule"));
        schoolRepository.save(newSchool);
        newSchool = new School("Merz Schule");
        newSchool.getGraduations().add(findGraduation("Gymnasium_MerzSchule"));
        schoolRepository.save(newSchool);

        newSchool = new School("Humboldgymnasium");
        newSchool.getGraduations().add(findGraduation("Gymnasium_Humboldgymnasium"));
        schoolRepository.save(newSchool);

        newSchool = new School("Lukas-Schule");
        newSchool.getGraduations().add(findGraduation("Gymnasium_Lukas-Schule"));
        schoolRepository.save(newSchool);

        newSchool = new School("School Bartholdi");
        newSchool.getGraduations().add(findGraduation("Gymnasium_SchoolBartholdi"));
        schoolRepository.save(newSchool);

        newSchool = new School("Toronto Public School");
        newSchool.getGraduations().add(findGraduation("Gymnasium_TorontoPublicSchool"));
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
        personRepository.save(new Person("Carl", "Friedenshorst"));
        personRepository.save(new Person("Daniel", "Köster"));
        personRepository.save(new Person("Jaqueline", "Jaunefevre"));
        personRepository.save(new Person("Cookie", "Ashton"));

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
        if(person == null){
            log.error("Person '"+firstname+" "+lastname+"' could not be found" );
        }
        person.getTerms().add(term);
        return person;
    }

    private Person findByFirstAndLastName(String firstname, String lastname){
        Person person = personRepository.findByFirstnameAndLastname(firstname, lastname);
        if(person == null){
            log.error("Person '"+firstname+" "+lastname+"' could not be found" );
        }
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
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Carl", "Friedenshorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Daniel", "Köster"));
        }
        optionalTerm = termRepository.findByWord("Haus");
        if(optionalTerm.isPresent()){
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Hans", "Kohlhorst"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Mario", "Gruenkamp"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Niklas", "Kohlfeld"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Cookie", "Ashton"));
            personRepository.save(findByFirstAndLastNameAndAddTermToPerson(optionalTerm.get(),"Jaqueline", "Jaunefevre"));
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
