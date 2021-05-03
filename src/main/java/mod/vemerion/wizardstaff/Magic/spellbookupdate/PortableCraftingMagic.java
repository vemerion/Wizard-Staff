package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PortableCraftingMagic extends Magic {

	private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.crafting");

	public PortableCraftingMagic(MagicType<? extends PortableCraftingMagic> type) {
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			cost(player);
			SimpleNamedContainerProvider provider = new SimpleNamedContainerProvider((id, inventory,
					p) -> new PortableCrafterContainer(id, inventory, IWorldPosCallable.of(world, p.getPosition())),
					CONTAINER_NAME);
			NetworkHooks.openGui((ServerPlayerEntity) player, provider);
		}
		player.playSound(ModSounds.ANVIL, 1, soundPitch(player));
		return super.magicFinish(world, player, staff);
	}

	private class PortableCrafterContainer extends WorkbenchContainer {

		public PortableCrafterContainer(int syncid, PlayerInventory playerInv, IWorldPosCallable posCallable) {
			super(syncid, playerInv, posCallable);
		}

		@Override
		public boolean canInteractWith(PlayerEntity playerIn) {
			return playerIn.getHeldItemMainhand().getItem() instanceof WizardStaffItem;
		}

	}

}
