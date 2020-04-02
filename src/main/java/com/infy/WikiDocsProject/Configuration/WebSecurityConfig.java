package com.infy.WikiDocsProject.Configuration;

import com.infy.WikiDocsProject.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/AuthAPI/login", "/AuthAPI/register").permitAll()
                .antMatchers(
                        "/ArticleAPI/getAllArticlesByEmail/**",
                        "/ArticleAPI/getAllApprovedArticlesByEmail/**",
                        "/ArticleAPI/getAllBetaArticlesByEmail/**",
                        "/ArticleAPI/getAllInitialArticlesByEmail/**",
                        "/ArticleAPI/getAllRejectedArticlesByEmail/**",
                        "/ArticleAPI/getAllDiscardedArticlesByEmail/**",
                        "/ArticleAPI/submitArticleForApproval/",
                        "/ArticleAPI/createNewArticle/**",
                        "/ArticleAPI/saveArticle/**",
                        "/ArticleAPI/getAllInvitedArticlesByEmail/**",
                        "/ArticleAPI/inviteUserToCollaborateByEmail/")
                        .hasAuthority("USER")
                .antMatchers(
                        "/ArticleAPI/getAllBetaArticles/**",
                        "/ArticleAPI/approveArticle/**",
                        "/ArticleAPI/rejectArticle/**")
                        .hasAuthority("ADMIN")
                .antMatchers(
                        "/ArticleAPI/getAllApprovedArticles/**",
                        "/ArticleAPI/getEtherPadUrl/**",
                        "/UserAPI/getNameByEmail/**")
                        .hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated().and().csrf()
                .disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
                .and()
                .apply(new JwtConfig(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**","/static/**","/css/**","/js/**","/images/**");
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint(){
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    public UserDetailsService mongoUserDetails() {
        return new CustomUserDetailsService();
    }
}
