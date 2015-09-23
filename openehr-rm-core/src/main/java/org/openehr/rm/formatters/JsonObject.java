package org.openehr.rm.formatters;

import br.inf.ufg.fabrica.mr.ModeloDeReferencia;

import java.io.InputStream;
import java.io.OutputStream;

public class JsonObject implements ModeloDeReferencia {

    public String toJSON() {
        int idRaiz = obtemRaiz();
        String jsonFinal = "";
        return jsonFinal += "{" + buildJson(idRaiz) + "}";
    }

    public void fromJSON(String json) {

    }

    private String buildJson(int idNodoGrafo) {
        String out = "";
        String template = "";
        switch (obtemTipo(idNodoGrafo)) {
            case AUTHORED_RESOURCE:
                template = "{ 'originalLanguage' : #originalLanguage, 'translations' : #translations, 'description' : #description, 'revisionHistory' : #revisionHistory, 'isControlled' : #isControlled }";
                template = template.replaceAll("#originalLanguage",buildJson(obtemByte(idNodoGrafo,0)));
                //template = template.replaceAll("#translations",buildJson(obtemByte(idNodoGrafo,1))); // Translations é um map(String,TranslationDetails) checar como proceder.
                template = template.replaceAll("#description",buildJson(obtemByte(idNodoGrafo,2)));
                template = template.replaceAll("#revisionHistory",buildJson(obtemByte(idNodoGrafo,3)));
                template = template.replaceAll("#isControlled",String.valueOf(obtemValorLogico(idNodoGrafo,4)));
                template = template.replaceAll("'", "\"");
                break;
            case REVISION_HISTORY_ITEM:
                template = "{ 'audits' : [#audits], 'versionId' : #versionId }";
                int idListaAudits = obtemInteiro(idNodoGrafo,0);
                int tamanhoListaAudits = obtemTamanhoLista(idListaAudits);
                String listaAudits = "";
                for(int k = 0; k < tamanhoListaAudits; k++){
                    int idObjetoLista = obtemInteiro(idListaAudits,k);
                    listaAudits = (k == tamanhoListaAudits - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#audits",listaAudits);
                template = template.replaceAll("#originalLanguage",buildJson(obtemByte(idNodoGrafo,1)));
                template = template.replaceAll("'", "\"");
                break;
            case REVISION_HISTORY:
                template = "{ 'items' : [#items]}";
                int idListaItems = obtemInteiro(idNodoGrafo,0);
                int tamanhoListaItems = obtemTamanhoLista(idListaItems);
                String listaItems = "";
                for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#audits",listaItems);
                template = template.replaceAll("'", "\"");
                break;
            case AUDITY_DETAILS:
                template = "{ 'systemId' : '#systemId', 'committer' : #committer, 'timeCommitted' : #timeCommitted, 'changeType' : #changeType, 'description' : #description }";
                template = template.replaceAll("#systemId",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#committer",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCommitted",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#changeType",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#description",String.valueOf(obtemValorLogico(idNodoGrafo,4)));
                template = template.replaceAll("'", "\"");
                break;
            case ATTESTATION:
                template = "{ 'systemId' : '#systemId', 'committer' : #committer, 'timeCommitted' : #timeCommitted, 'changeType' : #changeType," +
                        "'description' : #description, 'attestedView' : attestedView#, 'proof' : '#proof', 'items' : items#, 'reason' : reason#, 'isPending' : isPending# }";
                template = template.replaceAll("#systemId",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#committer",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCommitted",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#changeType",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#description",buildJson(obtemInteiro(idNodoGrafo, 4)));

                template = template.replaceAll("#attestedView",buildJson(obtemInteiro(idNodoGrafo, 5)));
                template = template.replaceAll("#proof",obtemString(idNodoGrafo,6));
                //template = template.replaceAll("#items",buildJson(obtemInteiro(idNodoGrafo, 7))); Items é um SET(DvEHRURI) checar como proceder.
                template = template.replaceAll("#reason",buildJson(obtemInteiro(idNodoGrafo, 8)));
                template = template.replaceAll("#isPending",String.valueOf(obtemValorLogico(idNodoGrafo,9)));
                template = template.replaceAll("'", "\"");
                break;
            case LINK:
                template = "{ 'meaning' : #meaning, 'type' : #type, 'target' : #target}";
                template = template.replaceAll("#meaning",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#type",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#target",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_ID:
                template = "{ 'value' : '#value'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("'", "\"");
                break;
            case TEMPLATE_ID:
                template = "{ 'value' : '#value'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("'", "\"");
                break;
            case TERMINOLOGY_ID:
                template = "{ 'value' : '#value','name' : '#name','version' : '#version'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#name",obtemString(idNodoGrafo,1));
                template = template.replaceAll("#version",obtemString(idNodoGrafo,2));
                template = template.replaceAll("'", "\"");
                break;
            case GENERIC_ID:
                template = "{'value' : '#value','scheme' : '#scheme'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#scheme",obtemString(idNodoGrafo,1));
                template = template.replaceAll("'", "\"");
                break;
            case ARCHETYPE_ID:
                template = "{ 'value' : '#value', 'rmOriginator' : '#rmOriginator', 'rmName' : '#rmName', 'rmEntity' : '#rmEntity'," +
                        "'domainConcept' : '#domainConcept', 'conceptName' : '#conceptName', 'specialisation' : [#specialisation], 'versionID' : '#versionID', }";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#rmOriginator",obtemString(idNodoGrafo,1));
                template = template.replaceAll("#rmName",obtemString(idNodoGrafo,2));
                template = template.replaceAll("#rmEntity",obtemString(idNodoGrafo,3));
                template = template.replaceAll("#domainConcept",obtemString(idNodoGrafo,4));
                template = template.replaceAll("#conceptName",obtemString(idNodoGrafo,5));

                int idListaSpecialisation = obtemInteiro(idNodoGrafo,6);
                int tamanhoListaSpecialisation = obtemTamanhoLista(idListaSpecialisation);
                String listaSpecisialisation = "";
                for(int k = 0; k < tamanhoListaSpecialisation; k++){
                    int idObjetoLista = obtemInteiro(idListaSpecialisation,k);
                    listaSpecisialisation = (k == tamanhoListaSpecialisation - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }

                template = template.replaceAll("#specialisation",listaSpecisialisation);
                template = template.replaceAll("#versionID",obtemString(idNodoGrafo,7));
                template = template.replaceAll("'", "\"");
                break;
            case UID_BASED_ID:
                // Classe Abstrata
                break;
            case HIER_OBJECT_ID:
                template = "{ 'value' : '#value','root':#root,'extesion' : '#extension'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#root",buildJson(obtemInteiro(idNodoGrafo,1)));
                template = template.replaceAll("#extesion",obtemString(idNodoGrafo,2));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_VERSION_ID:
                template = "{ 'value' : '#value','objectID':objectID#,'versionTreeID':versionTreeID#,'creatingSystemID':creatingSystemID#}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#objectID",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#versionTreeID",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#creatingSystemID",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_IDENTIFIER:
                template = "{ 'issuer' : '#issuer', 'assigner': '#assigner', 'id' : '#id', 'type': '#type'}";
                template = template.replaceAll("#issuer",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#assigner",obtemString(idNodoGrafo,1));
                template = template.replaceAll("#id",obtemString(idNodoGrafo,2));
                template = template.replaceAll("#type",obtemString(idNodoGrafo,3));
                template = template.replaceAll("'", "\"");
                break;
            case DV_TEXT:
                template = "{ 'value': '#value', 'mappings': [#mappings], 'formatting': '#formatting', 'hyperlink': #hyperlink, 'language': #language, 'encoding': #encoding}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));

                int idListaMappings_DvText = obtemInteiro(idNodoGrafo, 1);
                int tamanhoListaMappings_DvText = obtemTamanhoLista(idListaMappings_DvText);
                String listaMappings_DvText = "";
                for(int k=0; k<tamanhoListaMappings_DvText; k++){
                    int idObjetoLista = obtemInteiro(idListaMappings_DvText, k);
                    listaMappings_DvText = (k == tamanhoListaMappings_DvText - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#mappings",listaMappings_DvText);

                template = template.replaceAll("#formatting",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#hyperlink",buildJson(obtemInteiro(idNodoGrafo,3)));
                template = template.replaceAll("#language",buildJson(obtemInteiro(idNodoGrafo,4)));
                template = template.replaceAll("#language",buildJson(obtemInteiro(idNodoGrafo,5)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_CODED_TEXT:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value': '#value', 'mappings': [#mappings], 'formatting': '#formatting', 'hyperlink': #hyperlink, 'language': #language, 'charset': #charset, 'definingCode': #definingCode}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(DV_CODED_TEXT));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));

                int idListaMappings_DvCodedText = obtemInteiro(idNodoGrafo, 1);
                int tamanhoListaMappings_DvCodedText = obtemTamanhoLista(idListaMappings_DvCodedText);
                String listaMappings_DvCodedText = "";
                for(int k=0; k<tamanhoListaMappings_DvCodedText; k++){
                    int idObjetoLista = obtemInteiro(idListaMappings_DvCodedText, k);
                    listaMappings_DvCodedText = (k == tamanhoListaMappings_DvCodedText - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#mappings",listaMappings_DvCodedText);

                template = template.replaceAll("#formatting",obtemString(idNodoGrafo,2));
                template = template.replaceAll("#hyperlink",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#language",buildJson(obtemInteiro(idNodoGrafo,4)));
                template = template.replaceAll("#charset",buildJson(obtemInteiro(idNodoGrafo,5)));
                template = template.replaceAll("#definingCode",buildJson(obtemInteiro(idNodoGrafo,6)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_PARAGRAPH:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'items' : [#items]}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(DV_PARAGRAPH));
                int idListaItems_DvParagraph = obtemInteiro(idNodoGrafo,0);
                int tamanhoListaItems_DvParagraph = obtemTamanhoLista(idListaItems_DvParagraph);
                String listaItems_DvParagraph = "";
                for(int k=0; k<tamanhoListaItems_DvParagraph; k++){
                    int idObjetoLista = obtemInteiro(idListaItems_DvParagraph,k);
                    listaItems_DvParagraph = (k == tamanhoListaItems_DvParagraph - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#items",listaItems_DvParagraph);
                template = template.replaceAll("'", "\"");
                break;
            case DV_URI:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(DV_URI));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("'", "\"");
                break;
            case DV_EHR_URI:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(DV_EHR_URI));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("'", "\"");
                break;
        }
        out += template;
        return out;
    }

    public byte[] toBytes() {
        return new byte[0];
    }

    public void toBytes(OutputStream destino) {

    }

    public void fromBytes(byte[] bytes) {

    }

    public void fromBytes(InputStream entrada) {

    }

    public String toXml() {
        return null;
    }

    public void toXml(OutputStream stream) {

    }

    public void fromXml(String xml) {

    }

    public void fromXml(InputStream stream) {

    }

    public String toJson() {
        return null;
    }

    public void toJson(OutputStream stream) {

    }

    public void fromJson(String json) {

    }

    public void fromJson(InputStream entrada) {

    }

    public String toXML() {
        return null;
    }

    public void fromXML(String xml) {

    }


    public int obtemQtdeBytes(int id, int campo) {
        return 0;
    }

    public byte[] obtemBytes(int id, int campo, int ini, int fim) {
        return new byte[0];
    }

    public byte[] obtemBytes(int id, int campo) {
        return new byte[0];
    }

    public void defineRaiz(int raiz) {

    }

    public int obtemRaiz() {
        return 0;
    }

    public int totalObjetos() {
        return 0;
    }

    public int obtemTipo(int id) {
        return 0;
    }

    public int obtemTipo(int id, int campo) {
        return 0;
    }

    public byte obtemByte(int id, int campo) {
        return 0;
    }

    public String obtemString(int id, int campo) {
        return null;
    }

    public boolean obtemLogico(int id, int campo) {
        return false;
    }

    public int obtemChave(int id, int campo) {
        return 0;
    }

    public int obtemInteiro(int id, int campo) {
        return 0;
    }

    public float obtemFloat(int id, int campo) {
        return 0;
    }

    public double obtemDouble(int id, int campo) {
        return 0;
    }

    public int adicionaDvBoolean(boolean valor) {
        return 0;
    }

    public boolean obtemDvBoolean(int id) {
        return false;
    }

    public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
        return 0;
    }

    public boolean obtemValorLogico(int id, int campo) {
        return false;
    }

    public String obtemTexto(int id, int campo) {
        return null;
    }

    public byte[] obtemVetorBytes(int id, int campo) {
        return new byte[0];
    }

    public int obtemTamanhoVetorBytes(int id, int campo) {
        return 0;
    }

    public InputStream obtemStreamVetorBytes(int id, int campo) {
        return null;
    }

    public int adicionaLista(int quantidade) {
        return 0;
    }

    public int adicionaItem(int lista, int item) {
        return 0;
    }

    public int obtemTamanhoLista(int lista) {
        return 0;
    }

    public int buscaEmLista(int lista, int objeto) {
        return 0;
    }

    public void elimineObjeto(int objeto) {

    }

    public int adicionaHash(int chaves, int valores) {
        return 0;
    }

    public int adicionaDvUri(String uri) {
        return 0;
    }

    public int adicionaDvEhrUri(String uri) {
        return 0;
    }

    public int adicionaTerminologyId(String nome, String versao) {
        return 0;
    }

    public int adicionaTerminologyId(String valor) {
        return 0;
    }

    public int adicionaVersionTreeId(String valor) {
        return 0;
    }

    public int adicionaArchetypeId(String valor) {
        return 0;
    }

    public int adicionaGenericId(String valor, String scheme) {
        return 0;
    }

    public int adicionaCodePhrase(String terminologyId, String codeString) {
        return 0;
    }

    public int adicionaDvParsable(String valor, String formalismo) {
        return 0;
    }

    public int adicionaDvParsable(String codePhraseCharSet, String codePhraseLanguage, String valor, String formalismo) {
        return 0;
    }

    public int adicionaDvMultimedia(String codePhraseCharSet, String codePhraseLinguagem, String textoAlternativo, String codePhraseTipoMidia, String codePhraseAlgoritmoCompressao, byte[] integridade, String codePhraseAlgoritmoIntegridade, int hDvMultimediaThumbnail, String dvUri, byte[] dado) {
        return 0;
    }

    public int adicionaIsoOid(String valor) {
        return 0;
    }

    public int adicionaLocatableRef(String namespace, String type, String path, int object_id, int uid_based_id) {
        return 0;
    }

    public int adicionaObjectRef(String namespace, String type, int object_id) {
        return 0;
    }

    public int adicionaPartyRef(String namespace, String type, int object_id) {
        return 0;
    }

    public int adicionaAccessGroupRef(String namespace, String type, int object_id) {
        return 0;
    }

    public int adicionaUuid(String valor) {
        return 0;
    }

    public int adicionaInternetId(String valor) {
        return 0;
    }

    public int adicionaHierObjectId(String valor) {
        return 0;
    }

    public int adicionaHierObjectId(String raiz, String extensao) {
        return 0;
    }

    public int adicionaObjectVersionId(String valor) {
        return 0;
    }

    public int adicionaTemplateId(String valor) {
        return 0;
    }

    public int adicionaObjectVersionId(String objectId, String versionTreeId, String creatingSystemId) {
        return 0;
    }
}

