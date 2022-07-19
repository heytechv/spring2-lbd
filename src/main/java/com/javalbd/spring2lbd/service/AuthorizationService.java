package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.component.AuthRole;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class AuthorizationService {

    public boolean isAuthorized(String uri, String headerRole) {

        headerRole = headerRole == null ? "" : headerRole;

        if (uri == null)
            return false;

        if (uri.startsWith("/api/student") && !Arrays.asList(AuthRole.TEACHER_ROLE.toString(), AuthRole.STUDENT_ROLE.toString()).contains(headerRole))
            return false;

        if (uri.startsWith("/api/teacher") && !AuthRole.TEACHER_ROLE.toString().equals(headerRole))
            return false;

        return true;
    }


}
