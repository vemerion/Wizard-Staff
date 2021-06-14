package mod.vemerion.wizardstaff.Magic.swap;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class SwapTradeMagic extends RayMagic {

	private Ingredient blacklist;

	public SwapTradeMagic(MagicType<? extends SwapTradeMagic> type) {
		super(type);
	}

	@Override
	protected IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count) {
		return ParticleTypes.HAPPY_VILLAGER;
	}

	public SwapTradeMagic setAdditionalParams(Ingredient blacklist) {
		this.blacklist = blacklist;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		blacklist = Ingredient.deserialize(json.get("blacklist_ingredient"));
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.add("blacklist_ingredient", blacklist.serialize());
	}

	@Override
	protected void hitEntity(World world, PlayerEntity player, Entity target) {
		if (!(target instanceof VillagerEntity)) {
			playSoundServer(world, player, ModSounds.POOF, 1, soundPitch(player));
			return;
		}

		VillagerEntity villager = (VillagerEntity) target;
		cost(player);
		MerchantOffers offers = new MerchantOffers();
		for (MerchantOffer offer : villager.getOffers()) {
			ItemStack buyFirst = offer.getBuyingStackFirst();
			ItemStack buySecond = offer.getBuyingStackSecond();
			ItemStack sell = offer.getSellingStack();
			if (isBlacklisted(buyFirst) || isBlacklisted(buySecond) || isBlacklisted(sell)
					|| !(buyFirst.isEmpty() || buySecond.isEmpty())) {
				offers.add(new MerchantOffer(offer.write()));
			} else {
				ItemStack selling = (buyFirst.isEmpty() ? buySecond : buyFirst).copy();
				offers.add(new MerchantOffer(sell.copy(), ItemStack.EMPTY, selling, offer.getMaxUses(),
						offer.getMaxUses(), offer.getGivenExp(), offer.getPriceMultiplier(), offer.getDemand()));

			}
		}
		villager.setOffers(offers);
		playSoundServer(world, player, ModSounds.SWAP, 1, soundPitch(player));
	}

	private boolean isBlacklisted(ItemStack stack) {
		return blacklist.test(stack);
	}

}
