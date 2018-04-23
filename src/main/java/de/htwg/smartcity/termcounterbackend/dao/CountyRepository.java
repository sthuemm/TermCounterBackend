package de.htwg.smartcity.termcounterbackend.dao;

import de.htwg.smartcity.termcounterbackend.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends CrudRepository<Country, Long> {
}
