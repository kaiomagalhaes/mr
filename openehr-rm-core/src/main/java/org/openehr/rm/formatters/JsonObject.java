package org.openehr.rm.formatters;

import br.inf.ufg.fabrica.mr.ModeloDeReferencia;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;

public class JsonObject implements ModeloDeReferencia {

    public String toJSON() {
        int idRaiz = obtemRaiz();
        String jsonFinal = "";
        return jsonFinal += "{" + buildJson(idRaiz) + "}";
    }

    public int fromJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int idRaizGrafo = buildGraph(jsonObject);
            return idRaizGrafo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int buildGraph(JSONObject jsonObject) {
        int tipo = 0;
        try {
            tipo = jsonObject.getInt("globalTypeIdn");
            if (tipo == TERMINOLOGY_ID) {
                return adicionaTerminologyId(jsonObject.getString("name"), jsonObject.getString("value"));
            } else if (tipo == CODE_PHRASE) {
                int idTerminologyID = buildGraph(jsonObject.getJSONObject("terminologyId"));
                return adicionaCodePhrase(idTerminologyID, jsonObject.getString("codeString"));
            }else if(tipo == ITEM){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				return adicionaItem(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent);
			}else if(tipo == ITEM_LIST){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				//Lista
				int idItems = buildGraph(jsonObject.getJSONObject("items"));
				return adicionaItemList(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItems);
			}else if(tipo == ITEM_SINGLE){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idItem = buildGraph(jsonObject.getJSONObject("item"));
				return adicionaItemSingle(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItem);
			}else if(tipo == ITEM_STRUCTURE){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				return adicionaItemStructure(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent);
			}else if(tipo == ITEM_TABLE){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				//Lista
				int idRows = buildGraph(jsonObject.getJSONObject("rows"));
				return adicionaItemTable(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idRows);
			}else if(tipo == ITEM_TREE){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				//Lista
				int idItems = buildGraph(jsonObject.getJSONObject("items"));
				return adicionaItemTree(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItems);
			}else if(tipo == ELEMENT){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idValue = buildGraph(jsonObject.getJSONObject("value"));
				int idNullFlavour = buildGraph(jsonObject.getJSONObject("nullFlavour"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaElement(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idValue, idNullFlavour, idTerminologyService);
			}else if(tipo == CLUSTER){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				//Lista
				int idItems = buildGraph(jsonObject.getJSONObject("items"));
				return adicionaCluster(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItems);
			}else if(tipo == FOLDER){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				//Lista
				int idFolders = buildGraph(jsonObject.getJSONObject("folders"));
				//Lista
				int idItems = buildGraph(jsonObject.getJSONObject("items"));
				return adicionaFolder(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idFolders, idItems);
			}else if(tipo == PARTY_RELATIONSHIP){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				int idTimeValidity = buildGraph(jsonObject.getJSONObject("timeValidity"));
				int idSource = buildGraph(jsonObject.getJSONObject("source"));
				int idTarget = buildGraph(jsonObject.getJSONObject("target"));
				return adicionaPartyRelationship(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idDetails, idTimeValidity, idSource, idTarget);
			}else if(tipo == XFOLDER){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				//Lista
				int idFolders = buildGraph(jsonObject.getJSONObject("folders"));
				//Lista
				int idCompositions = buildGraph(jsonObject.getJSONObject("compositions"));
				return adicionaXFolder(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idFolders, idCompositions);
			}else if(tipo == COMPOSITION){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				//Lista
				int idContent = buildGraph(jsonObject.getJSONObject("content"));
				int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
				int idContext = buildGraph(jsonObject.getJSONObject("context"));
				int idComposer = buildGraph(jsonObject.getJSONObject("composer"));
				int idCategory = buildGraph(jsonObject.getJSONObject("category"));
				int idTerritory = buildGraph(jsonObject.getJSONObject("territory"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaComposition(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idContent, idLanguage, idContext, idComposer, idCategory, idTerritory, idTerminologyService);
			}else if(tipo == ADDRESS){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				return adicionaAddress(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idDetails);
			}else if(tipo == PARTY){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				//Lista
				int idIdentities = buildGraph(jsonObject.getJSONObject("identities"));
				//Lista
				int idContacts = buildGraph(jsonObject.getJSONObject("contacts"));
				//Lista
				int idRelationships = buildGraph(jsonObject.getJSONObject("relationships"));
				//Lista
				int idReverseRelationships = buildGraph(jsonObject.getJSONObject("reverseRelationships"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				return adicionaParty(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idIdentities, idContacts, idRelationships, idReverseRelationships, idDetails);
			}else if(tipo == ROLE){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				//Lista
				int idIdentities = buildGraph(jsonObject.getJSONObject("identities"));
				//Lista
				int idContacts = buildGraph(jsonObject.getJSONObject("contacts"));
				//Lista
				int idRelationships = buildGraph(jsonObject.getJSONObject("relationships"));
				//Lista
				int idReverseRelationships = buildGraph(jsonObject.getJSONObject("reverseRelationships"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				//Lista
				int idCapabilities = buildGraph(jsonObject.getJSONObject("capabilities"));
				int idTimeValidity = buildGraph(jsonObject.getJSONObject("timeValidity"));
				int idPerformer = buildGraph(jsonObject.getJSONObject("performer"));
				return adicionaRole(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idIdentities, idContacts, idRelationships, idReverseRelationships, idDetails, idCapabilities, idTimeValidity, idPerformer);
			}else if(tipo == ACTOR){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				//Lista
				int idIdentities = buildGraph(jsonObject.getJSONObject("identities"));
				//Lista
				int idContacts = buildGraph(jsonObject.getJSONObject("contacts"));
				//Lista
				int idRelationships = buildGraph(jsonObject.getJSONObject("relationships"));
				//Lista
				int idReverseRelationships = buildGraph(jsonObject.getJSONObject("reverseRelationships"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				//Lista
				int idRoles = buildGraph(jsonObject.getJSONObject("roles"));
				//Lista
				int idLanguages = buildGraph(jsonObject.getJSONObject("languages"));
				return adicionaActor(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idIdentities, idContacts, idRelationships, idReverseRelationships, idDetails, idRoles, idLanguages);
			}else if(tipo == PERSON){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				//Lista
				int idIdentities = buildGraph(jsonObject.getJSONObject("identities"));
				//Lista
				int idContacts = buildGraph(jsonObject.getJSONObject("contacts"));
				//Lista
				int idRelationships = buildGraph(jsonObject.getJSONObject("relationships"));
				//Lista
				int idReverseRelationships = buildGraph(jsonObject.getJSONObject("reverseRelationships"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				//Lista
				int idRoles = buildGraph(jsonObject.getJSONObject("roles"));
				//Lista
				int idLanguages = buildGraph(jsonObject.getJSONObject("languages"));
				return adicionaPerson(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idIdentities, idContacts, idRelationships, idReverseRelationships, idDetails, idRoles, idLanguages);
			}else if(tipo == AGENT){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				//Lista
				int idIdentities = buildGraph(jsonObject.getJSONObject("identities"));
				//Lista
				int idContacts = buildGraph(jsonObject.getJSONObject("contacts"));
				//Lista
				int idRelationships = buildGraph(jsonObject.getJSONObject("relationships"));
				//Lista
				int idReverseRelationships = buildGraph(jsonObject.getJSONObject("reverseRelationships"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				//Lista
				int idRoles = buildGraph(jsonObject.getJSONObject("roles"));
				//Lista
				int idLanguages = buildGraph(jsonObject.getJSONObject("languages"));
				return adicionaAgent(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idIdentities, idContacts, idRelationships, idReverseRelationships, idDetails, idRoles, idLanguages);
			}else if(tipo == ORGANISATION){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				//Lista
				int idIdentities = buildGraph(jsonObject.getJSONObject("identities"));
				//Lista
				int idContacts = buildGraph(jsonObject.getJSONObject("contacts"));
				//Lista
				int idRelationships = buildGraph(jsonObject.getJSONObject("relationships"));
				//Lista
				int idReverseRelationships = buildGraph(jsonObject.getJSONObject("reverseRelationships"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				//Lista
				int idRoles = buildGraph(jsonObject.getJSONObject("roles"));
				//Lista
				int idLanguages = buildGraph(jsonObject.getJSONObject("languages"));
				return adicionaOrganisation(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idIdentities, idContacts, idRelationships, idReverseRelationships, idDetails, idRoles, idLanguages);
			}else if(tipo == EHR_STATUS){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idSubject = buildGraph(jsonObject.getJSONObject("subject"));
				//SUPONHO
				boolean isQueryable = jsonObject.getBoolean("isQueryable");
				boolean isModifiable = jsonObject.getBoolean("isModifiable");
				int idOtherDetails = buildGraph(jsonObject.getJSONObject("otherDetails"));
				return adicionaEHRStatus(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idSubject, isQueryable, isModifiable, idOtherDetails);
			}else if(tipo == ACTIVITY){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idDescription = buildGraph(jsonObject.getJSONObject("description"));
				int idTiming = buildGraph(jsonObject.getJSONObject("timing"));
				int idActionArchetypeId = buildGraph(jsonObject.getJSONObject("actionArchetypeId"));
				return adicionaActivity(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idDescription, idTiming, idActionArchetypeId);
			}else if(tipo == EVENT){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idTime = buildGraph(jsonObject.getJSONObject("time"));
				int idData = buildGraph(jsonObject.getJSONObject("data"));
				int idState = buildGraph(jsonObject.getJSONObject("state"));
				return adicionaEvent(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idTime, idData, idState);
			}else if(tipo == INTERVAL_EVENT){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idTime = buildGraph(jsonObject.getJSONObject("time"));
				int idData = buildGraph(jsonObject.getJSONObject("data"));
				int idState = buildGraph(jsonObject.getJSONObject("state"));
				int idWidth = buildGraph(jsonObject.getJSONObject("width"));
				int idMathFunction = buildGraph(jsonObject.getJSONObject("mathFunction"));
				int idSampleCount = buildGraph(jsonObject.getJSONObject("sampleCount"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaIntervalEvent(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idTime, idData, idState, idWidth, idMathFunction, idSampleCount, idTerminologyService);
			}else if(tipo == POINT_EVENT){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idTime = buildGraph(jsonObject.getJSONObject("time"));
				int idData = buildGraph(jsonObject.getJSONObject("data"));
				int idState = buildGraph(jsonObject.getJSONObject("state"));
				return adicionaPointEvent(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idTime, idData, idState);
			}else if(tipo == MESSAGE_CONTENT){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				String originalArchetypeNodeId = jsonObject.getString("originalArchetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				return adicionaMessageContent(idUid, archetypeNodeId, originalArchetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent);
			}else if(tipo == EHR_ACCESS){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idSettings = buildGraph(jsonObject.getJSONObject("settings"));
				return adicionaEHRAccess(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idSettings);
			}else if(tipo == PARTY_IDENTITY){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idDetails = buildGraph(jsonObject.getJSONObject("details"));
				return adicionaPartyIdentity(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idDetails);
			}else if(tipo == CONTENT_ITEM){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				String originalArchetypeNodeId = jsonObject.getString("originalArchetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				return adicionaContentItem(idUid, archetypeNodeId, originalArchetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent);
			}else if(tipo == ADMIN_ENTRY){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
				int idEncoding = buildGraph(jsonObject.getJSONObject("encoding"));
				int idSubject = buildGraph(jsonObject.getJSONObject("subject"));
				int idProvider = buildGraph(jsonObject.getJSONObject("provider"));
				int idWorkflowId = buildGraph(jsonObject.getJSONObject("workflowId"));
				//Lista
				int idOtherParticipations = buildGraph(jsonObject.getJSONObject("otherParticipations"));
				int idData = buildGraph(jsonObject.getJSONObject("data"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaAdminEntry(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idLanguage, idEncoding, idSubject, idProvider, idWorkflowId, idOtherParticipations, idData, idTerminologyService);
			}else if(tipo == CARE_ENTRY){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
				int idEncoding = buildGraph(jsonObject.getJSONObject("encoding"));
				int idSubject = buildGraph(jsonObject.getJSONObject("subject"));
				int idProvider = buildGraph(jsonObject.getJSONObject("provider"));
				int idWorkflowId = buildGraph(jsonObject.getJSONObject("workflowId"));
				//Lista
				int idOtherParticipations = buildGraph(jsonObject.getJSONObject("otherParticipations"));
				int idProtocol = buildGraph(jsonObject.getJSONObject("protocol"));
				int idGuidelineId = buildGraph(jsonObject.getJSONObject("guidelineId"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaCareEntry(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idLanguage, idEncoding, idSubject, idProvider, idWorkflowId, idOtherParticipations, idProtocol, idGuidelineId, idTerminologyService);
			}else if(tipo == OBSERVATION){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
				int idEncoding = buildGraph(jsonObject.getJSONObject("encoding"));
				int idSubject = buildGraph(jsonObject.getJSONObject("subject"));
				int idProvider = buildGraph(jsonObject.getJSONObject("provider"));
				int idWorkflowId = buildGraph(jsonObject.getJSONObject("workflowId"));
				//Lista
				int idOtherParticipations = buildGraph(jsonObject.getJSONObject("otherParticipations"));
				int idProtocol = buildGraph(jsonObject.getJSONObject("protocol"));
				int idGuidelineId = buildGraph(jsonObject.getJSONObject("guidelineId"));
				int idData = buildGraph(jsonObject.getJSONObject("data"));
				int idState = buildGraph(jsonObject.getJSONObject("state"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaObservation(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idLanguage, idEncoding, idSubject, idProvider, idWorkflowId, idOtherParticipations, idProtocol, idGuidelineId, idData, idState, idTerminologyService);
			}else if(tipo == INSTRUCTION){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
				int idEncoding = buildGraph(jsonObject.getJSONObject("encoding"));
				int idSubject = buildGraph(jsonObject.getJSONObject("subject"));
				int idProvider = buildGraph(jsonObject.getJSONObject("provider"));
				int idWorkflowId = buildGraph(jsonObject.getJSONObject("workflowId"));
				//Lista
				int idOtherParticipations = buildGraph(jsonObject.getJSONObject("otherParticipations"));
				int idProtocol = buildGraph(jsonObject.getJSONObject("protocol"));
				int idGuidelineId = buildGraph(jsonObject.getJSONObject("guidelineId"));
				int idNarrative = buildGraph(jsonObject.getJSONObject("narrative"));
				//Lista
				int idActivities = buildGraph(jsonObject.getJSONObject("activities"));
				int idExpiryTime = buildGraph(jsonObject.getJSONObject("expiryTime"));
				int idWfDefinition = buildGraph(jsonObject.getJSONObject("wfDefinition"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaInstruction(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idLanguage, idEncoding, idSubject, idProvider, idWorkflowId, idOtherParticipations, idProtocol, idGuidelineId, idNarrative, idActivities, idExpiryTime, idWfDefinition, idTerminologyService);
			}else if(tipo == ACTION){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
				int idEncoding = buildGraph(jsonObject.getJSONObject("encoding"));
				int idSubject = buildGraph(jsonObject.getJSONObject("subject"));
				int idProvider = buildGraph(jsonObject.getJSONObject("provider"));
				int idWorkflowId = buildGraph(jsonObject.getJSONObject("workflowId"));
				//Lista
				int idOtherParticipations = buildGraph(jsonObject.getJSONObject("otherParticipations"));
				int idProtocol = buildGraph(jsonObject.getJSONObject("protocol"));
				int idGuidelineId = buildGraph(jsonObject.getJSONObject("guidelineId"));
				int idTime = buildGraph(jsonObject.getJSONObject("time"));
				int idDescription = buildGraph(jsonObject.getJSONObject("description"));
				int idIsmTransition = buildGraph(jsonObject.getJSONObject("ismTransition"));
				int idInstructionDetails = buildGraph(jsonObject.getJSONObject("instructionDetails"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaAction(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idLanguage, idEncoding, idSubject, idProvider, idWorkflowId, idOtherParticipations, idProtocol, idGuidelineId, idTime, idDescription, idIsmTransition, idInstructionDetails, idTerminologyService);
			}else if(tipo == EVALUATION){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
				int idEncoding = buildGraph(jsonObject.getJSONObject("encoding"));
				int idSubject = buildGraph(jsonObject.getJSONObject("subject"));
				int idProvider = buildGraph(jsonObject.getJSONObject("provider"));
				int idWorkflowId = buildGraph(jsonObject.getJSONObject("workflowId"));
				//Lista
				int idOtherParticipations = buildGraph(jsonObject.getJSONObject("otherParticipations"));
				int idProtocol = buildGraph(jsonObject.getJSONObject("protocol"));
				int idGuidelineId = buildGraph(jsonObject.getJSONObject("guidelineId"));
				int idData = buildGraph(jsonObject.getJSONObject("data"));
				int idTerminologyService = buildGraph(jsonObject.getJSONObject("terminologyService"));
				return adicionaEvaluation(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idLanguage, idEncoding, idSubject, idProvider, idWorkflowId, idOtherParticipations, idProtocol, idGuidelineId, idData, idTerminologyService);
			}else if(tipo == SECTION){
				int idUid = buildGraph(jsonObject.getJSONObject("uid"));
				String archetypeNodeId = jsonObject.getString("archetypeNodeId");
				int idName = buildGraph(jsonObject.getJSONObject("name"));
				int idArchetypeDetails = buildGraph(jsonObject.getJSONObject("archetypeDetails"));
				int idFeederAudit = buildGraph(jsonObject.getJSONObject("feederAudit"));
				//Lista
				int idLinks = buildGraph(jsonObject.getJSONObject("links"));
				int idParent = buildGraph(jsonObject.getJSONObject("parent"));
				//Lista
				int idItems = buildGraph(jsonObject.getJSONObject("items"));
				return adicionaEvaluation(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItems);
			}
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tipo;
    }

    private String buildJson(int idNodoGrafo) {
        String out = "";
        String template = "";
        switch (obtemTipo(idNodoGrafo)) {
            case DV_TEMPORAL:
                template = "{ 'dateTime' : #dateTime, 'value' : #value }";
                template = template.replaceAll("#dateTime", buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("'", "\"");
                break;
            case DV_PARSABLE:
                template = "{ 'charset' : #charset, 'language' : #language, 'value' : #value, 'formalism' : #formalism, 'terminologyService' : #terminologyService }";
                template = template.replaceAll("#charset", buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#language", buildJson(obtemByte(idNodoGrafo, 1)));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#formalism", obtemString(idNodoGrafo, 3));
                template = template.replaceAll("#terminologyService", buildJson(obtemByte(idNodoGrafo, 4)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_PERIODIC_TIME_SPECIFICATION:
                template = "{ 'value' : #value }";
                template = template.replaceAll("#value", buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_GENERAL_TIME_SPECIFICATION:
                template = "{ 'value' : #value }";
                template = template.replaceAll("#value", buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("'", "\"");
                break;
            case LOCATABLE:
                template = "{ 'uid' : #uid, 'archetypeNodeId' : #archetypeNodeId, 'originalArchetypeNodeId' : #originalArchetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, " +
                        "'feederAudit' : #feederAudit, 'links' : #links, 'parent' : #parent }";
                template = template.replaceAll("#uid", buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#originalArchetypeNodeId", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#name", buildJson(obtemByte(idNodoGrafo, 3)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemByte(idNodoGrafo, 4)));
                template = template.replaceAll("#feederAudit", buildJson(obtemByte(idNodoGrafo, 5)));
                int idListaLinks = obtemInteiro(idNodoGrafo, 6);
                int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                String listaLinks = "";
                for (int k = 0; k < tamanhoListaLinks; k++) {
                    int idObjetoLista = obtemInteiro(idListaLinks, k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemByte(idNodoGrafo, 7)));
                template = template.replaceAll("'", "\"");
                break;
            case DATA_STRUCTURE:
                // verificar se deve ser feito ou não pois possui os mesmos atributos de Locatable (também extende ela)
                break;
            case GROUP:
                template = "{ 'name' : #name }";
                template = template.replaceAll("#name", obtemString(idNodoGrafo, 0));
                int idListaConcepts = obtemInteiro(idNodoGrafo, 1);
                int tamanhoListaConcepts = obtemTamanhoLista(idListaConcepts);
                String listaConcepts = "";
                for (int k = 0; k < tamanhoListaConcepts; k++) {
                    int idObjetoLista = obtemInteiro(idListaConcepts, k);
                    listaConcepts = (k == tamanhoListaConcepts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#concepts", listaConcepts);
                template = template.replaceAll("'", "\"");
                break;
            case ENTRY:
                template = "{ 'uid' : #uid, 'archetypeNodeId' : #archetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, 'feederAudit' : #feederAudit, 'links' : #links, 'parent' : #parent," +
                        "'language' : #language, 'encoding' : #encoding, 'subject' : #subject, 'provider' : #provider, 'workflowId' : #workflowId, 'otherParticipations' : #otherParticipations, 'terminologyService' : #terminologyService }";
                template = template.replaceAll("#uid", buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#name", buildJson(obtemByte(idNodoGrafo, 2)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemByte(idNodoGrafo, 3)));
                template = template.replaceAll("#feederAudit", buildJson(obtemByte(idNodoGrafo, 4)));
                idListaLinks = obtemInteiro(idNodoGrafo, 5);
                tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                listaLinks = "";
                for (int k = 0; k < tamanhoListaLinks; k++) {
                    int idObjetoLista = obtemInteiro(idListaLinks, k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemByte(idNodoGrafo, 7)));
                template = template.replaceAll("#language", buildJson(obtemByte(idNodoGrafo, 8)));
                template = template.replaceAll("#encoding", buildJson(obtemByte(idNodoGrafo, 9)));
                template = template.replaceAll("#subject", buildJson(obtemByte(idNodoGrafo, 10)));
                template = template.replaceAll("#provider", buildJson(obtemByte(idNodoGrafo, 11)));
                template = template.replaceAll("#workflowId", buildJson(obtemByte(idNodoGrafo, 12)));
                int idListaParticipations = obtemInteiro(idNodoGrafo, 13);
                int tamanhoListaParticipations = obtemTamanhoLista(idListaParticipations);
                String listaParticipations = "";
                for (int k = 0; k < tamanhoListaParticipations; k++) {
                    int idObjetoLista = obtemInteiro(idListaParticipations, k);
                    listaParticipations = (k == tamanhoListaParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherParticipations", listaParticipations);
                template = template.replaceAll("#terminologyService", buildJson(obtemByte(idNodoGrafo, 14)));
                template = template.replaceAll("'", "\"");
                break;
            case CONTACT:
                template = "{ 'uid' : #uid, 'archetypeNodeId' : #archetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, " +
                        "'feederAudit' : #feederAudit, 'links' : #links, 'parent' : #parent, 'timeValidity' : #timeValidity, 'addresses' : #addresses }";
                template = template.replaceAll("#uid", buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#name", buildJson(obtemByte(idNodoGrafo, 2)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemByte(idNodoGrafo, 3)));
                template = template.replaceAll("#feederAudit", buildJson(obtemByte(idNodoGrafo, 4)));
                idListaLinks = obtemInteiro(idNodoGrafo, 5);
                tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                listaLinks = "";
                for (int k = 0; k < tamanhoListaLinks; k++) {
                    int idObjetoLista = obtemInteiro(idListaLinks, k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemByte(idNodoGrafo, 6)));
                //template = template.replaceAll("#timeValidity", buildJson(obtemByte(idNodoGrafo, 7))); <-- timeValidity é um DvInterval<DvDate> - verificar como proceder
                int idListaAddresses = obtemInteiro(idNodoGrafo, 8);
                int tamanhoListaAddresses = obtemTamanhoLista(idListaAddresses);
                String listaAddresses = "";
                for (int k = 0; k < tamanhoListaAddresses; k++) {
                    int idObjetoLista = obtemInteiro(idListaAddresses, k);
                    listaAddresses = (k == tamanhoListaAddresses - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#addresses", listaAddresses);
                template = template.replaceAll("'", "\"");
                break;
            case AUTHORED_RESOURCE:
                template = "{ 'originalLanguage' : #originalLanguage, 'translations' : #translations, 'description' : #description, 'revisionHistory' : #revisionHistory, 'isControlled' : #isControlled }";
                template = template.replaceAll("#originalLanguage", buildJson(obtemByte(idNodoGrafo, 0)));
                //template = template.replaceAll("#translations",buildJson(obtemByte(idNodoGrafo,1))); // Translations é um map(String,TranslationDetails) checar como proceder.
                template = template.replaceAll("#description", buildJson(obtemByte(idNodoGrafo, 2)));
                template = template.replaceAll("#revisionHistory", buildJson(obtemByte(idNodoGrafo, 3)));
                template = template.replaceAll("#isControlled", String.valueOf(obtemValorLogico(idNodoGrafo, 4)));
                template = template.replaceAll("'", "\"");
                break;
            case REVISION_HISTORY_ITEM:
                template = "{ 'audits' : [#audits], 'versionId' : #versionId }";
                int idListaAudits = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaAudits = obtemTamanhoLista(idListaAudits);
                String listaAudits = "";
                for (int k = 0; k < tamanhoListaAudits; k++) {
                    int idObjetoLista = obtemInteiro(idListaAudits, k);
                    listaAudits = (k == tamanhoListaAudits - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#audits", listaAudits);
                template = template.replaceAll("#originalLanguage", buildJson(obtemByte(idNodoGrafo, 1)));
                template = template.replaceAll("'", "\"");
                break;
            case REVISION_HISTORY:
                template = "{ 'items' : [#items]}";
                int idListaItems = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaItems = obtemTamanhoLista(idListaItems);
                String listaItems = "";
                for (int k = 0; k < tamanhoListaItems; k++) {
                    int idObjetoLista = obtemInteiro(idListaItems, k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#audits", listaItems);
                template = template.replaceAll("'", "\"");
                break;
            case AUDITY_DETAILS:
                template = "{ 'systemId' : '#systemId', 'committer' : #committer, 'timeCommitted' : #timeCommitted, 'changeType' : #changeType, 'description' : #description }";
                template = template.replaceAll("#systemId", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#committer", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCommitted", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#changeType", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#description", String.valueOf(obtemValorLogico(idNodoGrafo, 4)));
                template = template.replaceAll("'", "\"");
                break;
            case ATTESTATION:
                template = "{ 'systemId' : '#systemId', 'committer' : #committer, 'timeCommitted' : #timeCommitted, 'changeType' : #changeType," +
                        "'description' : #description, 'attestedView' : attestedView#, 'proof' : '#proof', 'items' : items#, 'reason' : reason#, 'isPending' : isPending# }";
                template = template.replaceAll("#systemId", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#committer", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCommitted", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#changeType", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#description", buildJson(obtemInteiro(idNodoGrafo, 4)));

                template = template.replaceAll("#attestedView", buildJson(obtemInteiro(idNodoGrafo, 5)));
                template = template.replaceAll("#proof", obtemString(idNodoGrafo, 6));
                //template = template.replaceAll("#items",buildJson(obtemInteiro(idNodoGrafo, 7))); Items é um SET(DvEHRURI) checar como proceder.
                template = template.replaceAll("#reason", buildJson(obtemInteiro(idNodoGrafo, 8)));
                template = template.replaceAll("#isPending", String.valueOf(obtemValorLogico(idNodoGrafo, 9)));
                template = template.replaceAll("'", "\"");
                break;
            case LINK:
                template = "{ 'meaning' : #meaning, 'type' : #type, 'target' : #target}";
                template = template.replaceAll("#meaning", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#type", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#target", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_ID:
                template = "{ 'value' : '#value'}";
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("'", "\"");
                break;
            case TEMPLATE_ID:
                template = "{ 'value' : '#value'}";
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("'", "\"");
                break;
            case TERMINOLOGY_ID:
                template = "{ 'value' : '#value','name' : '#name','version' : '#version'}";
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#name", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#version", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("'", "\"");
                break;
            case GENERIC_ID:
                template = "{'value' : '#value','scheme' : '#scheme'}";
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#scheme", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("'", "\"");
                break;
            case ARCHETYPE_ID:
                template = "{ 'value' : '#value', 'rmOriginator' : '#rmOriginator', 'rmName' : '#rmName', 'rmEntity' : '#rmEntity'," +
                        "'domainConcept' : '#domainConcept', 'conceptName' : '#conceptName', 'specialisation' : [#specialisation], 'versionID' : '#versionID', }";
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#rmOriginator", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#rmName", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#rmEntity", obtemString(idNodoGrafo, 3));
                template = template.replaceAll("#domainConcept", obtemString(idNodoGrafo, 4));
                template = template.replaceAll("#conceptName", obtemString(idNodoGrafo, 5));

                int idListaSpecialisation = obtemInteiro(idNodoGrafo, 6);
                int tamanhoListaSpecialisation = obtemTamanhoLista(idListaSpecialisation);
                String listaSpecisialisation = "";
                for (int k = 0; k < tamanhoListaSpecialisation; k++) {
                    int idObjetoLista = obtemInteiro(idListaSpecialisation, k);
                    listaSpecisialisation = (k == tamanhoListaSpecialisation - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }

                template = template.replaceAll("#specialisation", listaSpecisialisation);
                template = template.replaceAll("#versionID", obtemString(idNodoGrafo, 7));
                template = template.replaceAll("'", "\"");
                break;
            case UID_BASED_ID:
                // Classe Abstrata
                break;
            case HIER_OBJECT_ID:
                template = "{ 'value' : '#value','root': #root, 'extesion' : '#extension'}";
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#root", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#extesion", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_VERSION_ID:
                template = "{ 'value' : '#value','objectID': '#objectID','versionTreeID': '#versionTreeID','creatingSystemID': '#creatingSystemID'}";
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#objectID", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#versionTreeID", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#creatingSystemID", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("'", "\"");
                break;
            case ORIGINALVERSION:
                template = "{'commitAudit': '#commitAudit', 'uid':'#uid', 'precedingVersionID':'#precedingVersionID', 'contribution':'#contribution', 'data':'#data', 'lifecycleState':'#lifecycleState', signature:'#signature', 'otherInputVersionUids':'#otherInputVersionUids', 'attestations':'#attestations', 'isMerged':'#isMerged'}";
                template = template.replaceAll("#commitAudit", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#precedingVersionID", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#contribution", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#lifecycleState", buildJson(obtemInteiro(idNodoGrafo, 5)));
                template = template.replaceAll("#signature", buildJson(obtemInteiro(idNodoGrafo, 6)));
                template = template.replaceAll("#otherInputVersionUids", buildJson(obtemInteiro(idNodoGrafo, 7)));
                template = template.replaceAll("#otherInputVersionUids", buildJson(obtemInteiro(idNodoGrafo, 7)));
                template = template.replaceAll("#attestations", buildJson(obtemInteiro(idNodoGrafo, 8)));
                //template = template.replaceAll("#isMerged",obtemBoolean(idNodoGrafo,9)); //TODO CRIAR METODO obtemBoolean(idNodoGrafo, posicao);
                template = template.replaceAll("'", "\"");
                break;
            case DV_IDENTIFIER:
                template = "{ 'issuer' : '#issuer', 'assigner': '#assigner', 'id' : '#id', 'type': '#type'}";
                template = template.replaceAll("#issuer", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#assigner", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#id", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#type", obtemString(idNodoGrafo, 3));
                template = template.replaceAll("'", "\"");
                break;
            case DV_TEXT:
                template = "{ 'value': '#value', 'mappings': [#mappings], 'formatting': '#formatting', 'hyperlink': #hyperlink, 'language': #language, 'encoding': #encoding}";
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));

                int idListaMappings_DvText = obtemInteiro(idNodoGrafo, 1);
                int tamanhoListaMappings_DvText = obtemTamanhoLista(idListaMappings_DvText);
                String listaMappings_DvText = "";
                for (int k = 0; k < tamanhoListaMappings_DvText; k++) {
                    int idObjetoLista = obtemInteiro(idListaMappings_DvText, k);
                    listaMappings_DvText = (k == tamanhoListaMappings_DvText - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#mappings", listaMappings_DvText);

                template = template.replaceAll("#formatting", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#hyperlink", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo, 5)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_CODED_TEXT:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value': '#value', 'mappings': [#mappings], 'formatting': '#formatting', 'hyperlink': #hyperlink, 'language': #language, 'charset': #charset, 'definingCode': #definingCode}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_CODED_TEXT));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));

                int idListaMappings_DvCodedText = obtemInteiro(idNodoGrafo, 1);
                int tamanhoListaMappings_DvCodedText = obtemTamanhoLista(idListaMappings_DvCodedText);
                String listaMappings_DvCodedText = "";
                for (int k = 0; k < tamanhoListaMappings_DvCodedText; k++) {
                    int idObjetoLista = obtemInteiro(idListaMappings_DvCodedText, k);
                    listaMappings_DvCodedText = (k == tamanhoListaMappings_DvCodedText - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#mappings", listaMappings_DvCodedText);

                template = template.replaceAll("#formatting", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#hyperlink", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#charset", buildJson(obtemInteiro(idNodoGrafo, 5)));
                template = template.replaceAll("#definingCode", buildJson(obtemInteiro(idNodoGrafo, 6)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_PARAGRAPH:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'items' : [#items]}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_PARAGRAPH));
                int idListaItems_DvParagraph = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaItems_DvParagraph = obtemTamanhoLista(idListaItems_DvParagraph);
                String listaItems_DvParagraph = "";
                for (int k = 0; k < tamanhoListaItems_DvParagraph; k++) {
                    int idObjetoLista = obtemInteiro(idListaItems_DvParagraph, k);
                    listaItems_DvParagraph = (k == tamanhoListaItems_DvParagraph - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#items", listaItems_DvParagraph);
                template = template.replaceAll("'", "\"");
                break;
            case DV_URI:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_URI));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("'", "\"");
                break;
            case DV_EHR_URI:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_EHR_URI));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("'", "\"");
                break;
            case FEEDER_AUDIT:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'originatingSystemAudit' : #originatingSystemAudit, 'originatingSystemItemIds' : [#originatingSystemItemIds], 'feederSystemAudit' : #feederSystemAudit, " +
                        "'feederSystemItemIds' : [#feederSystemItemIds], 'originalContent' : #originalContent }";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(FEEDER_AUDIT));
                template = template.replaceAll("#originatingSystemAudit", buildJson(obtemInteiro(idNodoGrafo, 0)));
                int idListaDvIdentifier = obtemInteiro(idNodoGrafo, 1);
                int tamanhoListaDvIdentifier = obtemTamanhoLista(idListaDvIdentifier);
                String listaDvIdentifier = "";
                for (int k = 0; k < tamanhoListaDvIdentifier; k++) {
                    int idObjetoLista = obtemInteiro(idListaDvIdentifier, k);
                    listaDvIdentifier = (k == tamanhoListaDvIdentifier - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#originatingSystemItemIds", listaDvIdentifier);
                template = template.replaceAll("#feederSystemAudit", buildJson(obtemInteiro(idNodoGrafo, 2)));
                int idListaDvIdentifier2 = obtemInteiro(idNodoGrafo, 3);
                int tamanhoListaDvIdentifier2 = obtemTamanhoLista(idListaDvIdentifier2);
                String listaDvIdentifier2 = "";
                for (int k = 0; k < tamanhoListaDvIdentifier2; k++) {
                    int idObjetoLista = obtemInteiro(idListaDvIdentifier2, k);
                    listaDvIdentifier2 = (k == tamanhoListaDvIdentifier2 - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#feederSystemItemIds", listaDvIdentifier2);
                template = template.replaceAll("#originalContent", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("'", "\"");
                break;
            case EHR:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'systemID' : #systemID, 'ehrID' : #ehrID, 'timeCreated' : #timeCreated, 'contributions' : [#contributions], " +
                        "'ehrStatus' : #ehrStatus, 'directory' : #directory, 'compositions' : [#compositions]}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(EHR));
                template = template.replaceAll("#systemID", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#ehrID", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCreated", buildJson(obtemInteiro(idNodoGrafo, 2)));
                int idListaContributions = obtemInteiro(idNodoGrafo, 3);
                int tamanhoListaContributions = obtemTamanhoLista(idListaContributions);
                String listaContributions = "";
                for (int k = 0; k < tamanhoListaContributions; k++) {
                    int idObjetoLista = obtemInteiro(idListaContributions, k);
                    listaContributions = (k == tamanhoListaContributions - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#contributions", listaContributions);
                template = template.replaceAll("#ehrStatus", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#directory", buildJson(obtemInteiro(idNodoGrafo, 5)));
                int idListaCompositions = obtemInteiro(idNodoGrafo, 6);
                int tamanhoListaCompositions = obtemTamanhoLista(idListaCompositions);
                String listaCompositions = "";
                for (int k = 0; k < tamanhoListaCompositions; k++) {
                    int idObjetoLista = obtemInteiro(idListaCompositions, k);
                    listaCompositions = (k == tamanhoListaCompositions - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#contributions", listaCompositions);
                template = template.replaceAll("'", "\"");
                break;
            case VERSION_TREE_ID:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value', 'trunkVersion' : '#trunkVersion', 'branchNumber' : '#branchNumber', 'branchVersion' : '#branchVersion'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(VERSION_TREE_ID));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#trunkVersion", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#branchNumber", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#branchVersion", obtemString(idNodoGrafo, 3));
                template = template.replaceAll("'", "\"");
                break;
            case DV_TIME:
                template = "{ 'globalTypeIdn': #globalTypeIdn, 'otherReferenceRanges': [#otherReferenceRanges], 'normalRange': #normalRange, 'normalStatus': #normalStatus, 'accuracy': #accuracy, 'magnitudeStatus': '#magnitudeStatus', 'value': '#value'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_TIME));

                int idListaOtherReferenceRanges_DvTime = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaOtherReferenceRanges_DvTime = obtemTamanhoLista(idListaOtherReferenceRanges_DvTime);
                String listaOtherReferenceRanges_DvTime = "";
                for (int k = 0; k < tamanhoListaOtherReferenceRanges_DvTime; k++) {
                    int idObjetoLista = obtemInteiro(idListaOtherReferenceRanges_DvTime, k);
                    listaOtherReferenceRanges_DvTime = (k == tamanhoListaOtherReferenceRanges_DvTime - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherReferenceRanges", listaOtherReferenceRanges_DvTime);

                template = template.replaceAll("#normalRange", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#normalStatus", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#accuracy", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#magnitudeStatus", obtemString(idNodoGrafo, 4));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 5));
                template = template.replaceAll("'", "\"");
                break;
            case DV_DATE_TIME:
                template = "{ 'globalTypeIdn': #globalTypeIdn, 'otherReferenceRanges': [#otherReferenceRanges], 'normalRange': #normalRange, 'normalStatus': #normalStatus, 'accuracy': #accuracy, 'magnitudeStatus': '#magnitudeStatus', 'value': '#value'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_DATE_TIME));

                int idListaOtherReferenceRanges_DvDateTime = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaOtherReferenceRanges_DvDateTime = obtemTamanhoLista(idListaOtherReferenceRanges_DvDateTime);
                String listaOtherReferenceRanges_DvDateTime = "";
                for (int k = 0; k < tamanhoListaOtherReferenceRanges_DvDateTime; k++) {
                    int idObjetoLista = obtemInteiro(idListaOtherReferenceRanges_DvDateTime, k);
                    listaOtherReferenceRanges_DvDateTime = (k == tamanhoListaOtherReferenceRanges_DvDateTime - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherReferenceRanges", listaOtherReferenceRanges_DvDateTime);

                template = template.replaceAll("#normalRange", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#normalStatus", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#accuracy", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#magnitudeStatus", obtemString(idNodoGrafo, 4));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 5));
                template = template.replaceAll("'", "\"");
                break;
            case DV_DATE:
                template = "{ 'globalTypeIdn': #globalTypeIdn, 'otherReferenceRanges': [#otherReferenceRanges], 'normalRange': #normalRange, 'normalStatus': #normalStatus, 'accuracy': #accuracy, 'magnitudeStatus': '#magnitudeStatus', 'value': '#value'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_DATE));

                int idListaOtherReferenceRanges_DvDate = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaOtherReferenceRanges_DvDate = obtemTamanhoLista(idListaOtherReferenceRanges_DvDate);
                String listaOtherReferenceRanges_DvDate = "";
                for (int k = 0; k < tamanhoListaOtherReferenceRanges_DvDate; k++) {
                    int idObjetoLista = obtemInteiro(idListaOtherReferenceRanges_DvDate, k);
                    listaOtherReferenceRanges_DvDate = (k == tamanhoListaOtherReferenceRanges_DvDate - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherReferenceRanges", listaOtherReferenceRanges_DvDate);

                template = template.replaceAll("#normalRange", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#normalStatus", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#accuracy", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#magnitudeStatus", obtemString(idNodoGrafo, 4));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 5));
                template = template.replaceAll("'", "\"");
                break;
            case DV_ORDINAL:
                template = "{ 'globalTypeIdn': #globalTypeIdn, 'otherReferenceRanges': [#otherReferenceRanges], 'normalRange': #normalRange, 'value': #value, 'symbol': #symbol}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_ORDINAL));

                int idListaOtherReferenceRanges_DvOrdinal = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaOtherReferenceRanges_DvOrdinal = obtemTamanhoLista(idListaOtherReferenceRanges_DvOrdinal);
                String listaOtherReferenceRanges_DvOrdinal = "";
                for (int k = 0; k < tamanhoListaOtherReferenceRanges_DvOrdinal; k++) {
                    int idObjetoLista = obtemInteiro(idListaOtherReferenceRanges_DvOrdinal, k);
                    listaOtherReferenceRanges_DvOrdinal = (k == tamanhoListaOtherReferenceRanges_DvOrdinal - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherReferenceRanges", listaOtherReferenceRanges_DvOrdinal);

                template = template.replaceAll("#normalRange", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#value", String.valueOf(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#symbol", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_DURATION:
                template = "{ 'globalTypeIdn': #globalTypeIdn, 'otherReferenceRanges': [#otherReferenceRanges], 'normalRange': #normalRange, 'normalStatus': #normalStatus, 'accuracy': #accuracy, 'accuracyPercent': #accuracyPercent, 'magnitudeStatus': '#magnitudeStatus', 'value': '#value'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_DURATION));

                int idListaOtherReferenceRanges_DvDuration = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaOtherReferenceRanges_DvDuration = obtemTamanhoLista(idListaOtherReferenceRanges_DvDuration);
                String listaOtherReferenceRanges_DvDuration = "";
                for (int k = 0; k < tamanhoListaOtherReferenceRanges_DvDuration; k++) {
                    int idObjetoLista = obtemInteiro(idListaOtherReferenceRanges_DvDuration, k);
                    listaOtherReferenceRanges_DvDuration = (k == tamanhoListaOtherReferenceRanges_DvDuration - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherReferenceRanges", listaOtherReferenceRanges_DvDuration);

                template = template.replaceAll("#normalRange", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#normalStatus", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#accuracy", String.valueOf(obtemDouble(idNodoGrafo, 3)));
                template = template.replaceAll("#accuracyPercent", String.valueOf(obtemValorLogico(idNodoGrafo, 4)));
                template = template.replaceAll("#magnitudeStatus", obtemString(idNodoGrafo, 5));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 6));
                template = template.replaceAll("'", "\"");
                break;
            case DV_QUANTITY:
                template = "{ 'globalTypeIdn': #globalTypeIdn, 'otherReferenceRanges': [#otherReferenceRanges], 'normalRange': #normalRange, 'normalStatus': #normalStatus, 'accuracy': #accuracy, 'accuracyPercent': #accuracyPercent, 'magnitudeStatus': '#magnitudeStatus', 'units': '#units', 'magnitude': #magnitude, 'precision': #precision, 'measurementService': #measurementService}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_QUANTITY));

                int idListaOtherReferenceRanges_DvQuantity = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaOtherReferenceRanges_DvQuantity = obtemTamanhoLista(idListaOtherReferenceRanges_DvQuantity);
                String listaOtherReferenceRanges_DvQuantity = "";
                for (int k = 0; k < tamanhoListaOtherReferenceRanges_DvQuantity; k++) {
                    int idObjetoLista = obtemInteiro(idListaOtherReferenceRanges_DvQuantity, k);
                    listaOtherReferenceRanges_DvQuantity = (k == tamanhoListaOtherReferenceRanges_DvQuantity - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherReferenceRanges", listaOtherReferenceRanges_DvQuantity);

                template = template.replaceAll("#normalRange", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#normalStatus", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#accuracy", String.valueOf(obtemDouble(idNodoGrafo, 3)));
                template = template.replaceAll("#accuracyPercent", String.valueOf(obtemValorLogico(idNodoGrafo, 4)));
                template = template.replaceAll("#magnitudeStatus", obtemString(idNodoGrafo, 5));
                template = template.replaceAll("#units", obtemString(idNodoGrafo, 6));
                template = template.replaceAll("#magnitude", String.valueOf(obtemDouble(idNodoGrafo, 7)));
                template = template.replaceAll("#precision", String.valueOf(obtemInteiro(idNodoGrafo, 8)));
                template = template.replaceAll("#measurementService", buildJson(obtemInteiro(idNodoGrafo, 9)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_PROPORTION:
                template = "{ 'globalTypeIdn': #globalTypeIdn, 'otherReferenceRanges': [#otherReferenceRanges], 'normalRange': #normalRange, 'normalStatus': #normalStatus, 'accuracy': #accuracy, 'accuracyPercent': #accuracyPercent, 'magnitudeStatus': '#magnitudeStatus', 'numerator': #numerator, 'denominator': #denominator, 'type': #type, 'precision': #precision}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_PROPORTION));

                int idListaOtherReferenceRanges_DvProportion = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaOtherReferenceRanges_DvProportion = obtemTamanhoLista(idListaOtherReferenceRanges_DvProportion);
                String listaOtherReferenceRanges_DvProportion = "";
                for (int k = 0; k < tamanhoListaOtherReferenceRanges_DvProportion; k++) {
                    int idObjetoLista = obtemInteiro(idListaOtherReferenceRanges_DvProportion, k);
                    listaOtherReferenceRanges_DvProportion = (k == tamanhoListaOtherReferenceRanges_DvProportion - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherReferenceRanges", listaOtherReferenceRanges_DvProportion);

                template = template.replaceAll("#normalRange", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#normalStatus", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#accuracy", String.valueOf(obtemDouble(idNodoGrafo, 3)));
                template = template.replaceAll("#accuracyPercent", String.valueOf(obtemValorLogico(idNodoGrafo, 4)));
                template = template.replaceAll("#magnitudeStatus", obtemString(idNodoGrafo, 5));
                template = template.replaceAll("#numerator", String.valueOf(obtemDouble(idNodoGrafo, 6)));
                template = template.replaceAll("#denominator", String.valueOf(obtemDouble(idNodoGrafo, 7)));
                template = template.replaceAll("#type", buildJson(obtemInteiro(idNodoGrafo, 8)));
                template = template.replaceAll("#precision", String.valueOf(obtemInteiro(idNodoGrafo, 9)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_COUNT:
                template = "{ 'globalTypeIdn': #globalTypeIdn, 'otherReferenceRanges': [#otherReferenceRanges], 'normalRange': #normalRange, 'normalStatus': #normalStatus, 'accuracy': #accuracy, 'accuracyPercent': #accuracyPercent, 'magnitudeStatus': '#magnitudeStatus', 'magnitude': #magnitude}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(DV_COUNT));

                int idListaOtherReferenceRanges_DvCount = obtemInteiro(idNodoGrafo, 0);
                int tamanhoListaOtherReferenceRanges_DvCount = obtemTamanhoLista(idListaOtherReferenceRanges_DvCount);
                String listaOtherReferenceRanges_DvCount = "";
                for (int k = 0; k < tamanhoListaOtherReferenceRanges_DvCount; k++) {
                    int idObjetoLista = obtemInteiro(idListaOtherReferenceRanges_DvCount, k);
                    listaOtherReferenceRanges_DvCount = (k == tamanhoListaOtherReferenceRanges_DvCount - 1) ? buildJson(idObjetoLista) + ", " : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherReferenceRanges", listaOtherReferenceRanges_DvCount);

                template = template.replaceAll("#normalRange", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#normalStatus", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#accuracy", String.valueOf(obtemDouble(idNodoGrafo, 3)));
                template = template.replaceAll("#accuracyPercent", String.valueOf(obtemValorLogico(idNodoGrafo, 4)));
                template = template.replaceAll("#magnitudeStatus", obtemString(idNodoGrafo, 5));
                template = template.replaceAll("#magnitude", String.valueOf(obtemInteiro(idNodoGrafo, 6)));
                template = template.replaceAll("'", "\"");
                break;
            case ISM_TRANSITION:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'currentState' : #currentState, 'transition': #transition, 'careflowStep':#careflowStep}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(ISM_TRANSITION));
                template = template.replaceAll("#currentState", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#transition", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#careflowStep", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_REF:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'id' : #id, 'namespace': '#namespace', 'type': '#type'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(OBJECT_REF));
                template = template.replaceAll("#id", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#namespace", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#type", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("'", "\"");
                break;
            case ACCESS_GROUP_REF:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'id' : #id}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(ACCESS_GROUP_REF));
                // super classe
                template = template.replaceAll("#id", buildJson(obtemInteiro(idNodoGrafo, 0)));
                // Classe não tem atributos próprios
                template = template.replaceAll("'", "\"");
                break;
            case PARTY_REF:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'id' : #id, 'type': '#type'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(PARTY_REF));
                // super classe
                template = template.replaceAll("#id", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#type", obtemString(idNodoGrafo, 2));
                // Classe não tem atributos próprios
                template = template.replaceAll("'", "\"");
                break;
            case LOCATABLE_REF:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'id' : #id, 'namespace': '#namespace', 'type': '#type', 'path': '#path'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(LOCATABLE_REF));
                // super classe
                template = template.replaceAll("#id", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#namespace", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#type", obtemString(idNodoGrafo, 2));

                // Classe LOCATABLE_REF
                template = template.replaceAll("#path", obtemString(idNodoGrafo, 3));
                template = template.replaceAll("'", "\"");
                break;
            case TRANSLATIONDETAILS:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'language' : #language, 'author': [#author], 'accreditation': '#accreditation', 'otherDetails': [#otherDetails]}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(TRANSLATIONDETAILS));

                template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#accreditation", obtemString(idNodoGrafo, 2));

                int idMapaAuthor = obtemInteiro(idNodoGrafo, 1);
                int tamanhoMapa = obtemTamanhoLista(idMapaAuthor);
                String mapa = "";
                for (int k = 0; k < tamanhoMapa; k++) {
                    int idObjetoMapa = obtemInteiro(idMapaAuthor, k);
                    mapa = mapa + ((k == tamanhoMapa - 1) ? "{'" + obtemString(idObjetoMapa, 0) + "' : '" + obtemString(idObjetoMapa, 1) + "'}," : "{'" + obtemString(idObjetoMapa, 0) + "' : '" + obtemString(idObjetoMapa, 1) + "}'");
                }
                template = template.replaceAll("#author", mapa);

                idMapaAuthor = obtemInteiro(idNodoGrafo, 3);
                tamanhoMapa = obtemTamanhoLista(idMapaAuthor);
                mapa = "";
                for (int k = 0; k < tamanhoMapa; k++) {
                    int idObjetoMapa = obtemInteiro(idMapaAuthor, k);
                    mapa = mapa + ((k == tamanhoMapa - 1) ? "{'" + obtemString(idObjetoMapa, 0) + "' : '" + obtemString(idObjetoMapa, 1) + "'}," : "{'" + obtemString(idObjetoMapa, 0) + "' : '" + obtemString(idObjetoMapa, 1) + "}'");
                }
                template = template.replaceAll("#otherDetails", mapa);
                template = template.replaceAll("'", "\"");
                break;
            case FEEDER_AUDIT_DETAILS:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'systemId' : '#systemId', 'provider': #provider, 'location': #location, 'time': #time, 'subject': #subject, 'versionId': '#versionId'}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(FEEDER_AUDIT_DETAILS));
                template = template.replaceAll("#systemId", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#location", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#versionId", obtemString(idNodoGrafo, 5));
                template = template.replaceAll("'", "\"");
                break;
            case INSTRUCTION_DETAILS:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'instructionId' : #instructionId, 'activityId': '#activityId', 'wfDetails': #wfDetails}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(INSTRUCTION_DETAILS));
                template = template.replaceAll("#instructionId", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#activityId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#wfDetails", buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("'", "\"");
                break;
            case EVENT_CONTEXT:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'healthCareFacility' : #healthCareFacility, 'startTime': #startTime, 'endTime': #endTime, 'participations': [#participations], 'location': '#location', 'setting': #setting, 'otherContext': #otherContext}";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(EVENT_CONTEXT));
                template = template.replaceAll("#healthCareFacility", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#startTime", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#endTime", buildJson(obtemInteiro(idNodoGrafo, 2)));

                int idListaParticipation = obtemInteiro(idNodoGrafo, 3);
                int tamanhoListaParticipation = obtemTamanhoLista(idListaParticipation);
                String listaParticipation = "";
                for (int k = 0; k < tamanhoListaParticipation; k++) {
                    int idObjetoLista = obtemInteiro(idListaParticipation, k);
                    listaParticipation += (k == tamanhoListaParticipation - 1) ? '{' + buildJson(idObjetoLista) + "}," : '{' + buildJson(idObjetoLista) + '}';
                }

                template = template.replaceAll("#participations", listaParticipation);
                template = template.replaceAll("#location", obtemString(idNodoGrafo, 4));
                template = template.replaceAll("#setting", buildJson(obtemInteiro(idNodoGrafo, 5)));
                template = template.replaceAll("#otherContext", buildJson(obtemInteiro(idNodoGrafo, 6)));
                template = template.replaceAll("'", "\"");
                break;
//            case DV_INTERVAL:
//                template= "{'interval':'#interval'}";
//                template = template.replaceAll("#interval", buildJson(obtemInteiro(idNodoGrafo,0)));
			case ITEM:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6));
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_LIST:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_LIST));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				
				int idListaItems = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_SINGLE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'item':#item}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_SINGLE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#item", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_STRUCTURE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_STRUCTURE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_TABLE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'rows':[#rows]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_TABLE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				
				int idListaClusters = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaClusters = obtemTamanhoLista(idListaClusters);
				String listaClusters = "";
				for(int k = 0; k < tamanhoListaClusters; k++){
                    int idObjetoLista = obtemInteiro(idListaClusters,k);
                    listaClusters = (k == tamanhoListaClusters - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("rows", listaClusters);
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_TREE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_TREE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				
				int idListaItems = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				template = template.replaceAll("'", "\"");
				break;
			case ELEMENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'value':#value, 'nullFlavour':#nullFlavour, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ELEMENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#value", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#nullFlavour", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("'", "\"");
				break;
			case CLUSTER:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(CLUSTER));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));

				int idListaItems = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				template = template.replaceAll("'", "\"");
				break;
			case FOLDER:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'folders':[#folders], 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(FOLDER));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));

				int idListaFolders = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaFolders = obtemTamanhoLista(idListaFolders);
				String listaFolders = "";
				for(int k = 0; k < tamanhoListaFolders; k++){
                    int idObjetoLista = obtemInteiro(idListaFolders,k);
                    listaFolders = (k == tamanhoListaFolders - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#folders", listaFolders);
				
				int idListaItems = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				template = template.replaceAll("'", "\"");
				break;
			case PARTY_RELATIONSHIP:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'details':#details, 'timeValidity':#timeValidity, 'source':#source, 'target':#target}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(PARTY_RELATIONSHIP));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#timeValidity", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#source", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#target", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("'", "\"");
				break;
			case XFOLDER:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'folders':[#folders], 'compositions':[#compositions]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(XFOLDER));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));

				int idListaFolders = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaFolders = obtemTamanhoLista(idListaFolders);
				String listaFolders = "";
				for(int k = 0; k < tamanhoListaFolders; k++){
                    int idObjetoLista = obtemInteiro(idListaFolders,k);
                    listaFolders = (k == tamanhoListaFolders - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#folders", listaFolders);
				
				int idListaCompositions = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaCompositions = obtemTamanhoLista(idListaCompositions);
				String listaCompositions = "";
				for(int k = 0; k < tamanhoListaCompositions; k++){
                    int idObjetoLista = obtemInteiro(idListaCompositions,k);
                    listaCompositions = (k == tamanhoListaCompositions - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#compositions", listaCompositions);
				template = template.replaceAll("'", "\"");
				break;
			case COMPOSITION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'content':[#content], 'language':#language, 'context':#context, 'composer':#composer, 'category':#category, 'territory':#territory, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(COMPOSITION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));

				int idListaContent = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContent = obtemTamanhoLista(idListaContent);
				String listaContent = "";
				for(int k = 0; k < tamanhoListaContent; k++){
                    int idObjetoLista = obtemInteiro(idListaContent,k);
                    listaContent = (k == tamanhoListaContent - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#content", listaContent);
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#context", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#composer", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#category", buildJson(obtemInteiro(idNodoGrafo,11)));
				template = template.replaceAll("#territory", buildJson(obtemInteiro(idNodoGrafo,12)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("'", "\"");
				break;
			case ADDRESS:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'details':#details}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ADDRESS));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("'", "\"");
				break;
			case PARTY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(PARTY));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("'", "\"");
				break;
			case ROLE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'capabilities':[#capabilities], 'timeValidity':#timeValidity, 'performer':#performer}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ROLE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaCapabilities = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaCapabilities = obtemTamanhoLista(idListaCapabilities);
				String listaCapabilities = "";
				for(int k = 0; k < tamanhoListaCapabilities; k++){
                    int idObjetoLista = obtemInteiro(idListaCapabilities,k);
                    listaCapabilities = (k == tamanhoListaCapabilities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#capabilities", listaCapabilities);
				
				template = template.replaceAll("#timeValidity", buildJson(obtemInteiro(idNodoGrafo,12)));
				template = template.replaceAll("#performer", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("'", "\"");
				break;
			case ACTOR:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'roles':[#roles], 'languages':[#languages]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ACTOR));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaRoles = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaRoles = obtemTamanhoLista(idListaRoles);
				String listaRoles = "";
				for(int k = 0; k < tamanhoListaRoles; k++){
                    int idObjetoLista = obtemInteiro(idListaRoles,k);
                    listaRoles = (k == tamanhoListaRoles - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#roles", listaRoles);
				
				int idListaLanguages = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaLanguages = obtemTamanhoLista(idListaLanguages);
				String listaLanguages = "";
				for(int k = 0; k < tamanhoListaLanguages; k++){
                    int idObjetoLista = obtemInteiro(idListaLanguages,k);
                    listaLanguages = (k == tamanhoListaLanguages - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#languages", listaLanguages);
				
				template = template.replaceAll("'", "\"");
				break;
			case PERSON:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'roles':[#roles], 'languages':[#languages]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(PERSON));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaRoles = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaRoles = obtemTamanhoLista(idListaRoles);
				String listaRoles = "";
				for(int k = 0; k < tamanhoListaRoles; k++){
                    int idObjetoLista = obtemInteiro(idListaRoles,k);
                    listaRoles = (k == tamanhoListaRoles - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#roles", listaRoles);
				
				int idListaLanguages = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaLanguages = obtemTamanhoLista(idListaLanguages);
				String listaLanguages = "";
				for(int k = 0; k < tamanhoListaLanguages; k++){
                    int idObjetoLista = obtemInteiro(idListaLanguages,k);
                    listaLanguages = (k == tamanhoListaLanguages - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#languages", listaLanguages);
				
				template = template.replaceAll("'", "\"");
				break;
			case AGENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'roles':[#roles], 'languages':[#languages]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(AGENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaRoles = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaRoles = obtemTamanhoLista(idListaRoles);
				String listaRoles = "";
				for(int k = 0; k < tamanhoListaRoles; k++){
                    int idObjetoLista = obtemInteiro(idListaRoles,k);
                    listaRoles = (k == tamanhoListaRoles - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#roles", listaRoles);
				
				int idListaLanguages = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaLanguages = obtemTamanhoLista(idListaLanguages);
				String listaLanguages = "";
				for(int k = 0; k < tamanhoListaLanguages; k++){
                    int idObjetoLista = obtemInteiro(idListaLanguages,k);
                    listaLanguages = (k == tamanhoListaLanguages - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#languages", listaLanguages);
				
				template = template.replaceAll("'", "\"");
				break;
			case ORGANISATION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'roles':[#roles], 'languages':[#languages]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ORGANISATION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaRoles = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaRoles = obtemTamanhoLista(idListaRoles);
				String listaRoles = "";
				for(int k = 0; k < tamanhoListaRoles; k++){
                    int idObjetoLista = obtemInteiro(idListaRoles,k);
                    listaRoles = (k == tamanhoListaRoles - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#roles", listaRoles);
				
				int idListaLanguages = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaLanguages = obtemTamanhoLista(idListaLanguages);
				String listaLanguages = "";
				for(int k = 0; k < tamanhoListaLanguages; k++){
                    int idObjetoLista = obtemInteiro(idListaLanguages,k);
                    listaLanguages = (k == tamanhoListaLanguages - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#languages", listaLanguages);
				
				template = template.replaceAll("'", "\"");
				break;
			case EHR_STATUS:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'subject':#subject, 'isQueryable':#isQueryable, 'isModifiable':#isModifiable, 'otherDetails':#otherDetails}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(EHR_STATUS));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#isQueryable",obtemBoolean(idNodoGrafo,8));
				template = template.replaceAll("#isModifiable",obtemBoolean(idNodoGrafo,9));
				template = template.replaceAll("#otherDetails", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("'", "\"");
				break;
			case ACTIVITY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'description':#description, 'timing':#timing, 'actionArchetypeId':'#actionArchetypeId'}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ACTIVITY));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#description", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#timing", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#actionArchetypeId", obtemString(idNodoGrafo,9));
				template = template.replaceAll("'", "\"");
				break;
			case EVENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'time':#time, 'data':#data, 'state':#state}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(EVENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#state", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("'", "\"");
				break;
			case INTERVAL_EVENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'time':#time, 'data':#data, 'state':#state, 'width':#width, 'mathFunction':#mathFunction, 'sampleCount':#sampleCount, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(INTERVAL_EVENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#state", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#width", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#mathFunction", buildJson(obtemInteiro(idNodoGrafo,11)));
				template = template.replaceAll("#sampleCount", obtemInteiro(idNodoGrafo,12));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("'", "\"");
				break;
			case POINT_EVENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'time':#time, 'data':#data, 'state':#state}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(POINT_EVENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#state", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("'", "\"");
				break;
			case MESSAGE_CONTENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid' : #uid, 'archetypeNodeId' : '#archetypeNodeId', 'originalArchetypeNodeId' : #originalArchetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, " +
                        "'feederAudit' : #feederAudit, 'links' : [#links], 'parent' : #parent }";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(MESSAGE_CONTENT));
                template = template.replaceAll("#uid",buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#originalArchetypeNodeId", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#feederAudit",buildJson(obtemInteiro(idNodoGrafo, 5)));
                int idListaLinks = obtemInteiro(idNodoGrafo, 6);
                int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                String listaLinks = "";
                for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo, 7)));
                template = template.replaceAll("'", "\"");
                break;
			case EHR_ACCESS:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'settings':#settings}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(EHR_ACCESS));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#settings", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("'", "\"");
                break;
			case PARTY_IDENTITY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'details':#details}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(PARTY_IDENTITY));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("'", "\"");
                break;
			case CONTENT_ITEM:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid' : #uid, 'archetypeNodeId' : '#archetypeNodeId', 'originalArchetypeNodeId' : #originalArchetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, " +
                        "'feederAudit' : #feederAudit, 'links' : [#links], 'parent' : #parent }";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(CONTENT_ITEM));
                template = template.replaceAll("#uid",buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#originalArchetypeNodeId", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#feederAudit",buildJson(obtemInteiro(idNodoGrafo, 5)));
                int idListaLinks = obtemInteiro(idNodoGrafo, 6);
                int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                String listaLinks = "";
                for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo, 7)));
                template = template.replaceAll("'", "\"");
                break;
			case ADMIN_ENTRY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'data':#data, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ADMIN_ENTRY));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("'", "\"");
                break;
			case CARE_ENTRY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid' : #uid, 'archetypeNodeId' : '#archetypeNodeId', 'name' : #name, 'archetypeDetails' : #archetypeDetails, 'feederAudit' : #feederAudit, 'links' : [#links], 'parent' : #parent," +
                        "'language' : #language, 'encoding' : #encoding, 'subject' : #subject, 'provider' : #provider, 'workflowId' : #workflowId, 'otherParticipations' : #otherParticipations, 'protocol':#protocol, 'guidelineId':#guidelineId,'terminologyService' : #terminologyService }";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(CARE_ENTRY));
				template = template.replaceAll("#uid",buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#name", buildJson(obtemByte(idNodoGrafo, 2)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemByte(idNodoGrafo, 3)));
                template = template.replaceAll("#feederAudit",buildJson(obtemByte(idNodoGrafo, 4)));
                idListaLinks = obtemInteiro(idNodoGrafo, 5);
                tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                listaLinks = "";
                for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemByte(idNodoGrafo, 6)));
                template = template.replaceAll("#language", buildJson(obtemByte(idNodoGrafo, 7)));
                template = template.replaceAll("#encoding", buildJson(obtemByte(idNodoGrafo, 8)));
                template = template.replaceAll("#subject", buildJson(obtemByte(idNodoGrafo, 9)));
                template = template.replaceAll("#provider", buildJson(obtemByte(idNodoGrafo, 10)));
                template = template.replaceAll("#workflowId", buildJson(obtemByte(idNodoGrafo, 11)));
                int idListaParticipations = obtemInteiro(idNodoGrafo, 12);
                int tamanhoListaParticipations = obtemTamanhoLista(idListaParticipations);
                String listaParticipations = "";
                for(int k = 0; k < tamanhoListaParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaParticipations,k);
                    listaParticipations = (k == tamanhoListaParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherParticipations", listaParticipations);
                template = template.replaceAll("#protocol", buildJson(obtemByte(idNodoGrafo, 13)));
                template = template.replaceAll("#guidelineId", buildJson(obtemByte(idNodoGrafo, 14)));
                template = template.replaceAll("#terminologyService", buildJson(obtemByte(idNodoGrafo, 15)));
                template = template.replaceAll("'", "\"");
                break;
			case OBSERVATION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'protocol':#protocol, 'guidelineId':#guidelineId, 'data':#data, 'state':#state, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(OBSERVATION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#protocol", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#guidelineId", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,15)));
				template = template.replaceAll("#state", buildJson(obtemInteiro(idNodoGrafo,16)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,17)));
				template = template.replaceAll("'", "\"");
                break;
			case INSTRUCTION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'protocol':#protocol, 'guidelineId':#guidelineId, 'narrative':#narrative, 'activities':[#activities], 'expiryTime':#expiryTime, 'wfDefinition':#wfDefinition, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(INSTRUCTION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#protocol", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#guidelineId", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("#narrative", buildJson(obtemInteiro(idNodoGrafo,15)));
				
				int idListaActivities = obtemInteiro(idNodoGrafo,16);
				int tamanhoListaActivities = obtemTamanhoLista(idListaActivities);
				String listaActivities = "";
				for(int k = 0; k < tamanhoListaActivities; k++){
                    int idObjetoLista = obtemInteiro(idListaActivities,k);
                    listaActivities = (k == tamanhoListaActivities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#activities", listaActivities);
				template = template.replaceAll("#expiryTime", buildJson(obtemInteiro(idNodoGrafo,17)));
				template = template.replaceAll("#wfDefinition", buildJson(obtemInteiro(idNodoGrafo,18)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,19)));
				template = template.replaceAll("'", "\"");
                break;
			case ACTION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'protocol':#protocol, 'guidelineId':#guidelineId, 'time':#time, 'description':#description, 'ismTransition':#ismTransition, 'instructionDetails':#instructionDetails, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ACTION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#protocol", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#guidelineId", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo,15)));
				template = template.replaceAll("#description", buildJson(obtemInteiro(idNodoGrafo,16)));
				template = template.replaceAll("#ismTransition", buildJson(obtemInteiro(idNodoGrafo,17)));
				template = template.replaceAll("#instructionDetails", buildJson(obtemInteiro(idNodoGrafo,18)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,19)));
				template = template.replaceAll("'", "\"");
                break;
			case EVALUATION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'protocol':#protocol, 'guidelineId':#guidelineId, 'data':#data, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(EVALUATION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#protocol", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#guidelineId", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,15)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,16)));
				template = template.replaceAll("'", "\"");
                break;
			case SECTION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(SECTION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				
				int idListaItems = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				
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