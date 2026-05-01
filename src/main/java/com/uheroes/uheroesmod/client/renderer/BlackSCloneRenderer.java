package com.uheroes.uheroesmod.client.renderer;

import com.uheroes.uheroesmod.entity.BlackSCloneEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BlackSCloneRenderer extends MobRenderer<BlackSCloneEntity, HumanoidModel<BlackSCloneEntity>> {

    public BlackSCloneRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackSCloneEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("uheroes", "textures/entity/blacks_clone.png");
    }
}
