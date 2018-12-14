package the_biber_project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import the_biber_project.NameCheck;
import the_biber_project.User;
import the_biber_project.repos.MemRepInMemoryMessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    MemRepInMemoryMessageRepository usrs = new MemRepInMemoryMessageRepository();



    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");

        if (username == null || username.isEmpty() || !(NameCheck.checkName(username))) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);

        List<String> otherUsers = usrs.usersStr.stream().filter(user -> !user.equals(username)).collect(Collectors.toList());
        model.addAttribute("users", otherUsers);

        System.out.println(usrs.findAll().toString());


        return "chat";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
        username = username.trim();

        if (username.isEmpty()) {
            return "login";
        }
        request.getSession().setAttribute("username", username);

        User s = new User();
        s.setUserName(username);
        if (NameCheck.checkName(username)) {
            usrs.save(s);
        }

        String id = request.getSession().getId();
        request.getSession().setAttribute("userid", s.getId());

        request.getSession().setAttribute("username", username);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("userid");
        String name = (String) request.getSession().getAttribute("username");
        System.out.println("ID" + id);
        usrs.delete(id,name);
        request.getSession(true).invalidate();

        return "redirect:/login";
    }


}