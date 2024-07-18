package com.kakinohana.deliveryapi.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kakinohana.deliveryapi.api.model.mixin.*;
import com.kakinohana.deliveryapi.domain.model.*;
import org.springframework.stereotype.Component;

import java.io.Serial;

@Component
public class JacksonMixinModule extends SimpleModule {

    @Serial
    private static final long serialVersionUID = 1L;

    public JacksonMixinModule(){
        // Diz que a classe x tem uma classe de configuração (mixin)
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
        setMixInAnnotation(City.class, CityMixin.class);
        setMixInAnnotation(Cuisine.class, CuisineMixin.class);
        setMixInAnnotation(User.class, UserMixin.class);
        setMixInAnnotation(Group.class, GroupMixin.class);
    }

}
