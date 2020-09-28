package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class Magic {

	protected static final int HOUR = 72000;

	protected float soundPitch(PlayerEntity player) {
		return 0.8f + player.getRNG().nextFloat() * 0.4f;
	}

	protected void cost(PlayerEntity player, double amount) {
		int whole = Experience.add(player, amount);
		double debt = debt(player, whole);
		player.giveExperiencePoints(-whole);
		if (debt > 0)
			player.attackEntityFrom(DamageSource.MAGIC, (float) debt);

	}

	private double debt(PlayerEntity player, double amount) {
		int trueLevel = player.experienceLevel;
		amount -= player.experience * player.xpBarCap();
		while (--player.experienceLevel >= 0 && amount > 0) {
			amount -= player.xpBarCap();
		}
		player.experienceLevel = trueLevel;
		return amount;
	}

	public abstract int getUseDuration(ItemStack staff);

	public abstract boolean isMagicItem(Item item);

	public abstract RenderMagic renderer();

	public void magicStart(World world, PlayerEntity player, ItemStack staff) {
	}

	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
	}

	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		return staff;
	}
}
