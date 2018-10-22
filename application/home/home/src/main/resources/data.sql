insert into adicional_imovel(id, descricao) select 1, 'CHURRASQUEIRA' where not exists (select id from adicional_imovel where id = 1);
insert into adicional_imovel(id, descricao) select 2, 'PLAYGROUD' where not exists (select id from adicional_imovel where id = 2);
insert into adicional_imovel(id, descricao) select 3, 'DESPENSA' where not exists (select id from adicional_imovel where id = 3);
insert into adicional_imovel(id, descricao) select 4, 'SALÃO DE FESTAS' where not exists (select id from adicional_imovel where id = 4);
insert into adicional_imovel(id, descricao) select 5, 'ÁREA DE SERVIÇO' where not exists (select id from adicional_imovel where id = 5);

insert into usuario(id, nome, telefone, email, senha, DTYPE, bloqueado) values (10, 'user', '(67) 99999-9999', 'email@email.com', '$2a$07$fi5QDS4w9KcGoLPhjraowejID5dihUCsSrz62Ns3/Rx5JR/5FNI0C', 'Anunciante', false);

insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (10, 'Dr Lelis Espartel 1', 1, 'Tijuca', 'Campo Grande', 'MS', 10, 'CASA', 0, 'VENDA');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (20, 'Dr Lelis Espartel 2', 2, 'Tijuca', 'Campo Grande', 'MS', 10, 'CASA', 0, 'VENDA');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (30, 'Dr Lelis Espartel 3', 3, 'Caranda', 'Campo Grande', 'MS', 10, 'CASA', 0, 'VENDA');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (40, 'Dr Lelis Espartel 4', 4, 'Tijuca', 'Sao Paulo', 'MS', 10, 'CASA', 0, 'VENDA');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (50, 'Dr Lelis Espartel 5', 5, 'Tijuca', 'Campo Grande', 'MS', 10, 'CASA', 0, 'ALUGUEL');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (60, 'Av Mato Grosso', 6, 'Tijuca', 'Campo Grande', 'MS', 10, 'CASA', 0, 'ALUGUEL');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (70, 'Av Afonso Pena 1955', 7, 'Tijuca', 'Campo Grande', 'MS', 10, 'CASA', 0, 'ALUGUEL');