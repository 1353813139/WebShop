package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginRegisterController {
    @Autowired
    private UserRespository userRespository;

    @RequestMapping(value = {"/login","/"},method = RequestMethod.GET)
    public String getLogin(HttpServletRequest request,Model model){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            String display = "on";
            model.addAttribute("display",display);
        }
        else {
            if(is_login.toString().equals("true")){
                String message = "不允许重复登录！";
                model.addAttribute("message",message);
            }
        }
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String Login(String username, String password, Model model, HttpSession session ){
        String message2 = "";
        int state = 0;
        if(username.equals("admin") && password.equals("admin")){
            session.setAttribute("is_login",true);
            session.setAttribute("username",username);
            session.setAttribute("is_admin",true);
            return "redirect:/backreport";
        }
        User user = userRespository.findUserByUsername(username);
        if(user == null){
            message2 = "用户不存在！";
            state = 1;
            model.addAttribute("message2",message2);
            return "/login";
        }
        if(!password.equals(user.getPassword())){
            message2 = "密码错误！";
            System.out.println(user.getPassword());
            System.out.println(password);
            state = 1;
            model.addAttribute("message2",message2);
            return "/login";
        }
        session.setAttribute("is_login",true);
        session.setAttribute("username",username);
        return  "redirect:/categories";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String getRegister(){
        return "register";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String Register(String username,String password,String e_mail,String address,Model model){
        int state = 0;
        String message = "";
        String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
        User user = userRespository.findUserByUsername(username);
        if(user != null){
            state = 1;
            message = "用户已存在！";
        }
        if(!e_mail.matches(regex)) {
            state = 1;
            message = "邮箱格式错误！";
        }
        if(username == "admin"){
            state = 1;
            message = "不允许注册管理员同名账号！";
        }
        if(state == 0){
            user = new User(username,password,e_mail,address);
            userRespository.save(user);
            message = "注册成功，请登录。";
            model.addAttribute("state",state);
            model.addAttribute("message",message);
            return "redirect:/login";
        }
        else{
            model.addAttribute("state",state);
            model.addAttribute("message",message);
            return "register";
        }

    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String Logout(HttpServletRequest request){
        request.getSession().removeAttribute("is_login");
        request.getSession().removeAttribute("is_admin");
        request.getSession().removeAttribute("username");
        return "redirect:/login";
    }
    @RequestMapping(value = "/noright",method = RequestMethod.GET)
    public String Noright(HttpServletRequest request,Model model){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        String message;
        if(is_admin == null){
            message = "你没有权限访问管理页面！请以管理员身份登录";
        }
        else {
            message = "管理员不允许访问用户界面！请以用户身份登录";
        }
        model.addAttribute("message",message);
        return "noright";
    }
}
