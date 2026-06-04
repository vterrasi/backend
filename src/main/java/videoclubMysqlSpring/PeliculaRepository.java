package videoclubMysqlSpring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    // Spring es tan listo que si escribes el método en inglés con este orden,
    // él solito genera la consulta SQL para buscar películas que duren MENOS o IGUAL a los minutos dados.
    List<Pelicula> findByDuracionLessThanEqual(int maxDuracion);
}
