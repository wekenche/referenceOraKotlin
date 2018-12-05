package ora.leadlife.co.jp.config

import ora.leadlife.co.jp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userService: UserService

    @Throws(Exception::class)
    override protected fun configure(http: HttpSecurity) {
        //
        // CSRF対策を無効化
        //
        http.csrf().disable();

        //
        // ログイン認証を行うパスを設定
        //
        http.authorizeRequests()
                // ログイン無しでアクセス許可するパス
                .antMatchers("/termsOfUse",
                        "/login/input",
                        "/login",
                        "/signup/**",
                        "/fingerprintRegistration/**",
                        "/passwordReminder/**",
                        "/others/fingerprint/login",
                        "/password/**",
                        "/smartPhone/**",
                        "/payment/save",
                        "/payment",
                        "/accountForApple/unused/**",
                        "/css/**",
                        "/js/**",
                        "/images/**").permitAll()
                .antMatchers("/admin/**").hasAuthority(Role.ADMIN.name)
                // その他はログインが必要
                .anyRequest().authenticated();

        //
        // フォーム認証を有効化
        //
        http.formLogin()
                .loginPage("/login/input")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login/input")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll().and()
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/input")
                .permitAll()
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth
                .userDetailsService<UserDetailsService>(userService)
                .passwordEncoder(BCryptPasswordEncoder())
    }
}