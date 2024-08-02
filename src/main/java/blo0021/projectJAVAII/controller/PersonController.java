package blo0021.projectJAVAII.controller;


import blo0021.projectJAVAII.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import blo0021.projectJAVAII.service.PersonService;


import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPersons(){
        log.info("Fetching all persons");
        return personService.findAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getProjectById(@PathVariable Long id){
        log.info("Fetching person by id: {}", id);
        return personService.findPersonById(id)
                .map(person -> {
                    log.info("Found person: {}", person);
                    return ResponseEntity.ok(person);
                })
                .orElseGet(() -> {
                    log.warn("Failed to find person with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        log.info("Creating a new person: {}", person);
        return personService.savePerson(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        log.info("Updating person with id: {}", id);
        return personService.findPersonById(id)
                .map(person -> {
                    log.info("Updating person details from {} to {}", person, personDetails);
                    person.setName(personDetails.getName());
                    person.setEmail(personDetails.getEmail());
                    return ResponseEntity.ok(personService.savePerson(person));
                })
                .orElseGet(() -> {
                    log.warn("Failed to find person with id: {} for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        log.info("Attempting to delete person with id: {}", id);
        return personService.findPersonById(id)
                .map(person -> {
                    personService.deletePerson(id);
                    log.info("Deleted person with id: {}", id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> {
                    log.warn("Failed to delete person with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

}

