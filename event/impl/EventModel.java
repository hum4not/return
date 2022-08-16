package org.returnclient.event.impl;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import org.returnclient.event.Event;

public class EventModel extends Event {

    public Entity entity;
    public ModelBiped model;

    public EventModel(Entity entity, ModelPlayer model) {
        this.entity = entity;
        this.model = model;
    }

    public Entity getEntity() {
        return entity;
    }

    public ModelBiped getModel() {
        return model;
    }
}
