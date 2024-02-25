package com.cs206.g2t2.repository;

import com.cs206.g2t2.models.BsTeam;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BsTeamRepository extends MongoRepository<BsTeam, String> {
    Optional<BsTeam> findBy_id(String _id);

    Optional<BsTeam> findByTeamName(String teamName);

}
