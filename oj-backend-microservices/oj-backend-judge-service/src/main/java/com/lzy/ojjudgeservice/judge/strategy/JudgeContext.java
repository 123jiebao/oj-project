package com.lzy.ojjudgeservice.judge.strategy;

import com.lzy.ojmodel.model.codesandbox.JudgeInfo;
import com.lzy.ojmodel.model.dto.question.JudgeCase;
import com.lzy.ojmodel.model.entity.Question;
import com.lzy.ojmodel.model.entity.QuestionSubmit;
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
