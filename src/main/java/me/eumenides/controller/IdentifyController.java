package me.eumenides.controller;

import com.sun.jna.Pointer;
import me.eumenides.entity.User;
import me.eumenides.interceptor.Auth;
import me.eumenides.mongo.Answer;
import me.eumenides.mongo.AnswerRepository;
import me.eumenides.mongo.Result;
import me.eumenides.mongo.ResultRepository;
import me.eumenides.service.AnswerSheetService;
import me.eumenides.service.UserService;
import me.eumenides.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static me.eumenides.interceptor.Constants.SESSION_KEY;

/**
 * Created by Eumenides on 2017/10/19.
 */
@Controller
public class IdentifyController {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private UserService userService;

    @Auth
    @RequestMapping("/load")
    public String answer() {
        return "answer";
    }

    @Auth
    @RequestMapping("/identifyPage")
    public String getPage() {
        return "identify";
    }

    @RequestMapping("detection")
    @ResponseBody
    public R detection(String answerId) {
        Answer rightAnswer = null;
        if (!StringUtils.isEmpty(answerId))
            rightAnswer = answerRepository.findOne(answerId);
        if (rightAnswer != null)
            return R.ok();
        return R.error();
    }

    @RequestMapping("identify")
    @ResponseBody
    public R identify(@RequestPart("files") MultipartFile[] files,
                      @RequestPart(required = false) String answerId,
                      HttpServletRequest request,
                      Integer userId) {
//        List<int[]> answers = new ArrayList<>(files.length);
        List<String> ansStr=new ArrayList<>(files.length);
        List<String> checkout = new ArrayList<>(files.length);
        List<String> testIdArr=new ArrayList<>(files.length);
        Answer rightAnswer = null;
        if (!StringUtils.isEmpty(answerId))
            rightAnswer = answerRepository.findOne(answerId);
        List<String> resultIdArr = new ArrayList<>();
        for (MultipartFile file : files) {
            int[] result = null;
            StringBuilder sb = new StringBuilder();
            try {
                result = identifyOne(file, request);
                for (int i = 0; i < 16; i++) {
                    sb.append(i + 1);
                    sb.append('~');
                    sb.append(i + 5);
                    sb.append(' ');
                    for (int j = 0; j < 5; j++) {
                        int a = result[i * 5 + j];
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
            } catch (Exception e) {
                ansStr.add("");
                continue;
            }
            ansStr.add(sb.toString());
            int[] testId = new int[9];
            for (int i = 80; i < 89; i++) {
                testId[i - 80] = result[i];
            }
            testIdArr.add(buildTestIdString(testId));
            if (rightAnswer != null) {
                List<Integer> check = checkout(result, rightAnswer.getAnswers());
                String resultId = saveResult(result, check, null,userId);
                resultIdArr.add(resultId);
                checkout.add(buildCheckString(check));
            } else
                checkout.add(null);
        }
        return R.ok().put("answers", ansStr).put("testId",testIdArr).put("resultId", resultIdArr).put("checkout", checkout);
    }

    private String buildTestIdString(int[] testId){
        StringBuilder sb=new StringBuilder();
        for (Integer i:testId){
            if (i>=0&&i<10){
                sb.append(i);
            }else
                sb.append('N');
        }
        return sb.toString();
    }
    private List<Integer> checkout(int[] result, int[] rightAnswer) {
        List<Integer> checkout = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            if (result[i] != rightAnswer[i]) {
                checkout.add(i);
            }
        }
        return checkout;
    }

    private String buildCheckString(List<Integer> check){
        StringBuilder sb=new StringBuilder();
        for (Integer c:check){
            sb.append(c+" ");
        }
        return sb.toString();
    }
    private String saveResult(int[] result, List<Integer> checkout, String id,Integer userId) {
        int[] answers = new int[80];
        for (int i = 0; i < 80; i++) {
            answers[i] = result[i];
        }
        int[] testId = new int[9];
        for (int i = 80; i < 89; i++) {
            testId[i - 80] = result[i];
        }
        Result answer = new Result();
        answer.setAnswers(answers);
        answer.setCheckout(checkout);
        answer.setTestId(testId);
        answer.setUserId(userId);
        answer.setUploadDate(new Date(System.currentTimeMillis()));
        if (!StringUtils.isEmpty(id))
            answer.setId(id);
        resultRepository.save(answer);
        return answer.getId();
    }

    @RequestMapping("/ensure")
    @ResponseBody
    public R ensureAnswer(@RequestParam("answers[]") int[] result, @RequestParam("id") String id) {
        try {
            Answer oldAnswer = answerRepository.findOne(id);
            oldAnswer.setAnswers(result);
            answerRepository.save(oldAnswer);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    private String saveAnswer(int[] result, String id, String name, String uploader) {
        int[] answers = new int[80];
        for (int i = 0; i < 80; i++) {
            if (result[i] < 1 || result[i] > 4)
                answers[i] = 0;
            else
                answers[i] = result[i];
        }
        Answer answer = new Answer();
        answer.setName(name);
        answer.setUploader(uploader);
        answer.setAnswers(answers);
        answer.setUploadDate(new Date(System.currentTimeMillis()));
        if (!StringUtils.isEmpty(id))
            answer.setId(id);
        answerRepository.save(answer);
        return answer.getId();
    }

    @RequestMapping("/uploadAnswer")
    @ResponseBody
    public R uploadAnswer(MultipartFile file, String name, HttpServletRequest request) {
        int[] result = null;
        int[] answer = new int[80];
        String id = null;
        try {
            result = identifyOne(file, request);
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute(SESSION_KEY);
            User uploader = userService.findByUserId(userId);
            String uploaderName = null;
            if (uploader != null)
                uploaderName = uploader.getName();
            id = saveAnswer(result, null, name, uploaderName);
            for (int i = 0; i < 80; i++) {
                answer[i] = result[i];
            }
        } catch (IOException e) {
            return R.error();
        }
        return R.ok().put("answers", answer).put("id", id);
    }

    private int[] identifyOne(MultipartFile file, final HttpServletRequest request) throws IOException {
        String path = "D:\\workspace\\answersheet\\src\\main\\resources\\upload\\";
        String filename = String.valueOf(System.currentTimeMillis()) + ".jpg";
        File saveFile = new File(path, filename);
        file.transferTo(saveFile);
        Pointer pointer = AnswerSheetService.INSTANCE.getAnswer(path + filename);
        return pointer.getIntArray(0, 89);
    }
}
