package com.lzy.oj.judge.codesandbox.impl;

import com.lzy.oj.judge.codesandbox.CodeSandbox;
import com.lzy.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lzy.oj.judge.codesandbox.model.ExecuteCodeResponse;

public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("ThirdPartyCodeSandbox");
        return null;
    }
}
