package cz.muni.fi.pa165.referenceManager.rest.controllers;

import cz.muni.fi.pa165.referenceManager.dto.UserCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.UserDTO;
import cz.muni.fi.pa165.referenceManager.facade.UserFacade;
import cz.muni.fi.pa165.referenceManager.rest.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserFacade userFacade;

    /**
     * Returns all users.
     * curl -i -X GET http://localhost:8080/pa165/rest/users
     * @return list of UserDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<UserDTO> getUsers() {
        logger.debug("rest getUsers()");
        return userFacade.getAllUsers();
    }

    /**
     * Return user with given id.
     * curl -i -X GET http://localhost:8080/pa165/rest/users/{id}
     * @param id
     * @return user with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO getUser(@PathVariable("id") long id) {
        logger.debug("rest getUser()");
        return userFacade.findUserById(id);
    }

    @RequestMapping(
        value = "/register",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final Long registerUser(@RequestBody UserCreateDTO userCreateDTO, @RequestParam String plainPassword) {
        logger.debug("rest registerUser()");
        return userFacade.registerUser(userCreateDTO, plainPassword);
    }

}
