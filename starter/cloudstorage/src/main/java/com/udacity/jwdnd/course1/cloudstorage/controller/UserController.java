package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.Message;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.udacity.jwdnd.course1.cloudstorage.constant.Template.*;
import static org.springframework.security.config.Elements.LOGOUT;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(LOGIN)
    public String login() {
        return LOGIN;
    }

    @PostMapping(LOGOUT)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return REDIRECT_LOGIN;
    }

    @GetMapping(SIGNUP)
    public String register() {
        return SIGNUP;
    }

    @PostMapping(SIGNUP)
    public String signupUser (@ModelAttribute User user, Model model) {
        String textError = null;

        try {
            int ret = userService.createUser(user);
            if (ret < 0) {
                textError = Message.SIGN_UP_ERROR;
            }
        } catch (InternalException i) {
            textError = i.getMessage();
        }
        catch (Exception e) {
            textError = Message.SIGN_UP_ERROR;
        }

        if (textError == null) {
            model.addAttribute("success", "Sign up successfully");
            return REDIRECT_LOGIN;
        } else {
            model.addAttribute("error", textError);
        }

        return REDIRECT_LOGIN;
    }
}
