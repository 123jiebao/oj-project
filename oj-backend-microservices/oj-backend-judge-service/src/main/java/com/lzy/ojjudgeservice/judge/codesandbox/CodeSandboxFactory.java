package com.lzy.ojjudgeservice.judge.codesandbox;


import com.lzy.ojjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.lzy.ojjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.lzy.ojjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

public class CodeSandboxFactory {

    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            case "example":
            default:
                return new ExampleCodeSandbox();
        }
    }
}
