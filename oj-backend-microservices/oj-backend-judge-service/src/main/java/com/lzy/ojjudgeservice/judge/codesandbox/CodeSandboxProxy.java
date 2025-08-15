package com.lzy.ojjudgeservice.judge.codesandbox;


import com.lzy.ojmodel.model.codesandbox.ExecuteCodeRequest;
import com.lzy.ojmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CodeSandboxProxy implements CodeSandbox{

    private CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("请求" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("响应" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
