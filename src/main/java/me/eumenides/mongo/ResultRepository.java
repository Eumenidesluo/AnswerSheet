package me.eumenides.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Eumenides on 2017/10/23.
 */
public interface ResultRepository extends MongoRepository<Result,String> {
    Page<Result> findResultsByUserId(Integer userId, Pageable pageable);
}
