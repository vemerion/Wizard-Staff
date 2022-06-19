package mod.vemerion.wizardstaff.Magic.netherupdate;

import java.util.List;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GoldNuggetMagic extends Magic {

	public GoldNuggetMagic(MagicType<? extends GoldNuggetMagic> type) {
		super(type);
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			int barterCount = 0;
			LootTable loottable = level.getServer().getLootTables()
					.get(BuiltInLootTables.PIGLIN_BARTERING);
			AABB range = player.getBoundingBox().inflate(3);
			for (Piglin piglin : level.getEntitiesOfClass(Piglin.class, range)) {
				barterCount++;
				List<ItemStack> loot = loottable.getRandomItems(
						(new LootContext.Builder((ServerLevel) level)).withParameter(LootContextParams.THIS_ENTITY, piglin)
								.withRandom(level.random).create(LootContextParamSets.PIGLIN_BARTER));
				for (ItemStack item : loot) {
					BehaviorUtils.throwItem(piglin, item, player.position().add(new Vec3(0, 1, 0)));
				}
				piglin.swing(InteractionHand.OFF_HAND);
			}
			if (barterCount > 0) {
				cost(player);
				playSoundServer(level, player, SoundEvents.PIGLIN_ADMIRING_ITEM, 1, soundPitch(player));
			}
		}
		return super.magicFinish(level, player, staff);
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
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

}
