----- CREACIÓN DE TABLAS ----- 
-- ACABO PARA MARCOA EMPEZO PARA NOSOTROS --

--Relacionadas a Usuarios --
create table Usuarios(
id_Usuario int primary key,
Nombre varchar(50) not null,
Apellido varchar(50) not null,
fecha_Nacimiento date,  
Direccion varchar(250) not null,
email varchar(250) not null,
Contraseña varchar(20) not null,
id_Tipo_Usuario int,
id_Seguro int,
Foto varchar(250),
Sexo char not null
);

create table Doctores(
id_Doctor int primary key,
Nombre varchar(20) not NULL,
Apellido varchar(20) not NULL,
codigo_Profesional varchar(15) not NULL,
imagen_Principal varchar(255),
id_Sucursal int,
id_Usuario int,
id_Especialidad int
);

create table Especialidades(
id_Especialidad int primary key,
Especialidad varchar(30) not NULL
);

create table Tipos_Usuarios(
id_Tipo_Usuario int primary key,
tipo_Usuario varchar(25) not NULL,
acceso_Movil number(1),
acceso_Desktop number(1)
);

create table Seguros(
id_Seguro int primary key,
Aseguradora char(25), 
Poliza char(20),
Carnet int,
id_Usuarios int
);
--Relacionadas a Establecimientos
create table Establecimientos(
id_Establecimiento int primary key,
nombre_Clinica varchar(250) not NULL,
imagen_Principal varchar(250)
);

create table Sucursales(
id_Sucursal int primary key,
nombre_Sucursal char(50) not NULL,
Direccion varchar(150) not NULL,
Ubicacion char(50) not NULL,
Telefono varchar(20),
Email char(25) not NULL,
Whatsapp varchar(20),
Codigo char(7),
Foto varchar(250),
Favorito number(1),
id_Establecimiento int,
id_Tipo_Establecimiento int
);

create table Tipos_Establecimientos(
id_Tipo_Establecimiento int primary key,
tipo_Establecimiento char(25) not NULL
);

create table Honorarios(
id_Honorario int primary key,
Tratamiento varchar(200) not NULL,
Honorario int,
id_Seguro int,
id_Doctor int
);

create table Horarios(
id_Horario int primary key,
Dias date,
hora_Inicio date,
hora_Salida date,
exclusiones date,
Lapsos date,
Almuerzo date,
Descansos date,
id_Doctor int,
id_Sucursal int
);

--Relacionadas a Procesos --
create table Fichas_Medicas(
id_Ficha int primary key,
Fecha timestamp not NULL,
Diagnostico varchar(250) not null,
Tratamiento varchar(250) not null,
Sintomas varchar(250) not null,
id_Seguro int,
id_Usuario int,
id_Doctor int,
id_Sucursal int,
id_Receta int,
id_Honorario int
);

create table Expedientes(
id_Expediente int primary key,
fecha_Receta Date,
Altura number(3,2) not null, --ALTURA EN METROS --
Peso number(5,2) not null, --PESO EN LIBRAS --
tipo_Sangre varchar(3) not null,
nombre_Madre varchar(20),
nombre_Padre varchar(20),
Responsable varchar(20),
presion_Arterial varchar(10) not null,
Saturacion int not null,
contacto_Emergencia varchar(20) not null,
antecedentes_Familiares varchar(250) not null,
antecedentes_Personales varchar(250) not null,
historial_Procedimientos varchar(250) not null,
medicaciones_Permanentes varchar(250) not null,
id_Usuario int,
id_Ficha int,
id_Doctor int
);

create table Recetas(
id_Receta int primary key,
fecha_Receta timestamp
);

create table Citas(
id_Cita int primary key,
Dia date,
Hora date,
id_Usuario int,
id_Doctor int,
id_Sucursal int,
id_Ficha int
);

create table Indicaciones(
id_Indicacion int primary key,
Frecuencia int not null,
horas int not null,
duracion int not null,
indicaciones varchar2(150) not null,
medicina varchar2(100) not null,
id_receta int
);

create table Reviews(
id_Review int primary key,
comentario varchar2(100) not null,
promedio_Estrellas number,
id_Usuario int,
id_Doctor int
);
----- CREACION DE CONSTRAINTS ----- 
alter table Usuarios add constraint FK_Seguros_Usuarios foreign key (id_Seguro) references Seguros (id_Seguro) on delete cascade;
alter table Usuarios add constraint FK_TiposUsuarios_Usuarios foreign key (id_Tipo_Usuario) references Tipos_Usuarios (id_Tipo_Usuario) on delete cascade;
alter table Doctores add constraint FK_Sucursales_Doctores foreign key (id_Sucursal) references Sucursales (id_Sucursal) on delete cascade;
alter table Doctores add constraint FK_Especialidades_Doctores foreign key (id_Especialidad) references Especialidades(id_Especialidad) on delete cascade;
alter table Doctores add constraint FK_Usuarios_Doctores foreign key (id_Usuario) references Usuarios (id_Usuario) on delete cascade;
alter table Sucursales add constraint FK_Establecimientos_Sucursales foreign key (id_Establecimiento) references Establecimientos (id_Establecimiento) on delete cascade;
alter table Sucursales add constraint FK_TiposEstablecimientos_Sucursales foreign key (id_Tipo_Establecimiento) references Tipos_Establecimientos (id_Tipo_Establecimiento) on delete cascade;
alter table Honorarios add constraint FK_Seguros_Honorarios foreign key (id_Seguro) references Seguros(id_Seguro) on delete cascade;
alter table Honorarios add constraint FK_Doctores_Honorarios foreign key (id_Doctor) references Doctores (id_Doctor) on delete cascade;
alter table Fichas_Medicas add constraint FK_Seguros_Fichas foreign key (id_Seguro) references Seguros (id_Seguro) on delete cascade;
alter table Fichas_Medicas add constraint FK_Usuarios_Fichas foreign key (id_Usuario) references Usuarios (id_Usuario) on delete cascade;
alter table Fichas_Medicas add constraint FK_Doctores_Fichas foreign key (id_Doctor) references Doctores (id_Doctor) on delete cascade;
alter table Fichas_Medicas add constraint FK_Sucursales_Fichas foreign key (id_Sucursal) references Sucursales (id_Sucursal) on delete cascade;
alter table Fichas_Medicas add constraint FK_Recetas_Fichas foreign key (id_Receta) references Recetas (id_Receta) on delete cascade;
alter table Fichas_Medicas add constraint FK_Honorarios_Fichas foreign key (id_Honorario) references Honorarios (id_Honorario)on delete cascade;
alter table Expedientes add constraint FK_Usuarios_Expedientes foreign key (id_Usuario) references Usuarios (id_Usuario) on delete cascade;
alter table Expedientes add constraint FK_Doctores_Expedientes foreign key (id_Doctor) references Doctores (id_Doctor) on delete cascade;
alter table Expedientes add constraint FK_Fichas_Expedientes foreign key (id_Ficha) references Fichas_Medicas (id_Ficha) on delete cascade;
alter table Citas add constraint FK_Usuarios_Citas foreign key (id_Usuario) references Usuarios (id_Usuario) on delete cascade;
alter table Citas add constraint FK_Doctores_Citas foreign key (id_Doctor) references Doctores (id_Doctor) on delete cascade;
alter table Citas add constraint FK_Sucursales_Citas foreign key (id_Sucursal) references Sucursales (id_Sucursal) on delete cascade;
alter table Citas add constraint FK_Fichas_Citas foreign key (id_Ficha) references Fichas_Medicas(id_Ficha) on delete cascade;
alter table Horarios add constraint FK_Doctores_Horarios foreign key (id_Doctor) references Doctores (id_Doctor) on delete cascade;
alter table Horarios add constraint FK_Sucursales_Horarios foreign key (id_Sucursal) references Sucursales (id_Sucursal) on delete cascade;
alter table Indicaciones add constraint FK_Recetas_Indicaciones foreign key (id_Receta) references Recetas (id_Receta) on delete cascade;
alter table Reviews add constraint FK_Usuarios_Reviews foreign key (id_Usuario) references Usuarios (id_Usuario) on delete cascade;
alter table Reviews add constraint FK_Doctores_Reviews foreign key (id_Doctor) references Doctores (id_Doctor) on delete cascade;


 select * from user_constraints WHERE CONSTRAINT_NAME LIKE 'FK%' -- PARA ENCONTRAR TODAS LAS FOREIGN KEYS
-- PARA DROPEAR TODO --
/*
alter table citas drop constraint fk_doctores_citas;
alter table expedientes drop constraint fk_doctores_expedientes;
alter table fichas_medicas drop constraint fk_doctores_fichas;
alter table honorarios drop constraint fk_doctores_honorarios;
alter table horarios drop constraint fk_doctores_horarios;
alter table reviews drop constraint fk_doctores_reviews;
alter table doctores drop constraint fk_especialidades_doctores;
alter table sucursales drop constraint fk_establecimientos_sucursales;
alter table citas drop constraint fk_fichas_citas;
alter table expedientes drop constraint fk_fichas_expedientes;
alter table fichas_medicas drop constraint fk_honorarios_fichas;
alter table fichas_medicas drop constraint fk_recetas_fichas;
alter table indicaciones drop constraint fk_recetas_indicaciones;
alter table honorarios drop constraint fk_seguros_honorarios;
alter table fichas_medicas drop constraint fk_seguros_fichas;
alter table honorarios drop constraint fk_seguros_honorarios;
alter table usuarios drop constraint fk_seguros_usuarios;
alter table citas drop constraint fk_sucursales_citas;
alter table fichas_medicas drop constraint fk_sucursales_fichas;
alter table horarios drop constraint fk_sucursales_horarios;
alter table doctores drop constraint fk_sucursales_doctores;
alter table sucursales drop constraint FK_TiposEstablecimientos_Sucursales;
alter table usuarios drop constraint fk_tiposusuarios_usuarios;
alter table citas drop constraint fk_usuarios_citas;
alter table doctores drop constraint fk_usuarios_doctores;
alter table expedientes drop constraint fk_usuarios_expedientes;
alter table fichas_medicas drop constraint fk_usuarios_fichas;
alter table reviews drop constraint fk_usuarios_reviews;
DROP TABLE USUARIOS;
DROP TABLE HONORARIOS;
DROP TABLE REVIEWS;
DROP TABLE INDICACIONES;
DROP TABLE HORARIOS;
DROP TABLE DOCTORES;
DROP TABLE TIPOS_ESTABLECIMIENTOS;
DROP TABLE ESTABLECIMIENTOS;
DROP TABLE EXPEDIENTES;
DROP TABLE FICHAS_MEDICAS;
DROP TABLE CITAS;
DROP TABLE RECETAS;
DROP TABLE SUCURSALES;
DROP TABLE SEGUROS;
DROP TABLE ESPECIALIDADES;
DROP TABLE TIPOS_USUARIOS;
*/
