package api.nick.repository;

import api.nick.entity.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {

    @Query("SELECT * FROM tb_login WHERE enrollment = :enrollment")
    Optional<Login> findByEnrollment(String enrollment);
}