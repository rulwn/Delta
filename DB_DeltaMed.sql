/*
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
CREATE USER DeltaMed IDENTIFIED BY "deltaTeam1";
GRANT "CONNECT" TO DeltaMed;
*/

--/////// TABLES /////////////////////////////////////////////////////////////--

CREATE TABLE tbTipoNoti
(
ID_TipoNoti NUMBER PRIMARY KEY,
nombreTipoNoti VARCHAR2(25)
);

INSERT ALL
INTO tbTipoNoti (nombreTipoNoti) VALUES ('Avisos')
INTO tbTipoNoti (nombreTipoNoti) VALUES ('Recordatorio')
INTO tbTipoNoti (nombreTipoNoti) VALUES ('Confirmación')
INTO tbTipoNoti (nombreTipoNoti) VALUES ('Configuración')
INTO tbTipoNoti (nombreTipoNoti) VALUES ('Recetas')
SELECT DUMMY FROM dual;

create TABLE tbTipoUsuario
(
ID_TipoUsuario NUMBER PRIMARY KEY,
nombreTipoUsuario VARCHAR2(50) not null unique
);

INSERT ALL
INTO tbTipoUsuario (nombreTipoUsuario) VALUES ('Administrador de Establecimientos')
INTO tbTipoUsuario (nombreTipoUsuario) VALUES ('Doctor')
INTO tbTipoUsuario (nombreTipoUsuario) VALUES ('Paciente')
SELECT DUMMY FROM dual;

create TABLE tbTiempo
(
ID_Tiempo NUMBER PRIMARY KEY,
lapsosTiempo timestamp not null
);

INSERT ALL
INTO tbTiempo (lapsosTiempo) VALUES (TimesTamp '2024-06-18 14:35:10.000000')
INTO tbTiempo (lapsosTiempo) VALUES (TimesTamp '2024-05-25 13:24:50.000000')
INTO tbTiempo (lapsosTiempo) VALUES (TimesTamp '2024-06-19 14:17:20.000000')
INTO tbTiempo (lapsosTiempo) VALUES (TimesTamp '2024-03-06 16:33:40.000000')
INTO tbTiempo (lapsosTiempo) VALUES (TimesTamp '2024-06-12 19:25:20.000000')
SELECT DUMMY FROM dual;

create TABLE tbTipoSucursal
(
ID_TipoSucursal NUMBER PRIMARY KEY,
nombreTipoSucursal VARCHAR2(50) not null unique
);

INSERT ALL
INTO tbTipoSucursal (nombreTipoSucursal) VALUES ('Clinica General')
INTO tbTipoSucursal (nombreTipoSucursal) VALUES ('Clinica Especializada')
SELECT DUMMY FROM dual;

create TABLE tbEspecialidad
(
ID_Especialidad NUMBER PRIMARY KEY,
nombreEspecialidad VARCHAR2(60) not null unique,
nuevaEspecialidad VARCHAR2(60) unique
);

INSERT ALL
INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) VALUES ('Pediatrìa', '')
INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) VALUES ('Neonatología', '')
INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) VALUES ('Cardiología', '')
INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) VALUES ('Ortopedia', '')
INTO tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) VALUES ('Odontología', '')
SELECT DUMMY FROM dual;

create TABLE tbEstablecimiento
(
ID_Establecimiento NUMBER PRIMARY KEY,
nombreClinica VARCHAR2(50) not null unique,
imgPrincipal VARCHAR2(256) not null unique
);

INSERT ALL
INTO tbEstablecimiento (nombreClinica, imgPrincipal) VALUES ('Sonrisas', 'img.ClinicaSonrisas')
INTO tbEstablecimiento (nombreClinica, imgPrincipal) VALUES ('Pronefro', 'img.ClinicaPronefro')
INTO tbEstablecimiento (nombreClinica, imgPrincipal) VALUES ('Medicare', 'img.ClinicaMedicare')
INTO tbEstablecimiento (nombreClinica, imgPrincipal) VALUES ('La Plus Belle', 'img.ClinicaLaPlusBelle')
INTO tbEstablecimiento (nombreClinica, imgPrincipal) VALUES ('Ecomedic', 'img.ClinicaEcomedic')
SELECT DUMMY FROM dual;

create TABLE tbAseguradora
(
ID_Aseguradora NUMBER PRIMARY KEY,
nombreAseguradora VARCHAR2(50) not null unique
);

INSERT ALL
INTO tbAseguradora (nombreAseguradora) VALUES ('MAPFRE')
INTO tbAseguradora (nombreAseguradora) VALUES ('SISA')
INTO tbAseguradora (nombreAseguradora) VALUES ('ACSA MED')
INTO tbAseguradora (nombreAseguradora) VALUES ('ATLÁNTIDA VIDA')
INTO tbAseguradora (nombreAseguradora) VALUES ('ASESUISA')
SELECT DUMMY FROM dual;

create TABLE tbReceta
(
ID_Receta NUMBER PRIMARY KEY,
fechaReceta date not null,
ubicacionPDF blob
);

INSERT ALL
INTO tbReceta (fechaReceta, ubicacionPDF) VALUES ('26-04-2024','')
INTO tbReceta (fechaReceta, ubicacionPDF) VALUES ('20-03-2023','')
INTO tbReceta (fechaReceta, ubicacionPDF) VALUES ('24-05-2023','')
INTO tbReceta (fechaReceta, ubicacionPDF) VALUES ('04-01-2024','')
INTO tbReceta (fechaReceta, ubicacionPDF) VALUES ('23-05-2024','')
SELECT DUMMY FROM dual;

--Creacion de tablas dependientes--

create TABLE tbDoctor
(
ID_Doctor NUMBER PRIMARY KEY,
codProfesional VARCHAR2(12) not null,
ID_Especialidad NUMBER not null,
constraint FK_Especialidad foreign key (ID_Especialidad) references tbEspecialidad(ID_Especialidad)
on delete cascade
);

INSERT ALL
INTO tbDoctor (codProfesional, ID_Especialidad) VALUES ('JVPM12345')
INTO tbDoctor (codProfesional, ID_Especialidad) VALUES ('JVPM67890')
INTO tbDoctor (codProfesional, ID_Especialidad) VALUES ('JVPM23456')
INTO tbDoctor (codProfesional, ID_Especialidad) VALUES ('JVPM78901')
INTO tbDoctor (codProfesional, ID_Especialidad) VALUES ('JVPM34567')
SELECT DUMMY FROM dual;

create TABLE tbSucursal
(
ID_Sucursal NUMBER PRIMARY KEY,
nombreSucursal VARCHAR2(60) not null,
codSucursal NUMBER(8) not null unique,
emailSucur VARCHAR2(30) not null unique,
telefonoSucur VARCHAR2(12) not null unique,
direccionSucur VARCHAR2(200) not null unique,
ubicacionSucur blob not null,
whatsapp NUMBER(12),
ID_Establecimiento NUMBER not null,
ID_TipoSucursal NUMBER not null,
constraint FK_Establecimiento foreign key (ID_Establecimiento) references tbEstablecimiento(ID_Establecimiento)
on delete cascade,
constraint FK_TipoSucursal foreign key (ID_TipoSucursal) references tbTipoSucursal(ID_TipoSucursal)
on delete cascade
);

INSERT ALL
INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) VALUES ('Clínica Ginecológica, San Salvador', 23565687, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia Médica, San Salvador', 'ubicacionSucur.Ginecologica', '7589-4365', 1, 2)
INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) VALUES ('Clínica Asistencial Salvadoreña, Santa Ana', 67542976, 'clinica_asistencial@gmail.com', '2264-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 'ubicacionSucur.Asistencial', '7559-4365', 4, 5)
INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) VALUES ('Hospital de Diagnóstico, San Salvador', 99076456, 'hospital_diagnostico@gmail.com', '2264-7887', '79 Av. Norte y 11 Calle Poniente, Colonia Escalón, San Salvador', 'ubicacionSucur.Diagnostico', '7589-4635', 3, 1)
INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) VALUES ('Centro Médico Escalón, San Salvador', 23565604, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan José Cañas, Colonia Escalón, San Salvador', 'ubicacionSucur.Escalon', '7589-4230', 4, 2)
INTO tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) VALUES ('Hospital La Divina Providencia, San Salvador', 23565607, 'divina_providencia@gmail.com', '2234-2956', 'Avenida Masferrer Norte, Colonia Escalón, San Salvador', 'ubicacionSucur.Providencia', '7589-3585', 2, 3)
SELECT DUMMY FROM dual;

create TABLE tbCentroMedico
(
ID_Centro NUMBER PRIMARY KEY,
favorito char(1) check (favorito in ('T', 'F')) not null,
ID_Doctor NUMBER not null unique,
ID_Sucursal NUMBER not null,
constraint FK_Doctor foreign key (ID_Doctor) references tbDoctor(ID_Doctor)
on delete cascade,
constraint FK_Sucursal foreign key (ID_Sucursal) references tbSucursal(ID_Sucursal)
on delete cascade
);

INSERT ALL
INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) VALUES ('T', 1, 3)
INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) VALUES ('F', 2, 5)
INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) VALUES ('F', 5, 3)
INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) VALUES ('T', 3, 4)
INTO tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) VALUES ('F', 4, 1)
SELECT DUMMY FROM dual;


create TABLE tbHorario
(
ID_Horario NUMBER PRIMARY KEY,
horaInicio timestamp not null unique,
horaSalida timestamp not null unique,
dias date not null,
exclusiones date not null,
almuerzo timestamp not null,
descansos date not null,
lapsosCita NUMBER(2) not null,
ID_Centro NUMBER not null,
constraint FK_Centro_Horario foreign key (ID_Centro) references tbCentroMedico(ID_Centro)
on delete cascade
);

INSERT ALL
INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) VALUES (TimesTamp'2024-06-18 07:00:00.000000', TimesTamp'2024-06-18 19:00:00.000000', '2024-06-18', '2024-06-17', TimesTamp'2024-06-18 12:00:00.000000', '2024-06-16', 30, 1)
INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) VALUES (TimesTamp'2024-06-19 07:00:00.000000', TimesTamp'2024-06-19 19:00:00.000000', '2024-06-19', '2024-05-10', TimesTamp'2024-06-18 12:00:00.000000', '2024-06-23', 30, 2)
INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) VALUES (TimesTamp'2024-06-20 07:00:00.000000', TimesTamp'2024-06-20 19:00:00.000000', '2024-06-20', '2024-05-03', TimesTamp'2024-06-18 12:00:00.000000', '2024-06-30', 30, 3)
INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) VALUES (TimesTamp'2024-06-21 07:00:00.000000', TimesTamp'2024-06-21 19:00:00.000000', '2024-06-21', '2024-09-15', TimesTamp'2024-06-18 12:00:00.000000', '2024-07-07', 30, 4)
INTO tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) VALUES (TimesTamp'2024-06-22 07:00:00.000000', TimesTamp'2024-06-22 19:00:00.000000', '2024-06-22', '2024-11-02', TimesTamp'2024-06-18 12:00:00.000000', '2024-07-14', 30, 5)
SELECT DUMMY FROM dual;

create TABLE tbServicio
(
ID_Servicio NUMBER PRIMARY KEY,
nombreServicio VARCHAR2(60) not null unique,
costo decimal(10,2) not null,
ID_Aseguradora NUMBER not null,
ID_Centro NUMBER not null,
constraint FK_Aseguradora_Servicio foreign key (ID_Aseguradora) references tbAseguradora(ID_Aseguradora)
on delete cascade,
constraint FK_Centro_Servicio foreign key (ID_Centro) references tbCentroMedico(ID_Centro)
on delete cascade
);

INSERT ALL
INTO tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) VALUES ('Chequeo General', 30.00, 1, 2)
INTO tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) VALUES ('Terapia Respiratoria', 50.00, 3, 4)
INTO tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) VALUES ('Limpieza Bucal', 65.00, 5, 2)
INTO tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) VALUES ('Ecografía', 200.00, 2, 1)
INTO tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) VALUES ('Radiografía', 40.00, 3, 3)
SELECT DUMMY FROM dual;

create TABLE tbSeguro
(
ID_Seguro NUMBER PRIMARY KEY,
carnetSeguro VARCHAR2(20) not null unique,
poliza VARCHAR2(60) not null unique,
ID_Aseguradora NUMBER not null,
constraint FK_Aseguradora_Seguro foreign key (ID_Aseguradora) references tbAseguradora(ID_Aseguradora)
on delete cascade
);

INSERT ALL
INTO tbSeguro (carnetSeguro, poliza, ID_Aseguradora) VALUES ('A2345B', '1234567890', 1)
INTO tbSeguro (carnetSeguro, poliza, ID_Aseguradora) VALUES ('C5678D', 'POL-987654', 2)
INTO tbSeguro (carnetSeguro, poliza, ID_Aseguradora) VALUES ('X1234Y', 'APL-567890', 3)
INTO tbSeguro (carnetSeguro, poliza, ID_Aseguradora) VALUES ('M8901N', 'SEG-234567', 4)
INTO tbSeguro (carnetSeguro, poliza, ID_Aseguradora) VALUES ('P4567Q', 'INS-789012', 5)
SELECT DUMMY FROM dual;

create TABLE tbUsuario
(
ID_Usuario NUMBER PRIMARY KEY,
nombreUsuario VARCHAR2(50) not null,
apellidoUsuario VARCHAR2(50) not null,
emailUsuario VARCHAR2(50) not null unique,
contrasena VARCHAR2(30) not null,
direccion VARCHAR2(200) not null,
sexo char(1) check (sexo in ('M', 'F')) not null,
fechaNacimiento date not null,
imgUsuario blob,
ID_TipoUsuario NUMBER not null,
ID_Seguro NUMBER not null,
constraint FK_TipoUsuario foreign key (ID_TipoUsuario) references tbTipoUsuario(ID_TipoUsuario)
on delete cascade,
constraint FK_Seguro foreign key (ID_Seguro) references tbSeguro(ID_Seguro)
on delete cascade
);

create TABLE tbReview
(
ID_Review NUMBER PRIMARY KEY,
nombreCentro VARCHAR2(50) not null,
promEstrellas NUMBER(5) not null,
comentario varchar(200),
ID_Usuario NUMBER not null,
constraint FK_Usuario_Review foreign key (ID_Usuario) references tbUsuario(ID_Usuario)
on delete cascade
);

INSERT ALL
INTO tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) VALUES ('Clinica Ginecológica Salvadoreña', 4, 'El personal fue muy atento y amable durante toda mi visita. Me sentí bien cuidado y escuchado', 1)
INTO tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) VALUES ('Clinica Ginecológica Salvadoreña', 4, 'El médico mostró un alto nivel de conocimiento y habilidad en su especialidad. Explicó claramente mi diagnóstico y el plan de tratamiento', 4)
INTO tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) VALUES ('Clinica Ginecológica Salvadoreña', 4, 'Las instalaciones estaban limpias y bien mantenidas, lo cual es fundamental para la comodidad y seguridad de los pacientes', 2)
INTO tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) VALUES ('Clinica Ginecológica Salvadoreña', 4, 'El tiempo de espera fue mínimo, lo cual fue muy apreciado. La clínica parece estar bien organizada en cuanto a gestionar las citas', 5)
INTO tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) VALUES ('Clinica Ginecológica Salvadoreña', 4, 'La ubicación del centro médico es conveniente y fácil de encontrar. Además, el estacionamiento estaba disponible y accesible', 3)
SELECT DUMMY FROM dual;

create TABLE tbNoti
(
ID_Notificacion NUMBER PRIMARY KEY,
fechaNoti date not null,
tipoNoti char(1) not null,
mensajeNoti VARCHAR2(200) not null,
flag char(1) check (flag in ('S', 'N')) not null,
ID_Usuario NUMBER not null,
ID_TipoNoti NUMBER not null,
constraint FK_Usuario_Noti foreign key (ID_Usuario) references tbUsuario(ID_Usuario)
on delete cascade,
constraint FK_TipoNoti foreign key (ID_TipoNoti) references tbTipoNoti(ID_TipoNoti)
on delete cascade
);

INSERT ALL
INTO tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) VALUES ('2024-06-18', 'Recuerda tu cita médica con el Dr. Huezo mañana a las 08:30 a.m.', 'S', 1, 1)
INTO tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) VALUES ('2024-06-19', 'Tus resultados de laboratorio están listos para ser revisados en la app. Accede ahora.', 'N', 2, 2)
INTO tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) VALUES ('2024-06-20', 'Es hora de tomar tu medicamento Salbutamol.', 'N', 3, 3)
INTO tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) VALUES ('2024-06-21', 'Recuerda programar tu seguimiento post-tratamiento con el especialista.', 'S', 4, 4)
INTO tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) VALUES ('2024-06-22', 'Hemos actualizado nuestras políticas de privacidad. Revísalas en la app para estar al tanto.', 'N', 5, 5)
SELECT DUMMY FROM dual;

create TABLE tbPaciente
(
ID_Paciente NUMBER PRIMARY KEY,
nombrePaciente VARCHAR2(50) not null,
apellidoPaciente VARCHAR2(50) not null,
imgPaciete blob,
parentesco VARCHAR2(30),
ID_Usuario NUMBER not null,
constraint FK_Usuario_Paciente foreign key (ID_Usuario) references tbUsuario(ID_Usuario)
on delete cascade
);

INSERT ALL 
INTO tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) VALUES ('Rocío', 'Molina', 'img.Rocio', 'Sobrina', 1)
INTO tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) VALUES ('Camila', 'Orellana', 'img.Camila', 'Tía', 2)
INTO tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) VALUES ('Mónica', 'Barahona', 'img.Monica', 'Prima', 3)
INTO tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) VALUES ('Nelson', 'Sandoval', 'img.Nelson', 'Hermano', 4)
INTO tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) VALUES ('Daniel', 'Brizuela', 'img.Daniel', 'Nieto', 5)
SELECT DUMMY FROM dual;

create TABLE tbExpediente
(
ID_Expediente NUMBER PRIMARY KEY,
antecedentes VARCHAR2(200) not null,
nombrePadre VARCHAR2(50),
nombreMadre VARCHAR2(50),
responsable VARCHAR2(50),
permaMedicamentos VARCHAR2(100) not null,
presionArterial VARCHAR2(20) not null,
peso decimal(4,2) not null,
altura NUMBER(3) not null,
contactoEmer VARCHAR2(12) not null,
saturacion NUMBER(3) not null,
historial VARCHAR2(200) not null,
tipoSangre VARCHAR2(10) not null,
fechaConsultas date not null,
ID_Paciente NUMBER not null,
constraint FK_Paciente_Expe foreign key (ID_Paciente) references tbPaciente(ID_Paciente)
on delete cascade
);

create TABLE tbCitaMedica
(
ID_Cita NUMBER PRIMARY KEY,
diaCita date not null,
horaCita timestamp not null,
motivo varchar(150) not null,
ID_Centro NUMBER not null,
ID_Paciente NUMBER not null,
constraint FK_Centro_Cita foreign key (ID_Centro) references tbCentroMedico(ID_Centro)
on delete cascade,
constraint FK_Paciente_Cita foreign key (ID_Paciente) references tbPaciente(ID_Paciente)
on delete cascade
);
INSERT ALL
INTO tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) VALUES ('2024-06-18', TimesTamp'2024-06-18 07:00:00.000000', 'Fractura', 1, 1)
INTO tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) VALUES ('2024-06-18', TimesTamp'2024-06-18 07:30:00.000000', 'Dolor Abdominal', 2, 2)
INTO tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) VALUES ('2024-06-18', TimesTamp'2024-06-18 08:00:00.000000', 'Cefalea', 3, 3)
INTO tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) VALUES ('2024-06-18', TimesTamp'2024-06-18 08:30:00.000000', 'Hemorragia Nasal', 4, 4)
INTO tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) VALUES ('2024-06-18', TimesTamp'2024-06-18 09:00:00.000000', 'Esguince', 5, 5)
SELECT DUMMY FROM dual;

create TABLE tbIndicacion
(
ID_Indicacion NUMBER PRIMARY KEY,
duracionMedi timestamp not null,
frecuenciaMedi timestamp not null,
dosisMedi NUMBER not null,
medicina VARCHAR2(90) not null,
horaMedi timestamp not null,
detalleIndi VARCHAR2(250) not null,
ID_Receta NUMBER not null,
ID_Tiempo NUMBER not null,
constraint FK_Tiempo foreign key (ID_Tiempo) references tbTiempo(ID_Tiempo)
on delete cascade,
constraint FK_Receta_Indi foreign key (ID_Receta) references tbReceta(ID_Receta)
on delete cascade
);



create TABLE tbFichaMedica
(
ID_Ficha NUMBER PRIMARY KEY,
diagnostico VARCHAR2(200) not null,
tratamiento VARCHAR2(200) not null,
sintomas VARCHAR2(150) not null,
fechaFicha date not null,
ID_Receta NUMBER not null,
ID_Cita NUMBER not null,
constraint FK_Receta_Ficha foreign key (ID_Receta) references tbReceta(ID_Receta)
on delete cascade,
constraint FK_Cita foreign key (ID_Cita) references tbCitaMedica(ID_Cita)
on delete cascade
);

--Creacion de sequencias--

create sequence tipoUsuarios start with 1 increment by 1;
create sequence tiempos start with 1 increment by 1;
create sequence tipoSucursales start with 1 increment by 1;
create sequence especialidades start with 1 increment by 1;
create sequence establecimientos start with 1 increment by 1;
create sequence aseguradoras start with 1 increment by 1;
create sequence recetas start with 1 increment by 1;
create sequence doctores start with 1 increment by 1;
create sequence sucursales start with 1 increment by 1;
create sequence centros start with 1 increment by 1;
create sequence horarios start with 1 increment by 1;
create sequence seguros start with 1 increment by 1;
create sequence usuarios start with 1 increment by 1;
create sequence reviews start with 1 increment by 1;
create sequence notis start with 1 increment by 1;
create sequence pacientes start with 1 increment by 1;
create sequence expedientes start with 1 increment by 1;
create sequence citas start with 1 increment by 1;
create sequence indicaciones start with 1 increment by 1;
create sequence fichas start with 1 increment by 1;

--Creacion de triggers--

create trigger Trigger_TipoUsuario before insert on tbTipoUsuario for each row begin SELECT tipoUsuarios.nextval INTO : new.ID_TipoUsuario FROM dual;end;
create trigger Trigger_Tiempo before insert on tbTiempo for each row begin SELECT tiempos.nextval INTO : new.ID_Tiempo FROM dual;end;
create trigger Trigger_TipoSucursal before insert on tbTipoSucursal for each row begin SELECT tipoSucursales.nextval INTO : new.ID_TipoSucursal FROM dual;end;
create trigger Trigger_Especialidad before insert on tbEspecialidad for each row begin SELECT especialidades.nextval INTO : new.ID_Especialidad FROM dual;end;
create trigger Trigger_Establecimiento before insert on tbEstablecimiento for each row begin SELECT establecimientos.nextval INTO : new.ID_Establecimiento FROM dual;end;
create trigger Trigger_Aseguradora before insert on tbAseguradora for each row begin SELECT aseguradoras.nextval INTO : new.ID_Aseguradora FROM dual;end;
create trigger Trigger_Receta before insert on tbReceta for each row begin SELECT recetas.nextval INTO : new.ID_Receta FROM dual;end;
create trigger Trigger_Doctor before insert on tbDoctor for each row begin SELECT doctores.nextval INTO : new.ID_Doctor FROM dual;end;
create trigger Trigger_Sucursal before insert on tbSucursal for each row begin SELECT sucursales.nextval INTO : new.ID_Sucursal FROM dual;end;
create trigger Trigger_CentroMedico before insert on tbCentroMedico for each row begin SELECT centros.nextval INTO : new.ID_Centro FROM dual;end;
create trigger Trigger_Horario before insert on tbHorario for each row begin SELECT horarios.nextval INTO : new.ID_Horario FROM dual;end;
create trigger Trigger_Seguro before insert on tbSeguro for each row begin SELECT seguros.nextval INTO : new.ID_Seguro FROM dual;end;
create trigger Trigger_Usuario before insert on tbUsuario for each row begin SELECT usuarios.nextval INTO : new.ID_Usuario FROM dual;end;
create trigger Trigger_Review before insert on tbReview for each row begin SELECT reviews.nextval INTO : new.ID_Review FROM dual;end;
create trigger Trigger_Noti before insert on tbNoti for each row begin SELECT notis.nextval INTO : new.ID_Notificacion FROM dual;end;
create trigger Trigger_Paciente before insert on tbPaciente for each row begin SELECT pacientes.nextval INTO : new.ID_Paciente FROM dual;end;
create trigger Trigger_Expediente before insert on tbExpediente for each row begin SELECT expedientes.nextval INTO : new.ID_Expediente FROM dual;end;
create trigger Trigger_CitaMedica before insert on tbCitaMedica for each row begin SELECT citas.nextval INTO : new.ID_Cita FROM dual;end;
create trigger Trigger_Indicacion before insert on tbIndicacion for each row begin SELECT indicaciones.nextval INTO : new.ID_Indicacion FROM dual;end;
create trigger Trigger_Ficha before insert on tbFichaMedica for each row begin SELECT fichas.nextval INTO : new.ID_Ficha FROM dual;end;


ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';
/*
drop TABLE tbTipoUsuarios;
drop TABLE tbTiempo;
drop TABLE tbTipoSucursal;
drop TABLE tbEspecialidad;
drop TABLE tbEstablecimiento;
drop TABLE tbAseguradora;
drop TABLE tbReceta;
drop TABLE tbDoctor;
drop TABLE tbSucursal;
drop TABLE tbCentroMedico;
drop TABLE tbHorario;
drop TABLE tbSeguro;
drop TABLE tbUsuario;
drop TABLE tbReview;
drop TABLE tbNoti;
drop TABLE tbPaciente;
drop TABLE tbExpediente;
drop TABLE tbCitaMedica;
drop TABLE tbIndicaciones;
drop TABLE tbFichaMedica;

drop sequence tipoUsuarios;
drop sequence tiempos;
drop sequence tipoSucursales;
drop sequence especialidades;
drop sequence establecimientos;
drop sequence aseguradoras;
drop sequence recetas;
drop sequence doctores;
drop sequence sucursales;
drop sequence centros;
drop sequence horarios;
drop sequence seguros;
drop sequence usuarios;
drop sequence reviews;
drop sequence notis;
drop sequence pacientes;
drop sequence expedientes;
drop sequence citas;
drop sequence indicaciones;
drop sequence fichas;

drop trigger Trigger_TipoUsuario;
drop trigger Trigger_Tiempo;
drop trigger Trigger_TipoSucursal;
drop trigger Trigger_Especialidad;
drop trigger Trigger_Establecimiento;
drop trigger Trigger_Aseguradora;
drop trigger Trigger_Receta;
drop trigger Trigger_Doctor;
drop trigger Trigger_Sucursal;
drop trigger Trigger_CentroMedico;
drop trigger Trigger_Horario;
drop trigger Trigger_Seguro;
drop trigger Trigger_Usuario;
drop trigger Trigger_Review;
drop trigger Trigger_Noti;
drop trigger Trigger_Paciente;
drop trigger Trigger_Expediente;
drop trigger Trigger_CitaMedica;
drop trigger Trigger_Indicacion;
drop trigger Trigger_Ficha;

SELECT DUMMY FROM tbTiempo;
*/