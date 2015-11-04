package com.example.kimyoungjoon.myapplication.backend.apis;

import com.example.kimyoungjoon.myapplication.backend.Constants;
import com.example.kimyoungjoon.myapplication.backend.models.MeetingRecord;
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
        resource = "meetings",
        clientIds = {
                Constants.WEB_CLIENT_ID,
                Constants.ANDROID_CLIENT_ID
        },
        audiences = {
                Constants.WEB_CLIENT_ID
        }
)
public class MeetingEndpoint {
    private static final Logger LOG = Logger
            .getLogger(MeetingEndpoint.class.getName());

    @ApiMethod(name = "getMeetings")
    public final List<MeetingRecord> getMeetings(){
        return ofy().load().type(MeetingRecord.class).list();
    }

    @ApiMethod(name = "getMeeting")
    public final MeetingRecord getMeeting(@Named("id") String id){
        return ofy().load().type(MeetingRecord.class).id(id).now();
    }

    @ApiMethod(name = "addMeeting")
    public final void addMeeting(MeetingRecord meeting){
        ofy().save().entity(meeting).now();
    }

    @ApiMethod(name = "deleteMeeting")
    public final void deleteMeeting(@Named("id") String id) throws ServiceException {

        MeetingRecord meeting = ofy().load().type(MeetingRecord.class).id(id).now();
        if(meeting==null){
            LOG.info(
                    "Meeting " + id + " not found, skipping deletion.");
        }else{
            ofy().delete().entity(meeting).now();
        }
    }
}
