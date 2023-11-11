package api.nick.controller;

import api.nick.entity.login.AuthenticationDTO;
import api.nick.entity.login.Login;
import api.nick.entity.login.LoginResponseDTO;
import api.nick.entity.user.User;
import api.nick.entity.user.UserDTO;
import api.nick.service.UserService;
import api.nick.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/user")
public class  UserController {
    @Autowired
    private UserService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) throws Exception{
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.enrollment(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((Login) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token, ((Login) auth.getPrincipal()).getUser().getId().toString()));
        }catch (Exception e){
            throw new Exception("Usuário ou senha inválidos");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'ADMIN')")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) throws Exception{
        try {
            Optional<User> user = service.findUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserDTO newUser) throws Exception{
        try {
            service.addUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'ADMIN')")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserDTO newUser) throws Exception{
        try {
            service.updateUserById(id, newUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'ADMIN')")
    public ResponseEntity deleteUser(@PathVariable Long id) throws Exception{
        try {
            service.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}