package com.studycollaboproject.scope.redis;

import org.springframework.data.repository.CrudRepository;

public interface ConnectedUserRedisRepository extends CrudRepository<ConnectedUser, String> {
}