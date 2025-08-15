package com.lzy.oj.judge.codesandbox;

import com.lzy.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lzy.oj.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
