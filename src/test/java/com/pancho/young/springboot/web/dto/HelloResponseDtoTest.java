package com.pancho.young.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void lombokTest(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name); //assertj가 좋은 이유 : https://www.youtube.com/watch?v=zLx_fI24UXM&t=408s
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
