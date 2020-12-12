package com.pancho.young.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // 선언된 모든 final 필드가 포함된 생성자 생성해줌 final이 없는 필드는 포함되지 않음.
public class HelloResponseDto {

    private final String name;
    private final int amount;
}
