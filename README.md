### Link a Delta-Escritorio: https://github.com/rulwn/Delta-Escritorio.git


### Base de datos
/*******************************************************************************

    ~ Creacion del usuario de DeltaMed ~

*******************************************************************************/

/*
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
CREATE USER DeltaMed IDENTIFIED BY "deltaTeam1";
GRANT "CONNECT" TO DeltaMed;

ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';
COMMIT;
*/

/*******************************************************************************

    ~ ELIMINACIÓN DE TABLAS EXISTENTES ~
    Para cada una de las tablas y se elimina junto con todas sus restricciones

*******************************************************************************/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbFichasMedicas CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbIndicaciones CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbCitasMedicas CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbExpedientes CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbPacientes CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbNotis CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbReviews CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbUsuarios CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbSeguros CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbServicios CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbHorarios CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbCentrosMedicos CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbSucursales CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbDoctores CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbRecetas CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbAseguradoras CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbEstablecimientos CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbEspecialidades CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbTipoSucursales CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbTiempos CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbTipoUsuarios CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbTipoNotis CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

/*******************************************************************************

    ~ ELIMINACIÓN DE SECUENCIAS EXISTENTES ~

*******************************************************************************/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE tipoNotis';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE tipoUsuarios';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE tiempos';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE tipoSucursales';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE especialidades';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE establecimientos';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE aseguradoras';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE recetas';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE doctores';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE sucursales';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE centros';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE horarios';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seguros';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE serviciosMedicos';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE usuarios';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE reviews';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE notis';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE pacientes';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE expedientes';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE citas';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE indicaciones';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE fichas';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

/*******************************************************************************

    ~ CREACIÓN DE TABLAS INDEPENDIENTES ~

*******************************************************************************/


/*******************************************************************************

    ~ Creación de la tabla indepentiente tbTipoNotis ~

*******************************************************************************/
CREATE TABLE tbTipoNotis (
    ID_TipoNoti INT PRIMARY KEY,
    nombreTipoNoti VARCHAR2(25)
);


/*******************************************************************************

    ~ Creación de la tabla indepentiente tbTipoUsuarios ~

*******************************************************************************/
CREATE TABLE tbTipoUsuarios (
    ID_TipoUsuario INT PRIMARY KEY,
    nombreTipoUsuario VARCHAR2(50) NOT NULL UNIQUE
);


/*******************************************************************************

    ~ Creación de la tabla indepentiente tbTiempos ~

*******************************************************************************/
CREATE TABLE tbTiempos (
    ID_Tiempo INT PRIMARY KEY,
    lapsosTiempo VARCHAR2(50) NOT NULL,
    frecuenciaMedi VARCHAR2(50) NOT NULL
);


/*******************************************************************************

    ~ Creación de la tabla indepentiente tbTipoSucursales ~

*******************************************************************************/
CREATE TABLE tbTipoSucursales (
    ID_TipoSucursal INT PRIMARY KEY,
    nombreTipoSucursal VARCHAR2(50) NOT NULL UNIQUE
);


/*******************************************************************************

    ~ Creación de la tabla indepentiente tbEspecialidades ~

*******************************************************************************/
CREATE TABLE tbEspecialidades (
    ID_Especialidad INT PRIMARY KEY,
    nombreEspecialidad VARCHAR2(60) NOT NULL UNIQUE,
    nuevaEspecialidad VARCHAR2(60) UNIQUE
);


/*******************************************************************************

    ~ Creación de la tabla indepentiente tbEstablecimientos ~

*******************************************************************************/
CREATE TABLE tbEstablecimientos (
    ID_Establecimiento INT PRIMARY KEY,
    nombreClinica VARCHAR2(50) NOT NULL UNIQUE,
    imgPrincipal VARCHAR2(256) NOT NULL UNIQUE
);


/*******************************************************************************

    ~ Creación de la tabla indepentiente tbAseguradoras ~

*******************************************************************************/
CREATE TABLE tbAseguradoras (
    ID_Aseguradora INT PRIMARY KEY,
    nombreAseguradora VARCHAR2(50) NOT NULL UNIQUE
);


/*******************************************************************************

    ~ Creación de la tabla indepentiente tbRecetas ~

*******************************************************************************/
CREATE TABLE tbRecetas (
    ID_Receta INT PRIMARY KEY,
    fechaReceta DATE NOT NULL,
    ubicacionPDF VARCHAR2(250)
);

/*************************************************************************************************

    ~ CREACIÓN DE TABLAS DEPENDIENTES ~

*************************************************************************************************/

/*******************************************************************************

    ~ Creación de la tabla depentiente tbUsuarios ~

*******************************************************************************/
CREATE TABLE tbUsuarios (
    ID_Usuario INT PRIMARY KEY,
    nombreUsuario VARCHAR2(50) NOT NULL,
    apellidoUsuario VARCHAR2(50) NOT NULL,
    emailUsuario VARCHAR2(50) NOT NULL UNIQUE,
    contrasena VARCHAR2(64) NOT NULL,
    direccion VARCHAR2(200) NOT NULL,
    telefonoUsuario VARCHAR2(9) NOT NULL,
    sexo VARCHAR2(1) NOT NULL,
    fechaNacimiento VARCHAR2(10) NOT NULL,
    imgUsuario VARCHAR2(250),
    ID_TipoUsuario INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_TipoUsuario FOREIGN KEY (ID_TipoUsuario) 
    REFERENCES tbTipoUsuarios(ID_TipoUsuario)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbSeguros ~

*******************************************************************************/
CREATE TABLE tbSeguros (
    ID_Seguro INT PRIMARY KEY,
    carnetSeguro VARCHAR2(20) NOT NULL UNIQUE,
    poliza VARCHAR2(60) NOT NULL UNIQUE,
    ID_Aseguradora INT NOT NULL,
    ID_Usuario INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Aseguradora_Seguro FOREIGN KEY (ID_Aseguradora)
    REFERENCES tbAseguradoras(ID_Aseguradora)
    ON DELETE CASCADE,

    CONSTRAINT FK_Seguro FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbDoctores ~

*******************************************************************************/
CREATE TABLE tbDoctores (
    ID_Doctor INT PRIMARY KEY,
    codProfesional VARCHAR2(12) NOT NULL,
    ID_Especialidad INT NOT NULL,
    ID_Usuario INT NOT NULL,
    --CONSTRAINTS------------------
    CONSTRAINT FK_Especialidad FOREIGN KEY (ID_Especialidad) 
    REFERENCES tbEspecialidades(ID_Especialidad)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Usuario_Doctor FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbSucursales ~

*******************************************************************************/
CREATE TABLE tbSucursales (
    ID_Sucursal INT PRIMARY KEY,
    nombreSucursal VARCHAR2(60) NOT NULL,
    codSucursal NUMBER(8) NOT NULL UNIQUE,
    emailSucur VARCHAR2(30) NOT NULL UNIQUE,
    telefonoSucur VARCHAR2(12) NOT NULL UNIQUE,
    direccionSucur VARCHAR2(200) NOT NULL UNIQUE,
    ubicacionSucur VARCHAR2(200) NOT NULL,
    whatsapp VARCHAR2(12),
    imgSucursal VARCHAR2(250) NOT NULL UNIQUE,
    ID_Establecimiento INT NOT NULL,
    ID_TipoSucursal INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Establecimiento FOREIGN KEY (ID_Establecimiento) 
    REFERENCES tbEstablecimientos(ID_Establecimiento)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_TipoSucursal FOREIGN KEY (ID_TipoSucursal) 
    REFERENCES tbTipoSucursales(ID_TipoSucursal)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbCentrosMedicos ~

*******************************************************************************/
CREATE TABLE tbCentrosMedicos (
    ID_Centro INT PRIMARY KEY,
    favorito CHAR(1) CHECK (favorito IN ('T', 'F')) NOT NULL,
    ID_Doctor INT NOT NULL UNIQUE,
    ID_Sucursal INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Doctor FOREIGN KEY (ID_Doctor) 
    REFERENCES tbDoctores(ID_Doctor)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Sucursal FOREIGN KEY (ID_Sucursal) REFERENCES tbSucursales(ID_Sucursal)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbHorarios ~

*******************************************************************************/
CREATE TABLE tbHorarios (
    ID_Horario INT PRIMARY KEY,
    horaInicio TIMESTAMP NOT NULL UNIQUE,
    horaSalida TIMESTAMP NOT NULL UNIQUE,
    dias DATE NOT NULL,
    exclusiones DATE NOT NULL,
    almuerzo TIMESTAMP NOT NULL,
    descansos DATE NOT NULL,
    lapsosCita NUMBER(2) NOT NULL,
    ID_Centro INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Centro_Horario FOREIGN KEY (ID_Centro) 
    REFERENCES tbCentrosMedicos(ID_Centro)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbServicios ~

*******************************************************************************/
CREATE TABLE tbServicios (
    ID_Servicio INT PRIMARY KEY,
    nombreServicio VARCHAR2(60) NOT NULL UNIQUE,
    costo DECIMAL(10,2) NOT NULL,
    ID_Aseguradora INT NOT NULL,
    ID_Centro INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Aseguradora_Servicio FOREIGN KEY (ID_Aseguradora) 
    REFERENCES tbAseguradoras(ID_Aseguradora)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Centro_Servicio FOREIGN KEY (ID_Centro) 
    REFERENCES tbCentrosMedicos(ID_Centro)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbReviews ~

*******************************************************************************/
CREATE TABLE tbReviews (
    ID_Review INT PRIMARY KEY,
    nombreCentro VARCHAR2(50) NOT NULL,
    promEstrellas NUMBER(5) NOT NULL,
    comentario VARCHAR2(200),
    ID_Usuario INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Usuario_Review FOREIGN KEY (ID_Usuario) 
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbNotis ~

*******************************************************************************/
CREATE TABLE tbNotis (
    ID_Notificacion INT PRIMARY KEY,
    fechaNoti DATE NOT NULL,
    tipoNoti CHAR(1) NOT NULL,
    mensajeNoti VARCHAR2(200) NOT NULL,
    flag CHAR(1) CHECK (flag in ('S', 'N')) NOT NULL,
    ID_Usuario INT NOT NULL,
    ID_TipoNoti INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Usuario_Noti FOREIGN KEY (ID_Usuario) 
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_TipoNoti FOREIGN KEY (ID_TipoNoti) 
    REFERENCES tbTipoNotis(ID_TipoNoti)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbPacientes ~

*******************************************************************************/
CREATE TABLE tbPacientes (
    ID_Paciente INT PRIMARY KEY,
    nombrePaciente VARCHAR2(50) NOT NULL,
    apellidoPaciente VARCHAR2(50) NOT NULL,
    imgPaciente VARCHAR2(250),
    parentesco VARCHAR2(30),
    ID_Usuario INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Usuario_Paciente FOREIGN KEY (ID_Usuario) 
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbExpedientes ~

*******************************************************************************/
CREATE TABLE tbExpedientes (
    ID_Expediente INT PRIMARY KEY,
    antecedentes VARCHAR2(200) NOT NULL,
    nombrePadre VARCHAR2(50),
    nombreMadre VARCHAR2(50),
    responsable VARCHAR2(50),
    permaMedicamentos VARCHAR2(100) NOT NULL,
    presionArterial VARCHAR2(20) NOT NULL,
    peso DECIMAL(4,2) NOT NULL,
    altura NUMBER(3) NOT NULL,
    contactoEmer VARCHAR2(12) NOT NULL,
    saturacion NUMBER(3) NOT NULL,
    historial VARCHAR2(200) NOT NULL,
    tipoSangre VARCHAR2(10) NOT NULL,
    fechaConsultas DATE NOT NULL,
    ID_Paciente INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Paciente_Expe FOREIGN KEY (ID_Paciente) 
    REFERENCES tbPacientes(ID_Paciente)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbCitasMedicas ~

*******************************************************************************/
CREATE TABLE tbCitasMedicas (
    ID_Cita INT PRIMARY KEY,
    diaCita DATE NOT NULL,
    horaCita TIMESTAMP NOT NULL,
    motivo VARCHAR2(150) NOT NULL,
    ID_Centro INT NOT NULL,
    ID_Paciente INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Centro_Cita FOREIGN KEY (ID_Centro) 
    REFERENCES tbCentrosMedicos(ID_Centro)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Paciente_Cita FOREIGN KEY (ID_Paciente) 
    REFERENCES tbPacientes(ID_Paciente)
    ON DELETE CASCADE
);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbIndicaciones ~

*******************************************************************************/
CREATE TABLE tbIndicaciones (
    ID_Indicacion INT PRIMARY KEY,
    duracionMedi TIMESTAMP NOT NULL,
    dosisMedi VARCHAR2(50) NOT NULL,
    medicina VARCHAR2(90) NOT NULL,
    detalleIndi VARCHAR2(250) NOT NULL,
    ID_Receta INT NOT NULL,
    ID_Tiempo INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Tiempo FOREIGN KEY (ID_Tiempo) 
    REFERENCES tbTiempos(ID_Tiempo)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Receta_Indi FOREIGN KEY (ID_Receta) 
    REFERENCES tbRecetas(ID_Receta)
    ON DELETE CASCADE
);
--SELECT ID_Tiempo FROM tbTiempos WHERE ID_Tiempo IN (1, 2, 3, 4, 5);
--SELECT ID_Receta FROM tbRecetas WHERE ID_Receta IN (1, 2, 3, 4, 5);


/*******************************************************************************

    ~ Creación de la tabla depentiente tbFichasMedicas ~

*******************************************************************************/
CREATE TABLE tbFichasMedicas (
    ID_Ficha INT PRIMARY KEY,
    diagnostico VARCHAR2(200) NOT NULL,
    tratamiento VARCHAR2(200) NOT NULL,
    sintomas VARCHAR2(150) NOT NULL,
    fechaFicha DATE NOT NULL,
    ID_Receta INT NOT NULL,
    ID_Cita INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Receta_Ficha FOREIGN KEY (ID_Receta) 
    REFERENCES tbRecetas(ID_Receta)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Cita FOREIGN KEY (ID_Cita) 
    REFERENCES tbCitasMedicas(ID_Cita)
    ON DELETE CASCADE
);

/*************************************************************************************************

    ~ CREACIÓN DE SECUENCIAS ~

*************************************************************************************************/


/*************************************************************************************************

    ~ Creación de la secuencia TipoNoti, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_TIPONOTI --
CREATE SEQUENCE tipoNotis
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia TipoUsuarios, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_TIPOUSUARIOS --
CREATE SEQUENCE tipoUsuarios 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Tiempos, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_TIEMPOS --
CREATE SEQUENCE tiempos 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia TipoSucursales, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_TIPOSUCURSALES --
CREATE SEQUENCE tipoSucursales 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Especialidades, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_ESPECIALIDADES --
CREATE SEQUENCE especialidades 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Establecimientos, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_ESTABLECIMIENTOS --
CREATE SEQUENCE establecimientos 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Aseguradoras, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_ASEGURADORAS --
CREATE SEQUENCE aseguradoras 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Recetas, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_RECETAS --
CREATE SEQUENCE recetas 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Doctores, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_DOCTORES --
CREATE SEQUENCE doctores 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Sucursales, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_SUCURSALES --
CREATE SEQUENCE sucursales 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Cent5ros, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_CENTROS --
CREATE SEQUENCE centros 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Horarios, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_HORARIOS --
CREATE SEQUENCE horarios 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Servicios, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_SERVICIOS --
CREATE SEQUENCE serviciosMedicos
START WITH 1
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Seguros, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_SEGUROS --
CREATE SEQUENCE seguros 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Usuarios, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_USUARIOS --
CREATE SEQUENCE usuarios 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Reviews, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_REVIEWS --
CREATE SEQUENCE reviews 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Notis, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_NOTIS --
CREATE SEQUENCE notis 
START WITH 1 
INCREMENT BY 1;



/*************************************************************************************************

    ~ Creación de la secuencia Pacientes, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_PACIENTES --
CREATE SEQUENCE pacientes 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Expedientes, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_EXPEDIENTES --
CREATE SEQUENCE expedientes 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Citas, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_CITAS --
CREATE SEQUENCE citas 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Indicaciones, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_INDICACIONES --
CREATE SEQUENCE indicaciones 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ Creación de la secuencia Fichas, se irá incrementando de 1 en 1~

*************************************************************************************************/
-- SECUENCIA_FICHAS --
CREATE SEQUENCE fichas 
START WITH 1 
INCREMENT BY 1;


/*************************************************************************************************

    ~ CREACIÓN DE TRIGGERS ~

*************************************************************************************************/


/*************************************************************************************************

    ~ Creación del trigger TipoNoti ~

*************************************************************************************************/
-- TRIGGER_TIPO_NOTI --
CREATE OR REPLACE TRIGGER Trigger_TipoNoti 
BEFORE INSERT ON tbTipoNotis 
FOR EACH ROW 
BEGIN 
    SELECT tipoNotis.NEXTVAL 
    INTO: NEW.ID_TipoNoti 
    FROM DUAL;
END Trigger_TipoNoti;
/

/*************************************************************************************************

    ~ Creación del trigger TipoUsuario ~

*************************************************************************************************/
-- TRIGGER_TIPO_USUARIO --
CREATE OR REPLACE TRIGGER Trigger_TipoUsuario 
BEFORE INSERT ON tbTipoUsuarios 
FOR EACH ROW 
BEGIN 
    SELECT tipoUsuarios.NEXTVAL 
    INTO: NEW.ID_TipoUsuario 
    FROM DUAL;
END Trigger_TipoUsuario;
/


/*************************************************************************************************

    ~ Creación del trigger Tiempo ~

*************************************************************************************************/
-- TRIGGER_TIEMPO --
CREATE OR REPLACE TRIGGER Trigger_Tiempo 
BEFORE INSERT ON tbTiempos 
FOR EACH ROW 
BEGIN 
    SELECT tiempos.NEXTVAL 
    INTO: NEW.ID_Tiempo 
    FROM DUAL;
END Trigger_Tiempo;
/


/*************************************************************************************************

    ~ Creación del trigger Sucursal ~

*************************************************************************************************/
-- TRIGGER_TIPO_SUCURSAL --
CREATE OR REPLACE TRIGGER Trigger_TipoSucursal 
BEFORE INSERT ON tbTipoSucursales 
FOR EACH ROW 
BEGIN 
    SELECT tipoSucursales.NEXTVAL 
    INTO: NEW.ID_TipoSucursal 
    FROM DUAL;
END Trigger_TipoSucursal;
/


/*************************************************************************************************

    ~ Creación del trigger Especialidad ~

*************************************************************************************************/
-- TRIGGER_ESPECIALIDAD --
CREATE OR REPLACE TRIGGER Trigger_Especialidad 
BEFORE INSERT ON tbEspecialidades 
FOR EACH ROW 
BEGIN 
    SELECT especialidades.NEXTVAL 
    INTO: NEW.ID_Especialidad 
    FROM DUAL;
END Trigger_Especialidad;
/


/*************************************************************************************************

    ~ Creación del trigger Establecimiento ~

*************************************************************************************************/
-- TRIGGER_ESTABLECIMIENTO --
CREATE OR REPLACE TRIGGER Trigger_Establecimiento 
BEFORE INSERT ON tbEstablecimientos 
FOR EACH ROW 
BEGIN 
    SELECT establecimientos.NEXTVAL 
    INTO: NEW.ID_Establecimiento 
    FROM DUAL;
END Trigger_Establecimiento;
/


/*************************************************************************************************

    ~ Creación del trigger Aseguradora ~

*************************************************************************************************/
-- TRIGGER_ASEGURADORA --
CREATE OR REPLACE TRIGGER Trigger_Aseguradora 
BEFORE INSERT ON tbAseguradoras 
FOR EACH ROW 
BEGIN 
    SELECT aseguradoras.NEXTVAL 
    INTO: NEW.ID_Aseguradora 
    FROM DUAL;
END Trigger_Aseguradora;
/


/*************************************************************************************************

    ~ Creación del trigger Receta ~

*************************************************************************************************/
-- TRIGGER_RECETA --
CREATE OR REPLACE TRIGGER Trigger_Receta 
BEFORE INSERT ON tbRecetas
FOR EACH ROW 
BEGIN 
    SELECT recetas.NEXTVAL 
    INTO: NEW.ID_Receta 
    FROM DUAL;
END Trigger_Receta;
/


/*************************************************************************************************

    ~ Creación del trigger Doctor ~

*************************************************************************************************/
-- TRIGGER_DOCTOR --
CREATE OR REPLACE TRIGGER Trigger_Doctor 
BEFORE INSERT ON tbDoctores 
FOR EACH ROW 
BEGIN 
    SELECT doctores.NEXTVAL 
    INTO: NEW.ID_Doctor 
    FROM DUAL;
END Trigger_Doctor;
/


/*************************************************************************************************

    ~ Creación del trigger Sucursal ~

*************************************************************************************************/
-- TRIGGER_SUCURSAL --
CREATE OR REPLACE TRIGGER Trigger_Sucursal 
BEFORE INSERT ON tbSucursales 
FOR EACH ROW 
BEGIN 
    SELECT sucursales.NEXTVAL 
    INTO: NEW.ID_Sucursal 
    FROM DUAL;
END Trigger_Sucursal;
/


/*************************************************************************************************

    ~ Creación del trigger Medico ~

*************************************************************************************************/
-- TRIGGER_CENTRO_MÉDICO --
CREATE OR REPLACE TRIGGER Trigger_CentroMedico 
BEFORE INSERT ON tbCentrosMedicos 
FOR EACH ROW 
BEGIN 
    SELECT centros.NEXTVAL 
    INTO: NEW.ID_Centro 
    FROM DUAL;
END Trigger_CentroMedico;
/


/*************************************************************************************************

    ~ Creación del trigger Horario ~

*************************************************************************************************/
-- TRIGGER_HORARIO --
CREATE OR REPLACE TRIGGER Trigger_Horario 
BEFORE INSERT ON tbHorarios
FOR EACH ROW 
BEGIN 
    SELECT horarios.NEXTVAL 
    INTO: NEW.ID_Horario 
    FROM DUAL;
END Trigger_Horario;
/


/*************************************************************************************************

    ~ Creación del trigger Servicio ~

*************************************************************************************************/
-- TRIGGER_SERVICIO --
CREATE OR REPLACE TRIGGER Trigger_Servicio 
BEFORE INSERT ON tbServicios 
FOR EACH ROW 
BEGIN 
    SELECT serviciosMedicos.NEXTVAL 
    INTO: NEW.ID_Servicio 
    FROM DUAL;
END Trigger_Servicio;
/


/*************************************************************************************************

    ~ Creación del trigger Seguro ~

*************************************************************************************************/
-- TRIGGER_SEGURO --
CREATE OR REPLACE TRIGGER Trigger_Seguro 
BEFORE INSERT ON tbSeguros 
FOR EACH ROW 
BEGIN 
    SELECT seguros.NEXTVAL 
    INTO: NEW.ID_Seguro 
    FROM DUAL;
END Trigger_Seguro;
/


/*************************************************************************************************

    ~ Creación del trigger Usuario ~

*************************************************************************************************/
-- TRIGGER_USUARIO --
CREATE OR REPLACE TRIGGER Trigger_Usuario 
BEFORE INSERT ON tbUsuarios 
FOR EACH ROW 
BEGIN 
    SELECT usuarios.NEXTVAL 
    INTO: NEW.ID_Usuario 
    FROM DUAL;
END Trigger_Usuario;
/


/*************************************************************************************************

    ~ Creación del trigger Review ~

*************************************************************************************************/
-- TRIGGER_REVIEW --
CREATE OR REPLACE TRIGGER Trigger_Review 
BEFORE INSERT ON tbReviews 
FOR EACH ROW 
BEGIN 
    SELECT reviews.NEXTVAL 
    INTO: NEW.ID_Review 
    FROM DUAL;
END Trigger_Review;
/


/*************************************************************************************************

    ~ Creación del trigger Noti ~

*************************************************************************************************/
-- TRIGGER_NOTI --
CREATE OR REPLACE TRIGGER Trigger_Noti 
BEFORE INSERT ON tbNotis
FOR EACH ROW 
BEGIN 
    SELECT notis.NEXTVAL 
    INTO: NEW.ID_Notificacion 
    FROM DUAL;
END Trigger_Noti;
/


/*************************************************************************************************

    ~ Creación del trigger Paciente ~

*************************************************************************************************/
-- TRIGGER_PACIENTE --
CREATE OR REPLACE TRIGGER Trigger_Paciente 
BEFORE INSERT ON tbPacientes 
FOR EACH ROW 
BEGIN 
    SELECT pacientes.NEXTVAL 
    INTO: NEW.ID_Paciente 
    FROM DUAL;
END Trigger_Paciente;
/


/*************************************************************************************************

    ~ Creación del trigger Expediente ~

*************************************************************************************************/
-- TRIGGER_EXPEDIENTE --
CREATE OR REPLACE TRIGGER Trigger_Expediente 
BEFORE INSERT ON tbExpedientes 
FOR EACH ROW 
BEGIN 
    SELECT expedientes.NEXTVAL 
    INTO: NEW.ID_Expediente 
    FROM DUAL;
END Trigger_Expediente;
/


/*************************************************************************************************

    ~ Creación del trigger CitaMedica ~

*************************************************************************************************/
-- TRIGGER_CITA_MÉDICA --
CREATE OR REPLACE TRIGGER Trigger_CitaMedica 
BEFORE INSERT ON tbCitasMedicas 
FOR EACH ROW 
BEGIN 
    SELECT citas.NEXTVAL 
    INTO: NEW.ID_Cita 
    FROM DUAL;
END Trigger_CitaMedica;
/


/*************************************************************************************************

    ~ Creación del trigger Indicacion ~

*************************************************************************************************/
-- TRIGGER_INDICACIÓN --
CREATE OR REPLACE TRIGGER Trigger_Indicacion 
BEFORE INSERT ON tbIndicaciones 
FOR EACH ROW 
BEGIN 
    SELECT indicaciones.NEXTVAL 
    INTO: NEW.ID_Indicacion 
    FROM DUAL;
END Trigger_Indicacion;
/


/*************************************************************************************************

    ~ Creación del trigger Ficha ~

*************************************************************************************************/
-- TRIGGER_FICHA --
CREATE OR REPLACE TRIGGER Trigger_Ficha 
BEFORE INSERT ON tbFichasMedicas 
FOR EACH ROW 
BEGIN 
    SELECT fichas.NEXTVAL 
    INTO: NEW.ID_Ficha 
    FROM DUAL;
END Trigger_Ficha;
/

/*************************************************************************************************

   ~ INSERTS A CADA TABLA ~

*************************************************************************************************/


/*************************************************************************************************

   ~ Insertar valores a la tabla tbTipoNotis ~

*************************************************************************************************/
INSERT ALL
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Avisos')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Recordatorio')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Confirmación')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Configuración')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Recetas')
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbTipoUsuarios ~

*************************************************************************************************/
INSERT ALL
    INTO tbTipoUsuarios (nombreTipoUsuario)
         VALUES ('Administrador de Establecimientos')
    INTO tbTipoUsuarios (nombreTipoUsuario)
         VALUES ('Doctor')
    INTO tbTipoUsuarios (nombreTipoUsuario)
         VALUES ('Paciente')
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbTiempos ~

*************************************************************************************************/
INSERT ALL
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('1 Vez al día', '4')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('2 Veces al día', '3')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('3 Veces al día', '5')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('4 Veces al día', '2')
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbTipoSucursales ~

*************************************************************************************************/
INSERT ALL
    INTO tbTipoSucursales (nombreTipoSucursal)
         VALUES ('Clinica General')
    INTO tbTipoSucursales (nombreTipoSucursal)
         VALUES ('Clinica Especializada')
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbEspecialidades ~

*************************************************************************************************/
INSERT ALL
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Pediatrìa', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Neonatología', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Cardiología', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Ortopedia', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Odontología', '')
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbEspecialidades ~

*************************************************************************************************/
INSERT ALL
    INTO tbEstablecimientos (nombreClinica, imgPrincipal)
         VALUES ('Sonrisas', 'img.ClinicaSonrisas')
    INTO tbEstablecimientos (nombreClinica, imgPrincipal)
         VALUES ('Pronefro', 'img.ClinicaPronefro')
    INTO tbEstablecimientos (nombreClinica, imgPrincipal)
         VALUES ('Medicare', 'img.ClinicaMedicare')
    INTO tbEstablecimientos (nombreClinica, imgPrincipal)
         VALUES ('La Plus Belle', 'img.ClinicaLaPlusBelle')
    INTO tbEstablecimientos (nombreClinica, imgPrincipal)
         VALUES ('Ecomedic', 'img.ClinicaEcomedic')
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbAseguradoras ~

*************************************************************************************************/
INSERT ALL
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('MAPFRE')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('SISA')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('ACSA MED')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('ATLÁNTIDA VIDA')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('ASESUISA')
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbRecetas ~

*************************************************************************************************/
INSERT ALL
    INTO tbRecetas (fechaReceta, ubicacionPDF)
         VALUES ('26-04-2024', '')
    INTO tbRecetas (fechaReceta, ubicacionPDF)
         VALUES ('20-03-2023', '')
    INTO tbRecetas (fechaReceta, ubicacionPDF)
         VALUES ('24-05-2023', '')
    INTO tbRecetas (fechaReceta, ubicacionPDF)
         VALUES ('04-01-2024', '')
    INTO tbRecetas (fechaReceta, ubicacionPDF)
         VALUES ('23-05-2024', '')
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbUsuarios ~

*************************************************************************************************/
INSERT ALL
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Francisco', 'Mejía', 'fran@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'San Salvador', '6143-1352', 'M', '20/02/1980', NULL, 1)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Steven', 'Palacios', 'venosin@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Ciudad Arce', '2245-9312', 'M', '15/07/1999', NULL, 1)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Xavier', 'Torres', 'xam@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Ciudad Delgado', '1292-1275', 'F', '11/01/2007', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Dennis', 'Alexander', 'darv@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Villa Olimpica', '6294-0283', 'M', '20/02/2000', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Hector', 'Gallardo', 'hector@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'La Paz', '8723-1293', 'M', '25/08/2000', NULL, 1)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbSeguros ~

*************************************************************************************************/
INSERT ALL
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('TOEWQ12', 'PRIMER2', 1, 1)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('ABCD1234', 'POLIZA1', 2, 2)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('EFGH5678', 'POLIZA2', 3, 4)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('IJKL9101', 'POLIZA3', 4, 3)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('MNOP2345', 'POLIZA4', 5, 5)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbSucursales ~

*************************************************************************************************/
INSERT ALL
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Ginecológica, San Salvador', 235656, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia Médica, San Salvador', 'ubicacionSucur.Ginecologica', '7589-4365', 'Esta sucursal no posee una fotografía', 1, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Asistencial Salvadoreña, Santa Ana', 675429, 'clinica_asistencial@gmail.com', '2256-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 'ubicacionSucur.Asistencial', '5383-4365', 'Esta sucursal no tiene una fotografía', 5, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital de Diagnóstico, San Salvador', 990764, 'hospital_diagnostico@gmail.com', '2224-7887', '79 Av. Norte y 11 Calle Poniente, Colonia Escalón, San Salvador', 'ubicacionSucur.Diagnostico', '7519-2335', 'Esta sucursal ha puesto una fotografía', 3, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Centro Médico Escalón, San Salvador', 224216, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan José Cañas, Colonia Escalón, San Salvador', 'ubicacionSucur.Escalon', '7509-3230', 'Neles', 4, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital La Divina Providencia, San Salvador', 012483, 'divina_providencia@gmail.com', '2211-2956', 'Avenida Masferrer Norte, Colonia Escalón, San Salvador', 'ubicacionSucur.Providencia', '3278-3561', 'Tampoco tu', 2, 2)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbIndicaciones ~

*************************************************************************************************/
INSERT ALL
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), '1 tableta', 'Paracetamol', 'Tomar después de las comidas', 1, 1)
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-02 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '2 cucharadas', 'Ibuprofeno', 'Tomar con agua', 2, 2)
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-03 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), '5 ml', 'Amoxicilina', 'Tomar cada 8 horas', 3, 3)
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-04 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), '1 cápsula', 'Omeprazol', 'Tomar antes de dormir', 4, 4)
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-05 07:00:00', 'YYYY-MM-DD HH24:MI:SS'), '10 gotas', 'Clorfenamina', 'Tomar en la mañana y noche', 5, 4)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbDoctores ~

*************************************************************************************************/
INSERT ALL
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario)
         VALUES ('JVPM12345', 1, 5)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario)
         VALUES ('JVPM67890', 2, 4)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario)
         VALUES ('JVPM23456', 3, 3)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario)
         VALUES ('JVPM78901', 4, 2)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario)
         VALUES ('JVPM34567', 5, 1)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbCentrosMedicos ~

*************************************************************************************************/
INSERT ALL
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('T', 5, 1)
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 4, 2)
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 3, 3)
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('T', 2, 4)
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 1, 5)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbHorarios ~

*************************************************************************************************/
INSERT ALL
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-18 07:00:00.000000', TIMESTAMP '2024-06-18 19:00:00.000000', TO_DATE('2024-06-18', 'YYYY-MM-DD'), TO_DATE('2024-06-17', 'YYYY-MM-DD'), TIMESTAMP '2024-06-18 12:00:00.000000', TO_DATE('2024-06-16', 'YYYY-MM-DD'), 1, 5)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-19 07:00:00.000000', TIMESTAMP '2024-06-19 19:00:00.000000', TO_DATE('2024-06-19', 'YYYY-MM-DD'), TO_DATE('2024-06-10', 'YYYY-MM-DD'), TIMESTAMP '2024-06-19 12:00:00.000000', TO_DATE('2024-06-10', 'YYYY-MM-DD'), 2, 4)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-20 07:00:00.000000', TIMESTAMP '2024-06-20 19:00:00.000000', TO_DATE('2024-06-20', 'YYYY-MM-DD'), TO_DATE('2024-06-14', 'YYYY-MM-DD'), TIMESTAMP '2024-06-20 12:00:00.000000', TO_DATE('2024-06-14', 'YYYY-MM-DD'), 3, 3)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-21 07:00:00.000000', TIMESTAMP '2024-06-21 19:00:00.000000', TO_DATE('2024-06-21', 'YYYY-MM-DD'), TO_DATE('2024-06-11', 'YYYY-MM-DD'), TIMESTAMP '2024-06-21 12:00:00.000000', TO_DATE('2024-06-11', 'YYYY-MM-DD'), 4, 2)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-22 07:00:00.000000', TIMESTAMP '2024-06-22 19:00:00.000000', TO_DATE('2024-06-22', 'YYYY-MM-DD'), TO_DATE('2024-06-15', 'YYYY-MM-DD'), TIMESTAMP '2024-06-22 12:00:00.000000', TO_DATE('2024-06-15', 'YYYY-MM-DD'), 5, 1)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbPacientes ~

*************************************************************************************************/
INSERT ALL
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES (1, 'Juan', 'Perez', NULL, 'Padre', 1)
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES (2, 'Maria', 'Garcia', NULL, 'Madre', 2)
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES (3, 'Carlos', 'Lopez', NULL, 'Hijo', 3)
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES (4, 'Ana', 'Martinez', NULL, 'Hija', 4)
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES (5, 'Luis', 'Sanchez', NULL, 'Hermano', 5)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbCitasMedicas ~

*************************************************************************************************/
INSERT ALL
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (1, TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general', 5, 1)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (2, TO_DATE('2023-01-02', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Revisión anual', 4, 2)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (3, TO_DATE('2023-01-03', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta de seguimiento', 3, 3)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (4, TO_DATE('2023-01-04', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-04 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general', 2, 4)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (5, TO_DATE('2023-01-05', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta especializada', 1, 5)
SELECT DUMMY FROM DUAL;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbNotis ~

*************************************************************************************************/
INSERT ALL
    INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (1, TO_DATE('2024-07-14', 'YYYY-MM-DD'), 'A', 'Este es un aviso importante.', 'S', 1, 1)

    INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (2, TO_DATE('2024-07-15', 'YYYY-MM-DD'), 'R', 'Recordatorio de cita para mañana.', 'S', 1, 2)

    INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (3, TO_DATE('2024-07-16', 'YYYY-MM-DD'), 'C', 'Confirmación de cita exitosa.', 'S', 1, 3)

    INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (4, TO_DATE('2024-07-17', 'YYYY-MM-DD'), 'P', 'Receta médica disponible.', 'S', 1, 5)
SELECT * FROM dual;


/*************************************************************************************************

   ~ Insertar valores a la tabla tbServicios ~

*************************************************************************************************/
INSERT ALL
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Limpieza Bucal', 40.00, 1, 1)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Chequeo General', 30.00, 2, 2)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Examen Visual', 45.00, 3, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Blanqueamiento Dental', 60.00, 4, 4)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Terapia Cognitiva', 55.00, 5, 5)
SELECT DUMMY FROM DUAL;

COMMIT;

/*****************************************************************************

              --Tablas prueba BETA--

*****************************************************************************/
/*
INSERT ALL
    INTO tbMotivo (motivo)
         VALUES ('Fiebre')
    INTO tbMotivo (motivo)
         VALUES ('Dolor abdominal')
    INTO tbMotivo (motivo)
         VALUES ('Dolor de cabeza')
    INTO tbMotivo (motivo)
         VALUES ('Heridas')
    INTO tbMotivo (motivo)
         VALUES ('Revisión de rutina')
SELECT DUMMY FROM DUAL;
*/

/*************************************************************************************************

    ~ Consultas Inner ~

*************************************************************************************************/
--INNER JOIN CENTROMEDICO
SELECT
    u.nombreUsuario,
    u.apellidoUsuario,
    u.imgUsuario,
    s.nombreSucursal,
    s.telefonoSucur ,
    s.direccionSucur ,
    s.ubicacionSucur ,
    srv.nombreServicio,
    srv.costo,
    cm.favorito
FROM
    tbCentrosMedicos cm
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
INNER JOIN
    tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
INNER JOIN
    tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
INNER JOIN
    tbServicios srv ON cm.ID_Centro = srv.ID_Centro;

--INNER JOIN CITASMEDICAS--

SELECT
    citas.ID_Cita,
    citas.diacita,
    citas.horacita,
    citas.motivo,
    citas.id_centro,
    citas.id_paciente,
    pacs.nombrepaciente,
    pacs.parentesco,
    usua.id_usuario,
    USUA.nombreUsuario,
    USUA.apellidoUsuario,
    esp.nombreespecialidad
FROM  tbcitasmedicas CITAS
    INNER JOIN
        tbcentrosmedicos CENTROS ON CITAS.id_centro=CENTROS.id_centro
    INNER JOIN
        tbdoctores DOCS ON CENTROS.id_doctor=DOCS.id_doctor
    INNER JOIN
        tbEspecialidades ESP ON docs.id_especialidad = esp.id_especialidad
    INNER JOIN
        tbUsuarios USUA ON DOCS.id_usuario = USUA.id_usuario
    INNER JOIN
        tbpacientes PACS ON citas.id_paciente = pacs.id_paciente WHERE pacs.id_usuario = 1

/*************************************************************************************************

    ~ Eliminar todos los Triggers ~

*************************************************************************************************/
/*
DROP TRIGGER Trigger_TipoUsuario;
DROP TRIGGER Trigger_Tiempo;
DROP TRIGGER Trigger_TipoSucursal;
DROP TRIGGER Trigger_Especialidad;
DROP TRIGGER Trigger_Establecimiento;
DROP TRIGGER Trigger_Aseguradora;
DROP TRIGGER Trigger_Receta;
DROP TRIGGER Trigger_Doctor;
DROP TRIGGER Trigger_Sucursal;
DROP TRIGGER Trigger_CentroMedico;
DROP TRIGGER Trigger_Horario;
DROP TRIGGER Trigger_Seguro;
DROP TRIGGER Trigger_Usuario;
DROP TRIGGER Trigger_Review;
DROP TRIGGER Trigger_Noti;
DROP TRIGGER Trigger_Paciente;
DROP TRIGGER Trigger_Expediente;
DROP TRIGGER Trigger_CitaMedica;
DROP TRIGGER Trigger_Indicacion;
DROP TRIGGER Trigger_Ficha;
*/

/*************************************************************************************************

    ~ Consultas Inner Extras~

*************************************************************************************************/
/*

SELECT 
    u.nombreUsuario, 
    u.apellidoUsuario, 
    u.imgUsuario, 
	e.nombreEspecialidad,
    s.nombreSucursal,
    s.telefonoSucur, 
    s.direccionSucur, 
    s.ubicacionSucur, 
    srv.nombreServicio, 
    srv.costo, 
    cm.favorito
FROM 
    tbCentrosMedicos cm
INNER JOIN 
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
INNER JOIN 
    tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
INNER JOIN
	tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
INNER JOIN
    tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
INNER JOIN 
    tbServicios srv ON cm.ID_Centro = srv.ID_Centro
WHERE 
    u.ID_Usuario IS NOT NULL;

SELECT citas.ID_Cita,
    citas.diacita,
    citas.horacita,
    citas.motivo,
    citas.id_centro,
    citas.id_paciente,
    pacs.nombrepaciente,
    pacs.parentesco,
    usua.id_usuario,
    USUA.nombreUsuario,
    USUA.apellidoUsuario,
    esp.nombreespecialidad 
FROM  tbcitasmedicas CITAS
    INNER JOIN tbcentrosmedicos CENTROS ON CITAS.id_centro=CENTROS.id_centro
    INNER JOIN tbdoctores DOCS ON CENTROS.id_doctor=DOCS.id_doctor
    INNER JOIN tbEspecialidades ESP ON docs.id_especialidad = esp.id_especialidad
    INNER JOIN tbUsuarios USUA ON DOCS.id_usuario = USUA.id_usuario
    INNER JOIN tbpacientes PACS ON citas.id_paciente = pacs.id_paciente 
        WHERE pacs.id_usuario = 1

*/
