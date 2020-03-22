package com.infy.WikiDocsProject.Repository;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Role role);
}
