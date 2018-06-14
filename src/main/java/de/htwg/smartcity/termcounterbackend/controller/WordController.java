package de.htwg.smartcity.termcounterbackend.controller;


import de.htwg.smartcity.termcounterbackend.dao.WorldRepository;
import de.htwg.smartcity.termcounterbackend.model.Sentence;
import de.htwg.smartcity.termcounterbackend.model.World;
import de.htwg.smartcity.termcounterbackend.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Controller
@Slf4j
@CrossOrigin(origins = "*")
public class WordController {

    @Resource
    private WordService wordService;

    @Resource
    private WorldRepository worldRepository;

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

    @PostMapping("/person/{personId}")
    public ResponseEntity addPostWords(@RequestBody Sentence sentence, @PathVariable Long personId){
        wordService.checkSentencesForNewTerms(sentence.getSentence(), personId);
        log.info("sentence per POST: "+sentence);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/pendingWords")
    public String getPendingWords(Model model){

        model.addAttribute("pendingWords", wordService.getPendingWords());

        return "pendingWords";
    }

    @GetMapping("")
    public String getPerson(Model model){
        List<World> world = (List<World>) worldRepository.findAll();

        model.addAttribute("world", world.get(0));

        return "index";
    }

    @PostMapping("/term")
    public ResponseEntity setWordAsTerm(@RequestBody String word){
        System.out.println("word as term: " +word);
        wordService.declarePendingWordToTerm(word);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getEverythingAsJson(){

        return new ResponseEntity(worldRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/nonTerm")
    public ResponseEntity setWordAsNonTerm(@RequestBody String word){
        System.out.println("word as no term: "+word);
        wordService.declarePendingWordToNonTerm(word);
        return new ResponseEntity(HttpStatus.OK);
    }


}
