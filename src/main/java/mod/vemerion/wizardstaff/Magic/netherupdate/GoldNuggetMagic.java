package mod.vemerion.wizardstaff.Magic.netherupdate;

import java.util.List;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class GoldNuggetMagic extends Magic {

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			int barterCount = 0;
			LootTable loottable = world.getServer().getLootTableManager()
					.getLootTableFromLocation(LootTables.PIGLIN_BARTERING);
			AxisAlignedBB range = player.getBoundingBox().grow(3);
			for (PiglinEntity piglin : world.getEntitiesWithinAABB(PiglinEntity.class, range)) {
				barterCount++;
				List<ItemStack> loot = loottable.generate(
						(new LootContext.Builder((ServerWorld) world)).withParameter(LootParameters.THIS_ENTITY, piglin)
								.withRandom(world.rand).build(LootParameterSets.field_237453_h_));
				for (ItemStack item : loot) {
					BrainUtil.spawnItemNearEntity(piglin, item, player.getPositionVec().add(new Vector3d(0, 1, 0)));
				}
				piglin.swingArm(Hand.OFF_HAND);
			}
			cost(player);
			if (barterCount > 0)
				playSoundServer(world, player, SoundEvents.ENTITY_PIGLIN_ADMIRING_ITEM, 1, soundPitch(player));
		}
		return super.magicFinish(world, player, staff);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

}
