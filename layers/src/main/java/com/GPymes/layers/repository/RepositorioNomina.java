package com.GPymes.layers.repository;

import com.GPymes.layers.domain.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositorioNomina extends JpaRepository<Nomina, UUID> {
    List<Nomina> findByEmpleadoId(UUID empleadoId);
}
