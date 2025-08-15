package com.lzy.oj.judge.strategy;

import com.lzy.oj.model.dto.question.JudgeCase;
import com.lzy.oj.judge.codesandbox.model.JudgeInfo;
import com.lzy.oj.model.entity.Question;
import com.lzy.oj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> intputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
