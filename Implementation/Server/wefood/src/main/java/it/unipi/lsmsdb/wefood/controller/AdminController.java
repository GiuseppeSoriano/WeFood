package it.unipi.lsmsdb.wefood.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unipi.lsmsdb.wefood.service.AdminService;
import it.unipi.lsmsdb.wefood.model.Admin;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final AdminService adminService;

    public AdminController() {
        this.adminService = new AdminService();
    }

    @PostMapping("/prova")
    public void loginAdmin(@RequestBody Map<String, String> admin) {
        // Admin currentAdmin = adminService.loginAdmin(username, password);
        System.out.println(admin);
        // return currentAdmin != null ? ResponseEntity.ok(currentAdmin) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/login")
    public ResponseEntity<Admin> loginAdmin(@RequestBody Admin admin) {
        // Admin currentAdmin = adminService.loginAdmin(username, password);
        System.out.println(admin.getUsername() + " " + admin.getPassword());
        return null;
        // return currentAdmin != null ? ResponseEntity.ok(currentAdmin) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /*
        RequestMapping -> path base (localhost:8080/admin) delle API che sono gestite da AdminController
        PostMapping -> login is a Post Request
        PathVariable -> 
    */
}
