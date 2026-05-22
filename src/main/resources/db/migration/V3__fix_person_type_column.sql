-- ============================================
-- Remove VIEW dependente
-- ============================================
DROP VIEW IF EXISTS vw_person_complete;
DROP VIEW IF EXISTS vw_active_clients;

-- ============================================
-- Ajusta tipo da coluna
-- ============================================
ALTER TABLE tb_person
ALTER COLUMN person_type TYPE VARCHAR(1);

-- ============================================
-- Recria VIEW original
-- ============================================
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
        WHEN 'N' THEN 'Não informar'
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