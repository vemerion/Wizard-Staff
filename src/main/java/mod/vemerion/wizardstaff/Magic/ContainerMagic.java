package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			Wizard.getWizardOptional(player).ifPresent(w -> {
				cost(player);
				SimpleNamedContainerProvider provider = new SimpleNamedContainerProvider(
						(id, inventory, p) -> getContainer(id, inventory, p, world, w), getDescription().getName());
				NetworkHooks.openGui((ServerPlayerEntity) player, provider);
			});
		}
		player.playSound(getSound(), 1, soundPitch(player));
		return super.magicFinish(world, player, staff);
	}

	abstract protected Container getContainer(int id, PlayerInventory playerInv, PlayerEntity player, World world, Wizard wizard);
	abstract protected SoundEvent getSound();

}
