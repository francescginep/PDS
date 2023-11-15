package PDS.pdsproject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Puedes agregar métodos personalizados si es necesario
}
