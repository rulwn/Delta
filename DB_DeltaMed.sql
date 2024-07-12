/******************************************************

        --Creacion del usuario de DeltaMed--

******************************************************/
    
/*
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
CREATE USER DeltaMed IDENTIFIED BY "deltaTeam1";
GRANT "CONNECT" TO DeltaMed;

ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';
*/

/*************************************************************************************************

    ~ CREACIÓN DE TABLAS INDEPENDIENTES ~

*************************************************************************************************/

Select * from tbUsuarios;

CREATE TABLE tbTipoNotis (
    ID_TipoNoti INT PRIMARY KEY,
    nombreTipoNoti VARCHAR2(25)
);
select * from tbTipoNotis;

CREATE TABLE tbTipoUsuarios (
    ID_TipoUsuario INT PRIMARY KEY,
    nombreTipoUsuario VARCHAR2(50) NOT NULL UNIQUE
);
select * from tbTipoUsuarios;

CREATE TABLE tbTiempos (
    ID_Tiempo INT PRIMARY KEY,
    lapsosTiempo VARCHAR2(50) NOT NULL,
    frecuenciaMedi VARCHAR2(50) NOT NULL
);
select * from tbTiempos;

CREATE TABLE tbTipoSucursales (
    ID_TipoSucursal INT PRIMARY KEY,
    nombreTipoSucursal VARCHAR2(50) NOT NULL UNIQUE
);
select * from tbTipoSucursales;

CREATE TABLE tbEspecialidades (
    ID_Especialidad INT PRIMARY KEY,
    nombreEspecialidad VARCHAR2(60) NOT NULL UNIQUE,
    nuevaEspecialidad VARCHAR2(60) UNIQUE
);
select * from tbEspecialidades;

CREATE TABLE tbEstablecimientos (
    ID_Establecimiento INT PRIMARY KEY,
    nombreClinica VARCHAR2(50) NOT NULL UNIQUE,
    imgPrincipal VARCHAR2(256) NOT NULL UNIQUE
);
select * from tbEstablecimientos;

CREATE TABLE tbAseguradoras (
    ID_Aseguradora INT PRIMARY KEY,
    nombreAseguradora VARCHAR2(50) NOT NULL UNIQUE
);
select * from tbAseguradoras;

CREATE TABLE tbRecetas (
    ID_Receta INT PRIMARY KEY,
    fechaReceta DATE NOT NULL,
    ubicacionPDF BLOB
);
select * from tbRecetas;

/*************************************************************************************************

    ~ CREACIÓN DE TABLAS DEPENDIENTES ~

*************************************************************************************************/

CREATE TABLE tbUsuarios (
    ID_Usuario INT PRIMARY KEY,
    nombreUsuario VARCHAR2(50) NOT NULL,
    apellidoUsuario VARCHAR2(50) NOT NULL,
    emailUsuario VARCHAR2(50) NOT NULL UNIQUE,
    contrasena VARCHAR2(30) NOT NULL,
    direccion VARCHAR2(200) NOT NULL,
    telefonoUsuario VARCHAR2(9) NOT NULL,
    sexo CHAR(1) CHECK (sexo in ('M', 'F')) NOT NULL,
    fechaNacimiento date NOT NULL,
    imgUsuario BLOB,
    ID_TipoUsuario INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_TipoUsuario FOREIGN KEY (ID_TipoUsuario) 
    REFERENCES tbTipoUsuarios(ID_TipoUsuario)
    ON DELETE CASCADE
);
select * from tbUsuarios;

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
select * from tbSeguros;

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
select * from tbDoctores;

CREATE TABLE tbSucursales (
    ID_Sucursal INT PRIMARY KEY,
    nombreSucursal VARCHAR2(60) NOT NULL,
    codSucursal NUMBER(8) NOT NULL UNIQUE,
    emailSucur VARCHAR2(30) NOT NULL UNIQUE,
    telefonoSucur VARCHAR2(12) NOT NULL UNIQUE,
    direccionSucur VARCHAR2(200) NOT NULL UNIQUE,
    ubicacionSucur VARCHAR2(200) NOT NULL,
    whatsapp VARCHAR2(12),
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
select * from tbSucursales;

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
select * from tbCentrosMedicos;

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
select * from tbHorarios;

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
select * from tbServicios;

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
select * from tbReviews;

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
select * from tbNotis;

CREATE TABLE tbPacientes (
    ID_Paciente INT PRIMARY KEY,
    nombrePaciente VARCHAR2(50) NOT NULL,
    apellidoPaciente VARCHAR2(50) NOT NULL,
    imgPaciente BLOB,
    parentesco VARCHAR2(30),
    ID_Usuario INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Usuario_Paciente FOREIGN KEY (ID_Usuario) 
    REFERENCES tbUsuarios(ID_Usuario)
    ON DELETE CASCADE
);
select * from tbPacientes;

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
SELECT ID_Tiempo FROM tbTiempos WHERE ID_Tiempo IN (1, 2, 3, 4, 5);
SELECT ID_Receta FROM tbRecetas WHERE ID_Receta IN (1, 2, 3, 4, 5);


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

-- SECUENCIA_USUARIOS --
CREATE SEQUENCE usuarios 
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

-- TRIGGER_TIPO_USUARIO --
CREATE OR REPLACE TRIGGER Trigger_TipoUsuario 
BEFORE INSERT ON tbTipoUsuarios 
FOR EACH ROW 
BEGIN 
    SELECT tipoUsuarios.NEXTVAL 
    INTO: NEW.ID_TipoUsuario 
    FROM DUAL;
END Trigger_TipoUsuario;

-- TRIGGER_TIEMPO --
CREATE OR REPLACE TRIGGER Trigger_Tiempo 
BEFORE INSERT ON tbTiempos 
FOR EACH ROW 
BEGIN 
    SELECT tiempos.NEXTVAL 
    INTO: NEW.ID_Tiempo 
    FROM DUAL;
END Trigger_Tiempo;

-- TRIGGER_TIPO_SUCURSAL --
CREATE OR REPLACE TRIGGER Trigger_TipoSucursal 
BEFORE INSERT ON tbTipoSucursales 
FOR EACH ROW 
BEGIN 
    SELECT tipoSucursales.NEXTVAL 
    INTO: NEW.ID_TipoSucursal 
    FROM DUAL;
END Trigger_TipoSucursal;

-- TRIGGER_ESPECIALIDAD --
CREATE OR REPLACE TRIGGER Trigger_Especialidad 
BEFORE INSERT ON tbEspecialidades 
FOR EACH ROW 
BEGIN 
    SELECT especialidades.NEXTVAL 
    INTO: NEW.ID_Especialidad 
    FROM DUAL;
END Trigger_Especialidad;

-- TRIGGER_ESTABLECIMIENTO --
CREATE OR REPLACE TRIGGER Trigger_Establecimiento 
BEFORE INSERT ON tbEstablecimientos 
FOR EACH ROW 
BEGIN 
    SELECT establecimientos.NEXTVAL 
    INTO: NEW.ID_Establecimiento 
    FROM DUAL;
END Trigger_Establecimiento;

-- TRIGGER_ASEGURADORA --
CREATE OR REPLACE TRIGGER Trigger_Aseguradora 
BEFORE INSERT ON tbAseguradoras 
FOR EACH ROW 
BEGIN 
    SELECT aseguradoras.NEXTVAL 
    INTO: NEW.ID_Aseguradora 
    FROM DUAL;
END Trigger_Aseguradora;

-- TRIGGER_RECETA --
CREATE OR REPLACE TRIGGER Trigger_Receta 
BEFORE INSERT ON tbRecetas
FOR EACH ROW 
BEGIN 
    SELECT recetas.NEXTVAL 
    INTO: NEW.ID_Receta 
    FROM DUAL;
END Trigger_Receta;

-- TRIGGER_DOCTOR --
CREATE OR REPLACE TRIGGER Trigger_Doctor 
BEFORE INSERT ON tbDoctores 
FOR EACH ROW 
BEGIN 
    SELECT doctores.NEXTVAL 
    INTO: NEW.ID_Doctor 
    FROM DUAL;
END Trigger_Doctor;

-- TRIGGER_SUCURSAL --
CREATE OR REPLACE TRIGGER Trigger_Sucursal 
BEFORE INSERT ON tbSucursales 
FOR EACH ROW 
BEGIN 
    SELECT sucursales.NEXTVAL 
    INTO: NEW.ID_Sucursal 
    FROM DUAL;
END Trigger_Sucursal;

-- TRIGGER_CENTRO_MÉDICO --
CREATE OR REPLACE TRIGGER Trigger_CentroMedico 
BEFORE INSERT ON tbCentrosMedicos 
FOR EACH ROW 
BEGIN 
    SELECT centros.NEXTVAL 
    INTO: NEW.ID_Centro 
    FROM DUAL;
END Trigger_CentroMedico;

-- TRIGGER_HORARIO --
CREATE OR REPLACE TRIGGER Trigger_Horario 
BEFORE INSERT ON tbHorarios
FOR EACH ROW 
BEGIN 
    SELECT horarios.NEXTVAL 
    INTO: NEW.ID_Horario 
    FROM DUAL;
END Trigger_Horario;

-- TRIGGER_SERVICIO --
CREATE OR REPLACE TRIGGER Trigger_Servicio 
BEFORE INSERT ON tbServicios 
FOR EACH ROW 
BEGIN 
    SELECT serviciosMedicos.NEXTVAL 
    INTO: NEW.ID_Servicio 
    FROM DUAL;
END Trigger_Servicio;

-- TRIGGER_SEGURO --
CREATE OR REPLACE TRIGGER Trigger_Seguro 
BEFORE INSERT ON tbSeguros 
FOR EACH ROW 
BEGIN 
    SELECT seguros.NEXTVAL 
    INTO: NEW.ID_Seguro 
    FROM DUAL;
END Trigger_Seguro;

-- TRIGGER_USUARIO --
CREATE OR REPLACE TRIGGER Trigger_Usuario 
BEFORE INSERT ON tbUsuarios 
FOR EACH ROW 
BEGIN 
    SELECT usuarios.NEXTVAL 
    INTO: NEW.ID_Usuario 
    FROM DUAL;
END Trigger_Usuario;

-- TRIGGER_REVIEW --
CREATE OR REPLACE TRIGGER Trigger_Review 
BEFORE INSERT ON tbReviews 
FOR EACH ROW 
BEGIN 
    SELECT reviews.NEXTVAL 
    INTO: NEW.ID_Review 
    FROM DUAL;
END Trigger_Review;

-- TRIGGER_NOTI --
CREATE OR REPLACE TRIGGER Trigger_Noti 
BEFORE INSERT ON tbNotis
FOR EACH ROW 
BEGIN 
    SELECT notis.NEXTVAL 
    INTO: NEW.ID_Notificacion 
    FROM DUAL;
END Trigger_Noti;

-- TRIGGER_PACIENTE --
CREATE OR REPLACE TRIGGER Trigger_Paciente 
BEFORE INSERT ON tbPacientes 
FOR EACH ROW 
BEGIN 
    SELECT pacientes.NEXTVAL 
    INTO: NEW.ID_Paciente 
    FROM DUAL;
END Trigger_Paciente;

-- TRIGGER_EXPEDIENTE --
CREATE OR REPLACE TRIGGER Trigger_Expediente 
BEFORE INSERT ON tbExpedientes 
FOR EACH ROW 
BEGIN 
    SELECT expedientes.NEXTVAL 
    INTO: NEW.ID_Expediente 
    FROM DUAL;
END Trigger_Expediente;

-- TRIGGER_CITA_MÉDICA --
CREATE OR REPLACE TRIGGER Trigger_CitaMedica 
BEFORE INSERT ON tbCitasMedicas 
FOR EACH ROW 
BEGIN 
    SELECT citas.NEXTVAL 
    INTO: NEW.ID_Cita 
    FROM DUAL;
END Trigger_CitaMedica;

-- TRIGGER_INDICACIÓN --
CREATE OR REPLACE TRIGGER Trigger_Indicacion 
BEFORE INSERT ON tbIndicaciones 
FOR EACH ROW 
BEGIN 
    SELECT indicaciones.NEXTVAL 
    INTO: NEW.ID_Indicacion 
    FROM DUAL;
END Trigger_Indicacion;

-- TRIGGER_FICHA --
CREATE OR REPLACE TRIGGER Trigger_Ficha 
BEFORE INSERT ON tbFichasMedicas 
FOR EACH ROW 
BEGIN 
    SELECT fichas.NEXTVAL 
    INTO: NEW.ID_Ficha 
    FROM DUAL;
END Trigger_Ficha;

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
         VALUES ('1 Vez al día', '4')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('2 Veces al día', '3')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('3 Veces al día', '5')
    INTO tbTiempos (lapsosTiempo, frecuenciaMedi)
         VALUES ('4 Veces al día', '2')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbTipoSucursales (nombreTipoSucursal)
         VALUES ('Clinica General')
    INTO tbTipoSucursales (nombreTipoSucursal)
         VALUES ('Clinica Especializada')
SELECT DUMMY FROM DUAL;

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
select * from tbAseguradoras;

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
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('TOEWQ12', 'PRIMER2', 1, 1)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('ABCD1234', 'POLIZA1', 2, 2)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('EFGH5678', 'POLIZA2', 3, 4)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('IJKL9101', 'POLIZA3', 4, 3)
    INTO tbSeguros (carnetSeguro, poliza, ID_Aseguradora, ID_Usuario) VALUES ('MNOP2345', 'POLIZA4', 5, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Francisco', 'Mejía', 'fran@gmail.com', '12345', 'San Salvador', '6143-1352', 'M', '20/02/1980', NULL, 1)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Steven', 'Palacios', 'venosin@gmail.com', 'steven', 'Ciudad Arce', '2245-9312', 'M', '15/07/1999', NULL, 1)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Xavier', 'Torres', 'xam@gmail.com', '123xam', 'Ciudad Delgado', '1292-1275', 'F', '11/01/2007', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Dennis', 'Alexander', 'darv@gmail.com', 'dennis123', 'Villa Olimpica', '6294-0283', 'M', '20/02/2000', NULL, 2)
    INTO tbUsuarios (nombreUsuario, apellidoUsuario, emailUsuario, contrasena, direccion, telefonoUsuario, sexo, fechaNacimiento, imgUsuario, ID_TipoUsuario)
        VALUES ('Hector', 'Gallardo', 'hector@gmail.com', 'rocah123', 'La Paz', '8723-1293', 'M', '25/08/2000', NULL, 1)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Ginecológica, San Salvador', 235656, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia Médica, San Salvador', 'ubicacionSucur.Ginecologica', '7589-4365', 1, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Asistencial Salvadoreña, Santa Ana', 675429, 'clinica_asistencial@gmail.com', '2256-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 'ubicacionSucur.Asistencial', '5383-4365', 5, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital de Diagnóstico, San Salvador', 990764, 'hospital_diagnostico@gmail.com', '2224-7887', '79 Av. Norte y 11 Calle Poniente, Colonia Escalón, San Salvador', 'ubicacionSucur.Diagnostico', '7519-2335', 3, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Centro Médico Escalón, San Salvador', 224216, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan José Cañas, Colonia Escalón, San Salvador', 'ubicacionSucur.Escalon', '7509-3230', 4, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital La Divina Providencia, San Salvador', 012483, 'divina_providencia@gmail.com', '2211-2956', 'Avenida Masferrer Norte, Colonia Escalón, San Salvador', 'ubicacionSucur.Providencia', '3278-3561', 2, 2)
SELECT DUMMY FROM DUAL;

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

INSERT ALL
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-18 07:00:00.000000', TIMESTAMP '2024-06-18 19:00:00.000000', TO_DATE('2024-06-18', 'YYYY-MM-DD'), TO_DATE('2024-06-17', 'YYYY-MM-DD'), TIMESTAMP '2024-06-18 12:00:00.000000', TO_DATE('2024-06-16', 'YYYY-MM-DD'), 1, 6)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-19 07:00:00.000000', TIMESTAMP '2024-06-19 19:00:00.000000', TO_DATE('2024-06-19', 'YYYY-MM-DD'), TO_DATE('2024-06-10', 'YYYY-MM-DD'), TIMESTAMP '2024-06-19 12:00:00.000000', TO_DATE('2024-06-10', 'YYYY-MM-DD'), 2, 7)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-20 07:00:00.000000', TIMESTAMP '2024-06-20 19:00:00.000000', TO_DATE('2024-06-20', 'YYYY-MM-DD'), TO_DATE('2024-06-14', 'YYYY-MM-DD'), TIMESTAMP '2024-06-20 12:00:00.000000', TO_DATE('2024-06-14', 'YYYY-MM-DD'), 3, 8)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-21 07:00:00.000000', TIMESTAMP '2024-06-21 19:00:00.000000', TO_DATE('2024-06-21', 'YYYY-MM-DD'), TO_DATE('2024-06-11', 'YYYY-MM-DD'), TIMESTAMP '2024-06-21 12:00:00.000000', TO_DATE('2024-06-11', 'YYYY-MM-DD'), 4, 9)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-22 07:00:00.000000', TIMESTAMP '2024-06-22 19:00:00.000000', TO_DATE('2024-06-22', 'YYYY-MM-DD'), TO_DATE('2024-06-15', 'YYYY-MM-DD'), TIMESTAMP '2024-06-22 12:00:00.000000', TO_DATE('2024-06-15', 'YYYY-MM-DD'), 5, 10)
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
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (1, TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general', 6, 1)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (2, TO_DATE('2023-01-02', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Revisión anual', 7, 2)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (3, TO_DATE('2023-01-03', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta de seguimiento', 8, 3)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (4, TO_DATE('2023-01-04', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-04 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general', 9, 4)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (5, TO_DATE('2023-01-05', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta especializada', 10, 5)
SELECT DUMMY FROM DUAL;
select * from tbCitasMedicas;

COMMIT;

SELECT * FROM tbUsuarios WHERE emailUsuario = 'hector@gmail.com';

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
    d.ID_Doctor,
    e.nombreEspecialidad,
    s.direccionSucur
    FROM
    tbCentrosMedicos cm
    INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
    INNER JOIN
    tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
    INNER JOIN
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad


SELECT 
    d.ID_Doctor,
    e.nombreEspecialidad,
    s.direccionSucur,
    u.nombreUsuario,
    u.apellidoUsuario,
    u.emailUsuario
FROM
    tbCentrosMedicos cm
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
INNER JOIN
    tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
INNER JOIN
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
LEFT JOIN
    tbHorarios h ON cm.ID_Centro = h.ID_Centro
LEFT JOIN
    tbCitasMedicas c ON h.ID_Horario = c.ID_Cita
LEFT JOIN
    tbUsuarios u ON c.ID_Paciente = u.ID_Usuario; 
    
SELECT 
    cm.ID_Doctor,
    e.nombreEspecialidad,
    u.nombreUsuario,
    u.apellidoUsuario,
    u.emailUsuario
FROM 
    tbCentrosMedicos cm
INNER JOIN 
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
INNER JOIN 
    tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
INNER JOIN 
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
LEFT JOIN 
    tbHorarios h ON cm.ID_Centro = h.ID_Centro
LEFT JOIN 
    tbCitasMedicas c ON h.ID_Horario = c.ID_Cita
LEFT JOIN 
    tbUsuarios u ON c.ID_Paciente = u.ID_Usuario
   
--INNER JOIN CITASMEDICAS--

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
    esp.nombreespecialidad FROM  tbcitasmedicas CITAS 
        INNER JOIN tbcentrosmedicos CENTROS ON CITAS.id_centro=CENTROS.id_centro
        INNER JOIN tbdoctores DOCS ON CENTROS.id_doctor=DOCS.id_doctor
        INNER JOIN tbEspecialidades ESP ON docs.id_especialidad = esp.id_especialidad
        INNER JOIN tbUsuarios USUA ON DOCS.id_usuario = USUA.id_usuario
        INNER JOIN tbpacientes PACS ON citas.id_paciente = pacs.id_paciente WHERE pacs.id_usuario = 1

--La siguiente sección sirve para eliminar la base, quitar el "/*" al inicio de las sentencias
/*************************************************************************************************

    ~ Eliminar todas las tablas ~

*************************************************************************************************/
/*
DROP TABLE tbFichasMedicas CASCADE CONSTRAINTS;
DROP TABLE tbIndicaciones CASCADE CONSTRAINTS;
DROP TABLE tbCitasMedicas CASCADE CONSTRAINTS;
DROP TABLE tbExpedientes CASCADE CONSTRAINTS;
DROP TABLE tbPacientes CASCADE CONSTRAINTS;
DROP TABLE tbNotis CASCADE CONSTRAINTS;
DROP TABLE tbReviews CASCADE CONSTRAINTS;
DROP TABLE tbUsuarios CASCADE CONSTRAINTS;
DROP TABLE tbSeguros CASCADE CONSTRAINTS;
DROP TABLE tbServicios CASCADE CONSTRAINTS;
DROP TABLE tbHorarios CASCADE CONSTRAINTS;
DROP TABLE tbCentrosMedicos CASCADE CONSTRAINTS;
DROP TABLE tbSucursales CASCADE CONSTRAINTS;
DROP TABLE tbDoctores CASCADE CONSTRAINTS;
DROP TABLE tbRecetas CASCADE CONSTRAINTS;
DROP TABLE tbAseguradoras CASCADE CONSTRAINTS;
DROP TABLE tbEstablecimientos CASCADE CONSTRAINTS;
DROP TABLE tbEspecialidades CASCADE CONSTRAINTS;
DROP TABLE tbTipoSucursales CASCADE CONSTRAINTS;
DROP TABLE tbTiempos CASCADE CONSTRAINTS;
DROP TABLE tbTipoUsuarios CASCADE CONSTRAINTS;
DROP TABLE tbTipoNotis CASCADE CONSTRAINTS;

/*************************************************************************************************

    ~ Eliminar todas las secuencias ~

*************************************************************************************************/
/*
DROP SEQUENCE tipoNotis;
DROP SEQUENCE tipoUsuarios;
DROP SEQUENCE tiempos;
DROP SEQUENCE tipoSucursales;
DROP SEQUENCE especialidades;
DROP SEQUENCE establecimientos;
DROP SEQUENCE aseguradoras;
DROP SEQUENCE recetas;
DROP SEQUENCE doctores;
DROP SEQUENCE sucursales;
DROP SEQUENCE centros;
DROP SEQUENCE horarios;
DROP SEQUENCE seguros;
DROP SEQUENCE serviciosMedicos;
DROP SEQUENCE usuarios;
DROP SEQUENCE reviews;
DROP SEQUENCE notis;
DROP SEQUENCE pacientes;
DROP SEQUENCE expedientes;
DROP SEQUENCE citas;
DROP SEQUENCE indicaciones;
DROP SEQUENCE fichas;

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