CREATE OR REPLACE PACKAGE JJBT_PKG_ESCOLAR_RRHH AS
          

        PROCEDURE CREAR_ALUMNO(
                PV_NOMBRE      VARCHAR2,
                PV_DIRECCION   VARCHAR2,
                PN_EDAD        NUMBER,
                PN_CODIGO      OUT NUMBER,
                PV_MENSAJE     OUT VARCHAR2,
                PR_CURSOR      OUT SYS_REFCURSOR
        );

        PROCEDURE CREAR_PROFESOR(
                PV_NOMBRE      VARCHAR2,
                PN_CODIGO      OUT NUMBER,
                PV_MENSAJE     OUT VARCHAR2,
                PR_CURSOR  OUT SYS_REFCURSOR
        );

    PROCEDURE CREAR_CALIFICACION(
            PN_ALUMNO_ID   NUMBER,
            PN_PROFESOR_ID NUMBER,
            PV_MATERIA     VARCHAR2,
            PN_NOTA1       NUMBER,
            PN_NOTA2       NUMBER,
            PN_NOTA3       NUMBER,
            PN_CODIGO      OUT NUMBER,
            PV_MENSAJE     OUT VARCHAR2,
            PR_CURSOR  OUT SYS_REFCURSOR
    );
    
    PROCEDURE OBTENER_CALIFICACION(
            PN_CODIGO      OUT NUMBER,
            PV_MENSAJE     OUT VARCHAR2,
            PR_CURSOR  OUT SYS_REFCURSOR
    );


END JJBT_PKG_ESCOLAR_RRHH;
/


CREATE OR REPLACE PACKAGE BODY JJBT_PKG_ESCOLAR_RRHH AS

   PROCEDURE  CREAR_ALUMNO(
            PV_NOMBRE      VARCHAR2,
            PV_DIRECCION   VARCHAR2,
            PN_EDAD        NUMBER,
            PN_CODIGO      OUT NUMBER,
            PV_MENSAJE     OUT VARCHAR2,
            PR_CURSOR      OUT SYS_REFCURSOR
    ) IS 
    
    LN_ID ALUMNO.ID%TYPE;
    LE_ERROR_NULL EXCEPTION;
    LE_ERROR_EDAD EXCEPTION;
    BEGIN

      IF  PV_NOMBRE IS NULL OR PN_EDAD IS NULL THEN
        RAISE LE_ERROR_NULL;
      END IF;

      IF  NVL(PN_EDAD, 0) < 0 THEN
        RAISE LE_ERROR_EDAD;
      END IF;

      SELECT ALUMNO_SEQ.NEXTVAL INTO LN_ID FROM DUAL;

      INSERT INTO ALUMNO (
            ID,
            NOMBRE,
            DIRECCION,
            EDAD
        ) VALUES (
            LN_ID,
            PV_NOMBRE,
            PV_DIRECCION,
            PN_EDAD
        );


        OPEN PR_CURSOR FOR
            SELECT ID, NOMBRE, DIRECCION, EDAD
            FROM ALUMNO
            WHERE ID = LN_ID;
            
        PN_CODIGO := 0;
        PV_MENSAJE := 'Alumno creado correctamente';

    EXCEPTION
     WHEN LE_ERROR_EDAD THEN
          PN_CODIGO := -2;
          PV_MENSAJE := 'Las edad debe de nmayor a 0';

     WHEN OTHERS THEN
            PN_CODIGO := -1;
            PV_MENSAJE := 'Error al crear alumno: ' || SQLERRM;

    END CREAR_ALUMNO;

   PROCEDURE CREAR_PROFESOR(
            PV_NOMBRE      VARCHAR2,
            PN_CODIGO      OUT NUMBER,
            PV_MENSAJE     OUT VARCHAR2,
            PR_CURSOR  OUT SYS_REFCURSOR
   ) IS 

     LN_ID PROFESOR.ID%TYPE;
     LE_ERROR_NULL     EXCEPTION;


    BEGIN

     IF  PV_NOMBRE IS NULL THEN
        RAISE LE_ERROR_NULL;
     END IF;

     SELECT PROFESOR_SEQ.NEXTVAL INTO LN_ID FROM DUAL;

        INSERT INTO PROFESOR (
            ID,
            NOMBRE
        ) VALUES (
            LN_ID,
            PV_NOMBRE
        );


    OPEN PR_CURSOR FOR
            SELECT ID, NOMBRE
            FROM PROFESOR
            WHERE ID = LN_ID;

    
     PN_CODIGO := 0;
     PV_MENSAJE := 'Profesor creado correctamente';
     
    EXCEPTION 
        WHEN LE_ERROR_NULL THEN
            PN_CODIGO := -2;
            PV_MENSAJE := 'Existen campos nulos';    

        WHEN OTHERS THEN
            PN_CODIGO := -1;
            PV_MENSAJE := 'Error al crear profesor: ' || SQLERRM;
    END CREAR_PROFESOR;

  PROCEDURE CREAR_CALIFICACION(
    PN_ALUMNO_ID   NUMBER,
    PN_PROFESOR_ID NUMBER,
    PV_MATERIA     VARCHAR2,
    PN_NOTA1       NUMBER,
    PN_NOTA2       NUMBER,
    PN_NOTA3       NUMBER,
    PN_CODIGO      OUT NUMBER,
    PV_MENSAJE     OUT VARCHAR2,
    PR_CURSOR    OUT SYS_REFCURSOR
  ) IS

    CURSOR C_OBTIENE_ALUMNO (C_ALUMNO_ID NUMBER) IS
      SELECT A.ID FROM ALUMNO A WHERE A.ID = C_ALUMNO_ID;

    CURSOR C_OBTIENE_PROFESOR (C_PROFESOR_ID NUMBER) IS
      SELECT P.ID FROM PROFESOR P WHERE P.ID = C_PROFESOR_ID; 

    LN_ID_ALUMNO   NUMBER;
    LN_ID_PROFESOR NUMBER;
    LN_ID NUMBER;

    LE_ERROR_ALUMNO   EXCEPTION;
    LE_ERROR_PROFESOR EXCEPTION;
    LE_ERROR_NULL     EXCEPTION;
    LE_ERROR_NOTA     EXCEPTION;

  BEGIN

    IF PN_ALUMNO_ID IS NULL OR PN_PROFESOR_ID IS NULL OR PV_MATERIA IS NULL OR
       PN_NOTA1 IS NULL OR PN_NOTA2 IS NULL OR PN_NOTA3 IS NULL THEN
       RAISE LE_ERROR_NULL;
    END IF;

    IF PN_NOTA1 < 0 OR PN_NOTA1 > 10 OR
       PN_NOTA2 < 0 OR PN_NOTA2 > 10 OR
       PN_NOTA3 < 0 OR PN_NOTA3 > 10 THEN
       RAISE LE_ERROR_NOTA;
    END IF;

    OPEN C_OBTIENE_ALUMNO(PN_ALUMNO_ID);
    FETCH C_OBTIENE_ALUMNO INTO LN_ID_ALUMNO;

    IF C_OBTIENE_ALUMNO%NOTFOUND THEN
      CLOSE C_OBTIENE_ALUMNO;
      RAISE LE_ERROR_ALUMNO;
    END IF;

    CLOSE C_OBTIENE_ALUMNO;

    OPEN C_OBTIENE_PROFESOR(PN_PROFESOR_ID);
    FETCH C_OBTIENE_PROFESOR INTO LN_ID_PROFESOR;

    IF C_OBTIENE_PROFESOR%NOTFOUND THEN
      CLOSE C_OBTIENE_PROFESOR;
      RAISE LE_ERROR_PROFESOR;
    END IF;

    CLOSE C_OBTIENE_PROFESOR;
    
    SELECT CALIFICACION_SEQ.NEXTVAL INTO LN_ID FROM DUAL;
    
    INSERT INTO CALIFICACION(
      ID,
      MATERIA,
      NOTA1,
      NOTA2,
      NOTA3,
      ALUMNO_ID,
      PROFESOR_ID
    ) VALUES (
      LN_ID,
      PV_MATERIA,
      PN_NOTA1,
      PN_NOTA2,
      PN_NOTA3,
      LN_ID_ALUMNO,
      LN_ID_PROFESOR
    );

    
    OPEN PR_CURSOR FOR
            SELECT 
            C.ID,
            C.MATERIA,
            C.NOTA1,
            C.NOTA2,
            C.NOTA3,
            A.NOMBRE AS ALUMNO,
            P.NOMBRE AS PROFESOR
        FROM CALIFICACION C
        JOIN ALUMNO A ON A.ID = C.ALUMNO_ID
        JOIN PROFESOR P ON P.ID = C.PROFESOR_ID
        WHERE C.ID = LN_ID;
     
    PN_CODIGO := 0;
    PV_MENSAJE := 'Calificacion registrada correctamente';   

  EXCEPTION

    WHEN LE_ERROR_NULL THEN
      PN_CODIGO := -3;
      PV_MENSAJE := 'Existen campos nulos';

    WHEN LE_ERROR_NOTA THEN
      PN_CODIGO := -4;
      PV_MENSAJE := 'Las notas deben estar entre 0 y 10';

    WHEN LE_ERROR_ALUMNO THEN
      PN_CODIGO := -1;
      PV_MENSAJE := 'El alumno no existe';

    WHEN LE_ERROR_PROFESOR THEN
      PN_CODIGO := -2;
      PV_MENSAJE := 'El profesor no existe';

    WHEN OTHERS THEN
      PN_CODIGO := -99;
      PV_MENSAJE := 'Error inesperado: ' || SQLERRM;

  END CREAR_CALIFICACION;
  
  PROCEDURE OBTENER_CALIFICACION(
    PN_CODIGO   OUT NUMBER,
    PV_MENSAJE  OUT VARCHAR2,
    PR_CURSOR   OUT SYS_REFCURSOR
    ) IS
    BEGIN
    
        OPEN PR_CURSOR FOR
            SELECT 
                A.NOMBRE AS ALUMNO,
                C.MATERIA,
                (C.NOTA1 + C.NOTA2 + C.NOTA3) / 3 AS PROMEDIO,
                CASE 
                    WHEN (C.NOTA1 + C.NOTA2 + C.NOTA3) / 3 <= 5 THEN 'Regular'
                    WHEN (C.NOTA1 + C.NOTA2 + C.NOTA3) / 3 <= 7.99 THEN 'Bueno'
                    ELSE 'Muy Bueno'
                END AS OBSERVACION
            FROM CALIFICACION C
            JOIN ALUMNO A ON A.ID = C.ALUMNO_ID
            JOIN PROFESOR P ON P.ID = C.PROFESOR_ID;
    
        PN_CODIGO := 0;
        PV_MENSAJE := 'Consulta exitosa';
    
    EXCEPTION
        WHEN OTHERS THEN
            PN_CODIGO := -99;
            PV_MENSAJE := 'Error inesperado: ' || SQLERRM;
    
    END OBTENER_CALIFICACION;

END JJBT_PKG_ESCOLAR_RRHH;
/
