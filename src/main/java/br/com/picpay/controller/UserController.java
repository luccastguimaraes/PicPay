package br.com.picpay.controller;

import br.com.picpay.domain.user.CommonUser;
import br.com.picpay.domain.user.MerchantUser;
import br.com.picpay.domain.user.User;
import br.com.picpay.dto.UserDTO;
import br.com.picpay.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

   @Autowired
   private UserService<User> userService;

   @PostMapping("/common")
   public ResponseEntity<User> createCommonUser(@RequestBody @Valid UserDTO userDTO){
      User newUser = userService.createUser(userDTO);
      return ResponseEntity.ok(newUser);
   }

   @PostMapping("/merchant")
   public ResponseEntity<User> createMerchantUser(@RequestBody @Valid UserDTO userDTO){
      User newUser = userService.createUser(userDTO);
      return ResponseEntity.ok(newUser);
   }

   @GetMapping
   public ResponseEntity<List<User>> getAllUsers(){
      List<User> users = this.userService.getAllUsers();
      return ResponseEntity.ok(users);
   }
}
