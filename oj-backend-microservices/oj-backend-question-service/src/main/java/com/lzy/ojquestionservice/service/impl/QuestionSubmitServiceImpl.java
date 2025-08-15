package com.lzy.ojquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.ojcommon.common.ErrorCode;
import com.lzy.ojcommon.constant.CommonConstant;
import com.lzy.ojcommon.exception.BusinessException;
import com.lzy.ojcommon.utils.SqlUtils;
import com.lzy.ojmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.lzy.ojmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.lzy.ojmodel.model.entity.Question;
import com.lzy.ojmodel.model.entity.QuestionSubmit;
import com.lzy.ojmodel.model.entity.User;
import com.lzy.ojmodel.model.enums.QuestionSubmitLanguageEnum;
import com.lzy.ojmodel.model.enums.QuestionSubmitStatusEnum;
import com.lzy.ojmodel.model.vo.QuestionSubmitVO;
import com.lzy.ojquestionservice.mapper.QuestionSubmitMapper;
import com.lzy.ojquestionservice.rabbitmq.Producer;
import com.lzy.ojquestionservice.service.QuestionService;
import com.lzy.ojquestionservice.service.QuestionSubmitService;
import com.lzy.ojserviceclient.service.JudgeFeignClient;
import com.lzy.ojserviceclient.service.UserFeignClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author Mrlzy
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2025-06-10 02:56:21
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private Producer producer;

    /**
     * 提交题目
     *
     * @param questionId
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        String laguage = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(laguage);
        if(languageEnum == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的编程语言");
        }
        long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(loginUser.getId());
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean result = this.save(questionSubmit);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, " 提交失败");
        }
//        CompletableFuture.runAsync( () -> {
//            judgeFeignClient.doJudge(questionSubmit.getId());
//        });
        producer.sendMessage("code_exchange","code", String.valueOf(questionSubmit.getId()));
        return questionSubmit.getId();
    }

    /**
     * 获取查询包装类
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }

        String language = questionSubmitQueryRequest.getLanguage();
        String questionTitle = questionSubmitQueryRequest.getQuestionTittle();
        List<Long> questionIdList;
        if (questionTitle == null || "".equals(questionTitle) || (questionTitle = questionTitle.trim()).isEmpty()) {
            questionIdList = Collections.emptyList();
        } else {
            questionIdList = questionService.list(
                            Wrappers.<Question>lambdaQuery()
                                    .like(Question::getTitle, questionTitle)
                    )
                    .stream()
                    .map(Question::getId)
                    .collect(Collectors.toList());
        }
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        if(!CollectionUtils.isNotEmpty(questionIdList) && questionTitle != null && !"".equals(questionTitle)){
            queryWrapper.eq("id", -1);
            return queryWrapper;
        }

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language) , "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.in(CollectionUtils.isNotEmpty(questionIdList), "questionId", questionIdList);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }


    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        long userId = loginUser.getId();
        if(userId != questionSubmit.getUserId() && !userFeignClient.isAdmin(loginUser) ){
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        Set<Long> questionIdSet = questionSubmitList.stream()
                .map(QuestionSubmit::getQuestionId)
                .collect(Collectors.toSet());

        // 批量查询所有题目，构建 id -> title 映射
        Map<Long, String> questionTitleMap = new HashMap<>();
        if (CollUtil.isNotEmpty(questionIdSet)) {
            List<Question> questionList = questionService.listByIds(questionIdSet);
            questionTitleMap = questionList.stream()
                    .collect(Collectors.toMap(Question::getId, Question::getTitle));
        }
        Map<Long, String> finalQuestionTitleMap = questionTitleMap;
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            QuestionSubmitVO vo = getQuestionSubmitVO(questionSubmit, loginUser);
            vo.setQuestionTittle(finalQuestionTitleMap.getOrDefault(questionSubmit.getQuestionId(), "题目已删除"));
            return vo;
        }).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }


}




