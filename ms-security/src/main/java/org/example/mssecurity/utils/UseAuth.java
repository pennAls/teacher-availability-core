package org.example.mssecurity.utils;

import org.example.mssecurity.modules.users.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class UseAuth {

    public static UUID GetAuthenticatedUser
            (){
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    var loggedUser = (User) authentication.getPrincipal();
    var userId = loggedUser.getId();
    return userId;

    }
}
