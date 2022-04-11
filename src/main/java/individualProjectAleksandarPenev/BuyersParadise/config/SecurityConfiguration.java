package individualProjectAleksandarPenev.BuyersParadise.config;

import individualProjectAleksandarPenev.BuyersParadise.filter.JWTAuthenticationFilter;
import individualProjectAleksandarPenev.BuyersParadise.filter.JWTAuthorizationFilter;
import individualProjectAleksandarPenev.BuyersParadise.service.AuthenticationUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;


    @Override protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and().csrf().disable();
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
                //ROLE BASED AUTHENTICATION START
                //.antMatchers("/account/**/**").permitAll()
                .antMatchers("/account/register").permitAll()
                .antMatchers("/account/checkRole").hasAnyAuthority("USER", "SELLER")
                .antMatchers("/account/upgrade").hasAnyAuthority("USER")
                .antMatchers("/account/personalInfo").hasAnyAuthority("SELLER")
                .antMatchers("/account/getSeller/**").permitAll()
                .antMatchers("/offer").permitAll()
                .antMatchers("/offer/{id}").permitAll()
                .antMatchers("/offer/create").hasAnyAuthority("SELLER")
                .antMatchers("/offer/delete").hasAnyAuthority("SELLER")
                .antMatchers("/file/offer/**").permitAll()
                .antMatchers("/file/upload/photo").hasAnyAuthority("SELLER")
                .antMatchers("/api/broadcast").permitAll()
                .antMatchers("/topic/**").permitAll()
                .antMatchers("/hello").permitAll()

//                .antMatchers("/account").permitAll() // HAS TO CHANGE ------------------
//            .antMatchers("/api/library/author/**").hasAnyAuthority("ADMIN")
//            .antMatchers("/api/library/member/**").hasAnyAuthority("ADMIN")
                //ROLE BASED AUTHENTICATION END
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }


}
