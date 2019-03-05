package com.github.itaborda.restmetadata.client.repositories;


import com.github.itaborda.restmetadata.client.model.SampleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleEntityRepository extends MongoRepository<SampleEntity, String> {


}