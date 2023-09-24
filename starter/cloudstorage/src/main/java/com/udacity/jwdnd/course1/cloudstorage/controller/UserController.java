package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.Message;
import com.udacity.jwdnd.course1.cloudstorage.constant.Template;
import com.udacity.jwdnd.course1.cloudstorage.dto.UserDto;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String register() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser (@ModelAttribute UserDto userDto, Model model) {
        String textError = null;

        try {
            int ret = userService.createUser(userDto);
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
            model.addAttribute("signupSuccess", true);
            return "redirect:/home";
        } else {
            model.addAttribute("signupError", textError);
        }

        return Template.SIGNUP;
    }
}
