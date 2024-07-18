package com.kakinohana.deliveryapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kakinohana.deliveryapi.domain.model.Group;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserMixin {

    @JsonIgnore
    private LocalDateTime registerDate;

    @JsonIgnore
    private List<Group> groups = new ArrayList<>();
}
