package com.lzy.oj.judge;

import cn.hutool.json.JSONUtil;
import com.lzy.oj.common.ErrorCode;
import com.lzy.oj.exception.BusinessException;
import com.lzy.oj.judge.codesandbox.CodeSandbox;
import com.lzy.oj.judge.codesandbox.CodeSandboxFactory;
import com.lzy.oj.judge.codesandbox.CodeSandboxProxy;
import com.lzy.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lzy.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.lzy.oj.judge.strategy.JudgeContext;
import com.lzy.oj.model.dto.question.JudgeCase;
import com.lzy.oj.judge.codesandbox.model.JudgeInfo;
import com.lzy.oj.model.entity.Question;
import com.lzy.oj.model.entity.QuestionSubmit;
import com.lzy.oj.model.enums.QuestionSubmitStatusEnum;
import com.lzy.oj.service.QuestionService;
import com.lzy.oj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long QuestionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(QuestionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        Integer status = questionSubmit.getStatus();
        if(!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交正在判题中");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(QuestionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交更新失败");
        }
        CodeSandbox codeSandbox = new CodeSandboxProxy(CodeSandboxFactory.newInstance(type));
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> intputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(intputList)
                .build();

        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setIntputList(intputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(QuestionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交更新失败");
        }

        QuestionSubmit result = questionSubmitService.getById(QuestionSubmitId);

        return result;
    }
}
