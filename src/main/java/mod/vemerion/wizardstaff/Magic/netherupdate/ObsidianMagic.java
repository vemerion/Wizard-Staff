package mod.vemerion.wizardstaff.Magic.netherupdate;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ObsidianMagic extends Magic {

	private ResourceKey<Level> dimension;
	private ResourceLocation texture;

	public ObsidianMagic(MagicType<? extends ObsidianMagic> type) {
		super(type);
	}

	public ObsidianMagic setAdditionalParams(ResourceKey<Level> dimension, ResourceLocation texture) {
		this.dimension = dimension;
		this.texture = texture;
		return this;
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, buffer.readResourceLocation());
		texture = buffer.readResourceLocation();
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(dimension.location());
		buffer.writeResourceLocation(texture);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY,
				new ResourceLocation(GsonHelper.getAsString(json, "dimension")));
		texture = new ResourceLocation(GsonHelper.getAsString(json, "texture"));
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("dimension", dimension.location().toString());
		json.addProperty("texture", texture.toString());
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { new TextComponent(dimension.location().getPath().toString()) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return getNameArgs();
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardWaving;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide && level.dimension() != dimension) {
			Vec3 spawnPos = player.position()
					.add(Vec3.directionFromRotation(player.getXRot(), player.getYRot()).scale(2));
			spawnPos = new Vec3(spawnPos.x, Math.max(player.getY(), spawnPos.y), spawnPos.z);
			BlockPos pos = new BlockPos(spawnPos);
			if (!level.getBlockState(pos).canOcclude() && !level.getBlockState(pos.above()).canOcclude()) {
				cost(player);
				NetherPortalEntity portal = new NetherPortalEntity(ModEntities.NETHER_PORTAL, level, dimension,
						texture);
				portal.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, player.getYRot(), 0);
				level.addFreshEntity(portal);
				playSoundServer(level, player, ModSounds.PORTAL, 1, soundPitch(player));
			}
		}
		return super.magicFinish(level, player, staff);
	}

}
