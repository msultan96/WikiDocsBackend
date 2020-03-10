package com.infy.WikiDocsProject.Repository;
import com.infy.WikiDocsProject.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	/**
	 * @name findUserByName
	 * @Desciption Retrieve user of give name
	 * @param name
	 * @return user object
	 */
	 Optional<User> findUserByName(String name);
}
