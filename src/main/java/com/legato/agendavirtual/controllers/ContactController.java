package com.legato.agendavirtual.controllers;

import com.legato.agendavirtual.dtos.ContactDTO;
import com.legato.agendavirtual.exceptions.ContactNotFoundException;
import com.legato.agendavirtual.services.contact.ContactImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ContactController {

    private final ContactImp contactImp; // No es necesario el Autowired para inyectar la dependencia. con @AllArgsConstructor se resuelve eso

    @Transactional(readOnly = true)
    @GetMapping("contacts")
    public ResponseEntity<Iterable<ContactDTO>> index() {
        return new ResponseEntity<>(contactImp.index(), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("contact/{id}")
    public ResponseEntity<ContactDTO> show(@PathVariable Long id) {
        ContactDTO contact = contactImp.show(id);
        if (contact != null)
            return new ResponseEntity<>(contact, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("contact")
    public ContactDTO store(@Validated @RequestBody ContactDTO contactDTO) {
        return contactImp.store(contactDTO);
    }

    @Transactional
    @PutMapping("contact/{id}")
    public ResponseEntity<ContactDTO> update(@Validated @PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = contactImp.update(id, contactDTO);
        if (updatedContact != null)
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("contact/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            contactImp.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ContactNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
