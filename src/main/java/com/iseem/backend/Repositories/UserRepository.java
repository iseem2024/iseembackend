package com.iseem.backend.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.iseem.backend.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
   Optional<User> findByEmail(String email);
   List<User> findByRole(String role);
}
