package de.htwg.smartcity.termcounterbackend.dao;

import de.htwg.smartcity.termcounterbackend.model.District;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends CrudRepository<District, Long> {
}
