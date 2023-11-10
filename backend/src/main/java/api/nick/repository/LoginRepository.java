package api.nick.repository;

import api.nick.entity.login.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {

    @Query(value = "SELECT * FROM tb_login WHERE enrollment = :enrollment", nativeQuery = true)
    Optional<Login> findByEnrollment(String enrollment);

    @Query(value = "SELECT * FROM tb_login " +
            "WHERE user_id = :id", nativeQuery = true)
    Optional<Login> findByUser(Long id);
}