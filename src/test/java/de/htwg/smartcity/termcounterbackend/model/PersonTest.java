package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;


@RunWith(SpringRunner.class)
public class PersonTest {

    @Test
    public void createNewPerson(){
        Person person = new Person();
        person.setId(1L);
        person.setFirstname("Max");
        person.setLastname("Mustermann");
        person.setDayOfBirth(LocalDate.of(2000, 1,1));

        Assert.assertEquals("Id must be 1L", 1L, person.getId());
        Assert.assertEquals("First name must be Max", "Max", person.getFirstname());
        Assert.assertEquals("Last name must be Mustermann", "Mustermann", person.getLastname());
        Assert.assertEquals("Day of Birth must be 2000/1/1", "2000-01-01", person.getDayOfBirth().toString());
    }

}
