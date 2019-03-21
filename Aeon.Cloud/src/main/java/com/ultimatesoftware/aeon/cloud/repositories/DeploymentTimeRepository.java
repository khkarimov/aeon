package com.ultimatesoftware.aeon.cloud.repositories;

import com.ultimatesoftware.aeon.cloud.models.DeploymentTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for runner information.
 */
@Repository
public interface DeploymentTimeRepository extends MongoRepository<DeploymentTime, String> {
}

