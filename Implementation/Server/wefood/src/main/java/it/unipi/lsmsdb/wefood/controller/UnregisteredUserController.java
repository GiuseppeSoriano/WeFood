package it.unipi.lsmsdb.wefood.controller;

import it.unipi.lsmsdb.wefood.apidto.UnregisteredUserRequestDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unipi.lsmsdb.wefood.service.UnregisteredUserService;

@RestController
@RequestMapping("/unregistereduser")
public class UnregisteredUserController {
    private final UnregisteredUserService unregisteredUserService;
    public UnregisteredUserController() {
        this.unregisteredUserService = new UnregisteredUserService();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisteredUser> register(@RequestBody UnregisteredUserRequestDTO request) {
        RegisteredUser user = unregisteredUserService.createRegisteredUser(request.getUsername(), request.getPassword(), request.getName(), request.getSurname());
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
