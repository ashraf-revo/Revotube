package org.revo.Service.Impl;

import org.revo.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ashraf on 22/04/17.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String current() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object details = ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
            Object principal = ((Map<String, Object>) details).get("principal");
            return ((Map<String, Object>) principal).get("id").toString();
        }
        return null;
    }
}
