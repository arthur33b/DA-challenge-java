INSERT INTO categoria (id, nome, descricao) VALUES (1, 'Desenvolvimento', 'Cursos relacionados ao desenvolvimento de software');
INSERT INTO categoria (id, nome, descricao) VALUES (2, 'Dados', 'Cursos sobre ciência de dados e análise de dados');
INSERT INTO categoria (id, nome, descricao) VALUES (3, 'Design', 'Cursos de design gráfico e UX/UI');

INSERT INTO instrutor (id, nome, email, especializacao) VALUES (1, 'Carlos Silva', 'carlos@alura.com', 'Desenvolvedor Java');
INSERT INTO instrutor (id, nome, email, especializacao) VALUES (2, 'Maria Oliveira', 'maria@alura.com', 'Cientista de Dados');
INSERT INTO instrutor (id, nome, email, especializacao) VALUES (3, 'João Pereira', 'joao@alura.com', 'Designer Gráfico');

INSERT INTO curso (id, nome, descricao, categoria_id, instrutor_id, popularidade, nota_media) VALUES (1, 'Java Completo', 'Aprenda Java do básico ao avançado', 1, 1, 1500, 4.5);
INSERT INTO curso (id, nome, descricao, categoria_id, instrutor_id, popularidade, nota_media) VALUES (2, 'Data Science com Python', 'Curso completo de ciência de dados', 2, 2, 1200, 4.7);
INSERT INTO curso (id, nome, descricao, categoria_id, instrutor_id, popularidade, nota_media) VALUES (3, 'Design de Interfaces', 'Aprenda a criar interfaces incríveis', 3, 3, 800, 4.6);
INSERT INTO curso (id, nome, descricao, categoria_id, instrutor_id, popularidade, nota_media) VALUES (4, 'Spring Boot', 'Desenvolvimento de aplicações com Spring Boot', 1, 1, 900, 4.8);
INSERT INTO curso (id, nome, descricao, categoria_id, instrutor_id, popularidade, nota_media) VALUES (5, 'Análise de Dados', 'Curso de análise de dados com Excel', 2, 2, 700, 4.4);