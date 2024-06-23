/*
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
CREATE USER DeltaMed IDENTIFIED BY "deltaTeam1";
GRANT "CONNECT" TO DeltaMed;
*/

/*************************************************************************************************

    ~ CREACIÓN DE TABLAS INDEPENDIENTES ~

*************************************************************************************************/

CREATE TABLE tbTipoNoti (
    ID_TipoNoti NUMBER PRIMARY KEY,
    nombreTipoNoti VARCHAR2(25)
);

CREATE TABLE tbTipoUsuario (
    ID_TipoUsuario NUMBER PRIMARY KEY,
    nombreTipoUsuario VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE tbTiempo (
    ID_Tiempo NUMBER PRIMARY KEY,
    lapsosTiempo TIMESTAMP NOT NULL
);

CREATE TABLE tbTipoSucursal (
    ID_TipoSucursal NUMBER PRIMARY KEY,
    nombreTipoSucursal VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE tbEspecialidad (
    ID_Especialidad NUMBER PRIMARY KEY,
    nombreEspecialidad VARCHAR2(60) NOT NULL UNIQUE,
    nuevaEspecialidad VARCHAR2(60) UNIQUE
);

CREATE TABLE tbEstablecimiento (
    ID_Establecimiento NUMBER PRIMARY KEY,
    nombreClinica VARCHAR2(50) NOT NULL UNIQUE,
    imgPrincipal VARCHAR2(256) NOT NULL UNIQUE
);

CREATE TABLE tbAseguradora (
    ID_Aseguradora NUMBER PRIMARY KEY,
    nombreAseguradora VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE tbReceta (
    ID_Receta NUMBER PRIMARY KEY,
    fechaReceta DATE NOT NULL,
    ubicacionPDF BLOB
);

/*************************************************************************************************

    ~ CREACIÓN DE TABLAS DEPENDIENTES ~

*************************************************************************************************/

CREATE TABLE tbDoctor (
    ID_Doctor NUMBER PRIMARY KEY,
    codProfesional VARCHAR2(12) NOT NULL,
    ID_Especialidad NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Especialidad FOREIGN KEY (ID_Especialidad) 
    REFERENCES tbEspecialidad(ID_Especialidad)
    ON DELETE CASCADE
);

CREATE TABLE tbSucursal (
    ID_Sucursal NUMBER PRIMARY KEY,
    nombreSucursal VARCHAR2(60) NOT NULL,
    codSucursal NUMBER(8) NOT NULL UNIQUE,
    emailSucur VARCHAR2(30) NOT NULL UNIQUE,
    telefonoSucur VARCHAR2(12) NOT NULL UNIQUE,
    direccionSucur VARCHAR2(200) NOT NULL UNIQUE,
    ubicacionSucur BLOB NOT NULL,
    whatsapp NUMBER(12),
    ID_Establecimiento NUMBER NOT NULL,
    ID_TipoSucursal NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Establecimiento FOREIGN KEY (ID_Establecimiento) 
    REFERENCES tbEstablecimiento(ID_Establecimiento)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_TipoSucursal FOREIGN KEY (ID_TipoSucursal) 
    REFERENCES tbTipoSucursal(ID_TipoSucursal)
    ON DELETE CASCADE
);

CREATE TABLE tbCentroMedico (
    ID_Centro NUMBER PRIMARY KEY,
    favorito CHAR(1) CHECK (favorito IN ('T', 'F')) NOT NULL,
    ID_Doctor NUMBER NOT NULL UNIQUE,
    ID_Sucursal NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Doctor FOREIGN KEY (ID_Doctor) 
    REFERENCES tbDoctor(ID_Doctor)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Sucursal FOREIGN KEY (ID_Sucursal) REFERENCES tbSucursal(ID_Sucursal)
    ON DELETE CASCADE
);

CREATE TABLE tbHorario (
    ID_Horario NUMBER PRIMARY KEY,
    horaInicio TIMESTAMP NOT NULL UNIQUE,
    horaSalida TIMESTAMP NOT NULL UNIQUE,
    dias DATE NOT NULL,
    exclusiones DATE NOT NULL,
    almuerzo TIMESTAMP NOT NULL,
    descansos DATE NOT NULL,
    lapsosCita NUMBER(2) NOT NULL,
    ID_Centro NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Centro_Horario FOREIGN KEY (ID_Centro) 
    REFERENCES tbCentroMedico(ID_Centro)
    ON DELETE CASCADE
);

CREATE TABLE tbServicio (
    ID_Servicio NUMBER PRIMARY KEY,
    nombreServicio VARCHAR2(60) NOT NULL UNIQUE,
    costo DECIMAL(10,2) NOT NULL,
    ID_Aseguradora NUMBER NOT NULL,
    ID_Centro NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Aseguradora_Servicio FOREIGN KEY (ID_Aseguradora) 
    REFERENCES tbAseguradora(ID_Aseguradora)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Centro_Servicio FOREIGN KEY (ID_Centro) 
    REFERENCES tbCentroMedico(ID_Centro)
    ON DELETE CASCADE
);

CREATE TABLE tbSeguro (
    ID_Seguro NUMBER PRIMARY KEY,
    carnetSeguro VARCHAR2(20) NOT NULL UNIQUE,
    poliza VARCHAR2(60) NOT NULL UNIQUE,
    ID_Aseguradora NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Aseguradora_Seguro FOREIGN KEY (ID_Aseguradora) 
    REFERENCES tbAseguradora(ID_Aseguradora)
    ON DELETE CASCADE
);

CREATE TABLE tbUsuario (
    ID_Usuario NUMBER PRIMARY KEY,
    nombreUsuario VARCHAR2(50) NOT NULL,
    apellidoUsuario VARCHAR2(50) NOT NULL,
    emailUsuario VARCHAR2(50) NOT NULL UNIQUE,
    contrasena VARCHAR2(30) NOT NULL,
    direccion VARCHAR2(200) NOT NULL,
    sexo CHAR(1) CHECK (sexo in ('M', 'F')) NOT NULL,
    fechaNacimiento date NOT NULL,
    imgUsuario BLOB,
    ID_TipoUsuario NUMBER NOT NULL,
    ID_Seguro NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_TipoUsuario FOREIGN KEY (ID_TipoUsuario) 
    REFERENCES tbTipoUsuario(ID_TipoUsuario)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Seguro FOREIGN KEY (ID_Seguro) 
    REFERENCES tbSeguro(ID_Seguro)
    ON DELETE CASCADE
);

CREATE TABLE tbReview (
    ID_Review NUMBER PRIMARY KEY,
    nombreCentro VARCHAR2(50) NOT NULL,
    promEstrellas NUMBER(5) NOT NULL,
    comentario VARCHAR2(200),
    ID_Usuario NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Usuario_Review FOREIGN KEY (ID_Usuario) 
    REFERENCES tbUsuario(ID_Usuario)
    ON DELETE CASCADE
);

CREATE TABLE tbNoti (
    ID_Notificacion NUMBER PRIMARY KEY,
    fechaNoti DATE NOT NULL,
    tipoNoti CHAR(1) NOT NULL,
    mensajeNoti VARCHAR2(200) NOT NULL,
    flag CHAR(1) CHECK (flag in ('S', 'N')) NOT NULL,
    ID_Usuario NUMBER NOT NULL,
    ID_TipoNoti NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Usuario_Noti FOREIGN KEY (ID_Usuario) 
    REFERENCES tbUsuario(ID_Usuario)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_TipoNoti FOREIGN KEY (ID_TipoNoti) 
    REFERENCES tbTipoNoti(ID_TipoNoti)
    ON DELETE CASCADE
);

CREATE TABLE tbPaciente (
    ID_Paciente NUMBER PRIMARY KEY,
    nombrePaciente VARCHAR2(50) NOT NULL,
    apellidoPaciente VARCHAR2(50) NOT NULL,
    imgPaciete BLOB,
    parentesco VARCHAR2(30),
    ID_Usuario NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Usuario_Paciente FOREIGN KEY (ID_Usuario) 
    REFERENCES tbUsuario(ID_Usuario)
    ON DELETE CASCADE
);

CREATE TABLE tbExpediente (
    ID_Expediente NUMBER PRIMARY KEY,
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
    ID_Paciente NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Paciente_Expe FOREIGN KEY (ID_Paciente) 
    REFERENCES tbPaciente(ID_Paciente)
    ON DELETE CASCADE
);

CREATE TABLE tbCitaMedica (
    ID_Cita NUMBER PRIMARY KEY,
    diaCita DATE NOT NULL,
    horaCita TIMESTAMP NOT NULL,
    motivo VARCHAR2(150) NOT NULL,
    ID_Centro NUMBER NOT NULL,
    ID_Paciente NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Centro_Cita FOREIGN KEY (ID_Centro) 
    REFERENCES tbCentroMedico(ID_Centro)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Paciente_Cita FOREIGN KEY (ID_Paciente) 
    REFERENCES tbPaciente(ID_Paciente)
    ON DELETE CASCADE
);

CREATE TABLE tbIndicacion (
    ID_Indicacion NUMBER PRIMARY KEY,
    duracionMedi TIMESTAMP NOT NULL,
    frecuenciaMedi TIMESTAMP NOT NULL,
    dosisMedi NUMBER NOT NULL,
    medicina VARCHAR2(90) NOT NULL,
    horaMedi TIMESTAMP NOT NULL,
    detalleIndi VARCHAR2(250) NOT NULL,
    ID_Receta NUMBER NOT NULL,
    ID_Tiempo NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Tiempo FOREIGN KEY (ID_Tiempo) 
    REFERENCES tbTiempo(ID_Tiempo)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Receta_Indi FOREIGN KEY (ID_Receta) 
    REFERENCES tbReceta(ID_Receta)
    ON DELETE CASCADE
);

CREATE TABLE tbFichaMedica (
    ID_Ficha NUMBER PRIMARY KEY,
    diagnostico VARCHAR2(200) NOT NULL,
    tratamiento VARCHAR2(200) NOT NULL,
    sintomas VARCHAR2(150) NOT NULL,
    fechaFicha DATE NOT NULL,
    ID_Receta NUMBER NOT NULL,
    ID_Cita NUMBER NOT NULL,
    
    --CONSTRAINTS------------------
    CONSTRAINT FK_Receta_Ficha FOREIGN KEY (ID_Receta) 
    REFERENCES tbReceta(ID_Receta)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Cita FOREIGN KEY (ID_Cita) 
    REFERENCES tbCitaMedica(ID_Cita)
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
CREATE SEQUENCE servicios
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
BEFORE INSERT ON tbTipoNoti 
FOR EACH ROW 
BEGIN 
    SELECT tipoNotis.NEXTVAL 
    INTO: NEW.ID_TipoNoti 
    FROM DUAL;
END;

-- TRIGGER_TIPO_USUARIO --
CREATE OR REPLACE TRIGGER Trigger_TipoUsuario 
BEFORE INSERT ON tbTipoUsuario 
FOR EACH ROW 
BEGIN 
    SELECT tipoUsuarios.NEXTVAL 
    INTO: NEW.ID_TipoUsuario 
    FROM DUAL;
END;

-- TRIGGER_TIEMPO --
CREATE OR REPLACE TRIGGER Trigger_Tiempo 
BEFORE INSERT ON tbTiempo 
FOR EACH ROW 
BEGIN 
    SELECT tiempos.NEXTVAL 
    INTO: NEW.ID_Tiempo 
    FROM DUAL;
END;

-- TRIGGER_TIPO_SUCURSAL --
CREATE OR REPLACE TRIGGER Trigger_TipoSucursal 
BEFORE INSERT ON tbTipoSucursal 
FOR EACH ROW 
BEGIN 
    SELECT tipoSucursales.NEXTVAL 
    INTO: NEW.ID_TipoSucursal 
    FROM DUAL;
END;

-- TRIGGER_ESPECIALIDAD --
CREATE OR REPLACE TRIGGER Trigger_Especialidad 
BEFORE INSERT ON tbEspecialidad 
FOR EACH ROW 
BEGIN 
    SELECT especialidades.NEXTVAL 
    INTO: NEW.ID_Especialidad 
    FROM DUAL;
END;

-- TRIGGER_ESTABLECIMIENTO --
CREATE OR REPLACE TRIGGER Trigger_Establecimiento 
BEFORE INSERT ON tbEstablecimiento 
FOR EACH ROW 
BEGIN 
    SELECT establecimientos.NEXTVAL 
    INTO: NEW.ID_Establecimiento 
    FROM DUAL;
END;

-- TRIGGER_ASEGURADORA --
CREATE OR REPLACE TRIGGER Trigger_Aseguradora 
BEFORE INSERT ON tbAseguradora 
FOR EACH ROW 
BEGIN 
    SELECT aseguradoras.NEXTVAL 
    INTO: NEW.ID_Aseguradora 
    FROM DUAL;
END;

-- TRIGGER_RECETA --
CREATE OR REPLACE TRIGGER Trigger_Receta 
BEFORE INSERT ON tbReceta 
FOR EACH ROW 
BEGIN 
    SELECT recetas.NEXTVAL 
    INTO: NEW.ID_Receta 
    FROM DUAL;
END;

-- TRIGGER_DOCTOR --
CREATE OR REPLACE TRIGGER Trigger_Doctor 
BEFORE INSERT ON tbDoctor 
FOR EACH ROW 
BEGIN 
    SELECT doctores.NEXTVAL 
    INTO: NEW.ID_Doctor 
    FROM DUAL;
END;

-- TRIGGER_SUCURSAL --
CREATE OR REPLACE TRIGGER Trigger_Sucursal 
BEFORE INSERT ON tbSucursal 
FOR EACH ROW 
BEGIN 
    SELECT sucursales.NEXTVAL 
    INTO: NEW.ID_Sucursal 
    FROM DUAL;
END;

-- TRIGGER_CENTRO_MÉDICO --
CREATE OR REPLACE TRIGGER Trigger_CentroMedico 
BEFORE INSERT ON tbCentroMedico 
FOR EACH ROW 
BEGIN 
    SELECT centros.NEXTVAL 
    INTO: NEW.ID_Centro 
    FROM DUAL;
END;

-- TRIGGER_HORARIO --
CREATE OR REPLACE TRIGGER Trigger_Horario 
BEFORE INSERT ON tbHorario 
FOR EACH ROW 
BEGIN 
    SELECT horarios.NEXTVAL 
    INTO: NEW.ID_Horario 
    FROM DUAL;
END;

-- TRIGGER_SERVICIO --
CREATE OR REPLACE TRIGGER Trigger_Servicio 
BEFORE INSERT ON tbServicio 
FOR EACH ROW 
BEGIN 
    SELECT servicios.NEXTVAL 
    INTO: NEW.ID_Servicio 
    FROM DUAL;
END;

-- TRIGGER_SEGURO --
CREATE OR REPLACE TRIGGER Trigger_Seguro 
BEFORE INSERT ON tbSeguro 
FOR EACH ROW 
BEGIN 
    SELECT seguros.NEXTVAL 
    INTO: NEW.ID_Seguro 
    FROM DUAL;
END;

-- TRIGGER_USUARIO --
CREATE OR REPLACE TRIGGER Trigger_Usuario 
BEFORE INSERT ON tbUsuario 
FOR EACH ROW 
BEGIN 
    SELECT usuarios.NEXTVAL 
    INTO: NEW.ID_Usuario 
    FROM DUAL;
END;

-- TRIGGER_REVIEW --
CREATE OR REPLACE TRIGGER Trigger_Review 
BEFORE INSERT ON tbReview 
FOR EACH ROW 
BEGIN 
    SELECT reviews.NEXTVAL 
    INTO: NEW.ID_Review 
    FROM DUAL;
END;

-- TRIGGER_NOTI --
CREATE OR REPLACE TRIGGER Trigger_Noti 
BEFORE INSERT ON tbNoti 
FOR EACH ROW 
BEGIN 
    SELECT notis.NEXTVAL 
    INTO: NEW.ID_Notificacion 
    FROM DUAL;
END;

-- TRIGGER_PACIENTE --
CREATE OR REPLACE TRIGGER Trigger_Paciente 
BEFORE INSERT ON tbPaciente 
FOR EACH ROW 
BEGIN 
    SELECT pacientes.NEXTVAL 
    INTO: NEW.ID_Paciente 
    FROM DUAL;
END;

-- TRIGGER_EXPEDIENTE --
CREATE OR REPLACE TRIGGER Trigger_Expediente 
BEFORE INSERT ON tbExpediente 
FOR EACH ROW 
BEGIN 
    SELECT expedientes.NEXTVAL 
    INTO: NEW.ID_Expediente 
    FROM DUAL;
END;

-- TRIGGER_CITA_MÉDICA --
CREATE OR REPLACE TRIGGER Trigger_CitaMedica 
BEFORE INSERT ON tbCitaMedica 
FOR EACH ROW 
BEGIN 
    SELECT citas.NEXTVAL 
    INTO: NEW.ID_Cita 
    FROM DUAL;
END;

-- TRIGGER_INDICACIÓN --
CREATE OR REPLACE TRIGGER Trigger_Indicacion 
BEFORE INSERT ON tbIndicacion 
FOR EACH ROW 
BEGIN 
    SELECT indicaciones.NEXTVAL 
    INTO: NEW.ID_Indicacion 
    FROM DUAL;
END;

-- TRIGGER_FICHA --
CREATE OR REPLACE TRIGGER Trigger_Ficha 
BEFORE INSERT ON tbFichaMedica 
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
    INTO tbTipoNoti (nombreTipoNoti)
         VALUES ('Avisos')
    INTO tbTipoNoti (nombreTipoNoti)
         VALUES ('Recordatorio')
    INTO tbTipoNoti (nombreTipoNoti)
         VALUES ('Confirmación')
    INTO tbTipoNoti (nombreTipoNoti)
         VALUES ('Configuración')
    INTO tbTipoNoti (nombreTipoNoti)
         VALUES ('Recetas')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbTipoUsuario (nombreTipoUsuario)
         VALUES ('Administrador de Establecimientos')
    INTO tbTipoUsuario (nombreTipoUsuario)
         VALUES ('Doctor')
    INTO tbTipoUsuario (nombreTipoUsuario)
         VALUES ('Paciente')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbTiempo (lapsosTiempo)
         VALUES (TIMESTAMP '2024-06-18 14:35:10.000000')
    INTO tbTiempo (lapsosTiempo)
         VALUES (TIMESTAMP '2024-05-25 13:24:50.000000')
    INTO tbTiempo (lapsosTiempo)
         VALUES (TIMESTAMP '2024-06-19 14:17:20.000000')
    INTO tbTiempo (lapsosTiempo)
         VALUES (TIMESTAMP '2024-03-06 16:33:40.000000')
    INTO tbTiempo (lapsosTiempo)
         VALUES (TIMESTAMP '2024-06-12 19:25:20.000000')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbTipoSucursal (nombreTipoSucursal)
         VALUES ('Clinica General')
    INTO tbTipoSucursal (nombreTipoSucursal)
         VALUES ('Clinica Especializada')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Pediatrìa', '')
    INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Neonatología', '')
    INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Cardiología', '')
    INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Ortopedia', '')
    INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad)
         VALUES ('Odontología', '')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbEstablecimiento (nombreClinica, imgPrincipal)
         VALUES ('Sonrisas', 'img.ClinicaSonrisas')
    INTO tbEstablecimiento (nombreClinica, imgPrincipal)
         VALUES ('Pronefro', 'img.ClinicaPronefro')
    INTO tbEstablecimiento (nombreClinica, imgPrincipal)
         VALUES ('Medicare', 'img.ClinicaMedicare')
    INTO tbEstablecimiento (nombreClinica, imgPrincipal)
         VALUES ('La Plus Belle', 'img.ClinicaLaPlusBelle')
    INTO tbEstablecimiento (nombreClinica, imgPrincipal)
         VALUES ('Ecomedic', 'img.ClinicaEcomedic')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbAseguradora (nombreAseguradora)
         VALUES ('MAPFRE')
    INTO tbAseguradora (nombreAseguradora)
         VALUES ('SISA')
    INTO tbAseguradora (nombreAseguradora)
         VALUES ('ACSA MED')
    INTO tbAseguradora (nombreAseguradora)
         VALUES ('ATLÁNTIDA VIDA')
    INTO tbAseguradora (nombreAseguradora)
         VALUES ('ASESUISA')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbReceta (fechaReceta, ubicacionPDF)
         VALUES ('26-04-2024', '')
    INTO tbReceta (fechaReceta, ubicacionPDF)
         VALUES ('20-03-2023', '')
    INTO tbReceta (fechaReceta, ubicacionPDF)
         VALUES ('24-05-2023', '')
    INTO tbReceta (fechaReceta, ubicacionPDF)
         VALUES ('04-01-2024', '')
    INTO tbReceta (fechaReceta, ubicacionPDF)
         VALUES ('23-05-2024', '')
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbDoctor (codProfesional, ID_Especialidad)
         VALUES ('JVPM12345', 1)
    INTO tbDoctor (codProfesional, ID_Especialidad)
         VALUES ('JVPM67890', 2)
    INTO tbDoctor (codProfesional, ID_Especialidad)
         VALUES ('JVPM23456', 3)
    INTO tbDoctor (codProfesional, ID_Especialidad)
         VALUES ('JVPM78901', 4)
    INTO tbDoctor (codProfesional, ID_Especialidad)
         VALUES ('JVPM34567', 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Ginecológica, San Salvador', 23565687, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia Médica, San Salvador', 'ubicacionSucur.Ginecologica', '7589-4365', 1, 2)
    INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Clínica Asistencial Salvadoreña, Santa Ana', 67542976, 'clinica_asistencial@gmail.com', '2264-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 'ubicacionSucur.Asistencial', '7559-4365', 4, 5)
    INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital de Diagnóstico, San Salvador', 99076456, 'hospital_diagnostico@gmail.com', '2264-7887', '79 Av. Norte y 11 Calle Poniente, Colonia Escalón, San Salvador', 'ubicacionSucur.Diagnostico', '7589-4635', 3, 1)
    INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Centro Médico Escalón, San Salvador', 23565604, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan José Cañas, Colonia Escalón, San Salvador', 'ubicacionSucur.Escalon', '7589-4230', 4, 2)
    INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal)
         VALUES ('Hospital La Divina Providencia, San Salvador', 23565607, 'divina_providencia@gmail.com', '2234-2956', 'Avenida Masferrer Norte, Colonia Escalón, San Salvador', 'ubicacionSucur.Providencia', '7589-3585', 2, 3)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('T', 1, 3)
    INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 2, 5)
    INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 5, 3)
    INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('T', 3, 4)
    INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal)
         VALUES ('F', 4, 1)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-18 07:00:00.000000', TIMESTAMP '2024-06-18 19:00:00.000000', '2024-06-18', '2024-06-17', TIMESTAMP '2024-06-18 12:00:00.000000', '2024-06-16', 30, 1)
    INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-19 07:00:00.000000', TIMESTAMP '2024-06-19 19:00:00.000000', '2024-06-19', '2024-06-10', TIMESTAMP '2024-06-19 12:00:00.000000', '2024-06-10', 30, 2)
    INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-20 07:00:00.000000', TIMESTAMP '2024-06-20 19:00:00.000000', '2024-06-20', '2024-06-14', TIMESTAMP '2024-06-20 12:00:00.000000', '2024-06-14', 30, 3)
    INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-21 07:00:00.000000', TIMESTAMP '2024-06-21 19:00:00.000000', '2024-06-21', '2024-06-11', TIMESTAMP '2024-06-21 12:00:00.000000', '2024-06-11', 30, 4)
    INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro)
         VALUES (TIMESTAMP '2024-06-22 07:00:00.000000', TIMESTAMP '2024-06-22 19:00:00.000000', '2024-06-22', '2024-06-15', TIMESTAMP '2024-06-22 12:00:00.000000', '2024-06-15', 30, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbCita (ID_Horario, ID_Paciente, ID_Doctor, ID_TipoNoti, ID_Tiempo)
         VALUES (1, 1, 1, 2, 1)
    INTO tbCita (ID_Horario, ID_Paciente, ID_Doctor, ID_TipoNoti, ID_Tiempo)
         VALUES (2, 2, 2, 3, 2)
    INTO tbCita (ID_Horario, ID_Paciente, ID_Doctor, ID_TipoNoti, ID_Tiempo)
         VALUES (3, 3, 3, 1, 3)
    INTO tbCita (ID_Horario, ID_Paciente, ID_Doctor, ID_TipoNoti, ID_Tiempo)
         VALUES (4, 4, 4, 4, 4)
    INTO tbCita (ID_Horario, ID_Paciente, ID_Doctor, ID_TipoNoti, ID_Tiempo)
         VALUES (5, 5, 5, 5, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
    INTO tbPaciente (ID_Aseguradora, ID_TipoUsuario, ID_Receta)
         VALUES (1, 1, 1)
    INTO tbPaciente (ID_Aseguradora, ID_TipoUsuario, ID_Receta)
         VALUES (2, 2, 2)
    INTO tbPaciente (ID_Aseguradora, ID_TipoUsuario, ID_Receta)
         VALUES (3, 3, 3)
    INTO tbPaciente (ID_Aseguradora, ID_TipoUsuario, ID_Receta)
         VALUES (4, 4, 4)
    INTO tbPaciente (ID_Aseguradora, ID_TipoUsuario, ID_Receta)
         VALUES (5, 5, 5)
SELECT DUMMY FROM DUAL;

INSERT ALL
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
SELECT DUMMY FROM DUAL;

COMMIT;

ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';

--La siguiente sección sirve para eliminar la base, quitar el "/*" al inicio de las sentencias
/*************************************************************************************************

    ~ Eliminar todas las tablas ~

*************************************************************************************************/
/*
DROP TABLE tbFichaMedica;
DROP TABLE tbIndicacion;
DROP TABLE tbCitaMedica;
DROP TABLE tbExpediente;
DROP TABLE tbPaciente;
DROP TABLE tbNoti;
DROP TABLE tbReview;
DROP TABLE tbUsuario;
DROP TABLE tbSeguro;
DROP TABLE tbServicio;
DROP TABLE tbHorario;
DROP TABLE tbCentroMedico;
DROP TABLE tbSucursal;
DROP TABLE tbDoctor;
DROP TABLE tbReceta;
DROP TABLE tbAseguradora;
DROP TABLE tbEstablecimiento;
DROP TABLE tbEspecialidad;
DROP TABLE tbTipoSucursal;
DROP TABLE tbTiempo;
DROP TABLE tbTipoUsuario;
DROP TABLE tbTipoNoti;

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
DROP SEQUENCE servicios;
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