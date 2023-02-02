package mod.vemerion.wizardstaff.Magic.wild;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class FrogEatMagic extends Magic {
	
	private int range;

	static final Map<FrogVariant, Item> LOOT = ImmutableMap.of(FrogVariant.COLD, Items.VERDANT_FROGLIGHT,
			FrogVariant.WARM, Items.PEARLESCENT_FROGLIGHT, FrogVariant.TEMPERATE, Items.OCHRE_FROGLIGHT);

	public FrogEatMagic(MagicType<? extends FrogEatMagic> type) {
		super(type);
	}

	public FrogEatMagic setAdditionalParams(int range) {
		this.range = range;
		return this;
	}
	
	@Override
	protected void readAdditional(JsonObject json) {
		range = GsonHelper.getAsInt(json, "range");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("range", range);
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

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			var frogs = level.getEntitiesOfClass(Frog.class, new AABB(player.blockPosition()).inflate(range), e -> true);
			if (frogs.size() > 1) {
				cost(player);
				for (var frog : frogs) {
					if (!frog.isAlive())
						continue;
					var target = findTarget(frog, frogs);
					frog.getNavigation().stop();
					frog.setPose(Pose.STANDING);
					frog.setPose(Pose.USING_TONGUE);
					frog.doHurtTarget(target);
					target.setDeltaMovement(target.position().vectorTo(frog.position()).normalize());
					if (!target.isAlive()) {
						target.spawnAtLocation(LOOT.getOrDefault(frog.getVariant(), Items.OCHRE_FROGLIGHT));
					}
				}
			} else {
				playSoundServer(level, player, ModSounds.POOF.get(), cost, soundPitch(player));
			}
		}

		return super.magicFinish(level, player, staff);
	}

	private Frog findTarget(Frog frog, List<Frog> frogs) {
		for (int i = 0; i < 10; i++) {
			Frog target = frogs.get(frog.getRandom().nextInt(frogs.size()));
			if (target != frog && target.isAlive())
				return target;
		}
		return null;
	}
}
