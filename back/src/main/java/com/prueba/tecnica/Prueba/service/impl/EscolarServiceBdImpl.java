package com.prueba.tecnica.Prueba.service.impl;

import com.prueba.tecnica.Prueba.dto.request.AlumnoRequestDto;
import com.prueba.tecnica.Prueba.dto.request.CalificacionRequestDto;
import com.prueba.tecnica.Prueba.dto.request.ProfesorRequestDto;
import com.prueba.tecnica.Prueba.dto.response.*;
import com.prueba.tecnica.Prueba.service.EscolarServiceBd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EscolarServiceBdImpl implements EscolarServiceBd {

    private final DataSource dataSource;

    @Override
    public ProfesorResponseDto crearProfesor(ProfesorRequestDto profesorRequestDto) {
        SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
                .withCatalogName("JJBT_PKG_ESCOLAR_RRHH")
                .withProcedureName("CREAR_PROFESOR")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PV_NOMBRE", Types.VARCHAR),
                        new SqlOutParameter("PN_CODIGO", Types.NUMERIC),
                        new SqlOutParameter("PV_MENSAJE", Types.VARCHAR),
                        new SqlOutParameter("PR_CURSOR", Types.REF_CURSOR ,
                                (rs, rowNum) -> new ProfesorResponseDto(rs.getLong("ID"), rs.getString("NOMBRE")))
                );

        Map<String, Object> params = new HashMap<>();
        params.put("PV_NOMBRE", profesorRequestDto.nombre());
        Map<String, Object> result = call.execute(params);
        log.info("Resultado de la ejecion para crear al profesor: {} ", result);
        List<ProfesorResponseDto> response = (List<ProfesorResponseDto>) result.get("PR_CURSOR");
        return response.get(0);
    }

    public AlumnoResponsetDto crearAlumno(AlumnoRequestDto alumnoRequestDto)  {
        SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
                .withCatalogName("JJBT_PKG_ESCOLAR_RRHH")
                .withProcedureName("CREAR_ALUMNO")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PV_NOMBRE", Types.VARCHAR),
                        new SqlParameter("PV_DIRECCION", Types.VARCHAR),
                        new SqlParameter("PN_EDAD", Types.NUMERIC),
                        new SqlOutParameter("PN_CODIGO", Types.NUMERIC),
                        new SqlOutParameter("PV_MENSAJE", Types.VARCHAR),
                        new SqlOutParameter("PR_CURSOR", Types.REF_CURSOR ,
                                (rs, rowNum) -> new AlumnoResponsetDto(rs.getLong("ID"), rs.getString("NOMBRE"), rs.getString("DIRECCION"), rs.getInt("EDAD")))
                );

        Map<String, Object> params = new HashMap<>();
        params.put("PV_NOMBRE", alumnoRequestDto.nombre());
        params.put("PV_DIRECCION", alumnoRequestDto.direccion());
        params.put("PN_EDAD", alumnoRequestDto.edad());
        Map<String, Object> result = call.execute(params);
        log.info("Resultado de la ejecion para crear al alumno: {} ", result);
        List<AlumnoResponsetDto> response = (List<AlumnoResponsetDto>) result.get("PR_CURSOR");
        return response.get(0);
    }

    @Override
    public CalificacionResponseDto crearCalificacion(CalificacionRequestDto calificacionRequestDto) {
        SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
                .withCatalogName("JJBT_PKG_ESCOLAR_RRHH")
                .withProcedureName("CREAR_CALIFICACION")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PN_ALUMNO_ID", Types.NUMERIC),
                        new SqlParameter("PN_PROFESOR_ID", Types.NUMERIC),
                        new SqlParameter("PV_MATERIA", Types.VARCHAR),
                        new SqlParameter("PN_NOTA1", Types.NUMERIC),
                        new SqlParameter("PN_NOTA2", Types.NUMERIC),
                        new SqlParameter("PN_NOTA3", Types.NUMERIC),
                        new SqlOutParameter("PN_CODIGO", Types.NUMERIC),
                        new SqlOutParameter("PV_MENSAJE", Types.VARCHAR),
                        new SqlOutParameter("PR_CURSOR", Types.REF_CURSOR ,
                                (rs, rowNum) ->
                                        new CalificacionResponseDto(rs.getString("ALUMNO"), rs.getString("PROFESOR"),
                                        new CalificacionDetalleDto(rs.getLong("ID"), rs.getString("MATERIA"), rs.getDouble("NOTA1"), rs.getDouble("NOTA2"), rs.getDouble("NOTA3"))))
                );

        Map<String, Object> params = new HashMap<>();
        params.put("PN_ALUMNO_ID", calificacionRequestDto.alumnoId());
        params.put("PN_PROFESOR_ID", calificacionRequestDto.profesorId());
        params.put("PV_MATERIA", calificacionRequestDto.materia());
        params.put("PN_NOTA1", calificacionRequestDto.nota1());
        params.put("PN_NOTA2", calificacionRequestDto.nota2());
        params.put("PN_NOTA3", calificacionRequestDto.nota3());
        Map<String, Object> result = call.execute(params);
        log.info("Resultado de la ejecion para crear la calificacion: {} ", result);
        List<CalificacionResponseDto> response = (List<CalificacionResponseDto>) result.get("PR_CURSOR");
        return response.get(0);
    }

    @Override
    public List<PromedioResponseDto> obtenerCalificaciones() {
        SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
                .withCatalogName("JJBT_PKG_ESCOLAR_RRHH")
                .withProcedureName("OBTENER_CALIFICACION")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("PN_CODIGO", Types.NUMERIC),
                        new SqlOutParameter("PV_MENSAJE", Types.VARCHAR),
                        new SqlOutParameter("PR_CURSOR", Types.REF_CURSOR,
                                (rs, rowNum) -> new PromedioResponseDto(
                                        rs.getString("ALUMNO"),
                                        rs.getString("MATERIA"),
                                        rs.getDouble("PROMEDIO"),
                                        rs.getString("OBSERVACION")
                                )
                        )
                );

        Map<String, Object> result = call.execute(new HashMap<>());
        log.info("Resultado de la ejecion para obtener las calificaciones: {} ", result);
        List<PromedioResponseDto> response = (List<PromedioResponseDto>) result.get("PR_CURSOR");
        return response;
    }


}


