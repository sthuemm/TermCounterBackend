package de.htwg.smartcity.termcounterbackend.controller;

import de.htwg.smartcity.termcounterbackend.dao.PersonRepository;
import de.htwg.smartcity.termcounterbackend.model.PendingWord;
import de.htwg.smartcity.termcounterbackend.model.Person;
import de.htwg.smartcity.termcounterbackend.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
public class WordController {

    @Resource
    private WordService wordService;

    @Resource
    private PersonRepository personRepository;

    @PostMapping("/")
    public ResponseEntity addWords(@RequestBody String sentence){
        wordService.checkSentencesForNewTerms(sentence, 1L);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/person/{personId}/{sentence}")
    public ResponseEntity addGetWords(@PathVariable String sentence, @PathVariable Long personId){
        wordService.checkSentencesForNewTerms(sentence, personId);
        System.out.println(sentence);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/pendingWords")
    public String getPendingWords(Model model){

        model.addAttribute("pendingWords", wordService.getPendingWords());

        return "pendingWords";
    }

    @GetMapping("/person/{personId}")
    public String getPerson(Model model, @PathVariable Long personId){
        Optional<Person> personOptional = personRepository.findById(personId);
        if(personOptional.isPresent()){
            Person person = personOptional.get();
            model.addAttribute("numberTerms", person.getTerms().size());
        }


        return "index";
    }

    @PostMapping("/term")
    public ResponseEntity setWordAsTerm(@RequestBody String word){
        System.out.println("word as term: " +word);
        wordService.declarePendingWordToTerm(word);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/nonTerm")
    public ResponseEntity setWordAsNonTerm(@RequestBody String word){
        System.out.println("word as no term: "+word);
        wordService.declarePendingWordToNonTerm(word);
        return new ResponseEntity(HttpStatus.OK);
    }


}