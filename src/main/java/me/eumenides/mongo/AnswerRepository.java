package me.eumenides.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Eumenides on 2017/10/20.
 */
@Service
public interface AnswerRepository extends MongoRepository<Answer,String> {
    public Page<Answer> findAnswersByNameLikeAndUploaderLike(String name,String uploader, Pageable pageable);
    Page<Answer> findAnswersByNameLike(String name,Pageable pageable);
    Page<Answer> findAnswersByUploaderLike(String uploader,Pageable pageable);
}
