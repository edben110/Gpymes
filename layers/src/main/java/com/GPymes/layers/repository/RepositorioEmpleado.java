package com.GPymes.layers.repository;

import com.GPymes.layers.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositorioEmpleado extends JpaRepository<Empleado, UUID> {
    List<Empleado> findByPymeId(UUID pymeId);
}
