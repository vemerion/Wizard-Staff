package mod.vemerion.wizardstaff.staff;

import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import mod.vemerion.wizardstaff.Magic.Magics;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.network.NetworkHooks;

public class WizardStaffItem extends Item {

	private static final AttributeModifier NO_COOLDOWN = new AttributeModifier(
			UUID.fromString("90c1a0db-5acd-4910-9257-587fdf003642"), "Wizard Staff", 100,
			AttributeModifier.Operation.MULTIPLY_TOTAL);

	public WizardStaffItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new RenderProperties());
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
		return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		return true;
	}

	public static Multimap<Attribute, AttributeModifier> getStaffModifiers() {
		return ImmutableMultimap.of(Attributes.ATTACK_SPEED, NO_COOLDOWN);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);

		if (playerIn.isCrouching()) { // Open inventory if crouching
			if (!worldIn.isClientSide) {
				boolean shouldAnimate = ScreenAnimations.getScreenAnimations(playerIn).shouldAnimate();
				SimpleMenuProvider provider = new SimpleMenuProvider(
						(id, inventory, player) -> new WizardStaffContainer(id, inventory,
								WizardStaffItemHandler.get(itemstack), itemstack, shouldAnimate, handIn),
						getName(itemstack));
				NetworkHooks.openGui((ServerPlayer) playerIn, provider, (buffer) -> {
					buffer.writeBoolean(handIn == InteractionHand.MAIN_HAND);
					buffer.writeBoolean(shouldAnimate);
				});
			}
			return InteractionResultHolder.success(itemstack);
		} else { // Use staff
			playerIn.startUsingItem(handIn);
			Magics.getInstance(worldIn).get(getMagic(itemstack)).magicStart(worldIn, playerIn, itemstack);

		}
		return InteractionResultHolder.pass(itemstack);
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
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new WizardStaffCapabilityProvider(stack);
	}

	// We only want this for the third person visual effect,
	// hence NONE on server
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		if (EffectiveSide.get().isClient()) {
			ItemStack magic = getMagic(stack);
			return Magics.getInstance(true).get(magic).getUseAnim(stack);
		} else {
			return UseAnim.NONE;
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		ItemStack magic = getMagic(stack);
		return Magics.getInstance(EffectiveSide.get().isClient()).get(magic).getUseDuration(stack);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		ItemStack magic = getMagic(stack);
		if (livingEntityIn instanceof Player) {
			Magics.getInstance(worldIn).get(magic).magicTick(worldIn, (Player) livingEntityIn, stack, count);
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		ItemStack magic = getMagic(stack);
		if (entityLiving instanceof Player) {
			return Magics.getInstance(worldIn).get(magic).magicFinish(worldIn, (Player) entityLiving, stack);
		}
		return stack;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		ItemStack magic = getMagic(context.getItemInHand());
		if (context.getPlayer() == null) {
			return InteractionResult.PASS;
		} else {
			return Magics.getInstance(context.getLevel()).get(magic).magicInteractBlock(context);
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		ItemStack magic = getMagic(stack);
		if (entityLiving instanceof Player) {
			Magics.getInstance(worldIn).get(magic).magicCancel(worldIn, (Player) entityLiving, stack, timeLeft);
		}
	}

	@Override
	public CompoundTag getShareTag(ItemStack stack) {
		CompoundTag result = new CompoundTag();
		CompoundTag tag = super.getShareTag(stack);
		CompoundTag cap = WizardStaffItemHandler.get(stack).serializeNBT();
		if (tag != null)
			result.put("tag", tag);
		if (cap != null)
			result.put("cap", cap);
		return result;
	}

	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
		if (nbt == null) {
			stack.setTag(nbt);
		} else {
			stack.setTag(nbt.getCompound("tag"));
			WizardStaffItemHandler.get(stack).deserializeNBT(nbt.getCompound("cap"));
		}
	}

	private static class RenderProperties implements IItemRenderProperties {
		@Override
		public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
			var mc = Minecraft.getInstance();
			return new WizardStaffTileEntityRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels());
		}
	}

}
