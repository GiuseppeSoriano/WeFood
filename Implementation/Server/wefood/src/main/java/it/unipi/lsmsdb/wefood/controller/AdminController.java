package it.unipi.lsmsdb.wefood.controller;

import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.service.IngredientService;
import it.unipi.lsmsdb.wefood.service.RegisteredUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unipi.lsmsdb.wefood.service.AdminService;
import it.unipi.lsmsdb.wefood.model.Admin;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final AdminService adminService;
    private final IngredientService ingredientService;
    private RegisteredUserService registeredUserService;

    public AdminController() {
        this.adminService = new AdminService();
        this.ingredientService = new IngredientService();
        this.registeredUserService = new RegisteredUserService();
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> loginAdmin(@RequestBody LoginRequestDTO credentials) {
        System.out.println(credentials.getUsername() + " " + credentials.getPassword());
        Admin admin = adminService.loginAdmin(credentials.getUsername(), credentials.getPassword());
        return admin != null ? ResponseEntity.ok(admin) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/createIngredient")
    public ResponseEntity<Boolean> createIngredient(@RequestBody Ingredient request) {
        return ResponseEntity.ok(ingredientService.createIngredient(request));
    }

    @PostMapping("/banUser")
    public ResponseEntity<Boolean> banUser(@RequestBody String request){
        return ResponseEntity.ok(registeredUserService.banUser(request));
    }
    @PostMapping("/unbanUser")
    public ResponseEntity<Boolean> unbanUser(@RequestBody String request){
        return ResponseEntity.ok(registeredUserService.unbanUser(request));
    }



}
