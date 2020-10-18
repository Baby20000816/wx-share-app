package com.soft1851.contentcenter.service;

import com.github.pagehelper.PageInfo;
import com.soft1851.contentcenter.domain.dto.ExchangeDTO;
import com.soft1851.contentcenter.domain.dto.ShareAuditDTO;
import com.soft1851.contentcenter.domain.dto.ShareDTO;
import com.soft1851.contentcenter.domain.dto.ShareRequestDTO;
import com.soft1851.contentcenter.domain.entity.Share;


/**
 * @author mqxu
 */
public interface ShareService {
    /**
     * 分享详情
     *
     * @param id
     * @return ShareDTO
     */
    ShareDTO findById(Integer id);

    /**
     * 根据标题模糊查询某个用户的分享列表数据，title为空则为所有数据，查询结果分页
     *
     * @param title
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return PageInfo<Share>
     */
    PageInfo<Share> query(String title, Integer pageNo, Integer pageSize, Integer userId);


    /**
     * 投稿
     *
     * @param shareRequestDTO
     * @return Share
     */
    int contribute(ShareRequestDTO shareRequestDTO);

    /**
     * 审核投稿
     *
     * @param id
     * @param shareAuditDTO
     * @return Share
     */
    Share auditById(Integer id, ShareAuditDTO shareAuditDTO);

    /**
     * 积分兑换资源
     *
     * @param exchangeDTO
     * @return Share
     */
    Share exchange(ExchangeDTO exchangeDTO);

    /**
     * 我的投稿
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo<Share> findMyContribute(Integer pageNo, Integer pageSize, Integer userId);

    /**
     * 我的兑换
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo<Share> findMyExchange(Integer pageNo, Integer pageSize, Integer userId);
}
