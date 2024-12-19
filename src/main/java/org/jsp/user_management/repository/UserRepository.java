package org.jsp.user_management.repository;
import java.util.Optional;

import org.jsp.user_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    Optional<User> login(String username, String password);

   
    Optional<User> findByEmail(String email);


}
