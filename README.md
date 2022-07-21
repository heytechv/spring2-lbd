## Useful links
- [Spring Security Full Course](https://www.youtube.com/watch?v=her_7pa0vrg)
- [Spring Security + JWT Token](https://www.youtube.com/watch?v=and2DR_N6tE)
- [Spring Security credentials from JSON request](https://ckinan.com/blog/spring-security-credentials-from-json-request/)

__________

## Json Object Authentication
### Możliwość logowania się (uwierzytelniania) przy pomocy JsonBody w request

### UWAGA!
#### Domyślnie `UsernamePasswordAuthenticationFilter` działa tylko dla `GET "/login"`<br/> jak mamy inne to zmieniamy dodajac w `SecurityConfig.java` w filtrze `authenticationJsonFilter()` (niżej jest pokazane)

### UWAGA!
#### W Postmanie musimy włączyć pliki cookie (odznaczamy opcję `Settings->Disable cookie jar`), następnie możemy raz wywołać `GET /api/login` z JSON body:
```json
{
    "username": "user",
    "password": "user"
}
```
#### wtedy pozostałe endpointy powinny działać dobrze

### 1. Maven
- [JJWT Library](https://github.com/jwtk/jjwt)
```xml
<dependencies>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if Gson is preferred -->
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### 2. Tworzymy klasę, na którą będzie mapowany JSON
*LoginCredentials.java*
```java
public class LoginCredentials {
    private String username;
    private String password;

    public LoginCredentials() { }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

### 3. Dodajemy controller do logowania
#### Za uwierzytelnianie bedzie odpowiadal filtr, ta metoda jest zeby w swaggerze byla widoczna

*LoginController.java*
```java
@RestController
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    /** Za uwierzytelnianie bedzie odpowiadal filtr, ta metoda jest zeby w swaggerze byla widoczna
     *      UWAGA! Domyslnie UsernamePasswordAuthenticationFilter dziala tylko dla GET "/login"
     *      jak mamy inne to zmieniamy dodajac w SecurityConfig.java w filtrze authenticationJsonFilter()
     *          jsonObjectAuthenticationFilter.setFilterProcessesUrl("/api/login"); */
    @PostMapping("/api/login")
    public void login(@RequestBody LoginCredentials credentials) { }
}
```

### 4. Tworzymy filtr dla JSON
#### Filtr pozwoli na uwierzytelnianie przez JSON zamiast przez formularz http. Mapujemy w nim JSON na nasz obiekt i sprawdzamy dane za bazy przy pomocy `getAuthenticationManager()` (dane/baza ustawione w SecurityConfig.java)
*JsonObjectAuthenticationFilter.java*
```java
public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // Mapujemy json JWT na nasza klase LoginCredentials
            LoginCredentials authRequest = new ObjectMapper().readValue(request.getInputStream(), LoginCredentials.class);
            // Tworzymy token
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            );
            // Przekazujemy token dalej do Details oraz AuthenticationManager
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
```

### 5. Tworzymy handlery dla success i failure
*AuthenticationSuccessHandler.java*
```java
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    @Override public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.warn("OK auth");
        response.getWriter().write("Signed in successfully");

        clearAuthenticationAttributes(request);
    }

}
```

*AuthenticationFailureHandler.java*
```java
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
    }
}
```

### 6. Podpinanie wszystkiego w SecurityConfig.java
*SecurityConfig.java*
```java
@Configuration
@EnableWebSecurity(debug = false)                                      // enable WebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)      // enable @PreAuthorize("hasAnyRole(...)")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /** Wstrzykujemy handlery */
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;


    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(encoder().encode("user"))
                .authorities(UserPermission.ADMIN.name());
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .addFilter(authenticationJsonFilter())  // dodajemy nasz filtr

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .anyRequest().authenticated()
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }


    @Bean public JsonObjectAuthenticationFilter authenticationJsonFilter() throws Exception {
        JsonObjectAuthenticationFilter jsonObjectAuthenticationFilter = new JsonObjectAuthenticationFilter();
        // ustawiamy successHandler oraz failureHandler
        jsonObjectAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        jsonObjectAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        // domyslny authenticationManager
        jsonObjectAuthenticationFilter.setAuthenticationManager(this.authenticationManager());

        /** UWAGA! Domyslnie UsernamePasswordAuthenticationFilter dziala tylko dla GET "/login"
         jak mamy inne to zmieniamy tutaj */
        jsonObjectAuthenticationFilter.setFilterProcessesUrl("/api/login");

        return jsonObjectAuthenticationFilter;
    }

    @Bean public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }

}
```


