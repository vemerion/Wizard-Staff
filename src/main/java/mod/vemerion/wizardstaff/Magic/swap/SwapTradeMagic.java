package mod.vemerion.wizardstaff.Magic.swap;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.Magic.RayMagic;
import mod.vemerion.wizardstaff.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SwapTradeMagic extends RayMagic {

	private Set<ResourceLocation> blacklist;

	public SwapTradeMagic(MagicType<? extends SwapTradeMagic> type) {
		super(type);
	}

	@Override
	protected IParticleData generateParticle(World world, PlayerEntity player, ItemStack staff, int count) {
		return ParticleTypes.HAPPY_VILLAGER;
	}

	public SwapTradeMagic setAdditionalParams(Set<ResourceLocation> blacklist) {
		this.blacklist = blacklist;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		blacklist = MagicUtil.readColl(json, "blacklist",
				e -> new ResourceLocation(JSONUtils.getString(e, "entity name")), new HashSet<>());
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "blacklist", blacklist, r -> new JsonPrimitive(r.toString()));
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
		return blacklist.contains(stack.getItem().getRegistryName());
	}

}
