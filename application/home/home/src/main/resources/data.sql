insert into adicional_imovel(id, descricao) select 1, 'CHURRASQUEIRA' where not exists (select id from adicional_imovel where id = 1);
insert into adicional_imovel(id, descricao) select 2, 'PLAYGROUD' where not exists (select id from adicional_imovel where id = 2);
insert into adicional_imovel(id, descricao) select 3, 'DESPENSA' where not exists (select id from adicional_imovel where id = 3);
insert into adicional_imovel(id, descricao) select 4, 'SALÃO DE FESTAS' where not exists (select id from adicional_imovel where id = 4);
insert into adicional_imovel(id, descricao) select 5, 'ÁREA DE SERVIÇO' where not exists (select id from adicional_imovel where id = 5);

insert into usuario(id, nome, telefone, email, senha, DTYPE, bloqueado) values (10, 'user', '(67) 99999-9999', 'email@email.com', '$2a$07$fi5QDS4w9KcGoLPhjraowejID5dihUCsSrz62Ns3/Rx5JR/5FNI0C', 'Anunciante', false);

insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (10, 'Rua das Garças', 1, 'Centro', 'Campo Grande', 'MS', 10, 'CASA', 200000, 'VENDA');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (20, 'Rua Bahia', 2, 'Centro', 'Campo Grande', 'MS', 10, 'CASA', 500000, 'VENDA');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (30, 'Av Afonso Pena', 3, 'Centro', 'Campo Grande', 'MS', 10, 'CASA', 170000, 'VENDA');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (40, 'Tv Faia', 4, 'São Francisco', 'Sao Paulo', 'SP', 10, 'CASA', 220000, 'VENDA');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (50, 'Rua Cabo Verde', 5, 'Tijuca', 'Campo Grande', 'MS', 10, 'CASA', 800, 'ALUGUEL');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (60, 'Av Mato Grosso', 6, 'Centro', 'Campo Grande', 'MS', 10, 'CASA', 600, 'ALUGUEL');
insert into imovel(id, rua, numero, bairro, cidade, estado, id_anunciante, tipo_imovel, valor_imovel, tipo_negocio) values (70, 'Rua Tocantins', 7, 'Centro', 'Campo Grande', 'MS', 10, 'CASA', 750, 'ALUGUEL');