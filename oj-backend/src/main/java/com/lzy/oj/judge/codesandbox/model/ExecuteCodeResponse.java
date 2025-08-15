package com.lzy.oj.judge.codesandbox.model;

import com.lzy.oj.judge.codesandbox.model.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    private List<String> outputList;

    private JudgeInfo judgeInfo;

    private String message;

    private Integer status;
}
