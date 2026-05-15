-- =============================================================================
-- 1. DROP DE OBJETOS EXISTENTES (CLEANUP)
-- =============================================================================
DROP VIEW IF EXISTS vw_person_complete CASCADE;
DROP VIEW IF EXISTS vw_active_employees CASCADE;
DROP VIEW IF EXISTS vw_active_clients CASCADE;
DROP VIEW IF EXISTS vw_legal_person_responsibles CASCADE;
DROP VIEW IF EXISTS vw_audit_status CASCADE;

DROP TABLE IF EXISTS tb_legal_person_responsibles CASCADE;
DROP TABLE IF EXISTS tb_employee CASCADE;
DROP TABLE IF EXISTS tb_client CASCADE;
DROP TABLE IF EXISTS tb_natural_person CASCADE;
DROP TABLE IF EXISTS tb_legal_person CASCADE;
DROP TABLE IF EXISTS tb_person CASCADE;
DROP TABLE IF EXISTS tb_audit_trail CASCADE;

DROP FUNCTION IF EXISTS fn_update_timestamp CASCADE;
DROP FUNCTION IF EXISTS fn_audit_insert CASCADE;
DROP FUNCTION IF EXISTS fn_audit_update CASCADE;
DROP FUNCTION IF EXISTS fn_audit_soft_delete CASCADE;
DROP FUNCTION IF EXISTS fn_validate_person_type CASCADE;
DROP FUNCTION IF EXISTS fn_prevent_duplicate_person_type CASCADE;
DROP FUNCTION IF EXISTS fn_validate_legal_person_responsible CASCADE;
DROP FUNCTION IF EXISTS fn_validate_employee_natural_person CASCADE;

DROP SEQUENCE IF EXISTS seq_audit_trail CASCADE;
DROP SEQUENCE IF EXISTS seq_person CASCADE;
DROP SEQUENCE IF EXISTS seq_natural_person CASCADE;
DROP SEQUENCE IF EXISTS seq_legal_person CASCADE;
DROP SEQUENCE IF EXISTS seq_legal_person_responsibles CASCADE;
DROP SEQUENCE IF EXISTS seq_employee CASCADE;
DROP SEQUENCE IF EXISTS seq_client CASCADE;

-- =============================================================================
-- 2. SEQUENCES
-- =============================================================================
CREATE SEQUENCE seq_audit_trail START 1 INCREMENT 1;
CREATE SEQUENCE seq_person START 1 INCREMENT 1;
CREATE SEQUENCE seq_natural_person START 1 INCREMENT 1;
CREATE SEQUENCE seq_legal_person START 1 INCREMENT 1;
CREATE SEQUENCE seq_legal_person_responsibles START 1 INCREMENT 1;
CREATE SEQUENCE seq_employee START 1 INCREMENT 1;
CREATE SEQUENCE seq_client START 1 INCREMENT 1;

-- =============================================================================
-- 3. TABELA DE AUDITORIA (tb_audit_trail)
-- =============================================================================
-- Tabela unica generica de auditoria com chave estrangeira flexivel.
-- Todas as entidades base referenciam esta tabela para rastreamento
-- de criacao, atualizacao e soft delete.
-- =============================================================================
CREATE TABLE tb_audit_trail (
                                id                  BIGINT          DEFAULT nextval('seq_audit_trail') PRIMARY KEY,
                                table_name          VARCHAR(100)    NOT NULL,
                                record_id           BIGINT          NOT NULL,
                                created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at          TIMESTAMP       NULL,
                                deleted_at          TIMESTAMP       NULL,
                                created_by          VARCHAR(100)    NULL,
                                updated_by          VARCHAR(100)    NULL,
                                deleted_by          VARCHAR(100)    NULL,

    -- Constraint: registro deletado deve ter data de delecao
                                CONSTRAINT chk_deleted_requires_date
                                    CHECK (deleted_at IS NULL OR deleted_by IS NOT NULL),

    -- Constraint: updated_at deve ser >= created_at
                                CONSTRAINT chk_updated_after_created
                                    CHECK (updated_at IS NULL OR updated_at >= created_at)
);

COMMENT ON TABLE tb_audit_trail IS 'Tabela central de auditoria para todas as entidades. Implementa soft delete e rastreamento de alteracoes.';
COMMENT ON COLUMN tb_audit_trail.id IS 'Chave primaria - identificador unico do registro de auditoria';
COMMENT ON COLUMN tb_audit_trail.table_name IS 'Nome da tabela auditada';
COMMENT ON COLUMN tb_audit_trail.record_id IS 'ID do registro na tabela referenciada';
COMMENT ON COLUMN tb_audit_trail.created_at IS 'Data/hora de criacao do registro';
COMMENT ON COLUMN tb_audit_trail.updated_at IS 'Data/hora da ultima atualizacao';
COMMENT ON COLUMN tb_audit_trail.deleted_at IS 'Data/hora do soft delete (NULL = registro ativo)';
COMMENT ON COLUMN tb_audit_trail.created_by IS 'Usuario/sistema que criou o registro';
COMMENT ON COLUMN tb_audit_trail.updated_by IS 'Usuario/sistema que atualizou o registro';
COMMENT ON COLUMN tb_audit_trail.deleted_by IS 'Usuario/sistema que removeu o registro';

-- =============================================================================
-- 4. TABELA BASE - PESSOA (tb_person)
-- =============================================================================
CREATE TABLE tb_person (
                           id                  BIGINT          DEFAULT nextval('seq_person') PRIMARY KEY,
                           name                VARCHAR(255)    NOT NULL,
                           person_type         CHAR(1)         NOT NULL,
                           registration_date   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           audit_id            BIGINT          NULL UNIQUE,

    -- Constraint: tipo de pessoa deve ser F (Fisica) ou J (Juridica)
                           CONSTRAINT chk_person_type
                               CHECK (person_type IN ('F', 'J')),

    -- Constraint: nome nao pode ser vazio
                           CONSTRAINT chk_name_not_empty
                               CHECK (name <> ''),

    -- Foreign Key para auditoria
                           CONSTRAINT fk_person_audit
                               FOREIGN KEY (audit_id) REFERENCES tb_audit_trail(id)
                                   ON DELETE RESTRICT ON UPDATE CASCADE
);

COMMENT ON TABLE tb_person IS 'Entidade base para todas as pessoas do sistema (fisicas e juridicas)';
COMMENT ON COLUMN tb_person.id IS 'Chave primaria - identificador unico da pessoa';
COMMENT ON COLUMN tb_person.name IS 'Nome da pessoa ou nome fantasia se for pessoa juridica';
COMMENT ON COLUMN tb_person.person_type IS 'F = Pessoa Fisica, J = Pessoa Juridica';
COMMENT ON COLUMN tb_person.registration_date IS 'Data do registro da pessoa no sistema';
COMMENT ON COLUMN tb_person.audit_id IS 'Referencia para a tabela de auditoria';

-- =============================================================================
-- 5. TABELA - PESSOA FISICA (tb_natural_person)
-- =============================================================================
CREATE TABLE tb_natural_person (
                                   id                  BIGINT          DEFAULT nextval('seq_natural_person') PRIMARY KEY,
                                   person_id           BIGINT          NOT NULL UNIQUE,
                                   tax_id              VARCHAR(11)     NOT NULL UNIQUE,
                                   birth_date          DATE            NOT NULL,
                                   mothers_name        VARCHAR(255)    NULL,
                                   gender              CHAR(1)         NOT NULL,

    -- Constraint: CPF deve conter exatamente 11 digitos numericos
                                   CONSTRAINT chk_cpf_format
                                       CHECK (tax_id ~ '^[0-9]{11}$'),

    -- Constraint: genero deve ser F, M, O ou N
    CONSTRAINT chk_gender
        CHECK (gender IN ('F', 'M', 'O', 'N')),

    -- Foreign Key para tb_person
    CONSTRAINT fk_natural_person_person
        FOREIGN KEY (person_id) REFERENCES tb_person(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE tb_natural_person IS 'Especializacao de tb_person para pessoas fisicas';
COMMENT ON COLUMN tb_natural_person.id IS 'Chave primaria';
COMMENT ON COLUMN tb_natural_person.person_id IS 'Referencia para tb_person (relacionamento 1:1)';
COMMENT ON COLUMN tb_natural_person.tax_id IS 'CPF - 11 digitos numericos';
COMMENT ON COLUMN tb_natural_person.birth_date IS 'Data de nascimento';
COMMENT ON COLUMN tb_natural_person.mothers_name IS 'Nome da mae da pessoa';
COMMENT ON COLUMN tb_natural_person.gender IS 'F=Feminino, M=Masculino, O=Outro, N=Nao informar';

-- =============================================================================
-- 6. TABELA - PESSOA JURIDICA (tb_legal_person)
-- =============================================================================
CREATE TABLE tb_legal_person (
                                 id                  BIGINT          DEFAULT nextval('seq_legal_person') PRIMARY KEY,
                                 person_id           BIGINT          NOT NULL UNIQUE,
                                 tax_id              VARCHAR(14)     NOT NULL UNIQUE,
                                 corporate_name      VARCHAR(255)    NOT NULL,
                                 open_date           DATE            NOT NULL,
                                 share_capital       DECIMAL(18,2)   NULL,
                                 employees_quant     INTEGER         NULL DEFAULT 0,

    -- Constraint: CNPJ deve conter exatamente 14 digitos numericos
                                 CONSTRAINT chk_cnpj_format
                                     CHECK (tax_id ~ '^[0-9]{14}$'),

    -- Constraint: capital social nao pode ser negativo
    CONSTRAINT chk_share_capital_positive
        CHECK (share_capital IS NULL OR share_capital >= 0),

    -- Constraint: quantidade de funcionarios nao pode ser negativa
    CONSTRAINT chk_employees_quant_positive
        CHECK (employees_quant IS NULL OR employees_quant >= 0),

    -- Constraint: razao social nao pode ser vazia
    CONSTRAINT chk_corporate_name_not_empty
        CHECK (corporate_name <> ''),

    -- Foreign Key para tb_person
    CONSTRAINT fk_legal_person_person
        FOREIGN KEY (person_id) REFERENCES tb_person(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE tb_legal_person IS 'Especializacao de tb_person para pessoas juridicas';
COMMENT ON COLUMN tb_legal_person.id IS 'Chave primaria';
COMMENT ON COLUMN tb_legal_person.person_id IS 'Referencia para tb_person (relacionamento 1:1)';
COMMENT ON COLUMN tb_legal_person.tax_id IS 'CNPJ - 14 digitos numericos';
COMMENT ON COLUMN tb_legal_person.corporate_name IS 'Razao social da pessoa juridica';
COMMENT ON COLUMN tb_legal_person.open_date IS 'Data de abertura da pessoa juridica';
COMMENT ON COLUMN tb_legal_person.share_capital IS 'Capital social da empresa';
COMMENT ON COLUMN tb_legal_person.employees_quant IS 'Numero de funcionarios da empresa';

-- =============================================================================
-- 7. TABELA ASSOCIATIVA - RESPONSAVEIS DA PJ (tb_legal_person_responsibles)
-- =============================================================================
CREATE TABLE tb_legal_person_responsibles (
                                              id                      BIGINT          DEFAULT nextval('seq_legal_person_responsibles') PRIMARY KEY,
                                              legal_person_id         BIGINT          NOT NULL,
                                              natural_person_id       BIGINT          NOT NULL,
                                              responsibility_type     VARCHAR(50)     NOT NULL DEFAULT 'LEGAL_REPRESENTATIVE',
                                              start_date              DATE            NOT NULL DEFAULT CURRENT_DATE,
                                              end_date                DATE            NULL,

    -- Constraint: data fim deve ser posterior a data inicio
                                              CONSTRAINT chk_date_range
                                                  CHECK (end_date IS NULL OR end_date >= start_date),

    -- Constraint: tipo de responsabilidade nao pode ser vazio
                                              CONSTRAINT chk_responsibility_type_not_empty
                                                  CHECK (responsibility_type <> ''),

    -- Foreign Key para tb_legal_person
                                              CONSTRAINT fk_responsibles_legal_person
                                                  FOREIGN KEY (legal_person_id) REFERENCES tb_legal_person(id)
                                                      ON DELETE CASCADE ON UPDATE CASCADE,

    -- Foreign Key para tb_natural_person
                                              CONSTRAINT fk_responsibles_natural_person
                                                  FOREIGN KEY (natural_person_id) REFERENCES tb_natural_person(id)
                                                      ON DELETE RESTRICT ON UPDATE CASCADE
);

COMMENT ON TABLE tb_legal_person_responsibles IS 'Tabela associativa entre Pessoa Juridica e seus responsaveis (Pessoas Fisicas)';
COMMENT ON COLUMN tb_legal_person_responsibles.id IS 'Chave primaria';
COMMENT ON COLUMN tb_legal_person_responsibles.legal_person_id IS 'Referencia para tb_legal_person (PJ)';
COMMENT ON COLUMN tb_legal_person_responsibles.natural_person_id IS 'Referencia para tb_natural_person (responsavel PF)';
COMMENT ON COLUMN tb_legal_person_responsibles.responsibility_type IS 'Tipo de responsabilidade (ex: LEGAL_REPRESENTATIVE, ADMINISTRATOR)';
COMMENT ON COLUMN tb_legal_person_responsibles.start_date IS 'Data inicio da responsabilidade';
COMMENT ON COLUMN tb_legal_person_responsibles.end_date IS 'Data fim da responsabilidade (NULL = ativo)';

-- =============================================================================
-- 8. TABELA - FUNCIONARIO/EMPLOYEE (tb_employee) - ROLE
-- =============================================================================
CREATE TABLE tb_employee (
                             id                      BIGINT          DEFAULT nextval('seq_employee') PRIMARY KEY,
                             person_id               BIGINT          NOT NULL UNIQUE,
                             registration_number     VARCHAR(12)     NOT NULL UNIQUE,
                             hire_date               DATE            NOT NULL,
                             termination_date        DATE            NULL,
                             termination_reason      VARCHAR(255)    NULL,
                             employee_role           VARCHAR(50)     NOT NULL DEFAULT 'GENERAL',
                             status                  VARCHAR(20)     NOT NULL DEFAULT 'ACTIVE',

    -- Constraint: data de demissao deve ser posterior a data de admissao
                             CONSTRAINT chk_termination_after_hire
                                 CHECK (termination_date IS NULL OR termination_date >= hire_date),

    -- Constraint: motivo de demissao obrigatorio se houver data de demissao
                             CONSTRAINT chk_termination_reason_required
                                 CHECK (termination_date IS NULL OR termination_reason IS NOT NULL),

    -- Constraint: status valido
                             CONSTRAINT chk_employee_status
                                 CHECK (status IN ('ACTIVE', 'INACTIVE')),

    -- Constraint: numero de matricula nao pode ser vazio
                             CONSTRAINT chk_registration_not_empty
                                 CHECK (registration_number <> ''),

    -- Foreign Key para tb_person
                             CONSTRAINT fk_employee_person
                                 FOREIGN KEY (person_id) REFERENCES tb_person(id)
                                     ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE tb_employee IS 'Role de funcionario - especializacao comportamental de tb_person';
COMMENT ON COLUMN tb_employee.id IS 'Chave primaria';
COMMENT ON COLUMN tb_employee.person_id IS 'Referencia para tb_person (relacionamento 1:1)';
COMMENT ON COLUMN tb_employee.registration_number IS 'Numero da matricula do funcionario';
COMMENT ON COLUMN tb_employee.hire_date IS 'Data de admissao do funcionario';
COMMENT ON COLUMN tb_employee.termination_date IS 'Data de demissao (NULL = ativo)';
COMMENT ON COLUMN tb_employee.termination_reason IS 'Motivo da demissao';
COMMENT ON COLUMN tb_employee.employee_role IS 'Funcao do empregado (ex: VENDEDOR, RH, ENGENHEIRO)';
COMMENT ON COLUMN tb_employee.status IS 'Status do funcionario (ACTIVE, INACTIVE)';

-- =============================================================================
-- 9. TABELA - CLIENTE/CLIENT (tb_client) - ROLE
-- =============================================================================
CREATE TABLE tb_client (
                           id              BIGINT          DEFAULT nextval('seq_client') PRIMARY KEY,
                           person_id       BIGINT          NOT NULL UNIQUE,
                           client_code     VARCHAR(15)     NOT NULL UNIQUE,
                           client_rank     VARCHAR(20)     NULL DEFAULT 'STANDARD',
                           status          VARCHAR(20)     NOT NULL DEFAULT 'ACTIVE',

    -- Constraint: status valido
                           CONSTRAINT chk_client_status
                               CHECK (status IN ('ACTIVE', 'INACTIVE')),

    -- Constraint: codigo do cliente nao pode ser vazio
                           CONSTRAINT chk_client_code_not_empty
                               CHECK (client_code <> ''),

    -- Foreign Key para tb_person
                           CONSTRAINT fk_client_person
                               FOREIGN KEY (person_id) REFERENCES tb_person(id)
                                   ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE tb_client IS 'Role de cliente - especializacao comportamental de tb_person';
COMMENT ON COLUMN tb_client.id IS 'Chave primaria';
COMMENT ON COLUMN tb_client.person_id IS 'Referencia para tb_person (relacionamento 1:1)';
COMMENT ON COLUMN tb_client.client_code IS 'Codigo unico do cliente no sistema';
COMMENT ON COLUMN tb_client.client_rank IS 'Classificacao do cliente (ex: STANDARD, PREMIUM, VIP)';
COMMENT ON COLUMN tb_client.status IS 'Status do cliente (ACTIVE, INACTIVE)';

-- =============================================================================
-- 10. FUNCTIONS (para triggers)
-- =============================================================================

-- Function: Atualizar timestamp de updated_at
CREATE OR REPLACE FUNCTION fn_update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Function: Auditoria - INSERT (criar registro de auditoria)
CREATE OR REPLACE FUNCTION fn_audit_insert()
RETURNS TRIGGER AS $$
DECLARE
v_audit_id BIGINT;
    v_table_name VARCHAR(100);
BEGIN
    v_table_name = TG_TABLE_NAME;

INSERT INTO tb_audit_trail (table_name, record_id, created_at, created_by)
VALUES (v_table_name, NEW.id, CURRENT_TIMESTAMP, COALESCE(current_setting('app.current_user', true), 'system'))
    RETURNING id INTO v_audit_id;

NEW.audit_id = v_audit_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Function: Auditoria - UPDATE (atualizar registro de auditoria)
CREATE OR REPLACE FUNCTION fn_audit_update()
RETURNS TRIGGER AS $$
BEGIN
UPDATE tb_audit_trail
SET updated_at = CURRENT_TIMESTAMP,
    updated_by = COALESCE(current_setting('app.current_user', true), 'system')
WHERE id = OLD.audit_id;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Function: Auditoria - SOFT DELETE (marcar como deletado)
CREATE OR REPLACE FUNCTION fn_audit_soft_delete()
RETURNS TRIGGER AS $$
BEGIN
UPDATE tb_audit_trail
SET deleted_at = CURRENT_TIMESTAMP,
    deleted_by = COALESCE(current_setting('app.current_user', true), 'system')
WHERE id = OLD.audit_id;

-- Impede o DELETE fisico
RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Function: Validar tipo de pessoa (F/J exclusividade)
CREATE OR REPLACE FUNCTION fn_validate_person_type()
RETURNS TRIGGER AS $$
DECLARE
v_count_f INTEGER;
    v_count_j INTEGER;
BEGIN
    -- Contar se ja existe como PF
SELECT COUNT(*) INTO v_count_f
FROM tb_natural_person np
         JOIN tb_person p ON np.person_id = p.id
WHERE p.id = NEW.id;

-- Contar se ja existe como PJ
SELECT COUNT(*) INTO v_count_j
FROM tb_legal_person lp
         JOIN tb_person p ON lp.person_id = p.id
WHERE p.id = NEW.id;

-- Se for PF e ja existe como PJ, ou vice-versa, erro
IF NEW.person_type = 'F' AND v_count_j > 0 THEN
        RAISE EXCEPTION 'Pessoa ja cadastrada como Pessoa Juridica. Uma pessoa nao pode ser ambos.';
END IF;

    IF NEW.person_type = 'J' AND v_count_f > 0 THEN
        RAISE EXCEPTION 'Pessoa ja cadastrada como Pessoa Fisica. Uma pessoa nao pode ser ambos.';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Function: Impedir duplicacao de tipo de pessoa no INSERT
CREATE OR REPLACE FUNCTION fn_prevent_duplicate_person_type()
RETURNS TRIGGER AS $$
DECLARE
v_person_type CHAR(1);
BEGIN
    -- Verificar o tipo da pessoa
SELECT person_type INTO v_person_type
FROM tb_person
WHERE id = NEW.person_id;

-- Se estiver inserindo em natural_person mas person_type for J
IF TG_TABLE_NAME = 'tb_natural_person' AND v_person_type = 'J' THEN
        RAISE EXCEPTION 'Nao e possivel cadastrar Pessoa Fisica para um registro de Pessoa Juridica.';
END IF;

    -- Se estiver inserindo em legal_person mas person_type for F
    IF TG_TABLE_NAME = 'tb_legal_person' AND v_person_type = 'F' THEN
        RAISE EXCEPTION 'Nao e possivel cadastrar Pessoa Juridica para um registro de Pessoa Fisica.';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Function: Validar responsavel da PJ (deve ser PF)
CREATE OR REPLACE FUNCTION fn_validate_legal_person_responsible()
RETURNS TRIGGER AS $$
DECLARE
v_person_type CHAR(1);
BEGIN
    -- Verificar se o natural_person_id referencia uma PF
SELECT p.person_type INTO v_person_type
FROM tb_natural_person np
         JOIN tb_person p ON np.person_id = p.id
WHERE np.id = NEW.natural_person_id;

IF v_person_type IS NULL THEN
        RAISE EXCEPTION 'Responsavel nao encontrado como Pessoa Fisica.';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Function: Validar que employee so pode ser vinculado a PF
CREATE OR REPLACE FUNCTION fn_validate_employee_natural_person()
RETURNS TRIGGER AS $$
DECLARE
v_person_type CHAR(1);
BEGIN
    -- Verificar o tipo da pessoa
SELECT person_type INTO v_person_type
FROM tb_person
WHERE id = NEW.person_id;

-- Employee deve ser vinculado a uma Pessoa Fisica
IF v_person_type <> 'F' THEN
        RAISE EXCEPTION 'Funcionario deve ser vinculado a uma Pessoa Fisica (person_type = F).';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- =============================================================================
-- 11. TRIGGERS
-- =============================================================================

-- Trigger: Atualizar updated_at em tb_audit_trail
CREATE TRIGGER trg_audit_trail_update
    BEFORE UPDATE ON tb_audit_trail
    FOR EACH ROW
    EXECUTE FUNCTION fn_update_timestamp();

-- Trigger: Auditoria INSERT em tb_person
CREATE TRIGGER trg_person_audit_insert
    BEFORE INSERT ON tb_person
    FOR EACH ROW
    EXECUTE FUNCTION fn_audit_insert();

-- Trigger: Auditoria UPDATE em tb_person
CREATE TRIGGER trg_person_audit_update
    AFTER UPDATE ON tb_person
    FOR EACH ROW
    WHEN (OLD.audit_id IS NOT NULL)
    EXECUTE FUNCTION fn_audit_update();

-- Trigger: Auditoria SOFT DELETE em tb_person (impede DELETE fisico)
CREATE TRIGGER trg_person_audit_delete
    BEFORE DELETE ON tb_person
    FOR EACH ROW
    WHEN (OLD.audit_id IS NOT NULL)
    EXECUTE FUNCTION fn_audit_soft_delete();

-- Trigger: Impedir insert em natural_person se person_type = J
CREATE TRIGGER trg_natural_person_validate_type
    BEFORE INSERT ON tb_natural_person
    FOR EACH ROW
    EXECUTE FUNCTION fn_prevent_duplicate_person_type();

-- Trigger: Impedir insert em legal_person se person_type = F
CREATE TRIGGER trg_legal_person_validate_type
    BEFORE INSERT ON tb_legal_person
    FOR EACH ROW
    EXECUTE FUNCTION fn_prevent_duplicate_person_type();

-- Trigger: Validar responsavel da PJ
CREATE TRIGGER trg_validate_responsible
    BEFORE INSERT OR UPDATE ON tb_legal_person_responsibles
                         FOR EACH ROW
                         EXECUTE FUNCTION fn_validate_legal_person_responsible();

-- Trigger: Validar que employee e vinculado a PF
CREATE TRIGGER trg_employee_validate_natural
    BEFORE INSERT OR UPDATE ON tb_employee
                         FOR EACH ROW
                         EXECUTE FUNCTION fn_validate_employee_natural_person();

-- =============================================================================
-- 12. INDEXES
-- =============================================================================

-- Indexes tb_person
CREATE INDEX idx_person_name ON tb_person(name);
CREATE INDEX idx_person_type ON tb_person(person_type);
CREATE INDEX idx_person_registration_date ON tb_person(registration_date);

-- Indexes tb_natural_person
CREATE INDEX idx_natural_person_tax_id ON tb_natural_person(tax_id);
CREATE INDEX idx_natural_person_birth_date ON tb_natural_person(birth_date);

-- Indexes tb_legal_person
CREATE INDEX idx_legal_person_tax_id ON tb_legal_person(tax_id);
CREATE INDEX idx_legal_person_corporate_name ON tb_legal_person(corporate_name);

-- Indexes tb_legal_person_responsibles
CREATE INDEX idx_responsibles_legal_person ON tb_legal_person_responsibles(legal_person_id);
CREATE INDEX idx_responsibles_natural_person ON tb_legal_person_responsibles(natural_person_id);
CREATE INDEX idx_responsibles_active ON tb_legal_person_responsibles(end_date) WHERE end_date IS NULL;

-- Indexes tb_employee
CREATE INDEX idx_employee_registration ON tb_employee(registration_number);
CREATE INDEX idx_employee_hire_date ON tb_employee(hire_date);
CREATE INDEX idx_employee_status ON tb_employee(status);
CREATE INDEX idx_employee_role ON tb_employee(employee_role);

-- Indexes tb_client
CREATE INDEX idx_client_code ON tb_client(client_code);
CREATE INDEX idx_client_status ON tb_client(status);
CREATE INDEX idx_client_rank ON tb_client(client_rank);

-- Indexes tb_audit_trail
CREATE INDEX idx_audit_table_record ON tb_audit_trail(table_name, record_id);
CREATE INDEX idx_audit_deleted ON tb_audit_trail(deleted_at) WHERE deleted_at IS NULL;
CREATE INDEX idx_audit_created ON tb_audit_trail(created_at);

-- =============================================================================
-- 13. VIEWS
-- =============================================================================

-- View: Pessoa completa (unindo PF e PJ com dados da pessoa base)
CREATE OR REPLACE VIEW vw_person_complete AS
SELECT
    p.id AS person_id,
    p.name,
    p.person_type,
    p.registration_date,
    CASE
        WHEN p.person_type = 'F' THEN 'Pessoa Fisica'
        WHEN p.person_type = 'J' THEN 'Pessoa Juridica'
        END AS person_type_description,
    -- Dados PF (quando aplicavel)
    np.tax_id AS cpf,
    np.birth_date,
    np.mothers_name,
    np.gender,
    CASE np.gender
        WHEN 'F' THEN 'Feminino'
        WHEN 'M' THEN 'Masculino'
        WHEN 'O' THEN 'Outro'
        WHEN 'N' THEN 'Nao informar'
        END AS gender_description,
    -- Dados PJ (quando aplicavel)
    lp.tax_id AS cnpj,
    lp.corporate_name,
    lp.share_capital,
    lp.employees_quant,
    -- Flags de roles
    CASE WHEN e.id IS NOT NULL THEN TRUE ELSE FALSE END AS is_employee,
    CASE WHEN c.id IS NOT NULL THEN TRUE ELSE FALSE END AS is_client,
    -- Auditoria
    at.created_at,
    at.updated_at,
    CASE WHEN at.deleted_at IS NULL THEN 'ATIVO' ELSE 'EXCLUIDO' END AS status
FROM tb_person p
         LEFT JOIN tb_natural_person np ON p.id = np.person_id AND p.person_type = 'F'
         LEFT JOIN tb_legal_person lp ON p.id = lp.person_id AND p.person_type = 'J'
         LEFT JOIN tb_employee e ON p.id = e.person_id
         LEFT JOIN tb_client c ON p.id = c.person_id
         LEFT JOIN tb_audit_trail at ON p.audit_id = at.id;

COMMENT ON VIEW vw_person_complete IS 'Visao completa unificada de todas as pessoas com dados de PF, PJ, roles e auditoria';

-- View: Funcionarios ativos
CREATE OR REPLACE VIEW vw_active_employees AS
SELECT
    e.id AS employee_id,
    p.id AS person_id,
    p.name,
    e.registration_number,
    e.hire_date,
    e.termination_date,
    e.termination_reason,
    e.employee_role,
    e.status,
    np.tax_id AS cpf,
    np.birth_date,
    at.created_at,
    at.updated_at
FROM tb_employee e
         JOIN tb_person p ON e.person_id = p.id
         JOIN tb_natural_person np ON p.id = np.person_id
         LEFT JOIN tb_audit_trail at ON p.audit_id = at.id
WHERE e.status = 'ACTIVE'
  AND e.termination_date IS NULL
  AND at.deleted_at IS NULL;

COMMENT ON VIEW vw_active_employees IS 'Lista de funcionarios ativos no sistema (sem demissao e nao excluidos)';

-- View: Clientes ativos
CREATE OR REPLACE VIEW vw_active_clients AS
SELECT
    c.id AS client_id,
    p.id AS person_id,
    p.name,
    c.client_code,
    c.client_rank,
    c.status,
    p.person_type,
    CASE
        WHEN p.person_type = 'F' THEN np.tax_id
        WHEN p.person_type = 'J' THEN lp.tax_id
        END AS document,
    at.created_at,
    at.updated_at
FROM tb_client c
         JOIN tb_person p ON c.person_id = p.id
         LEFT JOIN tb_natural_person np ON p.id = np.person_id AND p.person_type = 'F'
         LEFT JOIN tb_legal_person lp ON p.id = lp.person_id AND p.person_type = 'J'
         LEFT JOIN tb_audit_trail at ON p.audit_id = at.id
WHERE c.status = 'ACTIVE'
  AND at.deleted_at IS NULL;

COMMENT ON VIEW vw_active_clients IS 'Lista de clientes ativos no sistema (nao excluidos)';

-- View: Responsaveis por Pessoa Juridica
CREATE OR REPLACE VIEW vw_legal_person_responsibles AS
SELECT
    lpr.id,
    lp.id AS legal_person_id,
    lp.corporate_name,
    lp.tax_id AS cnpj,
    np.id AS natural_person_id,
    p_resp.name AS responsible_name,
    np.tax_id AS responsible_cpf,
    lpr.responsibility_type,
    lpr.start_date,
    lpr.end_date,
    CASE WHEN lpr.end_date IS NULL THEN 'ATIVO' ELSE 'INATIVO' END AS status
FROM tb_legal_person_responsibles lpr
         JOIN tb_legal_person lp ON lpr.legal_person_id = lp.id
         JOIN tb_natural_person np ON lpr.natural_person_id = np.id
         JOIN tb_person p_resp ON np.person_id = p_resp.id;

COMMENT ON VIEW vw_legal_person_responsibles IS 'Lista de responsaveis vinculados a cada Pessoa Juridica';

-- View: Status de auditoria
CREATE OR REPLACE VIEW vw_audit_status AS
SELECT
    at.id AS audit_id,
    at.table_name,
    at.record_id,
    at.created_at,
    at.updated_at,
    at.deleted_at,
    CASE WHEN at.deleted_at IS NULL THEN 'ATIVO' ELSE 'EXCLUIDO (Soft Delete)' END AS record_status,
    at.created_by,
    at.updated_by,
    at.deleted_by
FROM tb_audit_trail at
ORDER BY at.created_at DESC;

COMMENT ON VIEW vw_audit_status IS 'Visao geral do status de auditoria de todos os registros';

-- =============================================================================
-- 14. STORED PROCEDURES (Operacoes de negocio)
-- =============================================================================

-- Procedure: Soft delete de pessoa (por ID)
CREATE OR REPLACE PROCEDURE sp_soft_delete_person(p_person_id BIGINT, p_deleted_by VARCHAR(100) DEFAULT 'system')
LANGUAGE plpgsql
AS $$
BEGIN
UPDATE tb_audit_trail
SET deleted_at = CURRENT_TIMESTAMP,
    deleted_by = p_deleted_by
WHERE id = (SELECT audit_id FROM tb_person WHERE id = p_person_id);

IF NOT FOUND THEN
        RAISE EXCEPTION 'Pessoa com ID % nao encontrada.', p_person_id;
END IF;
END;
$$;

COMMENT ON PROCEDURE sp_soft_delete_person IS 'Executa soft delete de uma pessoa pelo ID';

-- Procedure: Restaurar pessoa excluida (soft delete)
CREATE OR REPLACE PROCEDURE sp_restore_person(p_person_id BIGINT)
LANGUAGE plpgsql
AS $$
BEGIN
UPDATE tb_audit_trail
SET deleted_at = NULL,
    deleted_by = NULL
WHERE id = (SELECT audit_id FROM tb_person WHERE id = p_person_id);

IF NOT FOUND THEN
        RAISE EXCEPTION 'Pessoa com ID % nao encontrada.', p_person_id;
END IF;
END;
$$;

COMMENT ON PROCEDURE sp_restore_person IS 'Restaura uma pessoa previamente soft-deleted';