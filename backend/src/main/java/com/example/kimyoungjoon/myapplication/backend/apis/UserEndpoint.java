package com.example.kimyoungjoon.myapplication.backend.apis;

import com.example.kimyoungjoon.myapplication.backend.Constants;
import com.example.kimyoungjoon.myapplication.backend.models.UserRecord;
import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import java.util.List;
import java.util.logging.Logger;

import static com.example.kimyoungjoon.myapplication.backend.OfyService.ofy;


/**
 * Created by kimyoungjoon on 2015. 11. 3..
 */

@Api(
        name = "matnamApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = Constants.API_OWNER,
                ownerName = Constants.API_OWNER,
                packagePath = Constants.API_PACKAGE_PATH
        )
)
@ApiClass(
        resource = "users",
        clientIds = {
                Constants.WEB_CLIENT_ID,
                Constants.ANDROID_CLIENT_ID
        },
        audiences = {
                Constants.WEB_CLIENT_ID
        }
)
public class UserEndpoint {

    private static final Logger LOG = Logger
            .getLogger(UserEndpoint.class.getName());

    @ApiMethod(name = "getUsers")
    public final List<UserRecord> getUsers(){
        return ofy().load().type(UserRecord.class).list();
    }

    @ApiMethod(name = "getUser")
    public final UserRecord getUser(@Named("id") String id){
        return ofy().load().type(UserRecord.class).id(id).now();
    }

    @ApiMethod(name = "addUser")
    public final void addUser(UserRecord user){
        ofy().save().entity(user).now();

        LOG.info("User succecfully added");
    }

    @ApiMethod(name = "deleteUser")
    public final void deleteUser(@Named("id") String id) throws ServiceException {

        UserRecord user = ofy().load().type(UserRecord.class).id(id).now();
        if(user==null){
            LOG.info(
                    "User " + id + " not found, skipping deletion.");
        }else{
            ofy().delete().entity(user).now();
        }
    }
}
