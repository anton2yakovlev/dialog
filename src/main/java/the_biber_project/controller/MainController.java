package the_biber_project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import the_biber_project.Colors;
import the_biber_project.NameCheck;
import the_biber_project.User;
import the_biber_project.repos.MemRepInMemoryMessageRepository;

import java.util.Optional;

@Controller
public class MainController {

    MemRepInMemoryMessageRepository usrs = new MemRepInMemoryMessageRepository();
    Colors colors = new Colors();

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        String color = (String) request.getSession().getAttribute("usercolor");

        if (username == null || username.isEmpty() || !(NameCheck.checkName(username))) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);
        model.addAttribute("usercolor", color);
        model.addAttribute("users", usrs.getOtherUsers(username));
        model.addAttribute("allUsers", usrs.getAllUsers());

        System.out.println(usrs.findAll().toString());

        return "chat";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String password) {
        username = username.trim();

        if (username.isEmpty()) {
            return "login";
        }

        Optional<User> res = this.usrs.find(username);
        if(!res.isPresent()) {
            return "login";
        }

        User user = res.get();
        if(!user.getPassword().equals(password)) {
            return "login";
        }
        String id = request.getSession().getId();

        request.getSession().setAttribute("userid", user.getId());
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("usercolor", user.getUsercolor());

        return "redirect:/";
    }

    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("userid");
        String name = (String) request.getSession().getAttribute("username");
        System.out.println("ID" + id);
        this.usrs.delete(id, name);
        request.getSession(true).invalidate();

        return "redirect:/login";
    }

    @RequestMapping(path = "/signup")
    public String showSignupPage(HttpServletRequest request,  Model model) {
        model.addAttribute("colors", colors.getAll());
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String signup(HttpServletRequest request, @RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String password, @RequestParam(defaultValue = "") String usercolor) {
        username = username.trim();
        System.out.println(username);
        System.out.println(password);
        System.out.print(usercolor);

        if (username.isEmpty() || password.isEmpty() || usercolor.isEmpty() || !NameCheck.checkName(username)) {
            return "signup";
        }
        Optional<User> res = this.usrs.find(username);
        if(res.isPresent()) {
            return "signup";
        }
        User s = new User();
        s.setUserName(username);
        s.setPassword(password);
        s.setUsercolor(usercolor);
        usrs.save(s);

        String id = request.getSession().getId();
       request.getSession().setAttribute("userid", s.getId());
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("usercolor", s.getUsercolor());

        return "redirect:/";
    }
}