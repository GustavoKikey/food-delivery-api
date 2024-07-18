package com.kakinohana.deliveryapi.api.model.mixin;

import com.kakinohana.deliveryapi.domain.model.Permission;

import java.util.ArrayList;
import java.util.List;

public class GroupMixin {

    private List<Permission> permissions = new ArrayList<>();
}
