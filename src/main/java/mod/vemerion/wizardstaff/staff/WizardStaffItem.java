package mod.vemerion.wizardstaff.staff;

import javax.annotation.Nullable;

import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

public class WizardStaffItem extends Item {

	public WizardStaffItem(Item.Properties properties) {
		super(properties);
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
					buffer.writeBoolean(shouldAnimate);
					buffer.writeBoolean(handIn == Hand.MAIN_HAND);
				});
			}
			return ActionResult.resultSuccess(itemstack);
		} else { // Use staff
			playerIn.setActiveHand(handIn);
			ItemStack magic = getMagic(itemstack);
			Magics.getInstance(worldIn).get(magic).magicStart(worldIn, playerIn, itemstack);
		}
		return ActionResult.resultPass(itemstack);
	}

	public static WizardStaffItemHandler getHandler(ItemStack itemstack) {
		WizardStaffItemHandler handler = (WizardStaffItemHandler) itemstack
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				.orElseThrow(() -> new IllegalArgumentException("Wizard staff item is missing capability"));
		return handler;
	}

	public static ItemStack getMagic(ItemStack itemstack) {
		return getHandler(itemstack).getStackInSlot(0);
	}

	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
		return oldStack == newStack;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return new WizardStaffCapabilityProvider();
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
