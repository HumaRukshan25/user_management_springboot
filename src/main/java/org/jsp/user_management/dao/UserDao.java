package org.jsp.user_management.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.user_management.entity.User;
import org.jsp.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private UserRepository repository;

    public Optional<User> login(String username, String password) {
        return repository.login(username, password);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return repository.findById(id);
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }
    
    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }
    
}
