--Creación de usuario primer uso--
/*
alter session set "_ORACLE_SCRIPT" = true;
create user DeltaMed identified by "deltaTeam1";
grant "CONNECT" to DeltaMed
*/
--Creacion de tablas independientes--

create table tbTipoNoti
(
ID_TipoNoti number primary key,
nombreTipoNoti varchar2(25)
);

Insert all
into tbTipoNoti (nombreTipoNoti) values ('Avisos')
into tbTipoNoti (nombreTipoNoti) values ('Recordatorio')
into tbTipoNoti (nombreTipoNoti) values ('Confirmación')
into tbTipoNoti (nombreTipoNoti) values ('Configuración')
into tbTipoNoti (nombreTipoNoti) values ('Recetas')
select * from dual;

create table tbTipoUsuario
(
ID_TipoUsuario number primary key,
nombreTipoUsuario varchar2(50) not null unique
);

Insert all
into tbTipoUsuario (nombreTipoUsuario) values ('Administrador de Establecimientos')
into tbTipoUsuario (nombreTipoUsuario) values ('Doctor')
into tbTipoUsuario (nombreTipoUsuario) values ('Paciente')
select * from dual;

create table tbTiempo
(
ID_Tiempo number primary key,
lapsosTiempo timestamp not null
);

Insert all
into tbTiempo (lapsosTiempo) values (TimesTamp '2024-06-18 14:35:10.000000')
into tbTiempo (lapsosTiempo) values (TimesTamp '2024-05-25 13:24:50.000000')
into tbTiempo (lapsosTiempo) values (TimesTamp '2024-06-19 14:17:20.000000')
into tbTiempo (lapsosTiempo) values (TimesTamp '2024-03-06 16:33:40.000000')
into tbTiempo (lapsosTiempo) values (TimesTamp '2024-06-12 19:25:20.000000')
select * from dual;

create table tbTipoSucursal
(
ID_TipoSucursal number primary key,
nombreTipoSucursal varchar2(50) not null unique
);

Insert all
into tbTipoSucursal (nombreTipoSucursal) values ('Clinica General')
into tbTipoSucursal (nombreTipoSucursal) values ('Clinica Especializada')
select * from dual;

create table tbEspecialidad
(
ID_Especialidad number primary key,
nombreEspecialidad varchar2(60) not null unique,
nuevaEspecialidad varchar2(60) unique
);

Insert all
into tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) values ('Pediatrìa', '')
into tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) values ('Neonatología', '')
into tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) values ('Cardiología', '')
into tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) values ('Ortopedia', '')
into tbEspecialidad (nombreEspecialidad, nuevaEspecialidad) values ('Odontología', '')
select * from dual;

create table tbEstablecimiento
(
ID_Establecimiento number primary key,
nombreClinica varchar2(50) not null unique,
imgPrincipal varchar2(256) not null unique
);

Insert all
into tbEstablecimiento (nombreClinica, imgPrincipal) values ('Sonrisas', 'img.ClinicaSonrisas')
into tbEstablecimiento (nombreClinica, imgPrincipal) values ('Pronefro', 'img.ClinicaPronefro')
into tbEstablecimiento (nombreClinica, imgPrincipal) values ('Medicare', 'img.ClinicaMedicare')
into tbEstablecimiento (nombreClinica, imgPrincipal) values ('La Plus Belle', 'img.ClinicaLaPlusBelle')
into tbEstablecimiento (nombreClinica, imgPrincipal) values ('Ecomedic', 'img.ClinicaEcomedic')
select * from dual;

create table tbAseguradora
(
ID_Aseguradora number primary key,
nombreAseguradora varchar2(50) not null unique
);

Insert all
into tbAseguradora (nombreAseguradora) values ('MAPFRE')
into tbAseguradora (nombreAseguradora) values ('SISA')
into tbAseguradora (nombreAseguradora) values ('ACSA MED')
into tbAseguradora (nombreAseguradora) values ('ATLÁNTIDA VIDA')
into tbAseguradora (nombreAseguradora) values ('ASESUISA')
select * from dual;

create table tbReceta
(
ID_Receta number primary key,
fechaReceta date not null,
ubicacionPDF blob
);

Insert all
into tbReceta (fechaReceta, ubicacionPDF) values ('26-04-2024','')
into tbReceta (fechaReceta, ubicacionPDF) values ('20-03-2023','')
into tbReceta (fechaReceta, ubicacionPDF) values ('24-05-2023','')
into tbReceta (fechaReceta, ubicacionPDF) values ('04-01-2024','')
into tbReceta (fechaReceta, ubicacionPDF) values ('23-05-2024','')
select * from dual;

--Creacion de tablas dependientes--

create table tbDoctor
(
ID_Doctor number primary key,
codProfesional varchar2(12) not null,
ID_Especialidad number not null,
constraint FK_Especialidad foreign key (ID_Especialidad) references tbEspecialidad(ID_Especialidad)
on delete cascade
);

Insert all
into tbDoctor (codProfesional, ID_Especialidad) values ('JVPM12345')
into tbDoctor (codProfesional, ID_Especialidad) values ('JVPM67890')
into tbDoctor (codProfesional, ID_Especialidad) values ('JVPM23456')
into tbDoctor (codProfesional, ID_Especialidad) values ('JVPM78901')
into tbDoctor (codProfesional, ID_Especialidad) values ('JVPM34567')
select * from dual;

create table tbSucursal
(
ID_Sucursal number primary key,
nombreSucursal varchar2(60) not null,
codSucursal number(8) not null unique,
emailSucur varchar2(30) not null unique,
telefonoSucur varchar2(12) not null unique,
direccionSucur varchar2(200) not null unique,
ubicacionSucur blob not null,
whatsapp number(12),
ID_Establecimiento number not null,
ID_TipoSucursal number not null,
constraint FK_Establecimiento foreign key (ID_Establecimiento) references tbEstablecimiento(ID_Establecimiento)
on delete cascade,
constraint FK_TipoSucursal foreign key (ID_TipoSucursal) references tbTipoSucursal(ID_TipoSucursal)
on delete cascade
);

Insert all
into tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) values ('Clínica Ginecológica, San Salvador', 23565687, 'clinica_ginecologica@gmail.com', '2264-7856', '25 Av. Norte, Colonia Médica, San Salvador', 'ubicacionSucur.Ginecologica', '7589-4365', 1, 2)
into tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) values ('Clínica Asistencial Salvadoreña, Santa Ana', 67542976, 'clinica_asistencial@gmail.com', '2264-6576', 'Calle Libertad y Avenida Independencia, Santa Ana', 'ubicacionSucur.Asistencial', '7559-4365', 4, 5)
into tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) values ('Hospital de Diagnóstico, San Salvador', 99076456, 'hospital_diagnostico@gmail.com', '2264-7887', '79 Av. Norte y 11 Calle Poniente, Colonia Escalón, San Salvador', 'ubicacionSucur.Diagnostico', '7589-4635', 3, 1)
into tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) values ('Centro Médico Escalón, San Salvador', 23565604, 'medico_escalon@gmail.com', '2235-7856', '85 Av. Norte y Calle Juan José Cañas, Colonia Escalón, San Salvador', 'ubicacionSucur.Escalon', '7589-4230', 4, 2)
into tbSucursal (nombreSucursal, codSucursal, emailSucur, telefonoSucur, direccionSucur, ubicacionSucur, whatsapp, ID_Establecimiento, ID_TipoSucursal) values ('Hospital La Divina Providencia, San Salvador', 23565607, 'divina_providencia@gmail.com', '2234-2956', 'Avenida Masferrer Norte, Colonia Escalón, San Salvador', 'ubicacionSucur.Providencia', '7589-3585', 2, 3)
select * from dual;

create table tbCentroMedico
(
ID_Centro number primary key,
favorito char(1) check (favorito in ('T', 'F')) not null,
ID_Doctor number not null unique,
ID_Sucursal number not null,
constraint FK_Doctor foreign key (ID_Doctor) references tbDoctor(ID_Doctor)
on delete cascade,
constraint FK_Sucursal foreign key (ID_Sucursal) references tbSucursal(ID_Sucursal)
on delete cascade
);

Insert all
into tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) values ('T', 1, 3)
into tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) values ('F', 2, 5)
into tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) values ('F', 5, 3)
into tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) values ('T', 3, 4)
into tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) values ('F', 4, 1)
select * from dual;


create table tbHorario
(
ID_Horario number primary key,
horaInicio timestamp not null unique,
horaSalida timestamp not null unique,
dias date not null,
exclusiones date not null,
almuerzo timestamp not null,
descansos date not null,
lapsosCita number(2) not null,
ID_Centro number not null,
constraint FK_Centro_Horario foreign key (ID_Centro) references tbCentroMedico(ID_Centro)
on delete cascade
);

Insert all
into tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) values (TimesTamp'2024-06-18 07:00:00.000000', TimesTamp'2024-06-18 19:00:00.000000', '2024-06-18', '2024-06-17', TimesTamp'2024-06-18 12:00:00.000000', '2024-06-16', 30, 1)
into tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) values (TimesTamp'2024-06-19 07:00:00.000000', TimesTamp'2024-06-19 19:00:00.000000', '2024-06-19', '2024-05-10', TimesTamp'2024-06-18 12:00:00.000000', '2024-06-23', 30, 2)
into tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) values (TimesTamp'2024-06-20 07:00:00.000000', TimesTamp'2024-06-20 19:00:00.000000', '2024-06-20', '2024-05-03', TimesTamp'2024-06-18 12:00:00.000000', '2024-06-30', 30, 3)
into tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) values (TimesTamp'2024-06-21 07:00:00.000000', TimesTamp'2024-06-21 19:00:00.000000', '2024-06-21', '2024-09-15', TimesTamp'2024-06-18 12:00:00.000000', '2024-07-07', 30, 4)
into tbHorario (horaInicio, horaSalida, dias, exclusiones, almuerzo, descansos, lapsosCita, ID_Centro) values (TimesTamp'2024-06-22 07:00:00.000000', TimesTamp'2024-06-22 19:00:00.000000', '2024-06-22', '2024-11-02', TimesTamp'2024-06-18 12:00:00.000000', '2024-07-14', 30, 5)
select * from dual;

create table tbServicio
(
ID_Servicio number primary key,
nombreServicio varchar2(60) not null unique,
costo decimal(10,2) not null,
ID_Aseguradora number not null,
ID_Centro number not null,
constraint FK_Aseguradora_Servicio foreign key (ID_Aseguradora) references tbAseguradora(ID_Aseguradora)
on delete cascade,
constraint FK_Centro_Servicio foreign key (ID_Centro) references tbCentroMedico(ID_Centro)
on delete cascade
);

Insert all
into tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) values ('Chequeo General', 30.00, 1, 2)
into tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) values ('Terapia Respiratoria', 50.00, 3, 4)
into tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) values ('Limpieza Bucal', 65.00, 5, 2)
into tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) values ('Ecografía', 200.00, 2, 1)
into tbServicio (nombreServicio, costo, ID_Aseguradora, ID_Centro) values ('Radiografía', 40.00, 3, 3)
select * from dual;

create table tbSeguro
(
ID_Seguro number primary key,
carnetSeguro varchar2(20) not null unique,
poliza varchar2(60) not null unique,
ID_Aseguradora number not null,
constraint FK_Aseguradora_Seguro foreign key (ID_Aseguradora) references tbAseguradora(ID_Aseguradora)
on delete cascade
);

Insert all
into tbSeguro (carnetSeguro, poliza, ID_Aseguradora) values ('A2345B', '1234567890', 1)
into tbSeguro (carnetSeguro, poliza, ID_Aseguradora) values ('C5678D', 'POL-987654', 2)
into tbSeguro (carnetSeguro, poliza, ID_Aseguradora) values ('X1234Y', 'APL-567890', 3)
into tbSeguro (carnetSeguro, poliza, ID_Aseguradora) values ('M8901N', 'SEG-234567', 4)
into tbSeguro (carnetSeguro, poliza, ID_Aseguradora) values ('P4567Q', 'INS-789012', 5)
select * from dual;

create table tbUsuario
(
ID_Usuario number primary key,
nombreUsuario varchar2(50) not null,
apellidoUsuario varchar2(50) not null,
emailUsuario varchar2(50) not null unique,
contrasena varchar2(30) not null,
direccion varchar2(200) not null,
sexo char(1) check (sexo in ('M', 'F')) not null,
fechaNacimiento date not null,
imgUsuario blob,
ID_TipoUsuario number not null,
ID_Seguro number not null,
constraint FK_TipoUsuario foreign key (ID_TipoUsuario) references tbTipoUsuario(ID_TipoUsuario)
on delete cascade,
constraint FK_Seguro foreign key (ID_Seguro) references tbSeguro(ID_Seguro)
on delete cascade
);

create table tbReview
(
ID_Review number primary key,
nombreCentro varchar2(50) not null,
promEstrellas number(5) not null,
comentario varchar(200),
ID_Usuario number not null,
constraint FK_Usuario_Review foreign key (ID_Usuario) references tbUsuario(ID_Usuario)
on delete cascade
);

Insert all
into tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) values ('Clinica Ginecológica Salvadoreña', 4, 'El personal fue muy atento y amable durante toda mi visita. Me sentí bien cuidado y escuchado', 1)
into tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) values ('Clinica Ginecológica Salvadoreña', 4, 'El médico mostró un alto nivel de conocimiento y habilidad en su especialidad. Explicó claramente mi diagnóstico y el plan de tratamiento', 4)
into tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) values ('Clinica Ginecológica Salvadoreña', 4, 'Las instalaciones estaban limpias y bien mantenidas, lo cual es fundamental para la comodidad y seguridad de los pacientes', 2)
into tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) values ('Clinica Ginecológica Salvadoreña', 4, 'El tiempo de espera fue mínimo, lo cual fue muy apreciado. La clínica parece estar bien organizada en cuanto a gestionar las citas', 5)
into tbReview (nombreCentro, promEstrellas, comentario, ID_Usuario) values ('Clinica Ginecológica Salvadoreña', 4, 'La ubicación del centro médico es conveniente y fácil de encontrar. Además, el estacionamiento estaba disponible y accesible', 3)
select * from dual;

create table tbNoti
(
ID_Notificacion number primary key,
fechaNoti date not null,
tipoNoti char(1) not null,
mensajeNoti varchar2(200) not null,
flag char(1) check (flag in ('S', 'N')) not null,
ID_Usuario number not null,
ID_TipoNoti number not null,
constraint FK_Usuario_Noti foreign key (ID_Usuario) references tbUsuario(ID_Usuario)
on delete cascade,
constraint FK_TipoNoti foreign key (ID_TipoNoti) references tbTipoNoti(ID_TipoNoti)
on delete cascade
);

Insert all
into tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) values ('2024-06-18', 'Recuerda tu cita médica con el Dr. Huezo mañana a las 08:30 a.m.', 'S', 1, 1)
into tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) values ('2024-06-19', 'Tus resultados de laboratorio están listos para ser revisados en la app. Accede ahora.', 'N', 2, 2)
into tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) values ('2024-06-20', 'Es hora de tomar tu medicamento Salbutamol.', 'N', 3, 3)
into tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) values ('2024-06-21', 'Recuerda programar tu seguimiento post-tratamiento con el especialista.', 'S', 4, 4)
into tbNoti (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti) values ('2024-06-22', 'Hemos actualizado nuestras políticas de privacidad. Revísalas en la app para estar al tanto.', 'N', 5, 5)
select * from dual;

create table tbPaciente
(
ID_Paciente number primary key,
nombrePaciente varchar2(50) not null,
apellidoPaciente varchar2(50) not null,
imgPaciete blob,
parentesco varchar2(30),
ID_Usuario number not null,
constraint FK_Usuario_Paciente foreign key (ID_Usuario) references tbUsuario(ID_Usuario)
on delete cascade
);

Insert all 
into tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) values ('Rocío', 'Molina', 'img.Rocio', 'Sobrina', 1)
into tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) values ('Camila', 'Orellana', 'img.Camila', 'Tía', 2)
into tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) values ('Mónica', 'Barahona', 'img.Monica', 'Prima', 3)
into tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) values ('Nelson', 'Sandoval', 'img.Nelson', 'Hermano', 4)
into tbPaciente (nombrePaciente, apellidoPaciente, imgPaciete, parentesco, ID_Usuario) values ('Daniel', 'Brizuela', 'img.Daniel', 'Nieto', 5)
select * from dual;

create table tbExpediente
(
ID_Expediente number primary key,
antecedentes varchar2(200) not null,
nombrePadre varchar2(50),
nombreMadre varchar2(50),
responsable varchar2(50),
permaMedicamentos varchar2(100) not null,
presionArterial varchar2(20) not null,
peso decimal(4,2) not null,
altura number(3) not null,
contactoEmer varchar2(12) not null,
saturacion number(3) not null,
historial varchar2(200) not null,
tipoSangre varchar2(10) not null,
fechaConsultas date not null,
ID_Paciente number not null,
constraint FK_Paciente_Expe foreign key (ID_Paciente) references tbPaciente(ID_Paciente)
on delete cascade
);

create table tbCitaMedica
(
ID_Cita number primary key,
diaCita date not null,
horaCita timestamp not null,
motivo varchar(150) not null,
ID_Centro number not null,
ID_Paciente number not null,
constraint FK_Centro_Cita foreign key (ID_Centro) references tbCentroMedico(ID_Centro)
on delete cascade,
constraint FK_Paciente_Cita foreign key (ID_Paciente) references tbPaciente(ID_Paciente)
on delete cascade
);
Insert all
into tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) values ('2024-06-18', TimesTamp'2024-06-18 07:00:00.000000', 'Fractura', 1, 1)
into tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) values ('2024-06-18', TimesTamp'2024-06-18 07:30:00.000000', 'Dolor Abdominal', 2, 2)
into tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) values ('2024-06-18', TimesTamp'2024-06-18 08:00:00.000000', 'Cefalea', 3, 3)
into tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) values ('2024-06-18', TimesTamp'2024-06-18 08:30:00.000000', 'Hemorragia Nasal', 4, 4)
into tbCitaMedica (diaCita, horaCita, motivo, ID_Centro, ID_Paciente) values ('2024-06-18', TimesTamp'2024-06-18 09:00:00.000000', 'Esguince', 5, 5)
select * from dual;

create table tbIndicacion
(
ID_Indicacion number primary key,
duracionMedi timestamp not null,
frecuenciaMedi timestamp not null,
dosisMedi number not null,
medicina varchar2(90) not null,
horaMedi timestamp not null,
detalleIndi varchar2(250) not null,
ID_Receta number not null,
ID_Tiempo number not null,
constraint FK_Tiempo foreign key (ID_Tiempo) references tbTiempo(ID_Tiempo)
on delete cascade,
constraint FK_Receta_Indi foreign key (ID_Receta) references tbReceta(ID_Receta)
on delete cascade
);



create table tbFichaMedica
(
ID_Ficha number primary key,
diagnostico varchar2(200) not null,
tratamiento varchar2(200) not null,
sintomas varchar2(150) not null,
fechaFicha date not null,
ID_Receta number not null,
ID_Cita number not null,
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

create trigger Trigger_TipoUsuario before insert on tbTipoUsuario for each row begin select tipoUsuarios.nextval into : new.ID_TipoUsuario from dual;end;
create trigger Trigger_Tiempo before insert on tbTiempo for each row begin select tiempos.nextval into : new.ID_Tiempo from dual;end;
create trigger Trigger_TipoSucursal before insert on tbTipoSucursal for each row begin select tipoSucursales.nextval into : new.ID_TipoSucursal from dual;end;
create trigger Trigger_Especialidad before insert on tbEspecialidad for each row begin select especialidades.nextval into : new.ID_Especialidad from dual;end;
create trigger Trigger_Establecimiento before insert on tbEstablecimiento for each row begin select establecimientos.nextval into : new.ID_Establecimiento from dual;end;
create trigger Trigger_Aseguradora before insert on tbAseguradora for each row begin select aseguradoras.nextval into : new.ID_Aseguradora from dual;end;
create trigger Trigger_Receta before insert on tbReceta for each row begin select recetas.nextval into : new.ID_Receta from dual;end;
create trigger Trigger_Doctor before insert on tbDoctor for each row begin select doctores.nextval into : new.ID_Doctor from dual;end;
create trigger Trigger_Sucursal before insert on tbSucursal for each row begin select sucursales.nextval into : new.ID_Sucursal from dual;end;
create trigger Trigger_CentroMedico before insert on tbCentroMedico for each row begin select centros.nextval into : new.ID_Centro from dual;end;
create trigger Trigger_Horario before insert on tbHorario for each row begin select horarios.nextval into : new.ID_Horario from dual;end;
create trigger Trigger_Seguro before insert on tbSeguro for each row begin select seguros.nextval into : new.ID_Seguro from dual;end;
create trigger Trigger_Usuario before insert on tbUsuario for each row begin select usuarios.nextval into : new.ID_Usuario from dual;end;
create trigger Trigger_Review before insert on tbReview for each row begin select reviews.nextval into : new.ID_Review from dual;end;
create trigger Trigger_Noti before insert on tbNoti for each row begin select notis.nextval into : new.ID_Notificacion from dual;end;
create trigger Trigger_Paciente before insert on tbPaciente for each row begin select pacientes.nextval into : new.ID_Paciente from dual;end;
create trigger Trigger_Expediente before insert on tbExpediente for each row begin select expedientes.nextval into : new.ID_Expediente from dual;end;
create trigger Trigger_CitaMedica before insert on tbCitaMedica for each row begin select citas.nextval into : new.ID_Cita from dual;end;
create trigger Trigger_Indicacion before insert on tbIndicacion for each row begin select indicaciones.nextval into : new.ID_Indicacion from dual;end;
create trigger Trigger_Ficha before insert on tbFichaMedica for each row begin select fichas.nextval into : new.ID_Ficha from dual;end;

/*
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';

drop table tbTipoUsuarios;
drop table tbTiempo;
drop table tbTipoSucursal;
drop table tbEspecialidad;
drop table tbEstablecimiento;
drop table tbAseguradora;
drop table tbReceta;
drop table tbDoctor;
drop table tbSucursal;
drop table tbCentroMedico;
drop table tbHorario;
drop table tbSeguro;
drop table tbUsuario;
drop table tbReview;
drop table tbNoti;
drop table tbPaciente;
drop table tbExpediente;
drop table tbCitaMedica;
drop table tbIndicaciones;
drop table tbFichaMedica;

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

select * from tbTiempo;
*/