package com.example.kimyoungjoon.myapplication.backend;

import com.example.kimyoungjoon.myapplication.backend.models.LikeRoomRecord;
import com.example.kimyoungjoon.myapplication.backend.models.PlaceRecord;
import com.example.kimyoungjoon.myapplication.backend.models.RegistrationRecord;
import com.example.kimyoungjoon.myapplication.backend.models.UserRecord;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(RegistrationRecord.class);
        ObjectifyService.register(PlaceRecord.class);
        ObjectifyService.register(UserRecord.class);
        ObjectifyService.register(LikeRoomRecord.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
