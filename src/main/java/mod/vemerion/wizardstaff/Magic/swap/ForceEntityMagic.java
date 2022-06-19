package mod.vemerion.wizardstaff.Magic.swap;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RegistryMatch;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class ForceEntityMagic extends Magic {

	private float force;
	private RegistryMatch<EntityType<?>> match;

	public ForceEntityMagic(MagicType<? extends ForceEntityMagic> type) {
		super(type);
	}

	public ForceEntityMagic setAdditionalParams(RegistryMatch<EntityType<?>> match, float force) {
		this.match = match;
		this.force = force;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		match = RegistryMatch.read(ForgeRegistries.ENTITIES, GsonHelper.getAsJsonObject(json, "entity_match"));
		force = GsonHelper.getAsFloat(json, "force");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("entity_match", match.write());
		json.addProperty("force", force);
	}

	@Override
	public void encodeAdditional(FriendlyByteBuf buffer) {
		match.encode(buffer);
		buffer.writeFloat(force);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		match = RegistryMatch.decode(ForgeRegistries.ENTITIES, buffer);
		force = buffer.readFloat();
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] {
				force >= 0
						? new TranslatableComponent("gui." + Main.MODID + "."
								+ ModMagics.FORCE_ENTITY_MAGIC.getRegistryName().getPath() + ".attract")
						: new TranslatableComponent("gui." + Main.MODID + "."
								+ ModMagics.FORCE_ENTITY_MAGIC.getRegistryName().getPath() + ".repel"),
				match.getName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return getNameArgs();
	}

	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::circling;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::swinging;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(3), e -> match.test(e.getType()))) {
			Vec3 motion = player.position().subtract(e.position()).normalize().scale(force).multiply(1, 0, 1)
					.add(0, e.getDeltaMovement().y, 0);
			e.setDeltaMovement(motion);
		}
		if (!level.isClientSide)
			cost(player);

		if (count % 6 == 0)
			player.playSound(ModSounds.MAGNET, 1, soundPitch(player));
	}

}
