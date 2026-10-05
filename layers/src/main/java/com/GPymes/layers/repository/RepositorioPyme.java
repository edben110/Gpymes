package main.java.com.GPymes.layers.repository;
import main.java.com.GPymes.layers.domain.Pyme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

public interface RepositorioPyme extends JpaRepository<Pyme, UUID> {
    List<Pyme> findByPymeId(UUID PymeId);
}
