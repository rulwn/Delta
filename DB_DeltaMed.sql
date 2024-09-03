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

    ~ ELIMINACIÃ“N DE TABLAS EXISTENTES ~

*******************************************************************************/
--Este procedimiento PL/SQL ejecuta el comando DROP TABLE, pero si ocurre un error
--y si ese error es que la tabla no existe, lo ignora y continua la ejecuciÃ³n.
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

    ~ ELIMINACIÃ“N DE SECUENCIAS EXISTENTES ~

*******************************************************************************/
--Este procedimiento PL/SQL ejecuta el comando DROP SEQCUENCE, pero si ocurre un error
--y si ese error es que la sequence no existe, lo ignora y continÃºa la ejecuciÃ³n.
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
    EXECUTE IMMEDIATE 'DROP SEQUENCE usuario';
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
    EXECUTE IMMEDIATE 'DROP SEQUENCE paciente';
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

COMMIT;
/*******************************************************************************

    ~ CREACIÃ“N DE TABLAS INDEPENDIENTES ~

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
    frecuenciaMedi INT NOT NULL
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
    imgPrincipal VARCHAR2(256) NOT NULL
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
    accion VARCHAR2(20) DEFAULT('Eliminó su cuenta.') NOT NULL
);

/*************************************************************************************************

    ~ CREACIÃ“N DE TABLAS DEPENDIENTES ~

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
    imgSucursal VARCHAR2(250) NOT NULL,
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
    ON DELETE CASCADE,

    /*Este constraint de acá básicamente hace que cada fila sea única en otras
    palabras en lugar de hacer que un campo sea único, hace que el conjunto de
    campos (osea la fila) sea única.*/

    CONSTRAINT Unique_Fav UNIQUE (ID_Usuario, ID_Doctor, ID_Sucursal)
);

CREATE TABLE tbRecientes (
    ID_Reciente INT PRIMARY KEY,
    ID_Sucursal INT NOT NULL,
    ID_Usuario INT NOT NULL,
    ID_Doctor INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_SucursalRec FOREIGN KEY (ID_Sucursal)
    REFERENCES tbSucursales(ID_Sucursal)
    ON DELETE CASCADE,

    CONSTRAINT FK_UsuarioRec FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_DoctorRec FOREIGN KEY (ID_Doctor)
    REFERENCES tbDoctores(ID_Doctor)
    ON DELETE CASCADE,


    CONSTRAINT Unique_Rec UNIQUE (ID_Usuario, ID_Doctor, ID_Sucursal)
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
    horarioTurno VARCHAR2(1) CHECK(horarioTurno IN ('M', 'V')) NOT NULL,
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
    promEstrellas NUMBER(5,2) NOT NULL,
    comentario VARCHAR2(200),
    ID_Centro INT NOT NULL,
    ID_Usuario INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Centro_Review FOREIGN KEY (ID_Centro)
    REFERENCES tbCentrosMedicos(ID_Centro)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Usuario_Review FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);

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
    InicioMedi DATE NOT NULL,
    FinalMedi DATE NOT NULL,
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

    ~ CREACIÃ“N DE SECUENCIAS ~

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

-- SECUENCIA_AUDITORÃ?A -
CREATE SEQUENCE auditoria
START WITH 1
INCREMENT BY 1;

-- SECUENCIA_USUARIOS --
CREATE SEQUENCE usuario
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
CREATE SEQUENCE paciente
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

COMMIT;

/*************************************************************************************************

    ~ CREACIÃ“N DE TRIGGERS ~

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
    SELECT usuario.NEXTVAL
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
    SELECT paciente.NEXTVAL
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

-- TRIGGER_CITA_MÃ‰DICA --
CREATE OR REPLACE TRIGGER Trigger_CitaMedica
BEFORE INSERT ON tbCitasMedicas
FOR EACH ROW
BEGIN
    SELECT citas.NEXTVAL
    INTO: NEW.ID_Cita
    FROM DUAL;
END Trigger_CitaMedica;
/

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

~ TRIGGER PARA TABLA AUDITORÃ?A ~

*************************************************************************************************/
--Este trigger se ejecuta antes de eliminar un usuario, lo que hace es guardarlo dentro de tbAuditorÃ?a
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

~ PROCEDURE PARA FAVORITOS ~

*************************************************************************************************/
--Procedimiento alamacenado para administrar los favoritos
CREATE OR REPLACE PROCEDURE PROC_ADMIN_FAVORITOS (
    arg_email IN tbUsuarios.EmailUsuario%TYPE,
    arg_ID_Doctor IN tbDoctores.ID_Doctor%TYPE,
    arg_ID_Sucursal IN tbSucursales.ID_Sucursal%TYPE,
    arg_fav IN CHAR  -- o NUMBER si prefieres
)
IS
    var_ID_Usuario tbUsuarios.ID_Usuario%TYPE;
BEGIN
    -- Obtener ID_Usuario basado en el email proporcionado
    SELECT u.ID_Usuario INTO var_ID_Usuario
    FROM tbUsuarios u
    WHERE u.EmailUsuario = arg_email;

    IF arg_fav = 'F' THEN  -- Usando 'F' para FALSE y 'T' para TRUE
        -- Insertar el nuevo favorito si no existe
        BEGIN
            INSERT INTO tbFavoritos (ID_Sucursal, ID_Doctor, ID_Usuario)
            VALUES (arg_ID_Sucursal, arg_ID_Doctor, var_ID_Usuario);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN
                -- Ignorar error si ya existe el favorito
                NULL;
        END;

    ELSIF arg_fav = 'T' THEN
        -- Eliminación del favorito
        DELETE FROM tbFavoritos
        WHERE ID_Usuario = var_ID_Usuario
        AND ID_Sucursal = arg_ID_Sucursal
        AND ID_Doctor = arg_ID_Doctor;
    END IF;

    COMMIT WORK;
END PROC_ADMIN_FAVORITOS;
/

/*************************************************************************************************

~ PROCEDURE PARA RECIENTES ~

*************************************************************************************************/

CREATE OR REPLACE PROCEDURE PROC_STATE_VALIDATION_RECIENTES (
    arg_email IN tbUsuarios.emailUsuario%TYPE, 
    arg_ID_Sucursal IN INT, 
    arg_ID_Doctor IN INT
) AS
    var_Cant_Recientes INT;
    var_ID_Usuario tbUsuarios.ID_Usuario%TYPE;
BEGIN

    -- Obtener el ID del usuario basado en el correo electrónico
    SELECT ID_Usuario
        INTO var_ID_Usuario
    FROM tbUsuarios
        WHERE emailUsuario = arg_email;

    -- Eliminar la fila de tbRecientes si ya existe una con los mismos IDs de usuario, sucursal y doctor
    DELETE FROM tbRecientes
    WHERE ID_Usuario = var_ID_Usuario
    AND ID_Sucursal = arg_ID_Sucursal
    AND ID_Doctor = arg_ID_Doctor;

    -- Contar cuántas entradas recientes tiene el usuario
    SELECT COUNT(*)
    INTO var_Cant_Recientes
    FROM tbRecientes
    WHERE ID_Usuario = var_ID_Usuario;

    -- Si hay más de 19 entradas, eliminar las más antiguas hasta que queden solo 19
    IF var_Cant_Recientes >= 19 THEN
        DELETE FROM tbRecientes
        WHERE ID_Reciente IN (
            SELECT ID_Reciente
            FROM (
                SELECT ID_Reciente
                FROM tbRecientes
                WHERE ID_Usuario = var_ID_Usuario
                ORDER BY ID_Reciente ASC
            )
            WHERE ROWNUM <= (var_Cant_Recientes - 18) -- Mantener solo las 19 más recientes
        );
    END IF;

    -- Insertar una nueva entrada reciente con el ID de usuario, sucursal y doctor
    INSERT INTO tbRecientes (ID_Usuario, ID_Sucursal, ID_Doctor)
    VALUES (var_ID_Usuario, arg_ID_Sucursal, arg_ID_Doctor);

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
         VALUES ('Confirmación')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Configuración')
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
         VALUES ('1 Vez al día', 1)
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('2 Veces al día', 2)
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('3 Veces al día', 3)
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('4 Veces al día', 4)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbTipoSucursales (nombreTipoSucursal)
         VALUES ('Clinica General')
    INTO tbTipoSucursales (nombreTipoSucursal)
         VALUES ('Clinica Especializada')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Pediatría', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Neonatología', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Cardiología', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Ortopedia', '')
    INTO tbEspecialidades (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Odontología', '')
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
         VALUES ('ATLÁNTIDA VIDA')
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
        VALUES ('Francisco', 'Mejía', 'fran@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'San Salvador', '6143-1352', 'M', '20/02/1980', NULL, 1)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Steven', 'Palacios', 'venosin@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Ciudad Arce', '2245-9312', 'M', '15/07/1999', NULL, 1)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Xavier', 'Torres', 'xam@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Ciudad Delgado', '1292-1275', 'F', '11/01/2007', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Dennis', 'Alexander', 'darv@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Villa Olimpica', '6294-0283', 'M', '20/02/2000', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Hector', 'Gallardo', 'hector@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'La Paz', '8723-1293', 'M', '25/08/2000', NULL, 1)
            INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Raul', 'Ochoa', 'eduardito.ochoa@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'San Salvador', '3241-7534', 'M', '25/08/2000', NULL, 1)
SELECT DUMMY FROM DUAL;

UPDATE tbUsuarios SET imgUsuario = 'https://w7.pngwing.com/pngs/312/283/png-transparent-man-s-face-avatar-computer-icons-user-profile-business-user-avatar-blue-face-heroes-thumbnail.png' WHERE ID_Usuario = 1;
UPDATE tbUsuarios SET imgUsuario = 'https://st3.depositphotos.com/12985790/15794/i/450/depositphotos_157947226-stock-photo-man-looking-at-camera.jpg' WHERE ID_Usuario = 2;
UPDATE tbUsuarios SET imgUsuario = 'https://us.123rf.com/450wm/antoniodiaz/antoniodiaz1510/antoniodiaz151000120/47228952-apuesto-joven-m%C3%A9dico-con-una-bata-de-laboratorio-y-un-estetoscopio-con-un-tablet-pc-para-comprobar.jpg' WHERE ID_Usuario = 3;
UPDATE tbUsuarios SET imgUsuario = 'https://img.freepik.com/fotos-premium/medico-sexo-masculino-bata-laboratorio-estetoscopio-brazos-cruzados-pie-pasillo-hospital_752325-3492.jpg' WHERE ID_Usuario = 4;
UPDATE tbUsuarios SET imgUsuario = 'https://cdn.agenciasinc.es/var/ezwebin_site/storage/images/_aliases/img_1col/en-exclusiva/embargos/el-hombre-de-flores-desaparecio-antes-de-lo-que-se-pensaba/5663917-2-esl-MX/El-Hombre-de-Flores-desaparecio-antes-de-lo-que-se-pensaba.jpg' WHERE ID_Usuario = 5;

INSERT ALL
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('TOEWQ12', 'PRIMER2', 1, 1)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('ABCD1234', 'POLIZA1', 2, 2)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('EFGH5678', 'POLIZA2', 3, 4)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('IJKL9101', 'POLIZA3', 4, 3)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('MNOP2345', 'POLIZA4', 5, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Ginecologica', 235656, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia Médica, San Salvador', 13.709362, -89.202990, '7589-4365', 'Esta sucursal no posee una fotografia', 1, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Asistencial Salvadoreña', 675429, 'clinica_asistencial@gmail.com', '2256-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 13.714547, -89.192849, '5383-4365', 'Esta sucursal no tiene una fotografÃ­a', 5, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital de Diagnostico', 990764, 'hospital_diagnostico@gmail.com', '2224-7887', '79 Av. Norte y 11 Calle Poniente, Colonia EscalÃ³n, San Salvador', 13.710252 , -89.202537, '7519-2335', 'Esta sucursal ha puesto una fotografÃ­a', 3, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Centro Médico Escalon', 224216, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan José Cañas, Colonia Escalón, San Salvador', 13.711853, -89.234307, '7509-3230', 'Neles', 4, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital La Divina Providencia', 012483, 'divina_providencia@gmail.com', '2211-2956', 'Avenida Masferrer Norte, Colonia Escalón, San Salvador', 13.711245, -89.223008, '3278-3561', 'Tampoco tu', 2, 2)
SELECT DUMMY FROM DUAL;

UPDATE tbSucursales SET imgSucursal = 'https://centroginecologico.com.sv/wp-content/uploads/2020/10/facahadanew20.jpg' WHERE ID_Sucursal = 1;
UPDATE tbSucursales SET imgSucursal = 'https://upload.wikimedia.org/wikipedia/commons/b/b3/Hospital_Nacional_de_Ni%C3%B1os_Benjamin_Bloom.jpg' WHERE ID_Sucursal = 2;
UPDATE tbSucursales SET imgSucursal = 'https://www.hospitaldiagnostico.com/images/sucursal/Escalon.png' WHERE ID_Sucursal = 3;
UPDATE tbSucursales SET imgSucursal = 'https://static.elmundo.sv/wp-content/uploads/2020/10/Centro-Me%CC%81dico-Escalo%CC%81n.jpg' WHERE ID_Sucursal = 4;
UPDATE tbSucursales SET imgSucursal = 'https://www.ecured.cu/images/e/e3/Hospita_Divina_Providencia_1.jpg' WHERE ID_Sucursal = 5;

INSERT ALL
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2024-9-01', 'YYYY-MM-DD'), TO_DATE('2024-9-10', 'YYYY-MM-DD'), '1 tableta', 'Paracetamol', 'Tomar después de las comidas', 1, 1)
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2024-10-01', 'YYYY-MM-DD'), TO_DATE('2024-10-10', 'YYYY-MM-DD'), '2 cucharadas', 'Ibuprofeno', 'Tomar con agua', 2, 2)
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2024-11-01', 'YYYY-MM-DD'), TO_DATE('2024-11-10', 'YYYY-MM-DD'), '5 ml', 'Amoxicilina', 'Tomar cada 8 horas', 3, 3)
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2024-12-01', 'YYYY-MM-DD'), TO_DATE('2024-12-10', 'YYYY-MM-DD'), '1 cápsula', 'Omeprazol', 'Tomar antes de dormir', 4, 4)
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2025-1-01', 'YYYY-MM-DD'), TO_DATE('2025-1-10', 'YYYY-MM-DD'), '10 gotas', 'Clorfenamina', 'Tomar en la mañana y noche', 5, 4)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario,ID_Sucursal)
         VALUES ('JVPM12345', 1, 1,1)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM67890', 2, 2,1)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM23456', 3, 3,2)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM78901', 4, 4,3)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM34567', 5, 5,4)
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
    INTO tbHorarios (horarioTurno, ID_Centro)
         VALUES ('V', 2)
    INTO tbHorarios (horarioTurno, ID_Centro)
         VALUES ('V', 1)
    INTO tbHorarios (horarioTurno, ID_Centro)
         VALUES ('M', 3)
    INTO tbHorarios (horarioTurno, ID_Centro)
         VALUES ('V', 4)
    INTO tbHorarios (horarioTurno, ID_Centro)
         VALUES ('V', 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbPacientes (nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES ('Juan', 'Peréz', NULL, 'Padre', 1)
    INTO tbPacientes (nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES ('Maria', 'García', NULL, 'Madre', 2)
    INTO tbPacientes (nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES ('Carlos', 'López', NULL, 'Hijo', 3)
    INTO tbPacientes (nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES ('Ana', 'Martínez', NULL, 'Hija', 4)
    INTO tbPacientes (nombrePaciente, apellidoPaciente, imgPaciente, parentesco, ID_Usuario)
         VALUES ('Luis', 'Sánchez', NULL, 'Hermano', 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (TO_DATE('2024-10-01', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general','A', 5, 1)
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (TO_DATE('2024-10-02', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'RevisiÃ³n anual','A', 4, 2)
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (TO_DATE('2024-10-03', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta de seguimiento','A', 3, 3)
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (TO_DATE('2024-10-04', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-04 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general','A', 2, 4)
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Centro, ID_Paciente)
         VALUES (TO_DATE('2024-10-05', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta especializada','A', 1, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (TO_DATE('2024-07-14', 'YYYY-MM-DD'), 'A', 'Cita cancelada para ma~ñana a las 2:00pm', 'S', 1, 1)
    INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (TO_DATE('2024-07-15', 'YYYY-MM-DD'), 'R', 'Recuerda tomar tu medicina a las 4:00pm', 'S', 1, 2)
    INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (TO_DATE('2024-07-16', 'YYYY-MM-DD'), 'C', 'Confirma tu cita con Dra. Luz María', 'S', 1, 3)
    INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (TO_DATE('2024-07-17', 'YYYY-MM-DD'), 'P', 'Receta 17/05/2024', 'S', 1, 5)
SELECT * FROM dual;

INSERT INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti)
VALUES (TO_DATE('2024-07-14', 'YYYY-MM-DD'), 'P', 'Descargar pdf de tu ultima cita', 'S', 1, 1);

INSERT ALL
INTO tbFichasMedicas (diagnostico,tratamiento,sintomas,fechaFicha,ID_Receta,ID_Cita)
VALUES ('Gripe', 'tomar medicina', 'Fiebre', TO_DATE('2023-08-28', 'YYYY-MM-DD'), 1, 1)
INTO tbFichasMedicas (diagnostico,tratamiento,sintomas,fechaFicha,ID_Receta,ID_Cita)
VALUES ('Influenza', 'tomar medicina', 'Fiebre', TO_DATE('2023-09-28', 'YYYY-MM-DD'), 2, 2)
INTO tbFichasMedicas (diagnostico,tratamiento,sintomas,fechaFicha,ID_Receta,ID_Cita)
VALUES ('Covid', 'tomar medicina', 'Fiebre', TO_DATE('2023-10-28', 'YYYY-MM-DD'), 3, 3)
INTO tbFichasMedicas (diagnostico,tratamiento,sintomas,fechaFicha,ID_Receta,ID_Cita)
VALUES ('Alergia', 'tomar medicina', 'Fiebre', TO_DATE('2023-11-28', 'YYYY-MM-DD'), 4, 4)
INTO tbFichasMedicas (diagnostico,tratamiento,sintomas,fechaFicha,ID_Receta,ID_Cita)
VALUES ('Pulmonia', 'tomar medicina', 'Fiebre', TO_DATE('2023-12-28', 'YYYY-MM-DD'), 5, 5)
SELECT * FROM dual;


INSERT ALL
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Limpieza Bucal', 40.00, 1, 4)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Chequeo General', 30.00, 2, 4)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Examen Visual', 45.00, 3, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Blanqueamiento Dental', 60.00, 4, 4)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Terapia Cognitiva', 55.00, 5, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Ayuda Personal', 25.00, 1, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Chequeo Lumbar', 35.00, 2, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Consulta General', 50.00, 1, 2)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Consulta Especializada', 25.00, 2, 5)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Centro)
        VALUES ('Anestesia General', 75.00, 1, 1)
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
        VALUES (1,2,2)
SELECT DUMMY FROM DUAL;


--UNICA INSERCION CON EL USUARIO DE FRANCISCO--
INSERT INTO tbNotis
(fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti)
VALUES
(TO_DATE('2024-07-15', 'YYYY-MM-DD'), 'R', 'Recordatorio: Consulta general programada', 'S',
(SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = 'fran@gmail.com'), 2);


INSERT ALL
    INTO tbReviews(promEstrellas, comentario, ID_Centro, ID_Usuario)
        VALUES(5, 'Excelente Servicio!', 3, 5)
INTO tbReviews(promEstrellas, comentario, ID_Centro, ID_Usuario)
        VALUES(1, 'El doctor no se presento a mi cita', 3, 1)
INTO tbReviews(promEstrellas, comentario, ID_Centro, ID_Usuario)
        VALUES(4.5, 'Muy buen ambiente en esa clinica', 4, 2)
INTO tbReviews(promEstrellas, comentario, ID_Centro, ID_Usuario)
        VALUES(3, 'Bueno pero pudo ser mejor con el tiempo', 3, 4)
INTO tbReviews(promEstrellas, comentario, ID_Centro, ID_Usuario)
        VALUES(4, 'Excelente música', 3, 2)
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
         VALUES ('RevisiÃ³n de rutina')
SELECT DUMMY FROM DUAL;
*/

/*************************************************************************************************

    ~ Consultas Inner ~

*************************************************************************************************/
--INNER JOIN CENTROMEDICO--
    SELECT
        u.nombreUsuario, 
        u.apellidoUsuario, 
        u.imgUsuario, 
        e.nombreEspecialidad,
        s.nombreSucursal,
        s.telefonoSucur, 
        s.direccionSucur, 
        s.longSucur,
        s.latiSucur,
        s.imgSucursal
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
    WHERE        
        (LOWER(u.nombreUsuario) LIKE LOWER('%X%')
    OR
        LOWER(u.apellidoUsuario) LIKE LOWER(''))
    AND
        u.ID_TipoUsuario = 2;
       
--INNER JOIN SERVICIOS--
SELECT
    srv.nombreServicio,
    srv.costo,
    a.nombreAseguradora
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
    citas.diacita,
    citas.horacita,
    citas.motivo,
    pacs.nombrepaciente,
    pacs.parentesco,
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
    u.nombreUsuario,
    u.imgUsuario,
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

--CONSULTA INNERJOIN REVIEW--
SELECT
    rv.comentario,
    rv.promEstrellas,
    u.nombreUsuario,
    u.apellidoUsuario,
    u.imgUsuario,
    d.ID_Doctor
FROM
    tbReviews rv
INNER JOIN
    tbUsuarios u ON rv.ID_Usuario = u.ID_Usuario
INNER JOIN
    tbCentrosMedicos cm ON rv.ID_Centro = cm.ID_Centro
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
WHERE
    d.ID_Doctor = 3;

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


SELECT indi.ID_Indicacion,
       indi.inicioMedi,
       indi.finalMedi,
       indi.dosisMedi,
       indi.medicina,
       indi.detalleindi,
       tiem.lapsostiempo,
       tiem.frecuenciamedi
FROM tbIndicaciones indi
INNER JOIN tbTiempos tiem ON indi.id_tiempo = tiem.id_tiempo
INNER JOIN tbRecetas rec ON indi.id_receta = rec.id_receta
INNER JOIN tbFichasMedicas fichi ON rec.id_receta = fichi.id_receta
INNER JOIN tbcitasmedicas citas ON fichi.id_cita = citas.id_cita
INNER JOIN tbpacientes PACS ON citas.id_paciente = PACS.id_paciente
INNER JOIN tbUsuarios USUA ON PACS.id_usuario = USUA.id_usuario
WHERE USUA.emailusuario = 'venosin@gmail.com';

    SELECT
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
        (LOWER(u.nombreUsuario) LIKE LOWER('x%'))
    AND
        u.ID_TipoUsuario = 2;


SELECT
    rv.comentario,
    rv.promEstrellas,
    u.nombreUsuario,
    u.apellidoUsuario,
    u.imgUsuario,
    d.ID_Doctor
FROM
    tbReviews rv
INNER JOIN
    tbUsuarios u ON rv.ID_Usuario = u.ID_Usuario
INNER JOIN
    tbCentrosMedicos cm ON rv.ID_Centro = cm.ID_Centro
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
WHERE
    d.ID_Doctor = 3;

   --seleccion de ids del favorito
SELECT
    s.ID_Sucursal AS SucursalID,
    s.nombreSucursal AS SucursalNombre,
    u.ID_Usuario AS UsuarioID,
    u.nombreUsuario AS UsuarioNombre,
    d.ID_Doctor AS DoctorID,
    u_doctor.nombreUsuario AS DoctorNombre
FROM
    tbFavoritos f
INNER JOIN tbUsuarios u ON u.ID_Usuario = f.ID_Usuario
INNER JOIN tbSucursales s ON s.ID_Sucursal = f.ID_Sucursal
INNER JOIN tbDoctores d ON d.ID_Doctor = f.ID_Doctor
INNER JOIN tbUsuarios u_doctor ON u_doctor.ID_Usuario = d.ID_Usuario
WHERE
    u.emailUsuario = 'fran@gmail.com';



SELECT * FROM tbCentrosMedicos WHERE ID_Doctor = 5;
SELECT se.* FROM tbServicios se
INNER JOIN tbCentrosMedicos cm ON se.ID_Centro = cm.ID_Centro
WHERE cm.ID_Doctor = 5;

SELECT
    h.horarioTurno
    FROM
        tbHorarios h
    INNER JOIN
        tbCentrosMedicos cm ON h.ID_Centro = cm.ID_Centro
    INNER JOIN
        tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
    WHERE
                d.ID_Doctor = 5;

SELECT

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
*/

select * from tbAuditorias;
select * from tbDoctores;
select * from tbUsuarios;
select * from tbFavoritos;
select * from tbRecientes;

SELECT
u.ID_Usuario,
u.nombreUsuario,
u.imgUsuario,
d.ID_Doctor,
s.ID_Sucursal,
s.imgSucursal,
ts.nombreTipoSucursal
FROM
tbRecientes f
INNER JOIN tbDoctores d ON d.ID_Doctor = f.ID_Doctor
INNER JOIN tbSucursales s ON s.ID_Sucursal = f.ID_Sucursal
INNER JOIN tbUsuarios u ON u.ID_Usuario = d.ID_Usuario
INNER JOIN tbTipoSucursales ts ON ts.ID_TipoSucursal = s.ID_TipoSucursal
WHERE
f.ID_Usuario = (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = 'fran@gmail.com');

SELECT * FROM tbRecientes;
SELECT * FROM tbDoctores WHERE ID_Doctor = 1;
SELECT * FROM tbUsuarios WHERE ID_Usuario = 1;

SELECT * FROM tbPacientes;