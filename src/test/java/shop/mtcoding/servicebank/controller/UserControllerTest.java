// package shop.mtcoding.servicebank.controller;

// import static org.mockito.ArgumentMatchers.any;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// import java.time.LocalDateTime;

// import org.hibernate.annotations.Immutable;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.EnableAspectJAutoProxy;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import shop.mtcoding.servicebank.core.advice.MyValidAdvice;
// import shop.mtcoding.servicebank.dto.user.UserRequest;
// import shop.mtcoding.servicebank.dto.user.UserResponse;
// import shop.mtcoding.servicebank.model.user.User;
// import shop.mtcoding.servicebank.service.UserService;

// // 스프링부트 테스트는 통합테스트입니다.
// // 필터 ds(핸들러,inter,뷰리졸버,메세지컨버터) 컨트롤러만 메모리에 띄워서 테스트함
// @WebMvcTest(UserController.class) // 내부에 @AutoConfigureWebMvc 어노테이션이 있습니다.
// @EnableAspectJAutoProxy // AOP 허용하는 옵션
// @Import(MyValidAdvice.class) // Aspect 클래스
// public class UserContrllerTest {
//     @Autowired
//     private MockMvc mvc;
//     @Autowired
//     private ObjectMapper om;
//     @MockBean // Mock 과의 차이는 ?
//     private UserService userService;

//     @Test
//     public void join_test() throws Exception {
//         // given
//         UserRequest.JoinInDTO joinInDTO = new UserRequest.JoinInDTO();
//         joinInDTO.setUsername("ssar");
//         joinInDTO.setPassword("1234");
//         joinInDTO.setEmail("ssar@nate.com");
//         joinInDTO.setFullName("쌀");
//         String requestBody = om.writeValueAsString(joinInDTO);

//         // stub (가정)
//         // when 일때 then 이 나올것(예상)
//         User user = User.builder().username("ssar")
//                 .id(1L)
//                 .password("1234")
//                 .email("ssar@nate.com")
//                 .fullName("쌀")
//                 .createdAt(LocalDateTime.now())
//                 .build();
//         UserResponse.JoinOutDTO joinOutDTO = new UserResponse.JoinOutDTO(user);
//         Mockito.when(userService.회원가입(any())).thenReturn(joinOutDTO);

//         // MockM
//         // when
//         // 컨트롤러만 메모리에 띄워서 테스트하는데 컨트롤러가 서비스를 의존해서 터진다.
//         ResultActions result = mvc.perform(post("/join").content(requestBody)
//                 .contentType(MediaType.APPLICATION_JSON_VALUE));
//         String responseBody = result.andReturn().getResponse().getContentAsString();
//         System.out.println(responseBody);
//         // then
//         result.andExpect(jsonPath("$.status").value(200));
//         result.andExpect(jsonPath("$.data.id").value(1));
//         result.andExpect(jsonPath("$.data.username").value("ssar"));
//         result.andExpect(jsonPath("$.data.fullName").value("쌀"));
//     }

// }


package shop.mtcoding.servicebank.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.servicebank.core.advice.MyValidAdvice;
import shop.mtcoding.servicebank.core.session.SessionUser;
import shop.mtcoding.servicebank.dto.user.UserRequest;
import shop.mtcoding.servicebank.dto.user.UserResponse;
import shop.mtcoding.servicebank.model.user.User;
import shop.mtcoding.servicebank.service.UserService;

@EnableAspectJAutoProxy // AOP 작동 활성화
@Import(MyValidAdvice.class) // Aspect 클래스 로드
@WebMvcTest(UserController.class) // f -> ds(exceptionHandler, interceptor, viewResolver, messageConverter) -> c
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;


    private ObjectMapper om = new ObjectMapper();

    @MockBean
    private UserService userService;

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinInDTO joinInDTO = new UserRequest.JoinInDTO();
        joinInDTO.setUsername("ssar");
        joinInDTO.setPassword("1234");
        joinInDTO.setEmail("ssar@nate.com");
        joinInDTO.setFullName("쌀만고");
        String requestBody = om.writeValueAsString(joinInDTO);
        System.out.println(requestBody);

        // stub (가정)
        User user = User.builder()
                .id(1L)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .fullName("쌀만고")
                .createdAt(LocalDateTime.now())
                .build();
        UserResponse.JoinOutDTO joinOutDTO = new UserResponse.JoinOutDTO(user);
        Mockito.when(userService.회원가입(any())).thenReturn(joinOutDTO);

        // when
        ResultActions resultActions = mvc.perform(post("/join").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andExpect(jsonPath("$.data.id").value(1));
        resultActions.andExpect(jsonPath("$.data.username").value("ssar"));
        resultActions.andExpect(jsonPath("$.data.fullName").value("쌀만고"));
    }

    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginInDTO loginInDTO = new UserRequest.LoginInDTO();
        loginInDTO.setUsername("ssar");
        loginInDTO.setPassword("1234");
        String requestBody = om.writeValueAsString(loginInDTO);
        System.out.println(requestBody);

        // stub (가정)
        User user = User.builder()
                .id(1L)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .fullName("쌀만고")
                .createdAt(LocalDateTime.now())
                .build();
        SessionUser sessionUser = new SessionUser(user);
        Mockito.when(userService.로그인(any())).thenReturn(sessionUser);

        // when
        ResultActions resultActions = mvc.perform(post("/login").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andExpect(jsonPath("$.data.id").value(1));
        resultActions.andExpect(jsonPath("$.data.username").value("ssar"));
    }
}