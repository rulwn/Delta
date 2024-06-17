--Creación de usuario primer uso--
/*
alter session set "_ORACLE_SCRIPT" = true;
create user DeltaMed identified by "deltaTeam1";
grant "CONNECT" to DeltaMed
*/
--Creacion de tablas independientes--

create table tbTipoUsuario
(
ID_TipoUsuario number primary key,
nombreTipoUsuario varchar2(50) not null unique
);

create table tbTiempo
(
ID_Tiempo number primary key,
lapsosTiempo timestamp not null
);

create table tbTipoSucursal
(
ID_TipoSucursal number primary key,
nombreTipoSucursal varchar2(50) not null unique
);

create table tbEspecialidad
(
ID_Especialidad number primary key,
nombreEspecialidad varchar2(60) not null unique,
nuevaEspecialidad varchar2(60) unique
);

create table tbEstablecimiento
(
ID_Establecimiento number primary key,
nombreClinica varchar2(50) not null unique,
imgPrincipal varchar2(256) not null unique
);

create table tbAseguradora
(
ID_Aseguradora number primary key,
nombreAseguradora varchar2(50) not null unique
);

create table tbReceta
(
ID_Receta number primary key,
fechaReceta date not null,
ubicacionPDF blob
);

--Creacion de tablas dependientes--

create table tbDoctor
(
ID_Doctor number primary key,
codProfesional varchar2(12) not null,
ID_Especialidad number not null,
constraint FK_Especialidad foreign key (ID_Especialidad) references tbEspecialidad(ID_Especialidad)
on delete cascade
);

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

create table tbCentroMedico
(
ID_Centro number primary key,
favorito char(1) check (favorito in ('T', 'F')) not null,
ID_Doctor number not null,
ID_Sucursal number not null,
constraint FK_Doctor foreign key (ID_Doctor) references tbDoctor(ID_Doctor)
on delete cascade,
constraint FK_Sucursal foreign key (ID_Sucursal) references tbSucursal(ID_Sucursal)
on delete cascade
);

create table tbHorario
(
ID_Horario number primary key,
horaInicio timestamp not null unique,
horaSalida timestamp not null unique,
dias date not null,
exclusiones date not null,
almuerzo timestamp not null,
descansos date not null,
lapsosCita timestamp not null,
ID_Centro number not null,
constraint FK_Centro_Horario foreign key (ID_Centro) references tbCentroMedico(ID_Centro)
on delete cascade
);

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

create table tbSeguro
(
ID_Seguro number primary key,
carnetSeguro varchar2(20) not null unique,
poliza varchar2(60) not null unique,
ID_Aseguradora number not null,
constraint FK_Aseguradora_Seguro foreign key (ID_Aseguradora) references tbAseguradora(ID_Aseguradora)
on delete cascade
);

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

create table tbNoti
(
ID_Notificacion number primary key,
fechaNoti date not null,
mensajeNoti varchar2(200) not null,
flag char(1) check (flag in ('S', 'N')) not null,
ID_Usuario number not null,
constraint FK_Usuario_Noti foreign key (ID_Usuario) references tbUsuario(ID_Usuario)
on delete cascade
);

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
*/