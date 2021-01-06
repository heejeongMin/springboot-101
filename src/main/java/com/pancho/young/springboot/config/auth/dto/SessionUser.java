package com.pancho.young.springboot.config.auth.dto;

import com.pancho.young.springboot.domain.user.User;
import lombok.Getter;

/*
SessionUser에는 인증된 사용자 정보만 필요.
User와 SessionUser를 분리하는 이유.
User는 인티티이기 때문에 언제 다른 엔티티와 관계가 형성될 지 모른다.
자식 엔티티를 갖는 경우 직렬화를 하게 되면 자식 까지 포함되니 성능이슈가 발생활 확률이 높다.

그래서 직렬화 기능을 가진 세션 Dto를 추가로 만드는게 운영 유지보수에 도움에 된다.
 */
@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
