package fitloop.member.controller;

import fitloop.member.dto.request.RegisterRequest;
import fitloop.member.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/v1")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public String registerProcess(RegisterRequest registerRequest) {

        System.out.println(registerRequest.getUsername());
        registerService.registerProcess(registerRequest);

        return "ok";
    }
}
