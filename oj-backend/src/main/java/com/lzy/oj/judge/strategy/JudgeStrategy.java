package com.lzy.oj.judge.strategy;

import com.lzy.oj.judge.codesandbox.model.JudgeInfo;

public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
