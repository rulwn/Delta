/*
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
CREATE USER DeltaMed IDENTIFIED BY "deltaTeam1";
GRANT "CONNECT" TO DeltaMed;
*/

/*************************************************************************************************

    ~ CREACIÓN DE TABLAS INDEPENDIENTES ~

*************************************************************************************************/

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
    ubicacionPDF BLOB
);

/*************************************************************************************************

    ~ CREACIÓN DE TABLAS DEPENDIENTES ~

*************************************************************************************************/

CREATE TABLE tbDoctores (
    ID_Doctor INT PRIMARY KEY,
    codProfesional VARCHAR2(12) NOT NULL,
    ID_Especialidad INT NOT NULL,
    --CONSTRAINTS------------------
    CONSTRAINT FK_Especialidad FOREIGN KEY (ID_Especialidad) 
    REFERENCES tbEspecialidades(ID_Especialidad)
    ON DELETE CASCADE
);


CREATE TABLE tbSucursales (
    ID_Sucursal INT PRIMARY KEY,
    nombreSucursal VARCHAR2(60) NOT NULL,
    codSucursal NUMBER(8) NOT NULL UNIQUE,
    emailSucur VARCHAR2(30) NOT NULL UNIQUE,
    telefonoSucur VARCHAR2(12) NOT NULL UNIQUE,
    direccionSucur VARCHAR2(200) NOT NULL UNIQUE,
    ubicacionSucur BLOB NOT NULL,
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

CREATE TABLE tbSeguros (
    ID_Seguro INT PRIMARY KEY,
    carnetSeguro VARCHAR2(20) NOT NULL UNIQUE,
    poliza VARCHAR2(60) NOT NULL UNIQUE,
    ID_Aseguradora INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Aseguradora_Seguro FOREIGN KEY (ID_Aseguradora) 
    REFERENCES tbAseguradoras(ID_Aseguradora)
    ON DELETE CASCADE
);

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
    ID_Seguro INT NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_TipoUsuario FOREIGN KEY (ID_TipoUsuario) 
    REFERENCES tbTipoUsuarios(ID_TipoUsuario)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Seguro FOREIGN KEY (ID_Seguro) 
    REFERENCES tbSeguros(ID_Seguro)
    ON DELETE CASCADE
);

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
    imgPaciente BLOB,
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
END;

-- TRIGGER_TIPO_USUARIO --
CREATE OR REPLACE TRIGGER Trigger_TipoUsuario 
BEFORE INSERT ON tbTipoUsuarios 
FOR EACH ROW 
BEGIN 
    SELECT tipoUsuarios.NEXTVAL 
    INTO: NEW.ID_TipoUsuario 
    FROM DUAL;
END;

-- TRIGGER_TIEMPO --
CREATE OR REPLACE TRIGGER Trigger_Tiempo 
BEFORE INSERT ON tbTiempos 
FOR EACH ROW 
BEGIN 
    SELECT tiempos.NEXTVAL 
    INTO: NEW.ID_Tiempo 
    FROM DUAL;
END;

-- TRIGGER_TIPO_SUCURSAL --
CREATE OR REPLACE TRIGGER Trigger_TipoSucursal 
BEFORE INSERT ON tbTipoSucursales 
FOR EACH ROW 
BEGIN 
    SELECT tipoSucursales.NEXTVAL 
    INTO: NEW.ID_TipoSucursal 
    FROM DUAL;
END;

-- TRIGGER_ESPECIALIDAD --
CREATE OR REPLACE TRIGGER Trigger_Especialidad 
BEFORE INSERT ON tbEspecialidades 
FOR EACH ROW 
BEGIN 
    SELECT especialidades.NEXTVAL 
    INTO: NEW.ID_Especialidad 
    FROM DUAL;
END;

-- TRIGGER_ESTABLECIMIENTO --
CREATE OR REPLACE TRIGGER Trigger_Establecimiento 
BEFORE INSERT ON tbEstablecimientos 
FOR EACH ROW 
BEGIN 
    SELECT establecimientos.NEXTVAL 
    INTO: NEW.ID_Establecimiento 
    FROM DUAL;
END;

-- TRIGGER_ASEGURADORA --
CREATE OR REPLACE TRIGGER Trigger_Aseguradora 
BEFORE INSERT ON tbAseguradoras 
FOR EACH ROW 
BEGIN 
    SELECT aseguradoras.NEXTVAL 
    INTO: NEW.ID_Aseguradora 
    FROM DUAL;
END;

-- TRIGGER_RECETA --
CREATE OR REPLACE TRIGGER Trigger_Receta 
BEFORE INSERT ON tbRecetas
FOR EACH ROW 
BEGIN 
    SELECT recetas.NEXTVAL 
    INTO: NEW.ID_Receta 
    FROM DUAL;
END;

-- TRIGGER_DOCTOR --
CREATE OR REPLACE TRIGGER Trigger_Doctor 
BEFORE INSERT ON tbDoctores 
FOR EACH ROW 
BEGIN 
    SELECT doctores.NEXTVAL 
    INTO: NEW.ID_Doctor 
    FROM DUAL;
END;

-- TRIGGER_SUCURSAL --
CREATE OR REPLACE TRIGGER Trigger_Sucursal 
BEFORE INSERT ON tbSucursales 
FOR EACH ROW 
BEGIN 
    SELECT sucursales.NEXTVAL 
    INTO: NEW.ID_Sucursal 
    FROM DUAL;
END;

-- TRIGGER_CENTRO_MÉDICO --
CREATE OR REPLACE TRIGGER Trigger_CentroMedico 
BEFORE INSERT ON tbCentrosMedicos 
FOR EACH ROW 
BEGIN 
    SELECT centros.NEXTVAL 
    INTO: NEW.ID_Centro 
    FROM DUAL;
END;

-- TRIGGER_HORARIO --
CREATE OR REPLACE TRIGGER Trigger_Horario 
BEFORE INSERT ON tbHorarios
FOR EACH ROW 
BEGIN 
    SELECT horarios.NEXTVAL 
    INTO: NEW.ID_Horario 
    FROM DUAL;
END;

-- TRIGGER_SERVICIO --
CREATE OR REPLACE TRIGGER Trigger_Servicio 
BEFORE INSERT ON tbServicios 
FOR EACH ROW 
BEGIN 
    SELECT serviciosMedicos.NEXTVAL 
    INTO: NEW.ID_Servicio 
    FROM DUAL;
END;

-- TRIGGER_SEGURO --
CREATE OR REPLACE TRIGGER Trigger_Seguro 
BEFORE INSERT ON tbSeguros 
FOR EACH ROW 
BEGIN 
    SELECT seguros.NEXTVAL 
    INTO: NEW.ID_Seguro 
    FROM DUAL;
END;

-- TRIGGER_USUARIO --
CREATE OR REPLACE TRIGGER Trigger_Usuario 
BEFORE INSERT ON tbUsuarios 
FOR EACH ROW 
BEGIN 
    SELECT usuarios.NEXTVAL 
    INTO: NEW.ID_Usuario 
    FROM DUAL;
END;

-- TRIGGER_REVIEW --
CREATE OR REPLACE TRIGGER Trigger_Review 
BEFORE INSERT ON tbReviews 
FOR EACH ROW 
BEGIN 
    SELECT reviews.NEXTVAL 
    INTO: NEW.ID_Review 
    FROM DUAL;
END;

-- TRIGGER_NOTI --
CREATE OR REPLACE TRIGGER Trigger_Noti 
BEFORE INSERT ON tbNotis
FOR EACH ROW 
BEGIN 
    SELECT notis.NEXTVAL 
    INTO: NEW.ID_Notificacion 
    FROM DUAL;
END;

-- TRIGGER_PACIENTE --
CREATE OR REPLACE TRIGGER Trigger_Paciente 
BEFORE INSERT ON tbPacientes 
FOR EACH ROW 
BEGIN 
    SELECT pacientes.NEXTVAL 
    INTO: NEW.ID_Paciente 
    FROM DUAL;
END;

-- TRIGGER_EXPEDIENTE --
CREATE OR REPLACE TRIGGER Trigger_Expediente 
BEFORE INSERT ON tbExpedientes 
FOR EACH ROW 
BEGIN 
    SELECT expedientes.NEXTVAL 
    INTO: NEW.ID_Expediente 
    FROM DUAL;
END;

-- TRIGGER_CITA_MÉDICA --
CREATE OR REPLACE TRIGGER Trigger_CitaMedica 
BEFORE INSERT ON tbCitasMedicas 
FOR EACH ROW 
BEGIN 
    SELECT citas.NEXTVAL 
    INTO: NEW.ID_Cita 
    FROM DUAL;
END;

-- TRIGGER_INDICACIÓN --
CREATE OR REPLACE TRIGGER Trigger_Indicacion 
BEFORE INSERT ON tbIndicaciones 
FOR EACH ROW 
BEGIN 
    SELECT indicaciones.NEXTVAL 
    INTO: NEW.ID_Indicacion 
    FROM DUAL;
END;

-- TRIGGER_FICHA --
CREATE OR REPLACE TRIGGER Trigger_Ficha 
BEFORE INSERT ON tbFichasMedicas 
FOR EACH ROW 
BEGIN 
    SELECT fichas.NEXTVAL 
    INTO: NEW.ID_Ficha 
    FROM DUAL;
END;

/*************************************************************************************************

    ~ INSERTS ~

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
    INTO tbDoctores (codProfesional, ID_Especialidad)
         VALUES ('JVPM12345', 1)
    INTO tbDoctores (codProfesional, ID_Especialidad)
         VALUES ('JVPM67890', 2)
    INTO tbDoctores (codProfesional, ID_Especialidad)
         VALUES ('JVPM23456', 3)
    INTO tbDoctores (codProfesional, ID_Especialidad)
         VALUES ('JVPM78901', 4)
    INTO tbDoctores (codProfesional, ID_Especialidad)
         VALUES ('JVPM34567', 5)
SELECT DUMMY FROM DUAL;


INSERT ALL
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Ginecológica, San Salvador', 235656, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia Médica, San Salvador', 'ubicacionSucur.Ginecologica', '7589-4365', 1, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Asistencial Salvadoreña, Santa Ana', 675429, 'clinica_asistencial@gmail.com', '2256-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 'ubicacionSucur.Asistencial', '7559-4365', 5, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital de Diagnóstico, San Salvador', 990764, 'hospital_diagnostico@gmail.com', '2224-7887', '79 Av. Norte y 11 Calle Poniente, Colonia Escalón, San Salvador', 'ubicacionSucur.Diagnostico', '7519-2335', 3, 1)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Centro Médico Escalón, San Salvador', 224216, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan José Cañas, Colonia Escalón, San Salvador', 'ubicacionSucur.Escalon', '7509-3230', 4, 2)
    INTO tbSucursales (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital La Divina Providencia, San Salvador', 012483, 'divina_providencia@gmail.com', '2234-2956', 'Avenida Masferrer Norte, Colonia Escalón, San Salvador', 'ubicacionSucur.Providencia', '7589-3585', 2, 2)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('T', 6, 20)
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 7, 21)
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 8, 22)
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('T', 9, 23)
    INTO tbCentrosMedicos (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 10, 24)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-18 07:00:00.000000', TIMESTAMP '2024-06-18 19:00:00.000000', '2024-06-18', '2024-06-17', TIMESTAMP '2024-06-18 12:00:00.000000', '2024-06-16', 30, 1)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-19 07:00:00.000000', TIMESTAMP '2024-06-19 19:00:00.000000', '2024-06-19', '2024-06-10', TIMESTAMP '2024-06-19 12:00:00.000000', '2024-06-10', 30, 2)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-20 07:00:00.000000', TIMESTAMP '2024-06-20 19:00:00.000000', '2024-06-20', '2024-06-14', TIMESTAMP '2024-06-20 12:00:00.000000', '2024-06-14', 30, 3)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-21 07:00:00.000000', TIMESTAMP '2024-06-21 19:00:00.000000', '2024-06-21', '2024-06-11', TIMESTAMP '2024-06-21 12:00:00.000000', '2024-06-11', 30, 4)
    INTO tbHorarios (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-22 07:00:00.000000', TIMESTAMP '2024-06-22 19:00:00.000000', '2024-06-22', '2024-06-15', TIMESTAMP '2024-06-22 12:00:00.000000', '2024-06-15', 30, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (1, TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general', 1, 1)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (2, TO_DATE('2023-01-02', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Revisión anual', 2, 2)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (3, TO_DATE('2023-01-03', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta de seguimiento', 3, 3)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (4, TO_DATE('2023-01-04', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-04 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta general', 4, 4)
    INTO tbCitasMedicas (ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente)
         VALUES (5, TO_DATE('2023-01-05', 'YYYY-MM-DD'), TO_TIMESTAMP('2023-01-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Consulta especializada', 5, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbIndicaciones (ID_Indicacion, duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (1, TO_TIMESTAMP('2023-06-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), '1 tableta', 'Paracetamol', 'Tomar después de las comidas', 1, 1)
    INTO tbIndicaciones (ID_Indicacion, duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (2, TO_TIMESTAMP('2023-06-02 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '2 cucharadas', 'Ibuprofeno', 'Tomar con agua', 2, 2)
    INTO tbIndicaciones (ID_Indicacion, duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (3, TO_TIMESTAMP('2023-06-03 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), '5 ml', 'Amoxicilina', 'Tomar cada 8 horas', 3, 3)
    INTO tbIndicaciones (ID_Indicacion, duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (4, TO_TIMESTAMP('2023-06-04 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), '1 cápsula', 'Omeprazol', 'Tomar antes de dormir', 4, 4)
    INTO tbIndicaciones (ID_Indicacion, duracionMedi, dosisMedi, medicina, detalleIndi, ID_Receta, ID_Tiempo)
         VALUES (5, TO_TIMESTAMP('2023-06-05 07:00:00', 'YYYY-MM-DD HH24:MI:SS'), '10 gotas', 'Clorfenamina', 'Tomar en la mañana y noche', 5, 5)
SELECT DUMMY FROM DUAL;


INSERT ALL
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario)
         VALUES (1, 'Juan', 'Perez', NULL, 'Padre', 1)
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario)
         VALUES (2, 'Maria', 'Garcia', NULL, 'Madre', 2)
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario)
         VALUES (3, 'Carlos', 'Lopez', NULL, 'Hijo', 3)
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario)
         VALUES (4, 'Ana', 'Martinez', NULL, 'Hija', 4)
    INTO tbPacientes (ID_Paciente, nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario)
         VALUES (5, 'Luis', 'Sanchez', NULL, 'Hermano', 5)
SELECT DUMMY FROM DUAL;


/*INSERT ALL
    INTO tbContacto (celular, whatsapp, telefonoContacto, direccionContacto, email)
         VALUES ('70335669', '70335669', '2444-5687', 'Condominio Mezzanine n° 2', 'olga_gonzalez@hotmail.com')
    INTO tbContacto (celular, whatsapp, telefonoContacto, direccionContacto, email)
         VALUES ('70798990', '70798990', '2444-5566', 'Condominio Mezzanine n° 3', 'patricia@gmail.com')
    INTO tbContacto (celular, whatsapp, telefonoContacto, direccionContacto, email)
         VALUES ('71563458', '71563458', '2444-5566', 'Condominio Mezzanine n° 4', 'beatriz@hotmail.com')
    INTO tbContacto (celular, whatsapp, telefonoContacto, direccionContacto, email)
         VALUES ('78446768', '78446768', '2444-5689', 'Condominio Mezzanine n° 5', 'alexis@gmail.com')
    INTO tbContacto (celular, whatsapp, telefonoContacto, direccionContacto, email)
         VALUES ('70112234', '70112234', '2444-1234', 'Condominio Mezzanine n° 6', 'miguel@hotmail.com')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbUsuario (ID_Contacto, ID_Paciente)
         VALUES (1, 1)
    INTO tbUsuario (ID_Contacto, ID_Paciente)
         VALUES (2, 2)
    INTO tbUsuario (ID_Contacto, ID_Paciente)
         VALUES (3, 3)
    INTO tbUsuario (ID_Contacto, ID_Paciente)
         VALUES (4, 4)
    INTO tbUsuario (ID_Contacto, ID_Paciente)
         VALUES (5, 5)
SELECT DUMMY FROM DUAL;

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
SELECT DUMMY FROM DUAL;*/

COMMIT;

ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';

--La siguiente sección sirve para eliminar la base, quitar el "/*" al inicio de las sentencias
/*************************************************************************************************

    ~ Eliminar todas las tablas ~

*************************************************************************************************/
/*
DROP TABLE tbFichasMedicas;
DROP TABLE tbIndicaciones;
DROP TABLE tbCitasMedicas;
DROP TABLE tbExpedientes;
DROP TABLE tbPacientes;
DROP TABLE tbNotis;
DROP TABLE tbReviews;
DROP TABLE tbUsuarios;
DROP TABLE tbSeguros;
DROP TABLE tbServicios;
DROP TABLE tbHorarios;
DROP TABLE tbCentrosMedicos;
DROP TABLE tbSucursales;
DROP TABLE tbDoctores;
DROP TABLE tbRecetas;
DROP TABLE tbAseguradoras;
DROP TABLE tbEstablecimientos;
DROP TABLE tbEspecialidades;
DROP TABLE tbTipoSucursales;
DROP TABLE tbTiempos;
DROP TABLE tbTipoUsuarios;
DROP TABLE tbTipoNotis;

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

/*************************************************************************************************

    ~ Consultas Inner ~

*************************************************************************************************/
/*
SELECT
    d.ID_Doctor,
    e.nombreEspecialidad,
    s.nombreSucursal,
    s.direccionSucur

FROM
    tbCentrosMedicos cm
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
INNER JOIN
    tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
INNER JOIN
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad;
    
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
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad;
*/