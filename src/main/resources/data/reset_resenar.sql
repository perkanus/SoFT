SELECT * FROM soft.soft_resenar where RESENAR_OWNER = 'PERIKLIS';
update soft.soft_resenar set RESENAR_CREATED = 'N';
update soft.soft_resenar set RESENAR_CREATE_FTJARENDE = 'N';
update soft.soft_resenar set RESENAR_KUNDNUMMER = '';
delete from soft.soft_arenden;
delete from soft.soft_sit_info;
update soft.soft_resenar set RESENAR_OWNER = '' WHERE RESENAR_OWNER = 'PERIKLIS'; 

