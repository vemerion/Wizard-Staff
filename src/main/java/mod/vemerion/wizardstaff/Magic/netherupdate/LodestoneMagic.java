package mod.vemerion.wizardstaff.Magic.netherupdate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class LodestoneMagic extends Magic {

	private Block waypoint;

	public LodestoneMagic(String name) {
		super(name);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		int blockKeyLen = buffer.readInt();
		waypoint = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(buffer.readString(blockKeyLen)));
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		String blockKey = waypoint.getRegistryName().toString();
		buffer.writeInt(blockKey.length());
		buffer.writeString(blockKey);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		ResourceLocation blockKey = new ResourceLocation(JSONUtils.getString(json, "waypoint"));
		if (ForgeRegistries.BLOCKS.containsKey(blockKey)) {
			waypoint = ForgeRegistries.BLOCKS.getValue(blockKey);
		} else {
			throw new JsonParseException("Invalid block name " + blockKey);
		}
	}
	
	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { waypoint.getTranslatedName() };
	}

	@Override
	public ActionResultType magicInteractBlock(ItemUseContext context) {
		World world = context.getWorld();
		PlayerEntity player = context.getPlayer();
		if (world.getBlockState(context.getPos()).getBlock() == waypoint) {
			player.playSound(Main.GONG_SOUND, 1, soundPitch(player));
			if (!world.isRemote) {
				Wizard.getWizard(player).trackLodestone(world, context.getPos(), waypoint);
			}
			return ActionResultType.SUCCESS;
		}

		return super.magicInteractBlock(context);
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			if (Wizard.getWizard(player).lodestoneTeleport((ServerPlayerEntity) player, waypoint)) {
				playSoundServer(world, player, Main.GONG_SOUND, 1, soundPitch(player));
				cost(player);
			}
		}
		return super.magicFinish(world, player, staff);
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 10 == 0)
			player.playSound(Main.TELEPORT_SOUND, 1, soundPitch(player));
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::buildup;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}
}
