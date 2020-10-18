package com.mqxu.usercenter.service.impl;

import com.mqxu.usercenter.dao.BonusEventLogMapper;
import com.mqxu.usercenter.dao.UserMapper;
import com.mqxu.usercenter.domain.dto.ResponseDTO;
import com.mqxu.usercenter.domain.dto.UserSignInDTO;
import com.mqxu.usercenter.domain.entity.BonusEventLog;
import com.mqxu.usercenter.domain.entity.User;
import com.mqxu.usercenter.service.BonusEventLogService;
import com.mqxu.usercenter.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BonusEventLogServiceImpl implements BonusEventLogService {
    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO signIn(UserSignInDTO signInDTO) {
        User user = this.userMapper.selectByPrimaryKey(signInDTO.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("该用户不存在！");
        }
        Example example = new Example(BonusEventLog.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id DESC");
        criteria.andEqualTo("userId", signInDTO.getUserId());
        criteria.andEqualTo("event", "SIGN_IN");
        List<BonusEventLog> bonusEventLogs = this.bonusEventLogMapper.selectByExample(example);
        if (bonusEventLogs.size() == 0) {
            this.bonusEventLogMapper.insert(BonusEventLog.builder()
                    .userId(signInDTO.getUserId())
                    .value(20)
                    .createTime(new Date())
                    .description("签到")
                    .event("SIGN_IN").build());
            user.setBonus(user.getBonus() + 20);
            this.userMapper.updateByPrimaryKeySelective(user);
            return new ResponseDTO(true, "200", "签到成功", user, 1L);
        } else {
            BonusEventLog bonusEventLog = bonusEventLogs.get(0);
            Date date = bonusEventLog.getCreateTime();
            try {
                if (DateUtil.checkAllSign(date) == 0) {
                    this.bonusEventLogMapper.insert(BonusEventLog.builder()
                            .userId(signInDTO.getUserId())
                            .value(20)
                            .createTime(new Date())
                            .description("签到")
                            .event("SIGN_IN").build());
                    user.setBonus(user.getBonus() + 20);
                    this.userMapper.updateByPrimaryKeySelective(user);
                    return new ResponseDTO(true, "200", "签到成功", user, 1L);
                } else if (DateUtil.checkAllSign(date) == 1) {
                    return new ResponseDTO(false, "201", "签到失败", user.getWxNickname() + "今天签到过了", 1L);
                } else if (DateUtil.checkAllSign(date) == 2) {
                    return new ResponseDTO(false, "202", "签到失败", user.getWxNickname() + "该用户今天的数据混乱了", 1L);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseDTO(true, "200", "签到成功", user, 1L);
    }

    @Override
    public ResponseDTO checkIsSign(UserSignInDTO signInDTO) {
        User user = this.userMapper.selectByPrimaryKey(signInDTO.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("该用户不存在！");
        }
        Example example = new Example(BonusEventLog.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id DESC");
        criteria.andEqualTo("userId", signInDTO.getUserId());
        criteria.andEqualTo("event", "SIGN_IN");
        List<BonusEventLog> bonusEventLogs = this.bonusEventLogMapper.selectByExample(example);
        if (bonusEventLogs.size() == 0) {
            return new ResponseDTO(true, "200", "该用户还未签到", "可以签到", 1L);
        } else {
            BonusEventLog bonusEventLog = bonusEventLogs.get(0);
            Date date = bonusEventLog.getCreateTime();
            try {
                if (DateUtil.checkAllSign(date) == 0) {
                    return new ResponseDTO(true, "200", "该用户还未签到", "可以签到", 1L);
                } else if (DateUtil.checkAllSign(date) == 1) {
                    return new ResponseDTO(false, "201", "已经签到了", "不可以签到", 1L);
                } else if (DateUtil.checkAllSign(date) == 2) {
                    return new ResponseDTO(false, "202", "数据出错了", "不可以签到", 1L);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseDTO(true, "200", "该用户还未签到", "可以签到", 1L);
    }
}
