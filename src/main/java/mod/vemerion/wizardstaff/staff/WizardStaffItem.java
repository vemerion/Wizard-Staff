package mod.vemerion.wizardstaff.staff;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.network.NetworkHooks;

public class WizardStaffItem extends Item {

	private static final AttributeModifier NO_COOLDOWN = new AttributeModifier(
			UUID.fromString("90c1a0db-5acd-4910-9257-587fdf003642"), "Wizard Staff", 100,
			AttributeModifier.Operation.MULTIPLY_TOTAL);

	public WizardStaffItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		return true;
	}

	public static Multimap<Attribute, AttributeModifier> getStaffModifiers() {
		return ImmutableMultimap.of(Attributes.ATTACK_SPEED, NO_COOLDOWN);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (playerIn.isCrouching()) { // Open inventory if crouching
			if (!worldIn.isRemote) {
				boolean shouldAnimate = ScreenAnimations.getScreenAnimations(playerIn).shouldAnimate();
				SimpleNamedContainerProvider provider = new SimpleNamedContainerProvider(
						(id, inventory, player) -> new WizardStaffContainer(id, inventory,
								WizardStaffItemHandler.get(itemstack), itemstack, shouldAnimate, handIn),
						getDisplayName(itemstack));
				NetworkHooks.openGui((ServerPlayerEntity) playerIn, provider, (buffer) -> {
					buffer.writeBoolean(handIn == Hand.MAIN_HAND);
					buffer.writeBoolean(shouldAnimate);
				});
			}
			return ActionResult.resultSuccess(itemstack);
		} else { // Use staff
			playerIn.setActiveHand(handIn);
			Magics.getInstance(worldIn).get(getMagic(itemstack)).magicStart(worldIn, playerIn, itemstack);

		}
		return ActionResult.resultPass(itemstack);
	}

	public static ItemStack getMagic(ItemStack itemstack) {
		WizardStaffItemHandler handler = WizardStaffItemHandler.get(itemstack);
		return handler == null ? ItemStack.EMPTY : handler.getCurrent();
	}

	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
		return oldStack == newStack;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return new WizardStaffCapabilityProvider(stack);
	}

	// We only want this for the third person visual effect,
	// hence NONE on server
	@Override
	public UseAction getUseAction(ItemStack stack) {
		if (EffectiveSide.get().isClient()) {
			ItemStack magic = getMagic(stack);
			return Magics.getInstance(true).get(magic).getUseAction(stack);
		} else {
			return UseAction.NONE;
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		ItemStack magic = getMagic(stack);
		return Magics.getInstance(EffectiveSide.get().isClient()).get(magic).getUseDuration(stack);
	}

	@Override
	public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		ItemStack magic = getMagic(stack);
		if (livingEntityIn instanceof PlayerEntity) {
			Magics.getInstance(worldIn).get(magic).magicTick(worldIn, (PlayerEntity) livingEntityIn, stack, count);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		ItemStack magic = getMagic(stack);
		if (entityLiving instanceof PlayerEntity) {
			return Magics.getInstance(worldIn).get(magic).magicFinish(worldIn, (PlayerEntity) entityLiving, stack);
		}
		return stack;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ItemStack magic = getMagic(context.getItem());
		if (context.getPlayer() == null) {
			return ActionResultType.PASS;
		} else {
			return Magics.getInstance(context.getWorld()).get(magic).magicInteractBlock(context);
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		ItemStack magic = getMagic(stack);
		if (entityLiving instanceof PlayerEntity) {
			Magics.getInstance(worldIn).get(magic).magicCancel(worldIn, (PlayerEntity) entityLiving, stack, timeLeft);
		}
	}

	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT result = new CompoundNBT();
		CompoundNBT tag = super.getShareTag(stack);
		CompoundNBT cap = WizardStaffItemHandler.get(stack).serializeNBT();
		if (tag != null)
			result.put("tag", tag);
		if (cap != null)
			result.put("cap", cap);
		return result;
	}

	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
		if (nbt == null) {
			stack.setTag(nbt);
		} else {
			stack.setTag(nbt.getCompound("tag"));
			WizardStaffItemHandler.get(stack).deserializeNBT(nbt.getCompound("cap"));
		}
	}

}
