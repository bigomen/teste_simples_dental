\c simples_dental;

CREATE USER sdental WITH PASSWORD '987123';

GRANT ALL PRIVILEGES ON DATABASE simples_dental TO sdental;

CREATE TABLE profissionais (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cargo VARCHAR(255) NOT NULL CHECK (cargo IN ('Desenvolvedor', 'Designer', 'Suporte', 'Tester')),
    nascimento DATE NOT NULL,
    status BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL
);

CREATE TABLE contatos (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    contato VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    profissional_id UUID NOT NULL,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id)
);

GRANT ALL PRIVILEGES ON TABLE contatos TO sdental;
GRANT ALL PRIVILEGES ON TABLE profissionais TO sdental;