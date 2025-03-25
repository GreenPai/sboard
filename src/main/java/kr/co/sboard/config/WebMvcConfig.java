package kr.co.sboard.config;

import kr.co.sboard.interceptor.AppInfoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AppInfo appInfo;

    /*
    * Interceptor
    *  - 클라이언트의 요청과 컨트롤러 사이에서 특정 작업을 수행하기 위한 컴포넌트
    *  - HTTP 요청을 가로채서 요청을 컨트롤러 수행 전과 수행 후에 특정 작업을 수행
    *  - 모든 컨트롤러에서 공통의 작업을 처리하는데 사용
    * */
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new AppInfoInterceptor(appInfo));
    }

}
