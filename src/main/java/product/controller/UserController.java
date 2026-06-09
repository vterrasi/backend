package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.model.UserModel;
import product.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apicurso") // Mismo prefijo base que usas en Products
public class UserController {

    @Autowired
    private UserService userService;

    // 1. OBTENER TODOS LOS USUARIOS (CON EDAD OPCIONAL)
    // Ejemplo de URL con filtro: /apicurso/users?age=25
    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers(@RequestParam(required = false) Integer age) {
        List<UserModel> resultado = userService.obtenerUsuarios(age);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    // 2. OBTENER UN USUARIO POR ID
    // URL: /apicurso/users/1
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        Optional<UserModel> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // 3. CREAR UN USUARIO
    // URL: /apicurso/users
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody UserModel nuevoUsuario) {
        Optional<UserModel> usuarioCreado = userService.guardarUsuario(nuevoUsuario);

        if (usuarioCreado.isPresent()) {
            return new ResponseEntity<>(usuarioCreado.get(), HttpStatus.CREATED);
        } else {
            // Si devolvió vacío es porque contenía una 'ñ'
            return new ResponseEntity<>("Error: El nombre no puede contener la letra 'ñ'", HttpStatus.BAD_REQUEST);
        }
    }

    // 4. ACTUALIZAR UN USUARIO COMPLETO (MENOS ID)
    // URL: /apicurso/users/2
    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody UserModel datosNuevos) {
        String resultado = userService.actualizarUsuario(id, datosNuevos);

        switch (resultado) {
            case "OK":
                // Buscamos el usuario modificado para mostrar cómo quedó
                return new ResponseEntity<>(userService.findById(id).get(), HttpStatus.OK);
            case "MENOR_EDAD":
                return new ResponseEntity<>("No se permite actualizar usuarios cuya edad actual sea menor de 21 años", HttpStatus.BAD_REQUEST);
            case "NOT_FOUND":
            default:
                return new ResponseEntity<>("No se pudo actualizar, usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // 5. BORRAR UN USUARIO
    // URL: /apicurso/users/1
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        Optional<UserModel> usuarioBorrado = userService.borrarUsuario(id);

        if (usuarioBorrado.isPresent()) {
            // El enunciado pide expresamente devolver los datos completos del usuario borrado si se logra
            return new ResponseEntity<>(usuarioBorrado.get(), HttpStatus.OK);
        } else {
            // Si no se consigue, se devuelve un mensaje de que no se encontró
            return new ResponseEntity<>("No se ha encontrado el usuario a eliminar", HttpStatus.NOT_FOUND);
        }
    }
}