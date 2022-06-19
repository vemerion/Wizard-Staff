package mod.vemerion.wizardstaff.Magic.swap;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class SwapTradeMagic extends RayMagic {

	private Ingredient blacklist;

	public SwapTradeMagic(MagicType<? extends SwapTradeMagic> type) {
		super(type);
		blacklist = Ingredient.EMPTY;
	}

	@Override
	protected ParticleOptions generateParticle(Level level, Player player, ItemStack staff, int count) {
		return ParticleTypes.HAPPY_VILLAGER;
	}

	public SwapTradeMagic setAdditionalParams(Ingredient blacklist) {
		this.blacklist = blacklist;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		if (GsonHelper.isValidNode(json, "blacklist_ingredient"))
			blacklist = Ingredient.fromJson(json.get("blacklist_ingredient"));
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		if (blacklist != Ingredient.EMPTY)
			json.add("blacklist_ingredient", blacklist.toJson());
	}

	@Override
	protected void hitEntity(Level level, Player player, Entity target) {
		if (!(target instanceof Villager)) {
			playSoundServer(level, player, ModSounds.POOF, 1, soundPitch(player));
			return;
		}

		Villager villager = (Villager) target;
		cost(player);
		MerchantOffers offers = new MerchantOffers();
		for (MerchantOffer offer : villager.getOffers()) {
			ItemStack buyFirst = offer.getBaseCostA();
			ItemStack buySecond = offer.getCostB();
			ItemStack sell = offer.getResult();
			if (isBlacklisted(buyFirst) || isBlacklisted(buySecond) || isBlacklisted(sell)
					|| !(buyFirst.isEmpty() || buySecond.isEmpty())) {
				offers.add(new MerchantOffer(offer.createTag()));
			} else {
				ItemStack selling = (buyFirst.isEmpty() ? buySecond : buyFirst).copy();
				offers.add(new MerchantOffer(sell.copy(), ItemStack.EMPTY, selling, offer.getMaxUses(),
						offer.getMaxUses(), offer.getXp(), offer.getPriceMultiplier(), offer.getDemand()));

			}
		}
		villager.setOffers(offers);
		playSoundServer(level, player, SoundEvents.VILLAGER_CELEBRATE, 1, soundPitch(player));
	}

	private boolean isBlacklisted(ItemStack stack) {
		return !stack.isEmpty() && blacklist.test(stack);
	}

}
