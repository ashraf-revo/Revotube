package org.revo;

//import com.github.mthizo247.cloud.netflix.zuul.web.socket.EnableZuulWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
@Controller
//@EnableZuulWebSocket
//@EnableWebSocketMessageBroker

public class ZuulApplication extends WebSecurityConfigurerAdapter {
    private final String p0 = "{path:[^.]*}";
    private final String m = "/" + p0;
    private final String p1 = p0 + m;
    private final String p2 = p1 + m;
    private final String p3 = p2 + m;

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().logout()
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(200))
                .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/uaa/**");
    }


    @GetMapping(value = {p0, p1, p2, p3})
    public ModelAndView index() {
        return new ModelAndView("forward:/index.html");
    }
}
