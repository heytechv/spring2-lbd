## Postman
W postmanie zapisuje cookie, więc najlepiej włączyć opcję "Settings->Disable cookie jar"

## Useful links
- [Spring Security Full Course](https://www.youtube.com/watch?v=her_7pa0vrg)
- [Spring Security + JWT Token](https://www.youtube.com/watch?v=and2DR_N6tE)



## JWT
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

### 2.
