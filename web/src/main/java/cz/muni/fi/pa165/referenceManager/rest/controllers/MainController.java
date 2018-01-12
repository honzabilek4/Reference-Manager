package cz.muni.fi.pa165.referenceManager.rest.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.muni.fi.pa165.referenceManager.rest.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    final static Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * The main entry point of the REST API
     * Provides access to all the resources with links to resource URIs
     * Can be even extended further so that all the actions on all the resources are available
     * and can be reused in all the controllers (possibly in full HATEOAS style)
     *
     * @return resources uris
     */
    @RequestMapping(value = ApiUris.ROOT_URI_MAIN, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {

        Map<String,String> resourcesMap = new HashMap<>();

        resourcesMap.put("users_uri", ApiUris.ROOT_URI_USERS);
        resourcesMap.put("tags_uri", ApiUris.ROOT_URI_TAGS);
        resourcesMap.put("references_uri", ApiUris.ROOT_URI_REFERENCES);
        resourcesMap.put("notes_uri", ApiUris.ROOT_URI_NOTES);
        resourcesMap.put("export_uri", ApiUris.ROOT_URI_EXPORT);

        return Collections.unmodifiableMap(resourcesMap);

    }
}
