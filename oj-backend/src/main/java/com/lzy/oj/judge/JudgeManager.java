package com.lzy.oj.judge;

import com.lzy.oj.judge.strategy.DefaultJudgeStrategy;
import com.lzy.oj.judge.strategy.JavaLanguageJudgeStrategy;
import com.lzy.oj.judge.strategy.JudgeContext;
import com.lzy.oj.judge.strategy.JudgeStrategy;
import com.lzy.oj.judge.codesandbox.model.JudgeInfo;
import com.lzy.oj.model.entity.QuestionSubmit;
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
