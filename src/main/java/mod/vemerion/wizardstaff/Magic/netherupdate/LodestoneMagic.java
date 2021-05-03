package mod.vemerion.wizardstaff.Magic.netherupdate;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModSounds;
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
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class LodestoneMagic extends Magic {

	private Block waypoint;

	public LodestoneMagic(MagicType type) {
		super(type);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		waypoint = MagicUtil.decode(buffer, ForgeRegistries.BLOCKS);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, waypoint);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		waypoint = MagicUtil.read(json, ForgeRegistries.BLOCKS, "waypoint");
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
			player.playSound(ModSounds.GONG, 1, soundPitch(player));
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
				playSoundServer(world, player, ModSounds.GONG, 1, soundPitch(player));
				cost(player);
			}
		}
		return super.magicFinish(world, player, staff);
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 10 == 0)
			player.playSound(ModSounds.TELEPORT, 1, soundPitch(player));
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
