package com.pancho.young.springboot.config.auth;

import com.pancho.young.springboot.config.auth.dto.OAuthAttributes;
import com.pancho.young.springboot.config.auth.dto.SessionUser;
import com.pancho.young.springboot.domain.user.User;
import com.pancho.young.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 진행 중인 서비스를 구분하는 코드. 현재는 구글만 사용해서 불필요하지만, 이후 네이버 로그인 연동 시 네이버로그인인지 구글 로그인이지 구분하기 위해 사용됨.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드값. PrimaryKey와 같다고 보면 됨.
        // 구글은 기본적으로 코드를 지원하지만 네이버/카카오는 지원하지 않음. 구글 기본 코드는 sub.
        // 이후 네이버 로그인, 구글 로그인 동시 지원할때 사용됨.
        String userNameAttributeName = userRequest.getClientRegistration()
                                                    .getProviderDetails()
                                                    .getUserInfoEndpoint()
                                                    .getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담는 공통 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user)); //SessionUser는 세션에 사용자 정보를 저장하기 위한 DTO 클래스. user 클래스를 사용하지 않는 이유는 곧 나옴..

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }


    // 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 구현
    // 사용자 이름, 프로필 사진 등이 변경되면 User 엔티티에도 반영됨.
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                                    .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                                    .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
