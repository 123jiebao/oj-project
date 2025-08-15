package com.lzy.ojjudgeservice.judge.codesandbox;


import com.lzy.ojmodel.model.codesandbox.ExecuteCodeRequest;
import com.lzy.ojmodel.model.codesandbox.ExecuteCodeResponse;

public interface CodeSandbox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
