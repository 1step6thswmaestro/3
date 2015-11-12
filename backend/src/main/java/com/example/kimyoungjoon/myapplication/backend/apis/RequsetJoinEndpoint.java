package com.example.kimyoungjoon.myapplication.backend.apis;

import com.example.kimyoungjoon.myapplication.backend.Constants;
import com.example.kimyoungjoon.myapplication.backend.models.LikeRoomRecord;
import com.example.kimyoungjoon.myapplication.backend.models.RequestJoinRecord;
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
        resource = "requestjoin",
        clientIds = {
                Constants.WEB_CLIENT_ID,
                Constants.ANDROID_CLIENT_ID
        },
        audiences = {
                Constants.WEB_CLIENT_ID
        }
)
public class RequsetJoinEndpoint {
    private static final Logger LOG = Logger
            .getLogger(RequsetJoinEndpoint.class.getName());

    @ApiMethod(name = "getRequestJoins")
    public final List<RequestJoinRecord> getRequestJoins(){
        return ofy().load().type(RequestJoinRecord.class).list();
    }

    @ApiMethod(name = "getRequstJoin")
    public final RequestJoinRecord getRequstJoin(@Named("id") String id){
        return ofy().load().type(RequestJoinRecord.class).id(id).now();
    }

    @ApiMethod(name = "addRequestJoin")
    public final void addRequestJoin(RequestJoinRecord meeting){
        ofy().save().entity(meeting).now();
    }

    @ApiMethod(name = "deleteRequestJoin")
    public final void deleteRequestJoin(@Named("id") String id) throws ServiceException {

        RequestJoinRecord requestJoin = ofy().load().type(RequestJoinRecord.class).id(id).now();
        if(requestJoin==null){
            LOG.info(
                    "Meeting " + id + " not found, skipping deletion.");
        }else{
            ofy().delete().entity(requestJoin).now();
        }
    }
}
