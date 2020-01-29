create schema sistemasanitario collate latin1_swedish_ci;

use sistemasanitario;

create table medicidibase
(
	codiceFiscale varchar(16) not null
		primary key,
	codiceMedico int not null,
	constraint medicidibase_codiceMedico_uindex
		unique (codiceMedico)
);

create table ssp
(
	provincia varchar(2) not null
		primary key,
	password varchar(30) null
);

create table tipiesame
(
	tipo varchar(100) not null
		primary key,
	categoria varchar(100) null
);

create table utenti
(
	codiceFiscale varchar(16) not null
		primary key,
	password varchar(255) not null,
	nome varchar(30) not null,
	cognome varchar(30) not null,
	dataNascita date not null,
	luogoNascita varchar(100) not null,
	foto varchar(100) default '0.png' null comment 'link alla foto',
	sesso int not null comment '1 = male | 2 = female | 3 = unknown',
	codiceMedicoDiBase int null,
	email varchar(100) not null,
	provincia varchar(2) not null,
	constraint utente_medicoDiBase
		foreign key (codiceMedicoDiBase) references medicidibase (codiceMedico)
			on update cascade on delete cascade
);

create index utente_provincia
	on utenti (provincia);

create table visite
(
	codice int auto_increment
		primary key,
	dataOra datetime not null,
	resoconto varchar(1500) null,
	utente varchar(16) not null,
	codiceMedicoDiBase int not null,
	constraint visita_medicoDiBase
		foreign key (codiceMedicoDiBase) references medicidibase (codiceMedico)
			on update cascade on delete cascade,
	constraint visita_utente
		foreign key (utente) references utenti (codiceFiscale)
			on update cascade on delete cascade
);

create table esami
(
	codice int auto_increment
		primary key,
	ticket decimal(5,2) null,
	referto varchar(1000) null,
	dataOraFissata datetime not null,
	fatto tinyint(1) default 0 not null,
	codiceVisita int not null,
	medico int not null,
	tipo varchar(100) not null,
	constraint esame_medico
		foreign key (medico) references medicidibase (codiceMedico)
			on update cascade on delete cascade,
	constraint esame_tipo
		foreign key (tipo) references tipiesame (tipo)
			on update cascade on delete cascade,
	constraint esame_visita
		foreign key (codiceVisita) references visite (codice)
			on update cascade on delete cascade
);

create table ricette
(
	codice int auto_increment
		primary key,
	farmaco varchar(50) not null,
	quantita int not null,
	codiceVisita int null,
	codiceEsame int null,
	dataOraEvasa datetime null,
	descrizioneFarmaco varchar(100) null,
	provinciaPrescrizione varchar(2) null,
	constraint ricetta_esame
		foreign key (codiceEsame) references esami (codice)
			on update cascade on delete cascade,
	constraint ricetta_ssp
		foreign key (provinciaPrescrizione) references ssp (provincia)
			on update cascade on delete cascade,
	constraint ricetta_visita
		foreign key (codiceVisita) references visite (codice)
			on update cascade on delete cascade
);


/* INSERISCO I DATI */

INSERT INTO sistemasanitario.medicidibase (codiceFiscale, codiceMedico) VALUES ('GLNRRT46T12H194U', 1);
INSERT INTO sistemasanitario.medicidibase (codiceFiscale, codiceMedico) VALUES ('CRNGNN34M15A392P', 2);
INSERT INTO sistemasanitario.medicidibase (codiceFiscale, codiceMedico) VALUES ('MBLNTN93L02L378Y', 3);
INSERT INTO sistemasanitario.medicidibase (codiceFiscale, codiceMedico) VALUES ('PRIGND50R07H842L', 4);
INSERT INTO sistemasanitario.medicidibase (codiceFiscale, codiceMedico) VALUES ('SCHDIE65D61L578K', 5);
INSERT INTO sistemasanitario.medicidibase (codiceFiscale, codiceMedico) VALUES ('TRCGFR64C27E983Z', 6);


INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('BCCMCH98L25B289K', 'ee11cbb19052e40b07aac0ca060c23ee', 'Melchiorre', 'Bacchion', '1998-07-25', 'Vermezzo', '28.jpg', 1, 1, 'bccmch98l25b289k@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('BLLGZN52B24I512G', 'ee11cbb19052e40b07aac0ca060c23ee', 'Graziano', 'Billups', '1952-02-24', 'Burago', '38.jpg', 1, 1, 'bllgzn52b24i512g@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('BLLVNN91D41F287H', 'ee11cbb19052e40b07aac0ca060c23ee', 'Ivonne', 'Bellardinelli', '1991-04-01', 'Lavis', '15.jpg', 2, 3, 'bllvnn91d41f287h@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('BNCSNT44D29B231X', 'ee11cbb19052e40b07aac0ca060c23ee', 'Santo', 'Bancalari', '1944-04-29', 'Brusuglio', '49.jpg', 1, 1, 'bncsnt44d29b231x@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('BNDQRT93B02C521F', 'ee11cbb19052e40b07aac0ca060c23ee', 'Quarto', 'Bendaglia', '1993-02-02', 'Vermezzo', '29.jpg', 1, 1, 'bndqrt93b02c521f@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('BTLMDA49A06A729M', 'ee11cbb19052e40b07aac0ca060c23ee', 'Amedeo', 'Abitelli', '1949-01-06', 'Brusuglio', '39.jpg', 1, 1, 'btlmda49a06a729m@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CCCPRL54H54C110U', 'ee11cbb19052e40b07aac0ca060c23ee', 'Perla', 'Cucciatti', '1954-06-14', 'Prizzi', '10.jpg', 2, 2, 'cccprl54h54c110u@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CDLRNN32E23G843V', 'ee11cbb19052e40b07aac0ca060c23ee', 'Ermanno', 'Codalonga', '1932-05-23', 'Lavis', '26.jpg', 1, 3, 'cdlrnn32e23g843v@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CHMFBR54C48C124G', 'ee11cbb19052e40b07aac0ca060c23ee', 'Filiberta', 'Chemelli', '1954-03-08', 'Giovo', '13.jpg', 2, 6, 'chmfbr54c48c124g@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CLPDRN51C59B827X', 'ee11cbb19052e40b07aac0ca060c23ee', 'Adriana', 'Colipapa', '1951-03-19', 'Cles', '10.jpg', 2, 3, 'clpdrn51c59b827x@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CLSNRE88S41H091J', 'ee11cbb19052e40b07aac0ca060c23ee', 'Nerea', 'Celestre', '1988-11-01', 'Trento', '42.jpg', 2, 3, 'clsnre88s41h091j@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CMPFTN58L59E492A', 'ee11cbb19052e40b07aac0ca060c23ee', 'Fortunata', 'Campestrini', '1958-07-19', 'Carini', '37.jpg', 2, 2, 'cmpftn58l59e492a@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CNTLRT51D03I437F', 'ee11cbb19052e40b07aac0ca060c23ee', 'Liberato', 'Continanza', '1951-04-03', 'Vermezzo', '33.jpg', 1, 1, 'cntlrt51d03i437f@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CRGQTL64L02G698Z', 'ee11cbb19052e40b07aac0ca060c23ee', 'Quintiliano', 'Cerga', '1964-07-02', 'Trento', '50.jpg', 1, 3, 'crgqtl64l02g698z@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CRNGNN34M15A392P', '9a09b4dfda82e3e665e31092d1c3ec8d', 'Giovanni', 'Carnevalis', '1934-08-15', 'Villabate', '58.jpg', 1, 5, 'crngnn34m15a392p@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('CVFLBT88M50G755F', 'ee11cbb19052e40b07aac0ca060c23ee', 'Elisabetta', 'Cavafave', '1988-08-10', 'Carini', '12.jpg', 2, 2, 'cvflbt88m50g755f@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('DRANRN70R63B820Q', 'ee11cbb19052e40b07aac0ca060c23ee', 'Incoronata', 'Daro', '1970-10-23', 'Carpiano', '22.jpg', 2, 1, 'dranrn70r63b820q@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('FNCDRA69M28D292X', 'ee11cbb19052e40b07aac0ca060c23ee', 'Dario', 'Foncellino', '1969-08-28', 'Lavis', '25.jpg', 1, 6, 'fncdra69m28d292x@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('GGLBMN78C19G173Z', 'ee11cbb19052e40b07aac0ca060c23ee', 'Beniamino', 'Gagliardino', '1978-03-19', 'Villabate', '46.jpg', 1, 2, 'gglbmn78c19g173z@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('GLNRRT46T12H194U', '9a09b4dfda82e3e665e31092d1c3ec8d', 'Roberto', 'Galindo', '1946-12-12', 'Vermezzo', '72.jpg', 1, 4, 'glnrrt46t12h194u@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('GNCMNL53M59A336F', 'ee11cbb19052e40b07aac0ca060c23ee', 'Emanuela', 'Gnocchi', '1953-08-19', 'Villabate', '36.jpg', 2, 2, 'gncmnl53m59a336f@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('GNGDNA72A20M110Z', 'ee11cbb19052e40b07aac0ca060c23ee', 'Adone', 'Gengarelli', '1972-01-20', 'Trento', '53.jpg', 1, 3, 'gngdna72a20m110z@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('GRFDNN54P65E163H', 'ee11cbb19052e40b07aac0ca060c23ee', 'Donna', 'Agrifani', '1954-09-25', 'Trento', '43.jpg', 2, 3, 'grfdnn54p65e163h@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('GRSSST01P11G178B', 'ee11cbb19052e40b07aac0ca060c23ee', 'Sebastiano', 'Giurisato', '2001-01-11', 'Brusuglio', '54.jpg', 1, 1, 'grssst01p11g178b@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('HLMZRA36T41C320S', 'ee11cbb19052e40b07aac0ca060c23ee', 'Zaira', 'Uhlmann', '1936-12-01', 'Burago', '21.jpg', 2, 1, 'hlmzra36t41c320s@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('HZLDRN58R69H944L', 'ee11cbb19052e40b07aac0ca060c23ee', 'Adriana', 'Hazell', '1958-10-29', 'Brusuglio', '51.jpg', 2, 1, 'hzldrn58r69h944l@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('LCTCST76L14F137L', 'ee11cbb19052e40b07aac0ca060c23ee', 'Cristiano', 'Leocata', '1976-07-14', 'Burago', '56.jpg', 1, 1, 'lctcst76l14f137l@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('LPLCST52H65I543J', 'ee11cbb19052e40b07aac0ca060c23ee', 'Cristiana', 'Lapalombara', '1952-06-25', 'Prizzi', '31.jpg', 2, 2, 'lplcst52h65i543j@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('LVZLSL95M17F661O', 'ee11cbb19052e40b07aac0ca060c23ee', 'Ladislao', 'Lozivolo', '1995-08-17', 'Carini', '8.jpg', 1, 2, 'lvzlsl95m17f661o@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('MBLNTN93L02L378Y', '9a09b4dfda82e3e665e31092d1c3ec8d', 'Antonio', 'Amabile', '1993-02-07', 'Trento', '70.jpg', 1, 6, 'dgtmbl93l02f440y@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('MLFLRD98C31C977S', 'ee11cbb19052e40b07aac0ca060c23ee', 'Leonardo', 'Malfara', '1998-03-31', 'Carini', '9.jpg', 1, 2, 'mlflrd98c31c977s@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('MMLMNN80S51C919E', 'ee11cbb19052e40b07aac0ca060c23ee', 'Marianna', 'Ommelo', '1980-11-11', 'Burago', '17.jpg', 2, 1, 'mmlmnn80s51c919e@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('MRCVTI44D19L768W', 'ee11cbb19052e40b07aac0ca060c23ee', 'Vito', 'Marcuglia', '1944-04-19', 'Vermezzo', '27.jpg', 1, 1, 'mrcvti44d19l768w@mailstop.it
', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('MSSMLN63D48C877O', 'ee11cbb19052e40b07aac0ca060c23ee', 'Milena', 'Masserdotti', '1963-04-08', 'Villabate', '35.jpg', 2, 2, 'mssmln63d48c877o@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('MSTRNN54A31E107M', 'ee11cbb19052e40b07aac0ca060c23ee', 'Ermanno', 'Masotano', '1954-01-31', 'Villabate', '5.jpg', 1, 2, 'mstrnn54a31e107m@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('MZZGLC97D23L378K', '6c962996fd3a526a59556595a18ac9b5', 'Gianluca', 'Mazzucchi', '1997-04-23', 'Trento', '30.jpg', 1, 3, 'gmazzu97@gmail.com', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('MZZLTA83D46A868B', 'ee11cbb19052e40b07aac0ca060c23ee', 'Altea', 'Miozzari', '1983-04-06', 'Burago', '16.jpg', 2, 1, 'mzzlta83d46a868b@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('NRTLSN97S24L378A', '9f9d51bc70ef21ca5c14f307980a29d8', 'Alessandro', 'Oniarti', '1997-11-24', 'Trento', '19.jpg', 1, 3, 'onniale@gmail.com', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('PLLCLE70A61L916W', 'ee11cbb19052e40b07aac0ca060c23ee', 'Cleo', 'Pollefrone', '1970-01-21', 'Villabate', '2.jpg', 2, 2, 'pllcle70a61l916w@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('PLLCTN32C15C200F', 'ee11cbb19052e40b07aac0ca060c23ee', 'Costanza', 'Pellattiero', '1932-03-15', 'Villabate', '45.jpg', 1, 2, 'pllctn32c15c200f@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('PMSTLD78P16B272L', 'ee11cbb19052e40b07aac0ca060c23ee', 'Tebaldo', 'Pomesano', '1978-09-16', 'Burago', '3.jpg', 1, 1, 'pmstld78p16b272l@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('PRGNNI46R29D473S', 'ee11cbb19052e40b07aac0ca060c23ee', 'Nino', 'Pergameno', '1946-10-29', 'Trento', '40.jpg', 1, 3, 'prgnni46r29d473s@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('PRIGND50R07H842L', '9a09b4dfda82e3e665e31092d1c3ec8d', 'Gianandrea', 'Piro', '1950-10-07', 'Burago', '74.jpg', 1, 1, 'gianandreapiro1950@mail.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('PRNGLI60B57E632S', 'ee11cbb19052e40b07aac0ca060c23ee', 'Giulia', 'Pirano', '1960-02-17', 'Giovo', '14.jpg', 2, 3, 'prngli60b57e632s@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('RGLMCD52T70E019Y', 'ee11cbb19052e40b07aac0ca060c23ee', 'Mercede', 'Rigolini', '1952-12-30', 'Trento', '47.jpg', 2, 3, 'rglmcd52t70e019y@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('RJLVNT62L50E832J', 'ee11cbb19052e40b07aac0ca060c23ee', 'Violante', 'Rajola', '1962-07-10', 'Villabate', '4.jpg', 2, 2, 'rjlvnt62l50e832j@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('RVZZHL58H27L761O', 'ee11cbb19052e40b07aac0ca060c23ee', 'Ezechiele', 'Riavez', '1958-06-27', 'Carini', '48.jpg', 1, 2, 'rvzzhl58h27l761o@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('RWNGNZ56E48F988X', 'ee11cbb19052e40b07aac0ca060c23ee', 'Ignazia', 'Rwanyindo', '1956-05-08', 'Trento', '44.jpg', 2, 3, 'rwngnz56e48f988x@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('SCHDIE65D61L578K', '9a09b4dfda82e3e665e31092d1c3ec8d', 'Idea', 'Schuchtrup', '1965-04-21', 'Prizzi', '59.jpg', 2, 2, 'schdie65d61l578k@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('SCZNDR56S16I061G', 'ee11cbb19052e40b07aac0ca060c23ee', 'Indro', 'Scuzzo', '1956-11-16', 'Prizzi', '6.jpg', 1, 2, 'sczndr56s16i061g@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('SGGTBR92R11F424W', 'ee11cbb19052e40b07aac0ca060c23ee', 'Tiberio', 'Soggiri', '1992-10-11', 'Giovo', '20.jpg', 1, 3, 'sggtbr92r11f424w@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('SMLVNR55L64E238Y', 'ee11cbb19052e40b07aac0ca060c23ee', 'Venere', 'Samola', '1955-07-24', 'Carpiano', '60.jpg', 2, 1, 'smlvnr55l64e238y@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('SNKMCR38D49A705I', 'ee11cbb19052e40b07aac0ca060c23ee', 'Macaria', 'Sankovic', '1938-04-09', 'Burago', '1.jpg', 2, 1, 'snkmcr38d49a705i@mailstop.it', 'MI');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('TCCMRG60S56E694B', 'ee11cbb19052e40b07aac0ca060c23ee', 'Ambrogia', 'Ticchioni', '1960-11-16', 'Prizzi', '7.jpg', 2, 2, 'tccmrg60s56e694b@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('TRCGFR64C27E983Z', '9a09b4dfda82e3e665e31092d1c3ec8d', 'Goffredo', 'Tracz', '1964-03-27', 'Cles', '73.jpg', 1, 3, 'trcgfr64c27e983z@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('TRVLRD41L54A988J', 'ee11cbb19052e40b07aac0ca060c23ee', 'Alfreda', 'Trovarello', '1941-07-14', 'Prizzi', '34.jpg', 2, 2, 'trvlrd41l54a988j@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('VLLRLA89S49B436J', 'ee11cbb19052e40b07aac0ca060c23ee', 'Aurelia', 'Vallinoti', '1989-11-09', 'Prizzi', '23.jpg', 2, 2, 'vllrla89s49b436j@mailstop.it', 'PA');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('VNSFNN00S26D200I', 'ee11cbb19052e40b07aac0ca060c23ee', 'Fernando', 'Venusino', '2000-11-26', 'Cles', '11.jpg', 1, 3, 'vnsfnn00s26d200i@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('ZNICLD72S22F722B', 'ee11cbb19052e40b07aac0ca060c23ee', 'Euclide', 'Ziane', '1972-11-22', 'Cles', '18.jpg', 1, 3, 'znicld72s22f722b@mailstop.it', 'TN');
INSERT INTO sistemasanitario.utenti (codiceFiscale, password, nome, cognome, dataNascita, luogoNascita, foto, sesso, codiceMedicoDiBase, email, provincia) VALUES ('ZZNSSM44C31E951F', 'ee11cbb19052e40b07aac0ca060c23ee', 'Sigismondo', 'Azzuna', '1944-03-31', 'Vermezzo', '55.jpg', 1, 1, 'zznssm44c31e951f@mailstop.it', 'MI');


INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Albumina', 'Prestazioni di laboratorio');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Aldolasi', 'Prestazioni di laboratorio');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Altra estrazione chirurgica di dente', 'Estrazione e ricostruzione dei denti');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Apicectomia', 'Estrazione e ricostruzione dei denti');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Colesterolo', 'Esami del sangue');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Cortisolo', 'Esami del sangue');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Cromo', 'Prestazioni di laboratorio');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Esame allergologico strumentale per orticarie fisiche', 'Dermatologia allergologica');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Estrazione di dente deciduo', 'Estrazione e ricostruzione dei denti');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Estrazione di dente permanente', 'Estrazione e ricostruzione dei denti');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Ferro', 'Prestazioni di laboratorio');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Glicemia', 'Esami del sangue');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Impianto di dente', 'Estrazione e ricostruzione dei denti');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Irradiazione cutanea totale con elettroni', 'Medicina nucleare');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Progesterone', 'Esami del sangue');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Radioterapia stereotassica', 'Medicina nucleare');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Screening allergologico per inalanti', 'Dermatologia allergologica');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Tomografia compiuterizzata del rachide e dello speco vertebrale', 'Radiologia diagnostica');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Tomografia compiuterizzata del rachide e dello speco vertebrale senza e con contrasto', 'Radiologia diagnostica');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Tomografia compiuterizzata dell''arto inferiore', 'Radiologia diagnostica');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Tomografia compiuterizzata dell''arto superiore', 'Radiologia diagnostica');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Tomoscintigrafia cerebrale', 'Medicina nucleare');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Urea', 'Prestazioni di laboratorio');
INSERT INTO sistemasanitario.tipiesame (tipo, categoria) VALUES ('Zinco', 'Esami del sangue');


INSERT INTO sistemasanitario.visite (codice, dataOra, resoconto, utente, codiceMedicoDiBase) VALUES (46, '2020-01-28 17:40:00', 'Il paziente indica un fastidio nella respirazione in determinate situazioni', 'NRTLSN97S24L378A', 3);
INSERT INTO sistemasanitario.visite (codice, dataOra, resoconto, utente, codiceMedicoDiBase) VALUES (47, '2020-01-28 17:47:00', 'Il paziente ha un dente danneggiato, da rimuovere', 'NRTLSN97S24L378A', 3);
INSERT INTO sistemasanitario.visite (codice, dataOra, resoconto, utente, codiceMedicoDiBase) VALUES (48, '2020-01-28 17:49:00', 'paziente lamenta dolore atroce', 'NRTLSN97S24L378A', 3);


INSERT INTO sistemasanitario.esami (codice, ticket, referto, dataOraFissata, fatto, codiceVisita, medico, tipo) VALUES (20, null, null, '2020-01-30 14:30:00', 0, 46, 3, 'Screening allergologico per inalanti');
INSERT INTO sistemasanitario.esami (codice, ticket, referto, dataOraFissata, fatto, codiceVisita, medico, tipo) VALUES (21, null, null, '2020-02-20 10:30:00', 0, 47, 3, 'Estrazione di dente permanente');
INSERT INTO sistemasanitario.esami (codice, ticket, referto, dataOraFissata, fatto, codiceVisita, medico, tipo) VALUES (22, null, null, '2020-02-24 11:00:00', 0, 47, 3, 'Impianto di dente');
INSERT INTO sistemasanitario.esami (codice, ticket, referto, dataOraFissata, fatto, codiceVisita, medico, tipo) VALUES (23, 50.00, 'L''operazione è avvenuta con successo senza complicazioni', '2020-01-28 18:45:00', 1, 48, 3, 'Irradiazione cutanea totale con elettroni');


INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AG', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AL', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AN', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AP', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AQ', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AT', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('AV', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('BG', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('BI', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('BL', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('BN', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('BR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('BS', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('BT', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('BZ', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CB', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CE', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CH', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CL', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CN', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CS', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('CZ', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('EN', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('FC', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('FE', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('FG', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('FM', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('FR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('GO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('GR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('IM', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('IS', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('KR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('LC', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('LE', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('LI', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('LO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('LT', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('LU', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('MB', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('MC', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('MN', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('MO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('MS', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('MT', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('NO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('NU', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('OR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PC', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PD', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PE', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PG', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PI', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PN', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PT', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PU', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PV', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('PZ', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('RA', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('RE', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('RG', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('RI', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('RN', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('RO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('SA', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('SI', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('SO', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('SP', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('SR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('SS', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('SU', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('SV', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('TA', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('TE', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('TN', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('TP', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('TR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('TS', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('TV', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('UD', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('VA', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('VB', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('VC', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('VI', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('VR', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('VT', 'provincia');
INSERT INTO sistemasanitario.ssp (provincia, password) VALUES ('VV', 'provincia');


INSERT INTO sistemasanitario.ricette (codice, farmaco, quantita, codiceVisita, codiceEsame, dataOraEvasa, descrizioneFarmaco, provinciaPrescrizione) VALUES (31, 'Alozof', 1, 46, null, '2020-01-28 18:30:08', 'Da prendere una volta al giorno', 'TN');
INSERT INTO sistemasanitario.ricette (codice, farmaco, quantita, codiceVisita, codiceEsame, dataOraEvasa, descrizioneFarmaco, provinciaPrescrizione) VALUES (32, 'Aviflucox', 1, 46, null, '2020-01-28 18:30:16', 'Assumere in caso di fastidio elevato', 'TN');
INSERT INTO sistemasanitario.ricette (codice, farmaco, quantita, codiceVisita, codiceEsame, dataOraEvasa, descrizioneFarmaco, provinciaPrescrizione) VALUES (40, 'Diflucan', 3, null, 23, null, 'Da prendere una compressa la mattina e una la sera', 'TN');


alter table medicidibase
	add constraint medicodibase_utente
		foreign key (codiceFiscale) references utenti (codiceFiscale)
			on update cascade on delete cascade;