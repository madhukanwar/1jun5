package com.example.SSO_SingleSignOn;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @PreAuthorize("hasRole('ROLE_SUPER')")
    @RequestMapping("/super")
    public String Super(){
        return "super";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/admin")
    public String Admin(){
        return "admin";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/user")
    public String User(){
        return "user";
    }

    @RequestMapping("/test1")
    public String myee1() throws CustomExaception {
        try{
            int i=10;
            int j=10/0;
        }catch (Exception e){
            throw new CustomExaception("mymsg");
        }
        return "success";
    }



}
