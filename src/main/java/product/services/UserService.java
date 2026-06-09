package product.services;

import org.springframework.stereotype.Service;
import product.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Simulamos la base de datos con una lista estática en memoria
    private static List<UserModel> usuariosDB = new ArrayList<>();

    // Bloque estático para meter unos cuantos usuarios de prueba al arrancar
    static {
        usuariosDB.add(new UserModel("1", "Alejandro", 25));
        usuariosDB.add(new UserModel("2", "Maria", 19));
        usuariosDB.add(new UserModel("3", "Carlos", 30));
    }

    // 1. OBTENER TODOS (Y FILTRAR POR EDAD OPCIONAL)
    public List<UserModel> obtenerUsuarios(Integer edad) {
        if (edad == null) {
            return usuariosDB; // Si no pasan edad, devolvemos todos
        }
        // Si pasan edad, filtramos la lista y nos quedamos solo con los que coincidan
        List<UserModel> filtrados = new ArrayList<>();
        for (UserModel u : usuariosDB) {
            if (u.getAge() == edad) {
                filtrados.add(u);
            }
        }
        return filtrados;
    }

    // 2. BUSCAR POR ID
    public Optional<UserModel> findById(String id) {
        return usuariosDB.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    // 3. CREAR UN USUARIO (Con la regla de la "ñ")
    public Optional<UserModel> guardarUsuario(UserModel nuevoUsuario) {
        // Regla: Si el nombre contiene 'ñ' o 'Ñ', no se permite
        if (nuevoUsuario.getName() != null &&
                (nuevoUsuario.getName().contains("ñ") || nuevoUsuario.getName().contains("Ñ"))) {
            return Optional.empty(); // Devolvemos vacío para indicar que no se pudo crear
        }
        usuariosDB.add(nuevoUsuario);
        return Optional.of(nuevoUsuario);
    }

    // 4. ACTUALIZAR UN USUARIO (Con la regla de los 21 años)
    public String actualizarUsuario(String id, UserModel datosNuevos) {
        Optional<UserModel> usuarioExistente = findById(id);

        if (usuarioExistente.isEmpty()) {
            return "NOT_FOUND"; // El usuario no existe
        }

        UserModel usuarioViejo = usuarioExistente.get();

        // Regla: No se permite actualizar si la edad ACTUAL (la vieja) es menor de 21
        if (usuarioViejo.getAge() < 21) {
            return "MENOR_EDAD"; // Bloqueado por regla de negocio
        }

        // Si pasa el filtro, actualizamos t0do menos el ID
        usuarioViejo.setName(datosNuevos.getName());
        usuarioViejo.setAge(datosNuevos.getAge());
        return "OK";
    }

    // 5. BORRAR UN USUARIO (Devolviendo los datos del borrado)
    public Optional<UserModel> borrarUsuario(String id) {
        Optional<UserModel> usuarioABorrar = findById(id);

        if (usuarioABorrar.isPresent()) {
            usuariosDB.remove(usuarioABorrar.get()); // Lo sacamos de la lista
            return usuarioABorrar; // Devolvemos el usuario que acabamos de borrar
        }

        return Optional.empty(); // No se encontró
    }
}