package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public abstract class ContainerMagic extends Magic {

	public ContainerMagic(MagicType<? extends ContainerMagic> type) {
		super(type);
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
			Wizard.getWizardOptional(player).ifPresent(w -> {
				cost(player);
				SimpleMenuProvider provider = new SimpleMenuProvider(
						(id, inventory, p) -> getContainer(id, inventory, p, level, staff, w),
						getDescription().getName());
				NetworkHooks.openGui((ServerPlayer) player, provider, b -> addExtraData(b, player));
			});
		}
		player.playSound(getSound(), 1, soundPitch(player));
		return super.magicFinish(level, player, staff);
	}
	
	abstract protected AbstractContainerMenu getContainer(int id, Inventory playerInv, Player player, Level level,
			ItemStack staff, Wizard wizard);

	abstract protected SoundEvent getSound();
	
	protected void addExtraData(FriendlyByteBuf buffer, Player player) {
	}
}
