insert into adicional_imovel(id, descricao) select 1, 'CHURRASQUEIRA' where not exists (select id from adicional_imovel where id = 1);
insert into adicional_imovel(id, descricao) select 2, 'PLAYGROUD' where not exists (select id from adicional_imovel where id = 2);
insert into adicional_imovel(id, descricao) select 3, 'DESPENSA' where not exists (select id from adicional_imovel where id = 3);
insert into adicional_imovel(id, descricao) select 4, 'SALÃO DE FESTAS' where not exists (select id from adicional_imovel where id = 4);
insert into adicional_imovel(id, descricao) select 5, 'ÁREA DE SERVIÇO' where not exists (select id from adicional_imovel where id = 5);

insert into usuario(id, nome, telefone, email, senha, DTYPE, bloqueado) values (1, 'user', '(67) 99999-9999', 'email@email.com', '$2a$07$fi5QDS4w9KcGoLPhjraowejID5dihUCsSrz62Ns3/Rx5JR/5FNI0C', 'Anunciante', false);