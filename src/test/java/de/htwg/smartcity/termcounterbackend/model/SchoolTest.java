package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
public class SchoolTest {

    @Test
    public void createNewSchoolObject(){
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("Max");
        person.setLastName("Mustermann");
        person.setDayOfBirth(LocalDate.of(2000, 1,1));

        School school = new School();
        school.setId(1L);
        school.setName("Max Mustermann Schule");
        school.setAddress(createTestAdress());
        school.getPersons().add(person);

        Assert.assertEquals("Id must be 1L", 1L, school.getId());
        Assert.assertEquals("Name must be Max Mustermann Schule", "Max Mustermann Schule", school.getName());
        Assert.assertEquals("Street must be Musterstraße 1", "Musterstraße 1", school.getAddress().getStreet());
        Assert.assertEquals("City must be Musterstadt", "Musterstadt", school.getAddress().getCity());
        Assert.assertEquals("Postal must be 12345", 12345, school.getAddress().getPostal());
        Assert.assertEquals("Address id must be 1L", 1L, school.getAddress().getId());
        Assert.assertEquals("First name of person must be Max", "Max", school.getPersons().get(0).getFirstName());
    }

    private Address createTestAdress(){
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Musterstraße 1");
        address.setCity("Musterstadt");
        address.setPostal(12345);

        return address;
    }


}
