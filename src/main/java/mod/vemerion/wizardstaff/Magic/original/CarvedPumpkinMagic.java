package mod.vemerion.wizardstaff.Magic.original;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class CarvedPumpkinMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return 40;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.CARVED_PUMPKIN;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(Main.PUMPKIN_MAGIC_SOUND, 0.2f, soundPitch(player));
		if (!world.isRemote) {
			cost(player, 50);
			PumpkinMagicEntity entity = new PumpkinMagicEntity(Main.PUMPKIN_MAGIC_ENTITY, world, player);
			entity.setPositionAndRotation(player.getPosX(), player.getPosY() + 2, player.getPosZ(), player.rotationYaw,
					0);
			world.addEntity(entity);
		}
		return super.magicFinish(world, player, staff);
	}

	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::forwardBuildup;
	}

}
