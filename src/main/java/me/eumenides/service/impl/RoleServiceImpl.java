package me.eumenides.service.impl;

import me.eumenides.mongo.AnswerRepository;
import me.eumenides.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Eumenides on 2017/10/18.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    AnswerRepository answerRepository;
}
