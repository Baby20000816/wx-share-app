package com.soft1851.contentcenter.feignclient;

//import com.mqxu.contentcenter.configuration.GlobalFeignConfiguration;

import com.soft1851.contentcenter.configuration.UserCenterFeignConfiguration;
import com.soft1851.contentcenter.domain.dto.ResponseDTO;
import com.soft1851.contentcenter.domain.dto.UserAddBonusDTO;
import com.soft1851.contentcenter.domain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author mqxu
 * 用户中心对应的Feign客户端声明接口
 */
@FeignClient(name = "user-center-wjh", configuration = UserCenterFeignConfiguration.class)
//@FeignClient(name = "user-center")
public interface UserCenterFeignClient {
    /**
     * http://user-center/users/{id}
     *
     * @param id
     * @return UserDTO
     */
    @GetMapping("/users/{id}")
    ResponseDTO findUserById(@PathVariable Integer id);


    /**
     * 用户增加积分
     * @param userAddBonusDTO
     * @return
     */
    @PutMapping("/users/add-bonus")
    UserDTO addBonus(@RequestBody UserAddBonusDTO userAddBonusDTO);
}