package com.prueba.tecnica.Prueba.repository;

import com.prueba.tecnica.Prueba.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    boolean existsByAlumnoIdAndMateria(Long alumnoId, String materia);
}
