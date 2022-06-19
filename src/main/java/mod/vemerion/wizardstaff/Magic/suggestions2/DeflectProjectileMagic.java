package mod.vemerion.wizardstaff.Magic.suggestions2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class DeflectProjectileMagic extends Magic {

	private Set<ResourceLocation> blacklist;

	public DeflectProjectileMagic(MagicType<? extends DeflectProjectileMagic> type) {
		super(type);
	}

	public DeflectProjectileMagic setAdditionalParams(Set<ResourceLocation> blacklist) {
		this.blacklist = blacklist;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		blacklist = MagicUtil.readColl(json, "blacklist",
				e -> new ResourceLocation(GsonHelper.convertToString(e, "entity name")), new HashSet<>());
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "blacklist", blacklist, r -> new JsonPrimitive(r.toString()));
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::surround;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::surround;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide) {
			cost(player);
			AABB box = player.getBoundingBox().inflate(2);
			List<Projectile> projectiles = level.getEntitiesOfClass(Projectile.class, box, this::notBlacklisted);
			for (Entity projectile : projectiles) {
				projectile.discard();
			}

			if (!projectiles.isEmpty())
				playSoundServer(level, player, ModSounds.IMPACT, 1, soundPitch(player));
		}
	}

	private boolean notBlacklisted(Entity e) {
		return !blacklist.contains(e.getType().getRegistryName());
	}

}
