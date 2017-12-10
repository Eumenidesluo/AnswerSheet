package me.eumenides.controller;

import me.eumenides.beans.AnswerHistoryShowContent;
import me.eumenides.interceptor.Auth;
import me.eumenides.mongo.Answer;
import me.eumenides.mongo.AnswerRepository;
import me.eumenides.utils.R;
import me.eumenides.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eumenides on 2017/12/2.
 */
@Controller
@Auth
public class SearchAnswersController {
    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping("/searchPage")
    public String getSearchAnswersPage() {
        return "search_answers";
    }

    @RequestMapping("/search")
    @ResponseBody
    public R search(@RequestParam(required = false) String name, @RequestParam(required = false) String uploader
            , Integer page, Integer row) {
        PageRequest pageRequest = new PageRequest(page - 1, row);
        Page<Answer> answers = null;
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(uploader))
            answers = answerRepository.findAnswersByNameLikeAndUploaderLike(name, uploader, pageRequest);
        else if (!StringUtils.isEmpty(name))
            answers = answerRepository.findAnswersByNameLike(name, pageRequest);
        else if (!StringUtils.isEmpty(uploader))
            answers = answerRepository.findAnswersByUploaderLike(uploader, pageRequest);
        else
            answers = answerRepository.findAll(pageRequest);
        if (answers == null)
            return R.error();
        List<AnswerHistoryShowContent> contents = new ArrayList<>();
        for (Answer answer : answers.getContent()) {
            AnswerHistoryShowContent content = new AnswerHistoryShowContent();
            content.setTime(TimeUtils.convertDate2String(answer.getUploadDate()));
            content.setName(answer.getName());
            content.setId(answer.getId());
            content.setUploader(answer.getUploader());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append(i + 1);
                sb.append('~');
                sb.append(i + 5);
                sb.append(' ');
                for (int j = 0; j < 5; j++) {
                    int a = answer.getAnswers()[i * 5 + j];
                    switch (a) {
                        case 1:
                            sb.append('A');
                            break;
                        case 2:
                            sb.append('B');
                            break;
                        case 3:
                            sb.append('C');
                            break;
                        case 4:
                            sb.append('D');
                            break;
                        default:
                            sb.append('N');
                            break;
                    }
                }
                sb.append("  ");
            }
            content.setAnswers(sb.toString());
            contents.add(content);
        }
        return R.ok().put("resultPage", contents).put("totalPage", answers.getTotalPages());

    }
}
