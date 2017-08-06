package de.totalsurprise.userlogin.rs;

import de.totalsurprise.userlogin.persistence.UserDAO;
import de.totalsurprise.userlogin.persistence.UserEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.Collections;
import java.util.List;

@Stateless
@Path("users")
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<UserDTO> getAllUsrs(){
        return userService.findAll();
    }

    @POST
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(UserDTO userDTO) {
        userService.createUser(userDTO);
        return Response.status(Response.Status.CREATED).build();
    }
}
