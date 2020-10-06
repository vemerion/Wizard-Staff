package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class LodestoneMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return 20 * 6;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.LODESTONE;
	}

	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::buildup;
	}

	@Override
	public ActionResultType magicInteractBlock(ItemUseContext context) {
		World world = context.getWorld();
		PlayerEntity player = context.getPlayer();
		player.playSound(Main.GONG_SOUND, 1, soundPitch(player));
		if (world.getBlockState(context.getPos()).getBlock() == Blocks.LODESTONE) {
			if (!world.isRemote) {
				Wizard.getWizard(player).trackLodestone(world, context.getPos());
			}
			return ActionResultType.SUCCESS;
		}

		return super.magicInteractBlock(context);
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			if (Wizard.getWizard(player).lodestoneTeleport(player)) {
				cost(player, 500);
			}
		}
		return super.magicFinish(world, player, staff);
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 10 == 0)
			player.playSound(Main.TELEPORT_SOUND, 1, soundPitch(player));
	}
}
