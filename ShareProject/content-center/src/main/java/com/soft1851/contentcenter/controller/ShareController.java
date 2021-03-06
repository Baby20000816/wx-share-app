package com.soft1851.contentcenter.controller;

import com.soft1851.contentcenter.auth.CheckLogin;
import com.soft1851.contentcenter.domain.dto.ExchangeDTO;
import com.soft1851.contentcenter.domain.dto.ShareDTO;
import com.soft1851.contentcenter.domain.dto.ShareRequestDTO;
import com.soft1851.contentcenter.domain.entity.Share;
import com.soft1851.contentcenter.service.ShareService;
import com.soft1851.contentcenter.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @description: UserController
 * @author: mqxu
 * @create: 2020-10-12
 **/
@RestController
@RequestMapping(value = "/shares")
@Api(tags = "分享接口", value = "提供分享相关的Rest API")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareController {
    private final ShareService shareService;
    private final JwtOperator jwtOperator;

    @GetMapping(value = "/{id}")
    @CheckLogin
    @ApiOperation(value = "查询指定id的分享详情", notes = "查询指定id的分享详情")
    public ShareDTO findById(@PathVariable Integer id) {
        return this.shareService.findById(id);
    }

    @GetMapping("/query")
    @ApiOperation(value = "分享列表", notes = "分享列表")
    public List<Share> query(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestHeader(value = "X-Token", required = false) String token) {
        if (pageSize > 100) {
            pageSize = 100;
        }
        System.out.println(token);
        Integer userId = null;

        if (!"no-token".equals(token)) {
            System.out.println(token);
            Claims claims = this.jwtOperator.getClaimsFromToken(token);
            log.info(claims.toString());
            userId = (Integer) claims.get("id");
        } else {
            log.info("没有token");
        }
        return this.shareService.query(title, pageNo, pageSize, userId).getList();
    }


    @PostMapping("/contribute")
    @CheckLogin
    @ApiOperation(value = "投稿", notes = "投稿")
    public int contributeShare(@RequestBody ShareRequestDTO shareRequestDTO) {
        System.out.println(shareRequestDTO);
        return shareService.contribute(shareRequestDTO);
    }

    @PostMapping("/exchange")
    @CheckLogin
    public Share exchange(@RequestBody ExchangeDTO exchangeDTO) {
        System.out.println(exchangeDTO + ">>>>>>>>>>>>");
        return this.shareService.exchange(exchangeDTO);
    }
    @GetMapping(value = "/my/contributions")
//    @CheckLogin
    @ApiOperation(value = "我的投稿", notes = "我的投稿")
    public List<Share> findMyContribution(
            @RequestParam Integer userId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
//        System.out.println(this.shareService.findMyContribute(pageNo, pageSize, userId).getList());
        return this.shareService.findMyContribute(pageNo, pageSize, userId).getList();
    }

    @GetMapping(value = "/my")
    @ApiOperation(value = "我的兑换", notes = "我的兑换")
    public List<Share> findMyExchange(
            @RequestParam Integer userId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return this.shareService.findMyExchange(pageNo, pageSize, userId).getList();
    }
}
