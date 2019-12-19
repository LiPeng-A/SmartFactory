package com.smart.config;

import com.smart.service.impl.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(new MyPasswordEncode());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/js/**","/img/**","/fonts/**","/vendor/**")
                .permitAll() //允许访问的静态资源
                .anyRequest().authenticated() //任何不允许的访问需要验证才可以访问
                .and()
                .csrf().disable() //关闭 csrf
                .formLogin()
                .loginPage("/login").permitAll() //设置我们自己的登录页面 并允许访问
                .loginProcessingUrl("/login/form").defaultSuccessUrl("/",true)
                .failureForwardUrl("/loginError")
                .failureUrl("/loginError").permitAll()  //失败跳转的页面
                .and()
                .logout().permitAll(); //退出登录
        http.rememberMe()                                   // 记住我配置
                .tokenRepository(persistentTokenRepository())  // 配置数据库源
                .tokenValiditySeconds(3600)
                .userDetailsService(customUserService);
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

    }

    //记住我功能
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        // 将 DataSource 设置到 PersistentTokenRepository
        persistentTokenRepository.setDataSource(dataSource);
        // 第一次启动的时候自动建表（可以不用这句话，自己手动建表，源码中有语句的）
         //persistentTokenRepository.setCreateTableOnStartup(true);
        return persistentTokenRepository;
    }

}
