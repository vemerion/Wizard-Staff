package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion.Mode;

public class WizardStaffMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return 20;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Main.WIZARD_STAFF_ITEM;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			int depth = staffDepth(staff);
			world.createExplosion(null, DamageSource.causePlayerDamage(player), player.getPosX(), player.getPosY(),
					player.getPosZ(), depth, true, Mode.DESTROY);
		}
		return ItemStack.EMPTY;
	}
	
	private int staffDepth(ItemStack stack) {
		int i = 0;
		ItemStack current = stack;
		while (current.getItem() instanceof WizardStaffItem) {
			current = ((WizardStaffItem) current.getItem()).getMagic(current);
			i++;
		}
		return i;
	}
	
	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildup;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::buildup;
	}
}
