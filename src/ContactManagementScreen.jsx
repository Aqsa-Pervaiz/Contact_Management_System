import React, { useState, useEffect } from "react";
import './ContactManagementScreen.css'
const ContactManagement = () => {
  const [contacts, setContacts] = useState([]);
  const [search, setSearch] = useState("");
  const [selectedContact, setSelectedContact] = useState(null);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  useEffect(() => { fetch("http://localhost:8081/contacts")  .then((res) => res.json()) .then((data) => setContacts(data)) .catch((err) => console.error("Error fetching contacts:", err)); },[]);
  const filteredContacts = contacts.filter(
  (c) =>
  c.firstName.toLowerCase().includes(search.toLowerCase()) ||
  c.lastName.toLowerCase().includes(search.toLowerCase())
  );

    const handleCreate = (newContact) => { fetch("http://localhost:8081/contacts", { method: "POST", headers: { "Content-Type": "application/json", },
       body: JSON.stringify(newContact),
      })
      .then((res) => res.json()) .then((savedContact) => { setContacts([...contacts, savedContact]); setShowCreateModal(false);
        })
        .catch((err) => console.error("Error creating contact:", err));
    };
    const handleUpdate = (updatedContact) => {
      const formattedContact = {...updatedContact, emails: [{ label: "primary", value: updatedContact.email }], phones: [{ label: "primary", value: updatedContact.phone }]
    };

  fetch(`http://localhost:8081/contacts/${updatedContact.id}`, {
    method: "PUT", headers: { "Content-Type": "application/json" }, body: JSON.stringify(formattedContact),
  })
  .then(res => res.json())
  .then(savedContact => {
    setContacts(
      contacts.map(c => (c.id === savedContact.id ? savedContact : c))
    );
    setShowUpdateModal(false);
  })
  .catch(err => console.error("Error updating contact:", err));
};

    const handleDelete = () => {
      fetch(`http://localhost:8081/contacts/${selectedContact.id}`, {
      method: "DELETE",
      })
      .then(() => {
      setContacts(contacts.filter((c) => c.id !== selectedContact.id));
      setShowDeleteModal(false);
      })
      .catch((err) => console.error("Error deleting contact:", err));
    };
    return (
    <div>
        <input type="text" className="search" placeholder="Search by first or last name" value={search} onChange={(e) => setSearch(e.target.value)} />
        <button onClick={() => setShowCreateModal(true)}>+ New Contact</button>
        <table className="contact-table">
        <thead>
        <tr>
            <th>Title</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        {filteredContacts.map((contact) => (
        <tr key={contact.id}>
        <td>{contact.title}</td>
        <td>{contact.firstName}</td>
        <td>{contact.lastName}</td>
        <td>{contact.emails?.[0]?.value || ""}</td>
        <td>{contact.phones?.[0]?.value || ""}</td>
        <td>
          <button onClick={() => { setSelectedContact(contact); setShowUpdateModal(true);}}>
            Update
          </button>
          <button onClick={() => { setSelectedContact(contact); setShowDeleteModal(true); }}> Delete </button>
        </td>
        </tr>
        ))}
        </tbody>
        </table>

        {showCreateModal && (
          <ContactForm title="Create Contact" onSave={handleCreate} onCancel={() => setShowCreateModal(false)} /> 
        )}

        {showUpdateModal && selectedContact && (
          <ContactForm title="Update Contact" initialData={selectedContact} onSave={handleUpdate} onCancel={() => setShowUpdateModal(false)} />
        )}
        {showDeleteModal && (
        <div className="deleteConfirmation">
          <p>
            Are you sure you want to delete?
          </p>
          <button onClick={handleDelete}>Confirm Delete</button>
          <button onClick={() => setShowDeleteModal(false)}>Cancel</button>
        </div>
        )}
    </div>
  );
};

const ContactForm = ({ title, onSave, onCancel, initialData }) => {
  const [contact, setContact] = useState(
    initialData || { firstName: "", lastName: "", phone: "" }
  );
    const handleSubmit = (e) => {
    e.preventDefault();
    onSave(contact);
  };

  return (
    <div>
        <div className="ContactForm">
            <h3>Contact Details</h3>
        <form onSubmit={handleSubmit}>
        <input type="text" placeholder="First Name" value={contact.firstName} onChange={(e) => setContact({ ...contact, firstName: e.target.value })} required />
        
        <input type="text" placeholder="Last Name" value={contact.lastName} onChange={(e) => setContact({ ...contact, lastName: e.target.value })} required />
        
        <input type="text" placeholder="Phone Number" value={contact.phone} onChange={(e) => setContact({ ...contact, phone: e.target.value })}required />
        <input type="text" placeholder="Title" value={contact.title || ""} onChange={(e) => setContact({ ...contact, title: e.target.value })} />

        <input type="text" placeholder="Email Address" value={contact.email || ""} onChange={(e) => setContact({ ...contact, email: e.target.value })}/>
        <br></br>
        <button type="submit">Save</button>
        <button type="button" onClick={onCancel}> Cancel</button>
        </form>
        </div>
    </div>
  );
};

export default ContactManagement;
