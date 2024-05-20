package com.legato.agendavirtual.repositories;

import com.legato.agendavirtual.entities.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, Long> {
}
