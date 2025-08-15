package com.lzy.ojjudgeservice.judge;


import com.lzy.ojmodel.model.entity.QuestionSubmit;

public interface JudgeService {


    QuestionSubmit doJudge(long QuestionSubmitId);
}
