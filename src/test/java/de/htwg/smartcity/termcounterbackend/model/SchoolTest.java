package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SchoolTest {

    @Test
    public void createNewSchoolObjectNoArgsConstructor(){

        School school = new School();
        school.setId(1L);
        school.setName("Max Mustermann Schule");
        school.setAddress(createTestAdress());

        Assert.assertEquals("Id must be 1L", 1L, school.getId());
        Assert.assertEquals("Name must be Max Mustermann Schule", "Max Mustermann Schule", school.getName());
        Assert.assertEquals("Street must be Musterstraße 1", "Musterstraße 1", school.getAddress().getStreet());
        Assert.assertEquals("City must be Musterstadt", "Musterstadt", school.getAddress().getCity());
        Assert.assertEquals("Postal must be 12345", 12345, school.getAddress().getPostal());
        Assert.assertEquals("Address id must be 1L", 1L, school.getAddress().getId());
    }

    @Test
    public void createNewSchoolObjectAllConstructor(){

        School school = new School(1L, "Max Mustermann Schule");
        school.setAddress(createTestAdress());

        Assert.assertEquals("Id must be 1L", 1L, school.getId());
        Assert.assertEquals("Name must be Max Mustermann Schule", "Max Mustermann Schule", school.getName());
        Assert.assertEquals("Street must be Musterstraße 1", "Musterstraße 1", school.getAddress().getStreet());
        Assert.assertEquals("City must be Musterstadt", "Musterstadt", school.getAddress().getCity());
        Assert.assertEquals("Postal must be 12345", 12345, school.getAddress().getPostal());
        Assert.assertEquals("Address id must be 1L", 1L, school.getAddress().getId());
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
