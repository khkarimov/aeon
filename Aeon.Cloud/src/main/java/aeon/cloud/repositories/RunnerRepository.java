package aeon.cloud.repositories;

import aeon.cloud.models.Runner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for runner information.
 */
@Repository
public interface RunnerRepository extends MongoRepository<Runner, String> {
}

