package by.ladyka.family.api;

import by.ladyka.family.entity.UserData;
import by.ladyka.family.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/singup123")
public class SingUpController {
    private final UsersService usersService;

    @GetMapping
    public UserData singUp(String username, String password) {
        return usersService.singUp(username, password);
    }
}
