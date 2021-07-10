package org.typesense.model;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.typesense.api.SearchParameters;
import org.typesense.api.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;

public class Documents {

    private String collectionName;
    private Api api;
    private  Configuration configuration;

    public static final String RESOURCE_PATH = "/documents";

    Documents(String collectionName, Api api, Configuration configuration) {
        this.collectionName = collectionName;
        this.api = api;
        this.configuration = configuration;
    }

    public HashMap<String, Object> create(HashMap<String, Object> document){
        return this.api.post(getEndPoint("/"), document);
    }

    public String create(String document){
        return this.api.post(getEndPoint("/"),document,null,String.class);
    }

    public org.typesense.api.SearchResult search(SearchParameters searchParameters){
        return this.api.get(getEndPoint("search"), org.typesense.api.SearchResult.class, searchParameters);
    }

    public HashMap<String, Object> delete(HashMap<String, String> queryParameters){
        return this.api.delete(getEndPoint("/"), queryParameters);
    }

    public String export(){
        return  this.api.get(getEndPoint("export"),String.class);
    }

    public String import_(String document, HashMap<String, String> queryParameters){
            return this.api.post(this.getEndPoint("import"),document, queryParameters,String.class);
    }

    public String import_(ArrayList<HashMap<String, Object>> documents, HashMap<String, String> queryParameters){
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        for(int i=0;i<documents.size();i++){
            HashMap<String, Object> document = documents.get(i);
            try {
                //Convert Map to JSON
                json = json.concat(mapper.writeValueAsString(document) + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        json = json.trim();
        return this.api.post(this.getEndPoint("import"),json,queryParameters,String.class);
    }

    public String getEndPoint(String target){
        return Collections.RESOURCE_PATH + "/" + this.collectionName + Documents.RESOURCE_PATH + "/" + target;
    }

}
