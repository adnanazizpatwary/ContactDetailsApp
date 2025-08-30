# Contact Diary App

A simple Android application to manage contacts. Users can **add, view, update, delete, and search contacts**. The app uses **SQLite** for local storage and **Firebase Realtime Database** for cloud synchronization.

---

## Features

- Add new contacts with **name, phone number, and email**.
- View all contacts in a **RecyclerView**.
- Search contacts by **name, phone, or email**.
- Update existing contacts.
- Delete contacts with **confirmation dialog**.
- Data synchronization with **Firebase Realtime Database**.

---

## Tech Stack

- **Language:** Java  
- **Platform:** Android Studio  
- **Database:** SQLite & Firebase Realtime Database  
- **UI Components:** RecyclerView, SearchView, Buttons, EditText, TextView

---

## Object-Oriented Programming (OOP)

This project follows OOP principles:

- **Encapsulation:** `ContactPerson` class encapsulates contact data using private fields and public getters/setters.
- **Abstraction:** `DatabaseHandler` class abstracts SQLite operations.
- **Polymorphism:** `PersonAdapter` uses interface `OnItemClickListener` for click events.
- **Modularity:** Each Activity and class handles a specific task.

---

## Design Patterns Used

- **Observer Pattern:**  
  - Firebase Realtime Database updates are handled using `ValueEventListener`.
  - RecyclerView click events are handled using `OnItemClickListener` interface.

---

## Screenshots

*()*

---

## Installation & Setup

1. Clone this repository:

```bash
git clone https://github.com/adnanazizpatwary/ContactDetailsApp.git
