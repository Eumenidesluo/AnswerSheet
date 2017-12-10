package me.eumenides.controller;

import me.eumenides.beans.ResultHistoryShowContent;
import me.eumenides.interceptor.Auth;
import me.eumenides.mongo.Result;
import me.eumenides.mongo.ResultRepository;
import me.eumenides.utils.R;
import me.eumenides.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eumenides on 2017/12/2.
 */
@Controller
@Auth
public class HistoryController {
    @Autowired
    private ResultRepository resultRepository;

    @RequestMapping("/historyPage")
    public String getResultHistoryPage() {
        return "history";
    }

    @RequestMapping("/resultHistory")
    @ResponseBody
    public R getResultHistory(Integer page, Integer row, Integer userCode) {
        PageRequest pageRequest = new PageRequest(page - 1, row);
        Page<Result> findResults = resultRepository.findResultsByUserId(userCode,pageRequest);
        if (findResults != null) {
            List<ResultHistoryShowContent> contents = new ArrayList<>();
            for (Result rVal : findResults.getContent()) {
                ResultHistoryShowContent content = new ResultHistoryShowContent();
                content.setTime(TimeUtils.convertDate2String(rVal.getUploadDate()));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 16; i++) {
                    sb.append(i + 1);
                    sb.append('~');
                    sb.append(i + 5);
                    sb.append(' ');
                    for (int j = 0; j < 5; j++) {
                        int a = rVal.getAnswers()[i * 5 + j];
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
                sb = new StringBuilder();
                if (rVal.getCheckout() != null) {
                    for (int c : rVal.getCheckout()) {
                        sb.append(c+" ");
                    }
                    content.setCheckout(sb.toString());
                }
                contents.add(content);
            }
            return R.ok().put("resultPage", contents).put("totalPage",findResults.getTotalPages());
        }
        return R.error();
    }
}
