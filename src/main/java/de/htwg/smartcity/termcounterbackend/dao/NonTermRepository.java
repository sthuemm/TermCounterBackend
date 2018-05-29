package de.htwg.smartcity.termcounterbackend.dao;

import de.htwg.smartcity.termcounterbackend.model.NonTerm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Repository
public interface NonTermRepository extends CrudRepository<NonTerm, String> {

    Optional<NonTerm> findByWord(String id);

    @Async
    CompletableFuture<List<NonTerm>> readAllBy();

}
