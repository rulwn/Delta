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
--y si ese error es que la tabla no existe, lo ignora y continua la ejecución.
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
    EXECUTE IMMEDIATE 'DROP TABLE tbPropietarios CASCADE CONSTRAINTS';
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

    ~ ELIMINACI�N DE SECUENCIAS EXISTENTES ~

*******************************************************************************/
--Este procedimiento PL/SQL ejecuta el comando DROP SEQCUENCE, pero si ocurre un error
--y si ese error es que la sequence no existe, lo ignora y continúa la ejecución.
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
    accion VARCHAR2(20) DEFAULT('Elimin� su cuenta.') NOT NULL
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

create table tbPropietarios(
id_usuario int,
id_Establecimiento int
);

alter table tbPropietarios add constraint FKPrimera foreign key (id_usuario) references tbUsuarios(id_usuario) ON DELETE CASCADE;
alter table tbPropietarios add constraint FKSegunda foreign key (id_establecimiento) references tbEstablecimientos(id_establecimiento) ON DELETE CASCADE;


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
    valoFinal NUMBER(5,2) NOT NULL,
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

    /*Este constraint de ac� b�sicamente hace que cada fila sea �nica en otras
    palabras en lugar de hacer que un campo sea �nico, hace que el conjunto de
    campos (osea la fila) sea �nica.*/

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


CREATE TABLE tbHorarios (
    ID_Horario INT PRIMARY KEY,
    horarioTurno VARCHAR2(1) CHECK(horarioTurno IN ('M', 'V')) NOT NULL,
    ID_Doctor INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Doc_Horario FOREIGN KEY (ID_Doctor)
    REFERENCES tbDoctores(ID_Doctor)
    ON DELETE CASCADE
);

CREATE TABLE tbServicios (
    ID_Servicio INT PRIMARY KEY,
    nombreServicio VARCHAR2(60) NOT NULL UNIQUE,
    costo DECIMAL(10,2) NOT NULL,
    ID_Aseguradora INT NOT NULL,
    ID_Doctor INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Aseguradora_Servicio FOREIGN KEY (ID_Aseguradora)
    REFERENCES tbAseguradoras(ID_Aseguradora)
    ON DELETE CASCADE,

    CONSTRAINT FK_Doc_Servicio FOREIGN KEY (ID_Doctor)
    REFERENCES tbDoctores(ID_Doctor)
    ON DELETE CASCADE
);

CREATE TABLE tbReviews (
    ID_Review INT PRIMARY KEY,
    promEstrellas NUMBER(5,2) NOT NULL,
    comentario VARCHAR2(200),
    ID_Doctor INT NOT NULL,
    ID_Usuario INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Doc_Review FOREIGN KEY (ID_Doctor)
    REFERENCES tbDoctores(ID_Doctor)
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

CREATE TABLE tbExpedientes (
    ID_Expediente INT PRIMARY KEY,
    antecedentes VARCHAR2(200),
    nombrePadre VARCHAR2(50),
    nombreMadre VARCHAR2(50),
    responsable VARCHAR2(50),
    permaMedicamentos VARCHAR2(100),
    presionArterial VARCHAR2(20),
    peso NUMBER,
    altura NUMBER(3),
    contactoEmer VARCHAR2(12),
    saturacion NUMBER(3),
    historial VARCHAR2(200),
    tipoSangre VARCHAR2(10),
    fechaConsultas DATE,
    ID_Usuario INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Usuario_Expe FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);

CREATE TABLE tbCitasMedicas (
    ID_Cita INT PRIMARY KEY,
    diaCita DATE NOT NULL,
    estadoCita CHAR(1) CHECK (estadoCita in ('C', 'A')) NOT NULL,
    horaCita TIMESTAMP NOT NULL,
    motivo VARCHAR2(150) NOT NULL,
    ID_Doctor INT NOT NULL,
    ID_Usuario INT NOT NULL,

    --CONSTRAINTS------------------
    CONSTRAINT FK_Doc_Cita FOREIGN KEY (ID_Doctor)
    REFERENCES tbDoctores(ID_Doctor)
    ON DELETE CASCADE,

    CONSTRAINT FK_Us_Cita FOREIGN KEY (ID_Usuario)
    REFERENCES tbUsuarios(ID_Usuario)
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

-- SECUENCIA_AUDITOR�?A -
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

    ~ CREACIÓN DE TRIGGERS ~

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

-- TRIGGER_CITA_M�DICA --
CREATE OR REPLACE TRIGGER Trigger_CitaMedica
BEFORE INSERT ON tbCitasMedicas
FOR EACH ROW
BEGIN
    SELECT citas.NEXTVAL
    INTO: NEW.ID_Cita
    FROM DUAL;
END Trigger_CitaMedica;
/
--TRIGGER PARA NOTIFICACION DE TRATAMIENTO--
CREATE OR REPLACE TRIGGER TRG_InsertarNotificacionTratamiento
AFTER INSERT ON tbIndicaciones
FOR EACH ROW
DECLARE
    v_mensaje VARCHAR2(255);
    v_inicio_medicina DATE;
    v_id_usuario INT;
BEGIN
    v_inicio_medicina := :NEW.inicioMedi;
    BEGIN
        SELECT c.ID_Usuario
        INTO v_id_usuario
        FROM tbCitasMedicas c
        JOIN tbFichasMedicas f ON f.ID_Cita = c.ID_Cita
        WHERE f.ID_Receta = :NEW.ID_Receta
        AND ROWNUM = 1;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            v_id_usuario := NULL;
            DBMS_OUTPUT.PUT_LINE('No se encontr� ning�n usuario asociado al ID_Receta.');
            RETURN;
    END;


    v_mensaje := 'Recuerda tomar tu medicina: ' || :NEW.medicina || ' - ' || :NEW.dosisMedi || ' a las ' || TO_CHAR(v_inicio_medicina, 'HH24:MI');

    -- Insertar la notificaci�n en la tabla de notificaciones
    INSERT INTO tbNotis (
        ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti
    ) VALUES (
        notis.NEXTVAL, SYSDATE, 'R', v_mensaje, 'S', v_id_usuario, 2
    );
END;
/

--TRIGGER PARA CANCELACION DE CITA TBNOTIS--
CREATE OR REPLACE TRIGGER Trigger_Cancelacion_Cita
AFTER UPDATE ON tbCitasMedicas
FOR EACH ROW
WHEN (NEW.estadoCita = 'C')
BEGIN
    DECLARE
        mensaje VARCHAR2(200);
        doctorNombre VARCHAR2(100);
    BEGIN
        SELECT u.nombreUsuario INTO doctorNombre
        FROM tbDoctores d INNER
        JOIN tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
        WHERE d.ID_Doctor = :OLD.ID_Doctor;

        mensaje := 'Cita cancelada con ' || doctorNombre || ' el ' || TO_CHAR(:NEW.horaCita, 'DD-MM-YYYY HH24:MI');

        INSERT INTO tbNotis (ID_Notificacion, fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti)
        VALUES (notis.NEXTVAL, SYSDATE, 'A', mensaje, 'S', :NEW.ID_Usuario, 1);
    END;
END Trigger_Cancelacion_Cita;
/

-- TRIGGER_INDICACI�N --
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

~ TRIGGER PARA TABLA AUDITOR�?A ~

*************************************************************************************************/
--Este trigger se ejecuta antes de eliminar un usuario, lo que hace es guardarlo dentro de tbAuditor�?a
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
        -- Eliminaci�n del favorito
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

    -- Obtener el ID del usuario basado en el correo electr�nico
    SELECT ID_Usuario
        INTO var_ID_Usuario
    FROM tbUsuarios
        WHERE emailUsuario = arg_email;

    -- Eliminar la fila de tbRecientes si ya existe una con los mismos IDs de usuario, sucursal y doctor
    DELETE FROM tbRecientes
    WHERE ID_Usuario = var_ID_Usuario
    AND ID_Sucursal = arg_ID_Sucursal
    AND ID_Doctor = arg_ID_Doctor;

    -- Contar cu�ntas entradas recientes tiene el usuario
    SELECT COUNT(*)
    INTO var_Cant_Recientes
    FROM tbRecientes
    WHERE ID_Usuario = var_ID_Usuario;

    -- Si hay m�s de 19 entradas, eliminar las m�s antiguas hasta que queden solo 19
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
            WHERE ROWNUM <= (var_Cant_Recientes - 18) -- Mantener solo las 19 m�s recientes
        );
    END IF;

    -- Insertar una nueva entrada reciente con el ID de usuario, sucursal y doctor
    INSERT INTO tbRecientes (ID_Usuario, ID_Sucursal, ID_Doctor)
    VALUES (var_ID_Usuario, arg_ID_Sucursal, arg_ID_Doctor);

    COMMIT WORK;
END;
/

/*************************************************************************************************

~ PROCEDURE PARA REVIEWS ~

*************************************************************************************************/
CREATE OR REPLACE PROCEDURE actualizar_valoFinal_sucursal(p_id_sucursal INT) IS
    v_promedio_estrellas NUMBER(5,2);
BEGIN
    -- Calcular el promedio de estrellas para la sucursal
    SELECT NVL(AVG(r.promEstrellas), 9.0)
    INTO v_promedio_estrellas
    FROM tbReviews r
    INNER JOIN tbDoctores d ON r.ID_Doctor = d.ID_Doctor
    WHERE d.ID_Sucursal = p_id_sucursal;

    -- Limitar el valor promedio si excede el l�mite de precisi�n
    IF v_promedio_estrellas > 99.999 THEN
        v_promedio_estrellas := 99.999;
    END IF;

    -- Actualizar el valoFinal en la tabla tbSucursales
    UPDATE tbSucursales
    SET valoFinal = v_promedio_estrellas
    WHERE ID_Sucursal = p_id_sucursal;

    -- NO SE NECESITA COMMIT AQU�
END actualizar_valoFinal_sucursal;
/

CREATE OR REPLACE TRIGGER trg_actualizar_valoFinal_sucursal
AFTER INSERT ON tbReviews
DECLARE
    v_id_sucursal INT;
BEGIN
    -- Actualizamos todas las sucursales afectadas despu�s de la inserci�n
    FOR rec IN (
        SELECT DISTINCT d.ID_Sucursal
        FROM tbDoctores d
        INNER JOIN tbReviews r ON d.ID_Doctor = r.ID_Doctor
    )
    LOOP
        -- Llamamos al procedimiento para actualizar el valoFinal de cada sucursal
        actualizar_valoFinal_sucursal(rec.ID_Sucursal);
    END LOOP;
END;
/

create or replace procedure insertarPropietarios(
 Usuario in tbUsuarios.id_usuario%type,
Establecimiento in tbEstablecimientos.id_establecimiento%type
)
as
begin
insert into tbPropietarios values(Usuario, Establecimiento);
end insertarPropietarios;
/

create or replace procedure insertarHorarios(
Turno in tbHorarios.horarioTurno%type,
Doctor in tbHorarios.ID_Doctor%type
)
as
begin
insert into tbHorarios values(horarios.NEXTVAL, Turno, Doctor);
end insertarHorarios;
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
         VALUES ('Confirmaci�n')
    INTO tbTipoNotis (nombreTipoNoti)
         VALUES ('Configuraci�n')
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
         VALUES ('1 Vez al d�a', 1)
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('2 Veces al d�a', 2)
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('3 Veces al d�a', 3)
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('4 Veces al d�a', 4)
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
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Jorge', 'Orantes', 'orantes@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Los Encinos', '4131-1293', 'M', '28/05/2000', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Mirnix', 'Espinoza', 'mirnix@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Ricaldone', '3423-4241', 'F', '21/08/2000', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Bryan', 'Miranda', 'exequiel.miranda@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'Ricaldone', '5453-7672', 'M', '06/02/2000', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Raul', 'Ochoa', 'eduardito.ochoa@gmail.com', 'c9e4963ef907d66ee56fb928a06021a02520c3e969abef4e222150788c7016aa', 'San Salvador', '3241-7534', 'M', '25/08/2000', NULL, 1)
SELECT DUMMY FROM DUAL;

UPDATE tbUsuarios SET imgUsuario = 'https://w7.pngwing.com/pngs/312/283/png-transparent-man-s-face-avatar-computer-icons-user-profile-business-user-avatar-blue-face-heroes-thumbnail.png' WHERE ID_Usuario = 1;
UPDATE tbUsuarios SET imgUsuario = 'https://st3.depositphotos.com/12985790/15794/i/450/depositphotos_157947226-stock-photo-man-looking-at-camera.jpg' WHERE ID_Usuario = 2;
UPDATE tbUsuarios SET imgUsuario = 'https://us.123rf.com/450wm/antoniodiaz/antoniodiaz1510/antoniodiaz151000120/47228952-apuesto-joven-m%C3%A9dico-con-una-bata-de-laboratorio-y-un-estetoscopio-con-un-tablet-pc-para-comprobar.jpg' WHERE ID_Usuario = 3;
UPDATE tbUsuarios SET imgUsuario = 'https://img.freepik.com/fotos-premium/medico-sexo-masculino-bata-laboratorio-estetoscopio-brazos-cruzados-pie-pasillo-hospital_752325-3492.jpg' WHERE ID_Usuario = 4;
UPDATE tbUsuarios SET imgUsuario = 'https://conimagenes.com/wp-content/uploads/2021/08/retrato-perfil-profesional-1024x1024-1.jpg' WHERE ID_Usuario = 5;
UPDATE tbUsuarios SET imgUsuario = 'https://static.vecteezy.com/system/resources/thumbnails/028/287/555/small_2x/an-indian-young-female-doctor-isolated-on-green-ai-generated-photo.jpg' WHERE ID_Usuario = 7;
UPDATE tbUsuarios SET imgUsuario = 'https://superdoc.mx/wp-content/uploads/2023/05/doctora-1.png' WHERE ID_Usuario = 6;
UPDATE tbUsuarios SET imgUsuario = 'https://png.pngtree.com/png-clipart/20230918/ourmid/pngtree-photo-men-doctor-physician-chest-smiling-png-image_10132895.png' WHERE ID_Usuario = 8;
UPDATE tbUsuarios SET imgUsuario = 'https://hips.hearstapps.com/hmg-prod/images/portrait-of-a-happy-young-doctor-in-his-clinic-royalty-free-image-1661432441.jpg' WHERE ID_Usuario = 9;

INSERT ALL
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('TOEWQ12', 'PRIMER2', 1, 1)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('ABCD1234', 'POLIZA1', 2, 2)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('EFGH5678', 'POLIZA2', 3, 4)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('IJKL9101', 'POLIZA3', 4, 3)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('MNOP2345', 'POLIZA4', 5, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, valoFinal, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Cl�nica Ginecologica', 235656, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia M�dica, San Salvador', 13.709362, -89.202990, '7589-4365', 0.0,'Esta sucursal no posee una fotografia', 1, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, valoFinal, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Cl�nica Asistencial Salvadore�a', 675429, 'clinica_asistencial@gmail.com', '2256-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 13.714547, -89.192849, '5383-4365', 0.0,'Esta sucursal no tiene una fotograf�a', 5, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, valoFinal, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital de Diagnostico', 990764, 'hospital_diagnostico@gmail.com', '2224-7887', '79 Av. Norte y 11 Calle Poniente, Colonia Escalón, San Salvador', 13.710252 , -89.202537, '7519-2335', 0.0,'Esta sucursal ha puesto una fotografía', 3, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, valoFinal, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Centro M�dico Escalon', 224216, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan Jos� Ca�as, Colonia Escal�n, San Salvador', 13.711853, -89.234307, '7509-3230', 0.0,'Neles', 4, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, longSucur, latiSucur, whatsapp, valoFinal, imgSucursal, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital La Divina Providencia', 012483, 'divina_providencia@gmail.com', '2211-2956', 'Avenida Masferrer Norte, Colonia Escal�n, San Salvador', 13.711245, -89.223008, '3278-3561', 0.0,'Tampoco tu', 2, 2)
SELECT DUMMY FROM DUAL;

UPDATE tbSucursales SET imgSucursal = 'https://centroginecologico.com.sv/wp-content/uploads/2020/10/facahadanew20.jpg' WHERE ID_Sucursal = 1;
UPDATE tbSucursales SET imgSucursal = 'https://upload.wikimedia.org/wikipedia/commons/b/b3/Hospital_Nacional_de_Ni%C3%B1os_Benjamin_Bloom.jpg' WHERE ID_Sucursal = 2;
UPDATE tbSucursales SET imgSucursal = 'https://www.hospitaldiagnostico.com/images/sucursal/Escalon.png' WHERE ID_Sucursal = 3;
UPDATE tbSucursales SET imgSucursal = 'https://static.elmundo.sv/wp-content/uploads/2020/10/Centro-Me%CC%81dico-Escalo%CC%81n.jpg' WHERE ID_Sucursal = 4;
UPDATE tbSucursales SET imgSucursal = 'https://www.healthathomes.com/wp-content/uploads/2024/02/DOCTOR-AT-HOME-1.png' WHERE ID_Sucursal = 5;

INSERT ALL
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2024-9-01', 'YYYY-MM-DD'), TO_DATE('2024-9-10', 'YYYY-MM-DD'), '1 tableta', 'Paracetamol', 'Tomar despu�s de las comidas', 1, 1)
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2024-10-01', 'YYYY-MM-DD'), TO_DATE('2024-10-10', 'YYYY-MM-DD'), '2 cucharadas', 'Ibuprofeno', 'Tomar con agua', 2, 2)
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2024-11-01', 'YYYY-MM-DD'), TO_DATE('2024-11-10', 'YYYY-MM-DD'), '5 ml', 'Amoxicilina', 'Tomar cada 8 horas', 3, 3)
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2024-12-01', 'YYYY-MM-DD'), TO_DATE('2024-12-10', 'YYYY-MM-DD'), '1 c�psula', 'Omeprazol', 'Tomar antes de dormir', 4, 4)
    INTO tbIndicaciones (inicioMedi, finalMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (TO_DATE('2025-1-01', 'YYYY-MM-DD'), TO_DATE('2025-1-10', 'YYYY-MM-DD'), '10 gotas', 'Clorfenamina', 'Tomar en la ma�ana y noche', 5, 4)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM23456', 3, 3,2)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM78901', 4, 4,3)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM34567', 5, 6,4)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario,ID_Sucursal)
         VALUES ('JVPM12345', 1, 7,5)
    INTO tbDoctores (codProfesional, ID_Especialidad, ID_Usuario, ID_Sucursal)
         VALUES ('JVPM67890', 2, 8,1)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbHorarios (horarioTurno, ID_Doctor)
         VALUES ('V', 2)
    INTO tbHorarios (horarioTurno, ID_Doctor)
         VALUES ('V', 1)
    INTO tbHorarios (horarioTurno, ID_Doctor)
         VALUES ('M', 3)
    INTO tbHorarios (horarioTurno, ID_Doctor)
         VALUES ('V', 4)
    INTO tbHorarios (horarioTurno, ID_Doctor)
         VALUES ('V', 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Doctor, ID_Usuario)
         VALUES (TO_DATE('2024-10-01', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general','A', 5, 1)
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Doctor, ID_Usuario)
         VALUES (TO_DATE('2024-10-02', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Revisión anual','A', 4, 2)
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Doctor, ID_Usuario)
         VALUES (TO_DATE('2024-10-03', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta de seguimiento','A', 3, 3)
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Doctor, ID_Usuario)
         VALUES (TO_DATE('2024-10-04', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-04 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general','A', 2, 4)
    INTO tbCitasMedicas (diaCita, horaCita, motivo, estadoCita, ID_Doctor, ID_Usuario)
         VALUES (TO_DATE('2024-11-08', 'YYYY-MM-DD'), TO_TIMESTAMP('2024-11-08 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta especializada','A', 1, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (TO_DATE('2024-07-14', 'YYYY-MM-DD'), 'A', 'Cita cancelada para ma~�ana a las 2:00pm', 'S', 1, 1)
    INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (TO_DATE('2024-07-15', 'YYYY-MM-DD'), 'R', 'Recuerda tomar tu medicina a las 4:00pm', 'S', 1, 2)
    INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) 
        VALUES (TO_DATE('2024-07-16', 'YYYY-MM-DD'), 'C', 'Confirma tu cita con Dra. Luz Mar�a', 'S', 1, 3)
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
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Limpieza Bucal', 40.00, 1, 4)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Chequeo General', 30.00, 2, 4)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Examen Visual', 45.00, 3, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Blanqueamiento Dental', 60.00, 4, 4)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Terapia Cognitiva', 55.00, 5, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Ayuda Personal', 25.00, 1, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Chequeo Lumbar', 35.00, 2, 3)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Consulta General', 50.00, 1, 2)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
        VALUES ('Consulta Especializada', 25.00, 2, 5)
    INTO tbServicios (nombreServicio, costo, ID_Aseguradora, ID_Doctor)
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
    INTO tbReviews(promEstrellas, comentario, ID_Doctor, ID_Usuario)
        VALUES(5, 'Excelente Servicio!', 3, 5)
    INTO tbReviews(promEstrellas, comentario, ID_Doctor, ID_Usuario)
        VALUES(1, 'El doctor no se presento a mi cita', 3, 1)
    INTO tbReviews(promEstrellas, comentario, ID_Doctor, ID_Usuario)
        VALUES(4, 'Muy buen ambiente en esa clinica', 4, 2)
    INTO tbReviews(promEstrellas, comentario, ID_Doctor, ID_Usuario)
        VALUES(3, 'Bueno pero pudo ser mejor con el tiempo', 3, 4)
    INTO tbReviews(promEstrellas, comentario, ID_Doctor, ID_Usuario)
        VALUES(4, 'Excelente m�sica', 3, 2)
    INTO tbReviews(promEstrellas, comentario, ID_Doctor, ID_Usuario)
        VALUES(2, 'Mal Servicio', 1, 2)
SELECT DUMMY FROM DUAL;
       

INSERT ALL
    INTO tbPropietarios(ID_Usuario, ID_Establecimiento)
        VALUES(1, 1)
    INTO tbPropietarios(ID_Usuario, ID_Establecimiento)
        VALUES(2, 3)
    INTO tbPropietarios(ID_Usuario, ID_Establecimiento)
        VALUES(5, 4)
    INTO tbPropietarios(ID_Usuario, ID_Establecimiento)
        VALUES(9, 5)
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
        tbDoctores d
    INNER JOIN 
        tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
    INNER JOIN
        tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
    INNER JOIN
        tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
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
    tbDoctores d ON srv.ID_Doctor = d.ID_Doctor
WHERE 
    d.ID_Doctor = 1;

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
    INNER JOIN tbServicios se ON se.ID_Doctor = d.ID_Doctor
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
    tbDoctores d ON rv.ID_Doctor = d.ID_Doctor
WHERE
    d.ID_Doctor = 3;

/*************************************************************************************************

    ~ Consultas Inner Extras~

*************************************************************************************************/
select * from tbAuditorias;
select * from tbDoctores;
select * from tbUsuarios;
select * from tbFavoritos;
select * from tbRecientes;
select * from tbPropietarios;
select * from tbCitasMedicas;
select * from tbEstablecimientos;

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

SELECT * FROM (
    SELECT
        citas.ID_Cita,
        citas.diacita,
        citas.horacita,
        citas.motivo,
        citas.estadoCita,
        citas.id_usuario,
        usua.nombreUsuario,
        usua.apellidoUsuario,
        esp.nombreespecialidad
    FROM
        tbcitasmedicas citas
    INNER JOIN
        tbdoctores docs ON citas.id_doctor = docs.id_doctor
    INNER JOIN
        tbEspecialidades esp ON docs.id_especialidad = esp.id_especialidad
    INNER JOIN
        tbUsuarios usua ON docs.id_usuario = usua.id_usuario
    INNER JOIN
        tbUsuarios us ON citas.id_usuario = us.id_usuario
    WHERE
        us.emailUsuario = 'fran@gmail.com'
        AND citas.diacita >= CURRENT_DATE
        AND citas.estadoCita = 'A'
    ORDER BY
        citas.diacita ASC,
        citas.horacita ASC
)
WHERE
    ROWNUM = 1;


SELECT
    indi.ID_Indicacion,
    indi.inicioMedi,
    indi.finalMedi,
    indi.dosisMedi,
    indi.medicina,
    indi.detalleindi,
    tiem.lapsostiempo,
    tiem.frecuenciamedi
FROM
    tbIndicaciones indi
INNER JOIN
    tbTiempos tiem ON indi.id_tiempo = tiem.id_tiempo
INNER JOIN
    tbRecetas rec ON indi.id_receta = rec.id_receta
INNER JOIN
    tbFichasMedicas fichi ON rec.id_receta = fichi.id_receta
INNER JOIN
    tbcitasmedicas citas ON fichi.id_cita = citas.id_cita
INNER JOIN
    tbUsuarios USUA ON citas.id_usuario = USUA.id_usuario
WHERE
    USUA.emailusuario = 'mirnix@gmail.com'
    AND indi.inicioMedi <= CURRENT_DATE
    AND indi.finalMedi >= CURRENT_DATE;

SELECT nombreusuario || ' ' || apellidousuario AS nombre_completo FROM tbusuarios;

select * from tbIndicaciones;
SELECT e.ID_Expediente, e.antecedentes, e.nombrePadre, e.nombreMadre, e.responsable,
       e.permaMedicamentos, e.presionArterial, e.peso, e.altura, e.contactoEmer,
       e.saturacion, e.historial, e.tipoSangre, e.fechaConsultas,
       u.emailUsuario
FROM tbExpedientes e
INNER JOIN tbUsuarios u ON e.ID_Usuario = u.ID_Usuario
WHERE u.emailUsuario = 'fran@gmail.com';
SELECT * FROM tbExpedientes WHERE ID_Usuario = 1;

select * from tbHorarios;
select * from tbPropietarios where id_usuario = (select id_usuario from tbUsuarios where emailusuario = 'fran@gmail.com');
/*drop table tbpacientes;
drop table tbcentrosmedicos;*/

WITH estrellas AS (
    SELECT LEVEL AS PromEstrellas
    FROM DUAL
    CONNECT BY LEVEL <= 5
)
SELECT
    e.PromEstrellas,
    NVL(r.cantidad_reviews, 0) AS cantidad_reviews
FROM estrellas e
LEFT JOIN (
    SELECT r.promEstrellas, COUNT(*) AS cantidad_reviews
    FROM tbReviews r
    INNER JOIN tbDoctores d ON r.ID_Doctor = d.ID_Doctor
    INNER JOIN tbUsuarios u ON u.ID_Usuario = d.ID_Usuario
    WHERE u.emailUsuario = 'xam@gmail.com'
    GROUP BY r.promEstrellas
) r ON e.PromEstrellas = r.PromEstrellas
ORDER BY e.PromEstrellas;

WITH Meses AS (
    -- Genera 12 meses (5 hacia atr�s y 6 hacia adelante incluyendo el actual)
    SELECT ADD_MONTHS(TRUNC(SYSDATE, 'MM'), LEVEL - 6) AS Mes
    FROM DUAL
    CONNECT BY LEVEL <= 12
)
SELECT
    TO_CHAR(m.Mes, 'YYYY-MM') AS A�oMes,
    TO_CHAR(m.Mes, 'Month YYYY', 'NLS_DATE_LANGUAGE=SPANISH') AS NombreMes,
    NVL(COUNT(c.ID_Cita), 0) AS Total_Citas,
    NVL(SUM(CASE WHEN c.estadoCita = 'A' THEN 1 ELSE 0 END), 0) AS Citas_Activas,
    NVL(SUM(CASE WHEN c.estadoCita = 'C' THEN 1 ELSE 0 END), 0) AS Citas_Inactivas
FROM
    Meses m
LEFT JOIN
    tbCitasMedicas c
ON
    TO_CHAR(c.diaCita, 'YYYY-MM') = TO_CHAR(m.Mes, 'YYYY-MM')
LEFT JOIN
    tbDoctores d
ON
    c.ID_Doctor = d.ID_Doctor
LEFT JOIN
    tbUsuarios u
ON
    d.ID_Usuario = u.ID_Usuario
WHERE
    u.emailUsuario = 'xam@gmail.com'  -- Filtro por el correo del doctor
    OR u.emailUsuario IS NULL  -- Esto permite mantener los meses sin citas
GROUP BY
    m.Mes
ORDER BY
    m.Mes;

SELECT (U.nombreUsuario || ' ' || U.apellidoUsuario) AS NombreUsuario,
       C.Motivo, C.HoraCita, U.imgUsuario
FROM tbCitasMedicas C
INNER JOIN tbUsuarios U ON U.ID_Usuario = C.ID_Usuario
WHERE EXISTS (
    SELECT 1
    FROM tbDoctores D
    INNER JOIN tbUsuarios U2 ON U2.ID_Usuario = D.ID_Usuario
    WHERE U2.emailUsuario = 'xam@gmail.com'
    AND D.ID_Doctor = C.ID_Doctor
)
AND C.horacita > CURRENT_TIMESTAMP
AND C.ESTADOCITA = 'A'
ORDER BY C.diaCita ASC
FETCH FIRST 3 ROWS ONLY;

Select ID_Doctor, emailusuario, NombreUsuario from tbDoctores r INNER JOIN tbUSuarios u On u.ID_Usuario = r.ID_Usuario;

    SELECT
        (SUM(r.PromEstrellas) / NULLIF(COUNT(r.PromEstrellas), 0)) AS "Promedio",
        (SUM(r.PromEstrellas) * 100 / (COUNT(r.PromEstrellas) * 5)) AS "Porcentaje"
    FROM
        tbReviews r
    WHERE
        EXISTS (
            SELECT 1
            FROM tbDoctores d
            INNER JOIN tbUsuarios u ON u.ID_Usuario = d.ID_Usuario
            WHERE u.emailUsuario = 'orantes@gmail.com'
            AND d.ID_Doctor = r.ID_Doctor
        );

SELECT
    u.NombreUsuario || ' ' || u.ApellidoUsuario AS Doctor,
    e.NombreEspecialidad as Especialidad,
    u.ImgUsuario AS ImagenDoctor,
    s.ImgSucursal AS ImagenSucursal,
    s.ID_Sucursal AS Sucursal
FROM
    tbDoctores d
INNER JOIN
    tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
INNER JOIN
    tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
INNER JOIN
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
WHERE u.emailUsuario = 'xam@gmail.com';
select * from tbFichasmedicas;

Select * from tbReviews;
Select * from tbUsuarios;
--Para las reviews
SELECT 
    u.imgUsuario, 
    (u.nombreUsuario || ' ' || u.apellidoUsuario) AS "Nombre",
    r.Comentario,
    r.PromEstrellas
FROM
    tbReviews r
INNER JOIN
    tbUsuarios u ON r.ID_Usuario = u.ID_Usuario
WHERE
    EXISTS (
        SELECT 1
        FROM tbDoctores d
        INNER JOIN tbUsuarios u ON u.ID_Usuario = d.ID_Usuario
        WHERE u.emailUsuario = 'xam@gmail.com'
        AND d.ID_Doctor = r.ID_Doctor
    );

