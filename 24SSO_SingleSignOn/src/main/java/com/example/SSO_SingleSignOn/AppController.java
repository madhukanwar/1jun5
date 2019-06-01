package com.example.SSO_SingleSignOn;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
public class AppController  implements ErrorController {

    public FacebookConnectionFactory fcf=null;
    public OAuth2Operations auth=null;
    public OAuth2Parameters param=null;
    public AccessGrant ag=null;
    public String token=null;
    public String url= "http://localhost:8080/auth/facebook";
    public String []fields = {"id","name"};


    @RequestMapping("/")
    public String index(Principal principal){
        return principal != null ? "dashboardpage" : "index";
    }

    @RequestMapping("/mylogin")
    public String mylogin(){
        return "loginpage";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication, Principal principal,
                            HttpServletRequest request){
        Principal pal =request.getUserPrincipal();
        Authentication myauthentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println(myauthentication.getName());
        model.addAttribute("name",myauthentication.getName());
        model.addAttribute("auth",myauthentication.getAuthorities());
        return "dashboardpage";
    }

    @RequestMapping("/myerror")
    public String error(){
        return "errorpage";
    }

    @RequestMapping("/access")
    public String access(){
        return "accesspage";
    }

    @RequestMapping("/error")
    public String errorctrl(){
        return "errorctrl";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

   @RequestMapping("/facebooklogin")
    public void login(HttpServletResponse response) throws IOException {
        fcf= new FacebookConnectionFactory("366207870382295","a58161e4d58346f311665c2f6e98d2bd");
        auth= fcf.getOAuthOperations();
        param = new OAuth2Parameters();
        param.setRedirectUri(url);
        response.sendRedirect(auth.buildAuthorizeUrl(param));
    }

    @RequestMapping("/auth/facebook")
    public String login2(@RequestParam String code, Model model) {
        ag = auth.exchangeForAccess(code,url,null);
        String token =ag.getAccessToken();
        Facebook facebook = new FacebookTemplate(token);
        User user =facebook.fetchObject("me", User.class);
        model.addAttribute("name",user.getId());
        model.addAttribute("auth",user.getName());
        return "dashboardpage";
    }

}
