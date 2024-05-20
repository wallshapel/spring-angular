package com.legato.agendavirtual.services.contact;

import com.legato.agendavirtual.dtos.ContactDTO;
import com.legato.agendavirtual.entities.Contact;
import com.legato.agendavirtual.exceptions.ContactNotFoundException;
import com.legato.agendavirtual.repositories.ContactRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ContactImp implements IContact {

    private final ContactRepository contactRepository; // No es necesario el Autowired para inyectar la dependencia. con @AllArgsConstructor se resuelve eso
    private final ModelMapper modelMapper;

    private ContactDTO mapToDTO(Contact contact) {
        return modelMapper.map(contact, ContactDTO.class);
    }

    private Contact mapToEntity(ContactDTO contactDTO) {
        return modelMapper.map(contactDTO, Contact.class);
    }

    @Override
    public Iterable<ContactDTO> index() {
        return StreamSupport.stream(contactRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDTO show(Long id) {
        return contactRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null); // Consider throwing an exception or returning an optional
    }

    @Override
    public ContactDTO store(ContactDTO contactDTO) {
        Contact contact = mapToEntity(contactDTO);
        contact.setCreatedAt(LocalDateTime.now()); // Assuming you want to set the current time
        return mapToDTO(contactRepository.save(contact));
    }

    @Override
    public ContactDTO update(Long id, ContactDTO contactDTO) {
        if (contactRepository.existsById(id)) {
            Contact contact = mapToEntity(contactDTO);
            contact.setId(id); // Ensure the entity has the correct ID
            return mapToDTO(contactRepository.save(contact));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (contactRepository.existsById(id))
            contactRepository.deleteById(id);
        else
            throw new ContactNotFoundException("Contact with id " + id + " not found");
    }
}