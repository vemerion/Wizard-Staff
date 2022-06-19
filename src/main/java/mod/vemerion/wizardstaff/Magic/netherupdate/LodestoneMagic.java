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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class LodestoneMagic extends Magic {

	private Block waypoint;

	public LodestoneMagic(MagicType<? extends LodestoneMagic> type) {
		super(type);
	}
	
	public LodestoneMagic setAdditionalParams(Block waypoint) {
		this.waypoint = waypoint;
		return this;
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		waypoint = MagicUtil.decode(buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encode(buffer, waypoint);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		waypoint = MagicUtil.read(json, ForgeRegistries.BLOCKS, "waypoint");
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, waypoint, "waypoint");
	}
	
	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { waypoint.getName() };
	}

	@Override
	public InteractionResult magicInteractBlock(UseOnContext context) {
		Level level = context.getLevel();
		Player player = context.getPlayer();
		if (level.getBlockState(context.getClickedPos()).getBlock() == waypoint) {
			player.playSound(ModSounds.GONG, 1, soundPitch(player));
			if (!level.isClientSide) {
				Wizard.getWizard(player).trackLodestone(level, context.getClickedPos(), waypoint);
			}
			return InteractionResult.SUCCESS;
		}

		return super.magicInteractBlock(context);
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			if (Wizard.getWizard(player).lodestoneTeleport((ServerPlayer) player, waypoint)) {
				playSoundServer(level, player, ModSounds.GONG, 1, soundPitch(player));
				cost(player);
			}
		}
		return super.magicFinish(level, player, staff);
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
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
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.CROSSBOW;
	}
}
