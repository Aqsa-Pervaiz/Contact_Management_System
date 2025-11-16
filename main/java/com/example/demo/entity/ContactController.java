package com.example.demo.entity;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Integer id, @RequestBody Contact contactDetails) {
        Contact contact = contactRepository.findById(id).orElseThrow();
        contact.setTitle(contactDetails.getTitle());
        contact.setFirstName(contactDetails.getFirstName());
        contact.setLastName(contactDetails.getLastName());
        contact.setEmails(contactDetails.getEmails());
        contact.setPhones(contactDetails.getPhones());
        return contactRepository.save(contact);
        
    }
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Integer id) {
        contactRepository.deleteById(id);
    }
}
