package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

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
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			int depth = staffDepth(staff);
			world.createExplosion(null, DamageSource.causePlayerDamage(player), null, player.getPosX(), player.getPosY(),
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
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::buildup;
	}
}
