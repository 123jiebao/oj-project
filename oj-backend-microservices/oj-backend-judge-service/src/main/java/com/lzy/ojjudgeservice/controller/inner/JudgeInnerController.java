package com.lzy.ojjudgeservice.controller.inner;

import com.lzy.ojjudgeservice.judge.JudgeService;
import com.lzy.ojmodel.model.entity.QuestionSubmit;
import com.lzy.ojserviceclient.service.JudgeFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
@Slf4j
public class JudgeInnerController {

    @Resource
    private JudgeService judgeService;

    @GetMapping("/doJudge")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
