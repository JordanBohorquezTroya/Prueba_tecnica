package com.prueba.tecnica.Prueba.repository;

import com.prueba.tecnica.Prueba.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}
