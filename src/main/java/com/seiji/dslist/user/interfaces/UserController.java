package com.seiji.dslist.user.interfaces;

import com.seiji.dslist.user.application.services.UserService;
import com.seiji.dslist.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
}
