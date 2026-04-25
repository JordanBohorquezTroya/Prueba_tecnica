package com.prueba.tecnica.Prueba.repository;

import com.prueba.tecnica.Prueba.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

}
