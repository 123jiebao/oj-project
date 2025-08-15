package com.lzy.ojjudgeservice.judge;

import com.lzy.ojjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.lzy.ojjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.lzy.ojjudgeservice.judge.strategy.JudgeContext;
import com.lzy.ojjudgeservice.judge.strategy.JudgeStrategy;
import com.lzy.ojmodel.model.codesandbox.JudgeInfo;
import com.lzy.ojmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {

    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
