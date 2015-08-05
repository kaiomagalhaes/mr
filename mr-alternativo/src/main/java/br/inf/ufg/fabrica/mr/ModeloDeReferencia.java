package br.inf.ufg.fabrica.mr;

import java.util.Map;

/**
 * Fábrica de objetos baseados no Modelo de Referência
 * do openEHR.
 *
 * <p>Um objeto que implementa esta interface permite
 * criar um grafo de objetos em conformidade com o
 * Modelo de Referência do openEHR.</p>
 *
 * <p>Um objeto baseado no Modelo de Referência do openEHR
 * é um objeto em conformidade com as especificações
 * desse Modelo de Referência. Tais especificações são
 * acompanhadas de uma implementação de referência em
 * Java, disponível em
 * <a href="https://github.com/openEHR/java-libs">aqui</a>.
 * </p>
 *
 * <p>Esta é uma implementação alternativa. Não é um <i>fork</i>,
 * nem trabalho derivado da implementação (citada acima).
 * Trata-se de uma nova implementação, que emprega
 * estratégia distinta, para atender objetivos específicos,
 * dentre os quais, conformidade com as especificações
 * do openEHR.
 * </p>
 *
 * <p>Um dos principais objetivos dessa implementação
 * é atender o uso do Modelo de Referência por dispositivos
 * móveis que, em geral, apresentam restrições de capacidade de
 * processamento e memória.</p>
 *
 * <p>Neste sentido, duas decisões de projeto foram
 * estabelecidas: (a) reduzir o tamanho do arquivo jar
 * correspondente à implementação e (b) minimizar o espaço
 * exigido para guardar um grafo de objetos baseado no
 * openEHR.</p>
 *
 * <p>Em decorrência das decisões acima duas orientações
 * são experimentadas: (a) não é criada uma classe para
 * cada conceito (conforme implmentação de referência) e
 * (b) toBytes são armazenados em um vetor de bytes que
 * serializa um grafo típico baseado na implementação
 * de referência.</p>
 */
public interface ModeloDeReferencia {

    /**
     * Dados propriamente ditos correspondentes a objetos
     * compatíveis com o Modelo de Referência.
     *
     * <p>Este vetor de bytes mantém os dados correspondentes
     * a um grafo de objetos, baseados no Modelo de Referência,
     * conforme o modelo de dados estabelecido pela
     * implementação da presente interface.</p>
     *
     * <p>Um acréscimo de um elemento de dado é
     * serializado neste vetor. Metainformações
     * correspondentes devem ser registradas em
     * outra estrutura.</p>
     *
     * <p>A estrutura desta sequência de bytes é
     * obtida por {@code #estrutura}.</p>
     *
     * <p>O retorno deste método, em geral, é persistido.
     * Quando uma consulta aos dados correspondentes
     * for necessária, será "consumido" pelo
     * método {@link #fromBytes(byte[])}.</p>
     *
     * @return Vetor de bytes contendo uma instância
     * do Modelo de Referência (MR) devidamente serializada.
     *
     * @see #fromBytes(byte[])
     * @see #toJSON()
     * @see #toXML()
     */
    byte[] toBytes();

    /**
     * Realiza processo inverso à serialização, geralmente
     * empregado para permitir busca sobre os dados em
     * conformidade com o Modelo de Referência.
     *
     * @param bytes Vetor de bytes serializados por meio
     *              do método {@link #toBytes()}.
     */
    void fromBytes(byte[] bytes);

    /**
     * Serializa as informações do presente objeto, baseado
     * no MR, em um documento XML.
     *
     * <p>O documento XML produzido pelo presente método,
     * sequência de caracteres, deve estar em conformidade
     * com os esquemas adotados pelo openEHR.</p>
     *
     * @return Documento XML correspondente ao grafo
     * de objetos.
     */
    String toXML();

    /**
     * Cria um grafo de objetos, em conformidade com o
     * Modelo de Referência, correspondente ao documento
     * XML fornecido.
     *
     * @param xml Documento XML contendo grafo de objetos
     *            baseados no Modelo de Referência.
     */
    void fromXML(String xml);

    /**
     * Serializa a instância em uma sequência de caracteres
     * no formato JSON.
     *
     * @see #fromJSON(String)
     * @see #toBytes()
     * @see #toXML()
     *
     * @return Sequência de caracteres, no formato JSON,
     * correspondente à serialização do presente objeto.
     */
    String toJSON();

    /**
     * Cria o grafo de objetos, representado pelo presente
     * objeto, em conformidade com o Modelo de Referência e
     * serializado em JSON.
     *
     * <p>Este método faz o processo inverso ao do método
     * {@see #toJSON}.</p>
     *
     * @see #toJSON()
     * @see #fromXML(String)
     * @see #fromBytes(byte[])
     *
     * @param json Sequência de caracteres, no formato JSON,
     *             correspondentes a um grafo de objetos
     *             serializado do Modelo de Referência do
     *             openEHR.
     */
    void fromJSON(String json);

    /**
     * Adiciona um valor lógico ({@code DV_BOOLEAN}).
     *
     * @param valor Valor lógico (DvBoolean) a ser adicionado.
     * @return Identificador do valor lógico adicionado.
     *
     * @see #obtemDvBoolean(int)
     */
    int adicionaDvBoolean(boolean valor);

    /**
     * Recupera o valor lógico ({@code DV_BOOLEAN}) correspondente
     * ao identificador.
     *
     * @param id O identificador único do valor lógico.
     * @return O valor lógico.
     *
     * @see #adicionaDvBoolean(boolean)
     */
    boolean obtemDvBoolean(int id);

    /**
     * Adiciona um identificador ({@code DV_IDENTIFIER}).
     *
     * @param issuer Entidade que emite identificação.
     * @param assigner Entidade que assina identificação.
     * @param id Identificador propriamente dito.
     * @param type Tipo da identificação.
     *
     * @return O identificador único deste identificador
     * na estrutura.
     */
    int adicionaDvIdentifier(
            String issuer,
            String assigner,
            String id,
            String type);

    /**
     * Recupera o valor lógico do objeto.
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cujo valor lógico é desejado.
     * @return Valor lógico do campo do objeto.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o campo não é lógico; (b) o campo não existe;
     * (c) o objeto não existe.
     */
    boolean botemValorLogico(int id, int campo);

    /**
     * Recupera texto do objeto.
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cuja sequência de caracteres
     *              correspondente é desejada.
     * @return Sequência de caracteres correspondente ao
     * campo do objeto.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o campo não é texto; (b) o campo não existe;
     * (c) o objeto não existe.
     */
    String obtemTexto(int id, int campo);

    /**
     * Recupera vetor de bytes (valor do campo do objeto).
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, cujo
     *              valor, um vetor de bytes, é desejado.
     * @return Valor do campo do objeto.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o campo não é texto; (b) o campo não existe;
     * (c) o objeto não existe.
     */
    byte[] obtemVetorBytes(int id, int campo);

    /**
     * Adiciona um {@link java.net.URI} ({@code DV_URI}).
     *
     * @param uri Sequência de caracteres correspondentes
     *            à {@link java.net.URI}.
     *
     * @return O identificador único desta URI na estrutura.
     */
    int adicionaDvUri(String uri);

    /**
     * Adiciona um {@link java.net.URI} cujo esquema é
     * "ehr" ({@code DvEHRURI}).
     *
     * @param uri Sequência de caracteres correspondentes
     *            à {@link java.net.URI}.
     * @return O identificador único desta DvEHRURI na estrutura.
     */
    int adicionaDvEhrUri(String uri);

    /**
     * Adiciona um identificador de terminologia
     * ({@code TERMINOLOGY_ID}).
     *
     * @param nome Nome da terminologia.
     * @param versao Versão da terminologia.
     * @return O identificador único do identificador de
     * terminologia na estrutura.
     */
    int adicionaTerminologyId(String nome, String versao);

    /**
     * Adiciona um identificador de terminologia
     * ({@code TERMINOLOGY_ID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um identificador de
     *              terminologia ({TERMINOLOGY_ID}).
     * @return O identificador único deste identificador de
     * terminologia na estrutura.
     */
    int adicionaTerminologyId(String valor);

    /**
     * Adiciona um código ({@code CODE_PHRASE}).
     *
     * @param terminologyId Um identificador de terminologia.
     * @param codeString A sequência correspondente ao código.
     * @return O identificador único do código na estrutura.
     */
    int adicionaCodePhrase(String terminologyId, String codeString);

    /**
     * Adiciona dado encapsulado em uma sequência de caracteres
     * ({@code DV_PARSABLE}).
     *
     * @param valor Dado encapsulado propriamente dito.
     * @param formalismo Formalismo empregado pelo encapsulamento.
     * @return O identificador único do dado encapsulado na
     * estrutura.
     */
    int adicionaDvParsable(String valor, String formalismo);

    /**
     * Adiciona dado encapsulado em uma sequência de caracteres
     * ({@code DV_PARSABLE}).
     *
     * @param codePhraseCharSet A codificação empregada pelo
     *                          dado encapsulado.
     * @param codePhraseLanguage A linguagem empregada pelo
     *                           dado encapsulado.
     * @param valor O dado encapsulado propriamente dito.
     * @param formalismo O formalismo empregado pelo dado
     *                   encapsulado.
     * @return O identificador único do dado encapsulado na
     * estrutura.
     */
    int adicionaDvParsable(
            String codePhraseCharSet,
            String codePhraseLanguage,
            String valor,
            String formalismo);

    /**
     * Adiciona dado codificado
     * ({@code DV_MULTIMEDIA}).
     *
     * @param codePhraseCharSet A codificação empregada.
     * @param codePhraseLinguagem A linguagem empregada.
     * @param textoAlternativo Texto alternativo para os dados.
     * @param codePhraseTipoMidia A codificação do tipo de mídia.
     * @param codePhraseAlgoritmoCompressao O algoritmo de
     *                                      compressão empregado.
     * @param integridade A sequência de bytes que serve para
     *                    verificar a integridade dos dados.
     * @param codePhraseAlgoritmoIntegridade O algoritmo de
     *                                       verificação de
     *                                       integridade dos
     *                                       dados.
     * @param hDvMultimediaThumbnail O identificador único de
     *                               dados codificados que serve
     *                               como representação
     *                               comprimida do presente
     *                               dado codificado.
     * @param dvUri Sequência de caracteres que é a URI do
     *              dado codificado.
     * @param dado O dado codificado propriamente dito.
     * @return O identificador únido do dado codificado.
     */
    int adicionaDvMultimedia(
            String codePhraseCharSet,
            String codePhraseLinguagem,
            String textoAlternativo,
            String codePhraseTipoMidia,
            String codePhraseAlgoritmoCompressao,
            byte[] integridade,
            String codePhraseAlgoritmoIntegridade,
            int hDvMultimediaThumbnail,
            String dvUri,
            byte[] dado);
}
