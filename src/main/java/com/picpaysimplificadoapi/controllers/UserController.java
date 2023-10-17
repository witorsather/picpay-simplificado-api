package com.picpaysimplificadoapi.controllers;

import com.picpaysimplificadoapi.domain.user.User;
import com.picpaysimplificadoapi.dtos.UserDTO;
import com.picpaysimplificadoapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Cria um novo usuário com base nos dados fornecidos por um objeto UserDTO e retorna uma resposta HTTP indicando o sucesso da criação.
     *
     * @param user O objeto UserDTO contendo os dados do novo usuário.
     * @return Uma resposta HTTP com o novo usuário e o status "Created" (201).
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Obtém todos os usuários cadastrados na aplicação.
     *
     * @return Um ResponseEntity contendo uma lista de todos os usuários e status HTTP OK se bem-sucedido.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
