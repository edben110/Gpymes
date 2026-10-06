package com.GPymes.layers.repository;

import com.GPymes.layers.domain.CategoriasGasto;
import com.GPymes.layers.domain.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositorioGasto extends JpaRepository<Gasto, UUID> {
    List<Gasto> findByPymeId(UUID pymeId);

    List<Gasto> findByCategoria(CategoriasGasto categoria);
}