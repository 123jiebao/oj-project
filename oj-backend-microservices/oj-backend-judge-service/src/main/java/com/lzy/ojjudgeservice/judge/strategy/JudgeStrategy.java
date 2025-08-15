package com.lzy.ojjudgeservice.judge.strategy;


import com.lzy.ojmodel.model.codesandbox.JudgeInfo;

public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
