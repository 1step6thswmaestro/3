package com.example.kimyoungjoon.myapplication.backend.apis;

import com.example.kimyoungjoon.myapplication.backend.Constants;
import com.example.kimyoungjoon.myapplication.backend.models.PlaceRecord;
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
        resource = "places",
        clientIds = {
                Constants.WEB_CLIENT_ID,
                Constants.ANDROID_CLIENT_ID
        },
        audiences = {
                Constants.WEB_CLIENT_ID
        }
)
public class PlaceEndpoint {
    private static final Logger LOG = Logger
            .getLogger(PlaceEndpoint.class.getName());

    @ApiMethod(name = "getPlaces")
    public final List<PlaceRecord> getPlaces(){
        return ofy().load().type(PlaceRecord.class).list();
    }

    @ApiMethod(name = "getPlace")
    public final PlaceRecord getPlace(@Named("id") Long id){
        return ofy().load().type(PlaceRecord.class).id(id).now();
    }

    @ApiMethod(name = "addPlace")
    public final void addPlace(PlaceRecord place){
        ofy().save().entity(place).now();
    }

    @ApiMethod(name = "deletePlace")
    public final void deletePlace(@Named("id") Long id) throws ServiceException {

        PlaceRecord place = ofy().load().type(PlaceRecord.class).id(id).now();
        if (place == null) {
            LOG.info(
                    "Place " + id + " not found, skipping deletion.");
        } else {
            ofy().delete().entity(place).now();
        }
    }
}
