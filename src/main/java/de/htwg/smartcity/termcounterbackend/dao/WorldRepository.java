package de.htwg.smartcity.termcounterbackend.dao;

import de.htwg.smartcity.termcounterbackend.model.World;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldRepository extends CrudRepository<World, Long> {
}
