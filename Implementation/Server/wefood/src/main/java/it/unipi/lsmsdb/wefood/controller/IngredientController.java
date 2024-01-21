package it.unipi.lsmsdb.wefood.controller;

import it.unipi.lsmsdb.wefood.apidto.IngredientAndLimitRequestDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController() {
        this.ingredientService = new IngredientService();
    }


    @PostMapping("/findIngredientByName")
    public ResponseEntity<Ingredient> findIngredientByName(@RequestBody String request) {
        return ResponseEntity.ok(ingredientService.findIngredientByName(request));
    }

    @PostMapping("/getAllIngredients")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @PostMapping("/mostPopularCombinationOfIngredients")
    public ResponseEntity<List<String>> mostPopularCombinationOfIngredients(@RequestBody String request){
        return ResponseEntity.ok(ingredientService.mostPopularCombinationOfIngredients(request));
    }

    @PostMapping("/findNewIngredientsBasedOnFriendsUsage")
    public ResponseEntity<List<String>> findNewIngredientsBasedOnFriendsUsage(@RequestBody RegisteredUserDTO request){
        System.out.println(request.getUsername());
        return ResponseEntity.ok(ingredientService.findNewIngredientsBasedOnFriendsUsage(request));
    }
    
    @PostMapping("/findMostUsedIngredientsByUser")
    public ResponseEntity<List<String>> findMostUsedIngredientsByUser(@RequestBody RegisteredUserDTO request){
        return ResponseEntity.ok(ingredientService.findMostUsedIngredientsByUser(request));
    }

    @PostMapping("/findMostLeastUsedIngredients")
    public ResponseEntity<List<String>> findMostLeastUsedIngredients(@RequestBody Boolean request){
        return ResponseEntity.ok(ingredientService.findMostLeastUsedIngredients(request));
    }
}