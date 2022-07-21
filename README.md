## Useful links
- [Spring Security Full Course](https://www.youtube.com/watch?v=her_7pa0vrg)
- [Spring Security + JWT Token](https://www.youtube.com/watch?v=and2DR_N6tE)
- [Spring Security credentials from JSON request](https://ckinan.com/blog/spring-security-credentials-from-json-request/)

#### Konfiguracja do Postmana w `docs/Postman`
__________

## JWT Authentication

### UWAGA!
#### Korzysta z kodu z `branch 1-security-jsonObject` (tam wyjaśnione też parę rzeczy, warto zajrzeć)

### Potrzebne są dwa filtry:
- Filtr wywoływany raz - autoryzujacy (jak sie zgadzaja dane to successHandler i generujemy token)
- Filtr wywoływany za każdym razem - werfikuje token otrzymany z połączeniem czy jest on poprawny


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


### 2. Edytujemy SuccessHandler (żeby zwracał token JWT po poprawnej autoryzacji)
Zamiast tworzyć sesję ma zwracać token JWT

*AuthenticationSuccessHandler.java*
```java
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    // Wstrzykumey z 'application.properties'
    @Value("${jwt.expirationTimeMillis}") private long expirationTimeMillis;
    @Value("${jwt.secret}")               private String secret;


    @Override public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        // Pobieramy nasze UserDetails
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        // Tworzymy token (co ma zawierac)
        String token = Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(secretKey)
                .compact();
        // Zwracamy token w headerze
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

}
```

### 3. Sprawdzanie tokena przy każdym zapytaniu
Jak otrzymamy tokena z `AuthenticationSuccessHandler.class`, możemy go wysyłać z kolejnymi requestami<br/>
Potrzebujemy mechanizm, który przechwyci nam token i zweryfikuje jego poprawność

*JWTAuthorizationFilter.java*
```java
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final String secret;


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,  String secret) {
        super(authenticationManager);

        this.userDetailsService = userDetailsService;
        this.secret = secret;

    }

    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        // Jak nie znalazlo/nie autoryzowalo to przykro :/, jedziemy dalej z filterChain.doFilter()
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // Sprawdzamy czy token istnieje
        String token = request.getHeader(TOKEN_HEADER);
        if (token == null || !token.startsWith(TOKEN_PREFIX))
            return null;

        // Odszyfrowujemy token
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

        Claims body = claimsJws.getBody();

        String username = body.getSubject();
        if (username == null)
            return null;

        // Pobieramy naszego uzytkownika
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Zwracamy
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }

}
```

### 4. Podłączamy filtr `JWTAuthorizationFilter.java` do konfiguracji `SecurityConfig.java`
*SecurityConfig.java*
```java
@Configuration
@EnableWebSecurity(debug = false)                                      // enable WebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)      // enable @PreAuthorize("hasAnyRole(...)")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /** Wstrzykujemy handlery */
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;

    @Value("${jwt.secret}") private String secret;


    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(encoder().encode("user"))
                .authorities(UserPermission.ADMIN.name());
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)                             // bezstanowa (weryfikujemy JWT token, wiec nic na serverze nie musimy przechowywac o sesji)
                .and()

                .addFilter(authenticationJsonFilter())                                                                  // filtr autoryzujacy (jak sie zgadzaja dane to successHandler i generujemy token)
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService(), secret))           // filtr, ktory werfikuje przy kazdym polaczeniu z tokenem czy jest on poprawny

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

### 5. Konfiguracja
*application.properties*
```
jwt.expirationTimeMillis=120000
jwt.secret=secretKeyCreatedForAuthFiltersShouldBeOk
```

### 6. Postman
Konfiguracja w `docs/Postman`.<br/>
Wystarczy wywołać razm `GET /api/login`, żeby pozyskać token, i potem kopiujemy go do innych zapytań do headera:<br/>
>Accept-Encoding:...<br/>
>Authorization: Bearer eyJhbGciO


