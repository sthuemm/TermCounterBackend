package de.htwg.smartcity.termcounterbackend.dao;

import de.htwg.smartcity.termcounterbackend.model.FederalState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FederalStateRepository extends CrudRepository<FederalState, Long> {
}
