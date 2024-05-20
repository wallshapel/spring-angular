package com.legato.agendavirtual.services.contact;

import com.legato.agendavirtual.dtos.ContactDTO;

public interface IContact {

    Iterable<ContactDTO> index();
    ContactDTO show(Long id);
    ContactDTO store(ContactDTO contactDTO);
    ContactDTO update(Long id, ContactDTO contactDTO);
    void delete(Long id);
}
