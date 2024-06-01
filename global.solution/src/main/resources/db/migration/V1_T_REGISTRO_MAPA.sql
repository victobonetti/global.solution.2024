CREATE TABLE T_REGISTRO_MAPA (
    DT_ID         INTEGER,
    DT_Descricao  VARCHAR(400),
    DT_Altitude   VARCHAR(200),
    DT_Longitude  VARCHAR(200),
    DT_CreatedAt  DATE,
    DT_NomeImagem VARCHAR(100),
    DT_TipoLixo   VARCHAR(20) CHECK(DT_TipoLixo IN ('Metal', 'Papel', 'Orgânico', 'Plástico', 'Detritos marítimos'))
);
