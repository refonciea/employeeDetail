CREATE TABLE `Empform`(
`Name` varchar(50)  NOT NULL ,
'`Gender` varchar(50) DEFAULT NULL,
`Dateofjoin` date DEFAULT NULL,
`Designation` varchar(50) DEFAULT NULL,
`CTC` varchar(50) DEFAULT NULL,
`PF` varchar(50) DEFAULT NULL,
`ESI` varchar(30) DEFAULT NULL,
`Tax` varchar(30),
PRIMARY KEY (`Name`)
);