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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ObsidianMagic extends Magic {

	private RegistryKey<World> dimension;
	private ResourceLocation texture;

	public ObsidianMagic(MagicType<? extends ObsidianMagic> type) {
		super(type);
	}

	public ObsidianMagic setAdditionalParams(RegistryKey<World> dimension, ResourceLocation texture) {
		this.dimension = dimension;
		this.texture = texture;
		return this;
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		dimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, buffer.readResourceLocation());
		texture = buffer.readResourceLocation();
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		buffer.writeResourceLocation(dimension.getLocation());
		buffer.writeResourceLocation(texture);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		dimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
				new ResourceLocation(JSONUtils.getString(json, "dimension")));
		texture = new ResourceLocation(JSONUtils.getString(json, "texture"));
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("dimension", dimension.getLocation().toString());
		json.addProperty("texture", texture.toString());
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { new StringTextComponent(dimension.getLocation().getPath().toString()) };
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote && world.getDimensionKey() != dimension) {
			Vector3d spawnPos = player.getPositionVec()
					.add(Vector3d.fromPitchYaw(player.rotationPitch, player.rotationYaw).scale(2));
			spawnPos = new Vector3d(spawnPos.x, Math.max(player.getPosY(), spawnPos.y), spawnPos.z);
			BlockPos pos = new BlockPos(spawnPos);
			if (!world.getBlockState(pos).isSolid() && !world.getBlockState(pos.up()).isSolid()) {
				cost(player);
				NetherPortalEntity portal = new NetherPortalEntity(ModEntities.NETHER_PORTAL, world, dimension,
						texture);
				portal.setLocationAndAngles(spawnPos.x, spawnPos.y, spawnPos.z, player.rotationYaw, 0);
				world.addEntity(portal);
				playSoundServer(world, player, ModSounds.PORTAL, 1, soundPitch(player));
			}
		}
		return super.magicFinish(world, player, staff);
	}

}
