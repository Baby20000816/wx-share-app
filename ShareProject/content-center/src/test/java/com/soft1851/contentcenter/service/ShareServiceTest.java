package com.soft1851.contentcenter.service;

import com.github.pagehelper.PageInfo;
import com.soft1851.contentcenter.domain.entity.Share;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShareServiceTest {
    @Autowired
    private ShareService shareService;

    @Test
    void findById() {
    }

    @Test
    void query() {
        PageInfo<Share> query = shareService.query(null, 1, 10, 1);
        //List<Share> list = query.getList();
        //list.forEach(item-> System.out.println(item.getTitle()+","+item.getDownloadUrl()));
    }

    @Test
    void insert(){
        //ShareRequestDTO shareRequestDTO = ShareRequestDTO.builder()
        //        .author("陶然然")
        //        .downloadUrl("www.baidu.com")
        //        .price(20)
        //        .summary("测试资源")
        //        .title("测试资源")
        //        .isOriginal(Boolean.TRUE)
        //        .build();
        //shareService.contribute(shareRequestDTO);
    }

    @Test
    void auditById(){
        //Share share = shareService.auditById(11, ShareAuditDTO.builder().auditStatusEnum(AuditStatusEnum.PASS).reason("great").build());
        //System.out.println(share);
    }
}