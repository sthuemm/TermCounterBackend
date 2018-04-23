package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class WorldTest {

    @Test
    public void createNewWorld(){
        World world = new World();
        world.setName("Erde");
        world.setId(1L);
        Assert.assertEquals("Name must be Erde", "Erde", world.getName());
        Assert.assertEquals("Id musst be 1L", 1L, world.getId());
    }

}
