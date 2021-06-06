package mod.vemerion.wizardstaff.Magic.swap;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class ForceEntityMagic extends Magic {

	private float force;
	private EntityType<?> type;

	public ForceEntityMagic(MagicType<? extends ForceEntityMagic> type) {
		super(type);
	}

	public ForceEntityMagic setAdditionalParams(EntityType<?> type, float force) {
		this.type = type;
		this.force = force;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		type = MagicUtil.read(json, ForgeRegistries.ENTITIES, "entity");
		force = JSONUtils.getFloat(json, "force");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, type, "entity");
		json.addProperty("force", force);
	}

	@Override
	public void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, type);
		buffer.writeFloat(force);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		type = MagicUtil.decode(buffer, ForgeRegistries.ENTITIES);
		force = buffer.readFloat();
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] {
				force >= 0
						? new TranslationTextComponent("gui." + Main.MODID + "."
								+ ModMagics.FORCE_ENTITY_MAGIC.getRegistryName().getPath() + ".attract")
						: new TranslationTextComponent("gui." + Main.MODID + "."
								+ ModMagics.FORCE_ENTITY_MAGIC.getRegistryName().getPath() + ".repel"),
				type.getName() };
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		for (Entity e : world.getEntitiesWithinAABB(type, player.getBoundingBox().grow(3), e -> true)) {
			Vector3d motion = player.getPositionVec().subtract(e.getPositionVec()).normalize().scale(force).mul(1, 0, 1)
					.add(0, e.getMotion().y, 0);
			e.setMotion(motion);
		}
		if (!world.isRemote)
			cost(player);
	}

}
