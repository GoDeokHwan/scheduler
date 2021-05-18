package com.example.scheduler.api.domain.user;

import com.example.scheduler.api.domain.user.model.UserRequest;
import com.example.scheduler.api.support.api.ApiResult;
import com.example.scheduler.api.support.api.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 회원가입
     * @param userRequest
     * @return
     */
    @PostMapping("/v1/users")
    public ApiResult save(@Valid @RequestBody UserRequest userRequest) {
        userService.save(userRequest);
        return ApiResult.of(ApiStatus.SUCCESS);
    }

    @GetMapping("/v1/user/id-check")
    public ApiResult validationUserId(@RequestParam(name = "loginId") String loginId) {
        return ApiResult.of(ApiStatus.SUCCESS, userService.validationUserId(loginId));
    }

}
