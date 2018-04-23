package de.htwg.smartcity.termcounterbackend.dao;

import de.htwg.smartcity.termcounterbackend.model.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WorldRepositoryTest {

    @Resource
    private WorldRepository worldRepository;

    @Test
    public void saveWorldItem(){
        World world = new World();
        worldRepository.save(world);
    }



}
