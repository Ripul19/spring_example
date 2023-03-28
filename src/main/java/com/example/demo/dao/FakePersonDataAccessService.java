package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{
    private static List<Person> Db = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person) {
        Db.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return Db;
    }

    @Override
    public Optional<Person> selectPerosnById(UUID id) {
        return Db.stream().filter(Person -> Person.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPerosnById(id);
        if(personMaybe.isEmpty()){
            return 0;
        }
        else{
         Db.remove(personMaybe.get());
         return 1;
        }
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return selectPerosnById(id).map(p -> {
            int indexOfPersonToDelete=Db.indexOf(person);
            if(indexOfPersonToDelete>=0){
                Db.set(indexOfPersonToDelete,person);
                        return 1;
            }
            return 0;
        }).orElse(0);
    }
}
