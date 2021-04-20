package com.suitedllama.dreamskill.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ExampleMixin extends LivingEntity {

	protected ExampleMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow public abstract boolean isSwimming();



	private int fallCount;

	@Inject(at = @At("HEAD"), method = "tick")
	private void tick(CallbackInfo info) {
		if(!this.isOnGround() && !this.isSwimming() && !this.isClimbing()){
			fallCount ++;
		}
		if(this.isOnGround() || this.isSwimming() || this.isClimbing()){
			fallCount = 0;
		}
		if(!this.isOnGround() && fallCount > 13){
			if(this.world.getBlockState(this.getBlockPos().down(1)).isAir() && !this.world.getBlockState(this.getBlockPos().down(2)).isAir()){
				this.world.setBlockState(this.getBlockPos().down(1), Blocks.HAY_BLOCK.getDefaultState());
				this.world.playSound(this.getBlockPos().down(1).getX(), this.getBlockPos().down(1).getY(), this.getBlockPos().down(1).getZ(), SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS,1f, 1f, false);
				swingHand(this.getActiveHand(), true);
			}
		}
	}
}
