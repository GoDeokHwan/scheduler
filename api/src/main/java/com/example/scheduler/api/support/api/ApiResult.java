package com.example.scheduler.api.support.api;

import lombok.Getter;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ApiResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResult<T> of(ApiStatusResponsible apiStatus, T data) {
        return new ApiResult<>(apiStatus, data);
    }

    public static ApiResult of(ApiStatusResponsible apiStatus, List<Pair> pairs) {

        Map<String, String> map = pairs.stream()
                .collect(Collectors.toMap(x -> x.getFirst().toString(), x -> x.getSecond().toString()));

        return new ApiResult<>(apiStatus, map);
    }

    public static <T> ApiResult<T> of(ApiStatusResponsible apiStatus) {
        return new ApiResult<>(apiStatus, null);
    }

    private ApiResult(ApiStatusResponsible apiStatus, T data) {
        this.code = apiStatus.getCode();
        this.data = data;
        this.message = apiStatus.getMessage();
    }
}
