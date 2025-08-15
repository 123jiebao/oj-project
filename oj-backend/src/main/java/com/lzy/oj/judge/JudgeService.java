package com.lzy.oj.judge;

import com.lzy.oj.model.entity.QuestionSubmit;

public interface JudgeService {


    QuestionSubmit doJudge(long QuestionSubmitId);
}
