package com.cs206.g2t2.repository;

import com.cs206.g2t2.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    /**
     *  Finds Optional<User> by _id
     *
     * @param _id String object containing _id to be searched
     */
    Optional<User> findBy_id(String _id);

    /**
     *  Finds Optional<User> by username
     *
     * @param username String object containing username to be searched
     */
    Optional<User> findByUsername(String username);

    /**
     *  Finds Optional<User> by email
     *
     * @param email String object containing email to be searched
     */
    Optional<User> findByEmail(String email);

    /**
     * Deletes User by username
     *
     * @param username String object containing username of user to be deleted
     */
    void deleteByUsername(String username);
}
