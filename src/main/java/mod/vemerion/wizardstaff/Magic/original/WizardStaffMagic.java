package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class WizardStaffMagic extends Magic {
	
	public WizardStaffMagic(MagicType<? extends WizardStaffMagic> type) {
		super(type);
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.CROSSBOW;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			int depth = staffDepth(staff);
			level.explode(null, magicDamage(player), null, player.getX(), player.getY(),
					player.getZ(), depth, true, Explosion.BlockInteraction.DESTROY);
		}
		return ItemStack.EMPTY;
	}
	
	private int staffDepth(ItemStack stack) {
		int i = 0;
		ItemStack current = stack;
		while (current.getItem() instanceof WizardStaffItem) {
			current = WizardStaffItem.getMagic(current);
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
