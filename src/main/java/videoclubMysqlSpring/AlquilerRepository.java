package videoclubMysqlSpring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
    // De momento, con los superpoderes heredados de JpaRepository nos basta para guardar (save)
}