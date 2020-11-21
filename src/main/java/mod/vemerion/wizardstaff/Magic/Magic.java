package mod.vemerion.wizardstaff.Magic;

import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.item.MagicArmorItem;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class Magic {

	public static final int HOUR = 72000;
	
	private float cost;
	private int duration;
	private Ingredient ingredient;
	
	public void init(float cost, int duration, Ingredient ingredient) {
		this.cost = cost;
		this.duration = duration;
		this.ingredient = ingredient;
	}

	protected float soundPitch(PlayerEntity player) {
		return 0.8f + player.getRNG().nextFloat() * 0.4f;
	}

	protected void playSoundServer(World world, PlayerEntity player, SoundEvent sound, float volume, float pitch) {
		world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), sound, SoundCategory.PLAYERS,
				volume, pitch);
	}

	protected final void cost(PlayerEntity player) {
		int whole = Experience.add(player, cost * discount(player));
		double debt = debt(player, whole);
		player.giveExperiencePoints(-whole);
		if (debt > 0)
			player.attackEntityFrom(DamageSource.MAGIC, (float) debt);

	}

	private double discount(PlayerEntity player) {
		return 1 - 0.1 * MagicArmorItem.countMagicArmorPieces(player);
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

	public final int getUseDuration(ItemStack staff) {
		return duration;
	}

	public final boolean isMagicItem(ItemStack stack) {
		return ingredient.test(stack);
	}

	public abstract RenderFirstPersonMagic firstPersonRenderer();

	public abstract RenderThirdPersonMagic thirdPersonRenderer();

	public abstract UseAction getUseAction(ItemStack stack);

	public void magicStart(World world, PlayerEntity player, ItemStack staff) {
	}

	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
	}

	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		return staff;
	}
}
