/*******************************************************************************

    ~ Creacion del usuario de DeltaMed ~

*******************************************************************************/

/*
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
CREATE USER DeltaMed IDENTIFIED BY "deltaTeam1";
GRANT "CONNECT" TO DeltaMed;

ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';
COMMIT;

SET SERVEROUTPUT ON;
*/

/*******************************************************************************

    ~ ELIMINACI�N DE TABLAS EXISTENTES ~

*******************************************************************************/
--Este procedimiento PL/SQL ejecuta el comando DROP TABLE, pero si ocurre un error
--y si ese error es que la tabla no existe, lo ignora y continua la ejecuci�n.
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
    EXECUTE IMMEDIATE 'DROP TABLE tbAuditorias CASCADE CONSTRAINTS';
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
    EXECUTE IMMEDIATE 'DROP TABLE tbFavoritos CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE tbRecientes CASCADE CONSTRAINTS';
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

    ~ ELIMINACI?N DE SECUENCIAS EXISTENTES ~

*******************************************************************************/
--Este procedimiento PL/SQL ejecuta el comando DROP SEQCUENCE, pero si ocurre un error
--y si ese error es que la sequence no existe, lo ignora y contin�a la ejecuci�n.
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
    EXECUTE IMMEDIATE 'DROP SEQUENCE auditoria';
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
    EXECUTE IMMEDIATE 'DROP SEQUENCE favorito';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE reciente';
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

    ~ CREACI�N DE TABLAS INDEPENDIENTES ~

*******************************************************************************/

CREATE TABLE tbTipoNotis (
    ID_TipoNoti INT PRIMARY KEY,
    nombreTipoNoti VARCHAR2(25)
);

CREATE TABLE tbTipoUsuarios (
    ID_TipoUsuario INT PRIMARY KEY,
    nombreTipoUsuario VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE tbTiempos (
    ID_Tiempo INT PRIMARY KEY,
    lapsosTiempo VARCHAR2(50) NOT NULL,
    frecuenciaMedi VARCHAR2(50) NOT NULL
);

CREATE TABLE tbTipoSucursales (
    ID_TipoSucursal INT PRIMARY KEY,
    nombreTipoSucursal VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE tbEspecialidades (
    ID_Especialidad INT PRIMARY KEY,
    nombreEspecialidad VARCHAR2(60) NOT NULL UNIQUE,
    nuevaEspecialidad VARCHAR2(60) UNIQUE
);

CREATE TABLE tbEstablecimientos (
    ID_Establecimiento INT PRIMARY KEY,
    nombreClinica VARCHAR2(50) NOT NULL UNIQUE,
    imgPrincipal VARCHAR2(256) NOT NULL UNIQUE
);

CREATE TABLE tbAseguradoras (
    ID_Aseguradora INT PRIMARY KEY,
    nombreAseguradora VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE tbRecetas (
    ID_Receta INT PRIMARY KEY,
    fechaReceta DATE NOT NULL,
    ubicacionPDF VARCHAR2(250)
);

CREATE TABLE tbAuditorias (
    ID_Auditoria NUMBER PRIMARY KEY,
    nombreCompleto VARCHAR2(100) NOT NULL,
    emailUsuario VARCHAR2(50) NOT NULL,
    telefonoUsuario VARCHAR2(9) NOT NULL,
    acci�n VARCHAR2(20) DEFAULT('Elimin� su cuenta.') NOT NULL
);

/*************************************************************************************************

    ~ CREACI�N DE TABLAS DEPENDIENTES ~

*************************************************************************************************/

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

CREATE TABLE tbSucursales (
    ID_Sucursal INT PRIMARY KEY,
    nombreSucursal VARCHAR2(60) NOT NULL,
    codSucursal NUMBER(8) NOT NULL UNIQUE,
    emailSucur VARCHAR2(30) NOT NULL UNIQUE,
    telefonoSucur VARCHAR2(9) NOT NULL UNIQUE,
    direccionSucur VARCHAR2(200) NOT NULL UNIQUE,
    latiSucur NUMBER(15,10) NOT NULL,
    longSucur NUMBER(15,10) NOT NULL,
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

CREATE TABLE tbDoctores (
    ID_Doctor INT PRIMARY KEY,
    codProfesional VARCHAR2(12) NOT NULL,
    ID_Especialidad INT NOT NULL,
    ID_Usuario INT NOT NULL,
    ID_Sucursal INT NOT NULL,
    --CONSTRAINTS------------------
    CONSTRAINT FK_Especialidad FOREIGN KEY (ID_Especialidad)
    REFERENCES tbEspecialidades(ID_Especialidad)
    ON DELETE CASCADE,

    CONSTRAINT FK_Usuario_Doctor FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE,

    CONSTRAINT FK_Sucursal_Doctor FOREIGN KEY (ID_Sucursal)
    REFERENCES tbSucursales(ID_Sucursal)
    ON DELETE CASCADE
);

CREATE TABLE tbFavoritos (
    ID_Favorito INT PRIMARY KEY,
    ID_Sucursal INT NOT NULL,
    ID_Usuario INT NOT NULL,
    ID_Doctor INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_SucursalFav FOREIGN KEY (ID_Sucursal)
    REFERENCES tbSucursales(ID_Sucursal)
    ON DELETE CASCADE,

    CONSTRAINT FK_UsuarioFav FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE,

    CONSTRAINT FK_DoctorF FOREIGN KEY (ID_Doctor)
    REFERENCES tbDoctores(ID_Doctor)
    ON DELETE CASCADE
);

CREATE TABLE tbRecientes (
    ID_Reciente INT PRIMARY KEY,
    ID_Sucursal INT NOT NULL,
    ID_Usuario INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_SucursalRec FOREIGN KEY (ID_Sucursal)
    REFERENCES tbSucursales(ID_Sucursal)
    ON DELETE CASCADE,

    CONSTRAINT FK_UsuarioRec FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);

CREATE TABLE tbCentrosMedicos (
    ID_Centro INT PRIMARY KEY,
    ID_Doctor INT NOT NULL UNIQUE,
    ID_Sucursal INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Doctor FOREIGN KEY (ID_Doctor)
    REFERENCES tbDoctores(ID_Doctor)
    ON DELETE CASCADE,

    CONSTRAINT FK_Sucursal FOREIGN KEY (ID_Sucursal) REFERENCES tbSucursales(ID_Sucursal)
    ON DELETE CASCADE
);

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

CREATE TABLE tbReviews (
    ID_Review INT PRIMARY KEY,
    promEstrellas NUMBER(5) NOT NULL,
    comentario VARCHAR2(200),
    ID_Doctor INT NOT NULL,
    ID_Usuario INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Doctor_Review FOREIGN KEY (ID_Doctor)
    REFERENCES tbDoctores(ID_Doctor)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Usuario_Review FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);

SELECT
    

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

CREATE TABLE tbCitasMedicas (
    ID_Cita INT PRIMARY KEY,
    diaCita DATE NOT NULL,
    estadoCita CHAR(1) CHECK (estadoCita in ('C', 'A')) NOT NULL,
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

    ~ CREACI�N DE SECUENCIAS ~

*************************************************************************************************/

-- SECUENCIA_TIPONOTI --
CREATE SEQUENCE tipoNotis
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_TIPOUSUARIOS --
CREATE SEQUENCE tipoUsuarios
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_TIEMPOS --
CREATE SEQUENCE tiempos
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_TIPOSUCURSALES --
CREATE SEQUENCE tipoSucursales
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_ESPECIALIDADES --
CREATE SEQUENCE especialidades
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_ESTABLECIMIENTOS --
CREATE SEQUENCE establecimientos
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_ASEGURADORAS --
CREATE SEQUENCE aseguradoras
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_RECETAS --
CREATE SEQUENCE recetas
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_DOCTORES --
CREATE SEQUENCE doctores
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_SUCURSALES --
CREATE SEQUENCE sucursales
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_CENTROS --
CREATE SEQUENCE centros
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_HORARIOS --
CREATE SEQUENCE horarios
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_SERVICIOS --
CREATE SEQUENCE serviciosMedicos
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_SEGUROS --
CREATE SEQUENCE seguros
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_AUDITOR�A -
CREATE SEQUENCE auditoria
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_USUARIOS --
CREATE SEQUENCE usuarios
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_FAVORITOS --
CREATE SEQUENCE favorito
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_RECIENTES --
CREATE SEQUENCE reciente
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_REVIEWS --
CREATE SEQUENCE reviews
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_NOTIS --
CREATE SEQUENCE notis
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_PACIENTES --
CREATE SEQUENCE pacientes
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_EXPEDIENTES --
CREATE SEQUENCE expedientes
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_CITAS --
CREATE SEQUENCE citas
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_INDICACIONES --
CREATE SEQUENCE indicaciones
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_FICHAS --
CREATE SEQUENCE fichas
START WITH 1
INCREMENT BY 1;


/*************************************************************************************************

    ~ CREACI?N DE TRIGGERS ~

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

-- TRIGGER_CENTRO_MEDICO --
CREATE OR REPLACE TRIGGER Trigger_CentroMedico
BEFORE INSERT ON tbCentrosMedicos
FOR EACH ROW
BEGIN
    SELECT centros.NEXTVAL
    INTO: NEW.ID_Centro
    FROM DUAL;
END Trigger_CentroMedico;
/

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

-- TRIGGER_FAVORITO --
CREATE OR REPLACE TRIGGER Tigger_Favorito
BEFORE INSERT ON tbFavoritos
FOR EACH ROW
BEGIN
    SELECT favorito.NEXTVAL
    INTO: NEW.ID_Favorito
    FROM DUAL;
END Tigger_Favorito;
/

-- TRIGGER_RECIENTE --
CREATE OR REPLACE TRIGGER Tigger_Reciente
BEFORE INSERT ON tbRecientes
FOR EACH ROW
BEGIN
    SELECT reciente.NEXTVAL
    INTO: NEW.ID_Reciente
    FROM DUAL;
END Tigger_Reciente;
/

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

-- TRIGGER_CITA_M?DICA --
CREATE OR REPLACE TRIGGER Trigger_CitaMedica
BEFORE INSERT ON tbCitasMedicas
FOR EACH ROW
BEGIN
    SELECT citas.NEXTVAL
    INTO: NEW.ID_Cita
    FROM DUAL;
END Trigger_CitaMedica;
/

-- TRIGGER_INDICACI?N --
CREATE OR REPLACE TRIGGER Trigger_Indicacion
BEFORE INSERT ON tbIndicaciones
FOR EACH ROW
BEGIN
    SELECT indicaciones.NEXTVAL
    INTO: NEW.ID_Indicacion
    FROM DUAL;
END Trigger_Indicacion;
/

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

~ TRIGGER PARA TABLA AUDITOR�A ~

*************************************************************************************************/
--Este trigger se ejecuta antes de eliminar un usuario, lo que hace es guardarlo dentro de tbAuditor�a
CREATE OR REPLACE TRIGGER Trigger_INST_Auditoria
BEFORE DELETE ON tbUsuarios
FOR EACH ROW
DECLARE
    var_nombre tbUsuarios.nombreUsuario%TYPE;
    var_apellido tbUsuarios.apellidoUsuario%TYPE;
    var_nombreComp tbAuditorias.nombreCompleto%TYPE;
    var_tel tbAuditorias.telefonoUsuario%TYPE;
BEGIN
    var_nombre := :OLD.nombreUsuario;
   
    var_apellido := :OLD.apellidoUsuario;
    
    var_tel := :OLD.telefonoUsuario;
    
    var_nombreComp := var_nombre || ' ' || var_apellido;
    
    INSERT INTO tbAuditorias (ID_Auditoria, nombreCompleto, emailUsuario, telefonoUsuario)
    VALUES (auditoria.NEXTVAL, var_nombreComp, :OLD.emailUsuario, var_tel);
    
    DBMS_OUTPUT.PUT_LINE('Guardado de seguridad, usuario eliminado');
END Trigger_INST_Auditoria;
/

/*************************************************************************************************

~ PROCEDURE PARA RECIENTES ~

*************************************************************************************************/
CREATE OR REPLACE PROCEDURE PROC_STATE_VALIDATION_RECIENTES(var_ID_Usuario IN INT, var_ID_Sucursal IN INT) AS
    v_count INT;
BEGIN
    -- Elimina cualquier registro existente para el mismo ID_Usuario y ID_Sucursal
    DELETE FROM tbRecientes
    WHERE ID_Usuario = var_ID_Usuario
      AND ID_Sucursal = var_ID_Sucursal;

    -- Contar el n�mero de registros existentes para el usuario
    SELECT COUNT(*)
    INTO v_count
    FROM tbRecientes
    WHERE ID_Usuario = var_ID_Usuario;

    -- Si hay 19 registros, eliminar el m?s antiguo para dejar espacio al nuevo
    IF v_count > 19 THEN
        DELETE FROM tbRecientes
        WHERE ID_Usuario = var_ID_Usuario
          AND ID_Sucursal IN (
              SELECT ID_Sucursal
              FROM (
                  SELECT ID_Sucursal
                  FROM tbRecientes
                  WHERE ID_Usuario = var_ID_Usuario
                  ORDER BY ROWNUM  -- Orden natural de inserci?n
              )
              WHERE ROWNUM = 1  -- Selecciona solo la fila m?s antigua
          );
    END IF;

    -- Insertar el nuevo registro
    INSERT INTO tbRecientes (ID_Usuario, ID_Sucursal)
    VALUES (var_ID_Usuario, var_ID_Sucursal);
    COMMIT WORK;
END;
/

/*************************************************************************************************

~ INSERTS A CADA TABLA ~

*************************************************************************************************/

INSERT ALL
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Avisos')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Recordatorio')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Confirmaci?n')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Configuraci?n')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Recetas')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbTipoUsuarios (nombreTipoUsuario)
         VALUES ('Administrador de Establecimientos')
    INTO tbTipoUsuarios (nombreTipoUsuario)
         VALUES ('Doctor')
    INTO tbTipoUsuarios (nombreTipoUsuario)
         VALUES ('Paciente')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('1 Vez al d�a', '4')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('2 Veces al d�a', '3')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('3 Veces al d�a', '5')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('4 Veces al d�a', '2')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbTipoSucursales (nombreTipoSucursal)
         VALUES ('Clinica General')
    INTO tbTipoSucursales (nombreTipoSucursal)
         VALUES ('Clinica Especializada')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Pediatr�a', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Neonatolog�a', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Cardiolog�a', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Ortopedia', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Odontolog�a', '')
SELECT DUMMY FROM DUAL;

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

INSERT ALL
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('Ninguno')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('MAPFRE')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('SISA')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('ACSA MED')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('ATL�NTIDA VIDA')
    INTO tbAseguradoras (nombreAseguradora)
         VALUES ('ASESUISA')
SELECT DUMMY FROM DUAL;

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

INSERT ALL
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Francisco', 'Mej�a', 'fran@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'San Salvador', '6143-1352', 'M', '20/02/1980', NULL, 1)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Steven', 'Palacios', 'venosin@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Ciudad Arce', '2245-9312', 'M', '15/07/1999', NULL, 1)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Xavier', 'Torres', 'xam@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Ciudad Delgado', '1292-1275', 'F', '11/01/2007', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Dennis', 'Alexander', 'darv@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Villa Olimpica', '6294-0283', 'M', '20/02/2000', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Hector', 'Gallardo', 'hector@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'La Paz', '8723-1293', 'M', '25/08/2000', NULL, 1)
SELECT DUMMY FROM DUAL;

UPDATE tbUsuarios SET imgUsuario = 'https://us.123rf.com/450wm/antoniodiaz/antoniodiaz1510/antoniodiaz151000120/47228952-apuesto-joven-m%C3%A9dico-con-una-bata-de-laboratorio-y-un-estetoscopio-con-un-tablet-pc-para-comprobar.jpg' WHERE ID_Usuario = 3;
UPDATE tbUsuarios SET imgUsuario = 'https://img.freepik.com/fotos-premium/medico-sexo-masculino-bata-laboratorio-estetoscopio-brazos-cruzados-pie-pasillo-hospital_752325-3492.jpg' WHERE ID_Usuario = 4;

INSERT ALL
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('TOEWQ12', 'PRIMER2', 1, 1)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('ABCD1234', 'POLIZA1', 2, 2)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('EFGH5678', 'POLIZA2', 3, 4)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('IJKL9101', 'POLIZA3', 4, 3)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('MNOP2345', 'POLIZA4', 5, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Cl�nica Ginecol�gica', 235656, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia M?dica, San Salvador', 13.709362, -89.202990, '7589-4365', 'Esta sucursal no posee una fotograf?a', 1, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Cl�nica Asistencial Salvadore�a', 675429, 'clinica_asistencial@gmail.com', '2256-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 13.714547, -89.192849, '5383-4365', 'Esta sucursal no tiene una fotograf?a', 5, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital de Diagn�stico', 990764, 'hospital_diagnostico@gmail.com', '2224-7887', '79 Av. Norte y 11 Calle Poniente, Colonia Escal�n, San Salvador', 13.710252 , -89.202537, '7519-2335', 'Esta sucursal ha puesto una fotograf?a', 3, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Centro M�dico Escal�n', 224216, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan Jos� Ca�as, Colonia Escal�n, San Salvador', 13.711853, -89.234307, '7509-3230', 'Neles', 4, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital La Divina Providencia', 012483, 'divina_providencia@gmail.com', '2211-2956', 'Avenida Masferrer Norte, Colonia Escal?n, San Salvador', 13.711245, -89.223008, '3278-3561', 'Tampoco tu', 2, 2)
SELECT DUMMY FROM DUAL;

UPDATE tbSucursales SET imgSucursal = 'https://centroginecologico.com.sv/wp-content/uploads/2020/10/facahadanew20.jpg' WHERE ID_Sucursal = 1;
UPDATE tbSucursales SET imgSucursal = 'https://upload.wikimedia.org/wikipedia/commons/b/b3/Hospital_Nacional_de_Ni%C3%B1os_Benjamin_Bloom.jpg' WHERE ID_Sucursal = 2;
UPDATE tbSucursales SET imgSucursal = 'https://www.hospitaldiagnostico.com/images/sucursal/Escalon.png' WHERE ID_Sucursal = 3;
UPDATE tbSucursales SET imgSucursal = 'https://static.elmundo.sv/wp-content/uploads/2020/10/Centro-Me%CC%81dico-Escalo%CC%81n.jpg' WHERE ID_Sucursal = 4;
UPDATE tbSucursales SET imgSucursal = 'https://www.ecured.cu/images/e/e3/Hospita_Divina_Providencia_1.jpg' WHERE ID_Sucursal = 5;

INSERT ALL
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), '1 tableta', 'Paracetamol', 'Tomar despu?s de las comidas', 1, 1)
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-02 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '2 cucharadas', 'Ibuprofeno', 'Tomar con agua', 2, 2)
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-03 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), '5 ml', 'Amoxicilina', 'Tomar cada 8 horas', 3, 3)
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-04 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), '1 c?psula', 'Omeprazol', 'Tomar antes de dormir', 4, 4)
    INTO tbIndicaciones (duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_TIMESTAMP('2023-06-05 07:00:00', 'YYYY-MM-DD HH24:MI:SS'), '10 gotas', 'Clorfenamina', 'Tomar en la ma?ana y noche', 5, 4)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario,ID_Sucursal)
         VALUES ('JVPM12345', 1, 5,1)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM67890', 2, 4,1)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM23456', 3, 3,2)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM78901', 4, 2,3)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM34567', 5, 1,4)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbCentrosMedicos (ID_Doctor, ID_Sucursal)
         VALUES (5, 1)
    INTO tbCentrosMedicos (ID_Doctor, ID_Sucursal)
         VALUES (4, 2)
    INTO tbCentrosMedicos (ID_Doctor, ID_Sucursal)
         VALUES (3, 3)
    INTO tbCentrosMedicos (ID_Doctor, ID_Sucursal)
         VALUES (2, 4)
    INTO tbCentrosMedicos (ID_Doctor, ID_Sucursal)
         VALUES (1, 5)
SELECT DUMMY FROM DUAL;

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

INSERT ALL
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (1, TO_DATE('2024-10-01', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general','A', 5, 1)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (2, TO_DATE('2024-10-02', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Revisi?n anual','A', 4, 2)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (3, TO_DATE('2024-10-03', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta de seguimiento','A', 3, 3)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (4, TO_DATE('2024-10-04', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-04 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general','A', 2, 4)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (5, TO_DATE('2024-10-05', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta especializada','A', 1, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (1, TO_DATE('2024-07-14', 'YYYY-MM-DD'), 'A', 'Cita cancelada para ma�ana a las 2:00pm', 'S', 1, 1)

    INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (2, TO_DATE('2024-07-15', 'YYYY-MM-DD'), 'R', 'Recuerda tomar tu medicina a las 4:00pm', 'S', 1, 2)

    INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (3, TO_DATE('2024-07-16', 'YYYY-MM-DD'), 'C', 'Confirma tu cita con Dra. Luz Mar�a', 'S', 1, 3)

    INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (4, TO_DATE('2024-07-17', 'YYYY-MM-DD'), 'P', 'Receta 17/05/2024', 'S', 1, 5)
SELECT * FROM dual;

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
    
INSERT ALL
    INTO TBFAVORITOS(ID_Sucursal, ID_Usuario, ID_Doctor)
    VALUES (1,1,2)
    INTO TBFAVORITOS(ID_Sucursal, ID_Usuario, ID_Doctor)
    VALUES (2,1,3)
    INTO TBFAVORITOS(ID_Sucursal, ID_Usuario, ID_Doctor)
    VALUES (3,1,4)
    INTO TBFAVORITOS(ID_Sucursal, ID_Usuario, ID_Doctor)
    VALUES (4,1,5)
    INTO TBFAVORITOS(ID_Sucursal, ID_Usuario, ID_Doctor)
    VALUES (1,1,2)
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
         VALUES ('Revisi?n de rutina')
SELECT DUMMY FROM DUAL;
*/

/*************************************************************************************************

    ~ Consultas Inner ~

*************************************************************************************************/
--INNER JOIN CENTROMEDICO--
    SELECT 
        d.ID_Doctor,
        u.nombreUsuario, 
        u.apellidoUsuario, 
        u.imgUsuario, 
        e.nombreEspecialidad,
        s.nombreSucursal,
        s.telefonoSucur, 
        s.direccionSucur, 
        s.longSucur,
        s.latiSucur,
        s.imgSucursal,
        srv.nombreServicio, 
        srv.costo
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
        (LOWER(u.nombreUsuario) LIKE LOWER('')
    OR
        LOWER(u.apellidoUsuario) LIKE LOWER(''))
    AND
        u.ID_TipoUsuario = 2;
       
--INNER JOIN SERVICIOS--
SELECT
    srv.nombreServicio,
    srv.costo,
    a.nombreAseguradora,
            d.ID_Doctor
FROM 
    tbServicios srv
INNER JOIN 
    tbAseguradoras a ON srv.ID_Aseguradora = a.ID_Aseguradora
INNER JOIN
    tbCentrosMedicos cm ON srv.ID_Centro = cm.ID_Centro
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
WHERE 
    d.ID_Doctor = 1;

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
        tbpacientes PACS ON citas.id_paciente = pacs.id_paciente WHERE pacs.id_usuario = 1 ;

--INNER JOIN FAVORITOS--

SELECT
    u.ID_Usuario,
    u.nombreUsuario,
    u.imgUsuario,
    d.ID_Doctor,
    s.ID_Sucursal,
    s.imgSucursal,
    ts.nombreTipoSucursal
FROM
    tbFavoritos f
INNER JOIN tbDoctores d ON d.ID_Doctor = f.ID_Doctor
INNER JOIN tbSucursales s ON s.ID_Sucursal = f.ID_Sucursal
INNER JOIN tbUsuarios u ON u.ID_Usuario = d.ID_Usuario
INNER JOIN tbTipoSucursales ts ON ts.ID_TipoSucursal = s.ID_TipoSucursal
WHERE
    f.ID_Usuario = (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = 'fran@gmail.com');

--Este select selecciona todos los datos relacionados a un doctor en base a su ID_Doctor
SELECT 
    d.ID_Doctor,
    u.ID_Usuario,
    u.nombreUsuario, 
    u.apellidoUsuario, 
    u.imgUsuario, 
    e.nombreEspecialidad,
    s.ID_Sucursal,
    s.nombreSucursal, 
    s.telefonoSucur, 
    s.direccionSucur, 
    s.longSucur, 
    s.latiSucur, 
    s.imgSucursal, 
    se.nombreServicio, 
    se.costo
FROM 
    tbDoctores d
    INNER JOIN tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
    INNER JOIN tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
    INNER JOIN tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
    INNER JOIN tbCentrosMedicos cm ON d.ID_Doctor = cm.ID_Doctor
    INNER JOIN tbServicios se ON cm.ID_Centro = se.ID_Centro
WHERE 
    d.ID_Doctor = 5;

SELECT * FROM TBDOCTORES;
CREATE OR REPLACE PROCEDURE PROC_DELT_FAVORITOS(
    var_email IN tbUsuarios.EmailUsuario%TYPE,
    var_ID_Doctor IN tbDoctores.ID_Doctor%TYPE,
    var_ID_Sucursal IN tbSucursales.ID_Sucursal%TYPE
)
IS
    var_ID_Usuario tbUsuarios.ID_Usuario%TYPE;
BEGIN
    SELECT u.ID_Usuario INTO var_ID_Usuario
    FROM tbUsuarios u
    WHERE u.EmailUsuario = var_email;


    DELETE FROM tbFavoritos
    WHERE ID_Usuario = var_ID_Usuario
    AND ID_Sucursal = var_ID_Sucursal
    AND ID_Doctor = var_ID_Doctor;

    COMMIT WORK;
END PROC_DELT_FAVORITOS;
/


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

select * from tbUsuarios;