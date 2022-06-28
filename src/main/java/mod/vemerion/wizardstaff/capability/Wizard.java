package mod.vemerion.wizardstaff.capability;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.ItemStackHandler;

// Class for holding data on player related to magics
public class Wizard implements INBTSerializable<CompoundTag> {
	public static final Capability<Wizard> CAPABILITY = CapabilityManager.get(new CapabilityToken<Wizard>() {
	});

	public static final int INVENTORY_SIZE = 3 * 9;

	private GlobalPos lodestonePos;
	private boolean lodestoneTracked;

	private GrapplingHookEntity grapplingHook;

	private BlockPos surfaceStart, surfaceStop;

	private LinkedList<GlobalPos> revertPositions = new LinkedList<>();

	private ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE);
	
	private int mountJumpTimer;

	public Wizard() {

	}

	public ItemStackHandler getInventory() {
		return inventory;
	}

	public static Wizard getWizard(Player player) {
		return player.getCapability(CAPABILITY)
				.orElseThrow(() -> new IllegalArgumentException("Player is missing wizard capability"));
	}

	public static LazyOptional<Wizard> getWizardOptional(Player player) {
		return player.getCapability(CAPABILITY);
	}

	public void tick(Player player) {
		if (!player.level.isClientSide) {
			if (player.tickCount % 20 == 0) {
				revertPositions.addFirst(GlobalPos.of(player.level.dimension(), player.blockPosition()));
				if (revertPositions.size() > 5)
					revertPositions.removeLast();
			}
		}
		
		if (mountJumpTimer > 0)
			mountJumpTimer--;
	}
	
	public boolean mountJump() {
		if (mountJumpTimer == 0) {
			mountJumpTimer = 20 * 2;
			return true;
		}
		return false;
	}

	public BlockPos revertPosition(Player player) {
		if (revertPositions.size() < 5)
			return null;

		GlobalPos pos = revertPositions.getLast();
		return pos.dimension() == player.level.dimension() ? pos.pos() : null;
	}

	public boolean lodestoneTeleport(ServerPlayer player, Block waypoint) {
		if (lodestoneTracked && lodestonePos != null && player.level.dimension() == lodestonePos.dimension()
				&& (player.level.getBlockState(lodestonePos.pos()).getBlock() == waypoint)) {
			lodestoneTracked = false;
			BlockPos destination = lodestonePos.pos();
			player.teleportTo(player.getLevel(), destination.getX(), destination.getY() + 1, destination.getZ(),
					player.getYRot(), player.getXRot());
			player.level.destroyBlock(destination, false);
			return true;
		}
		return false;
	}

	public boolean throwGrapplingHook(Level world, Player player) {
		if (!world.isClientSide) {
			Vec3 start = player.getEyePosition(0.5f);
			Vec3 end = start.add(Vec3.directionFromRotation(player.getRotationVector()).scale(10));
			BlockHitResult raytrace = world
					.clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
			if (raytrace.getType() == HitResult.Type.BLOCK) {
				GrapplingHookEntity hook = new GrapplingHookEntity(world, player);
				Vec3 pos = raytrace.getLocation().subtract(end.subtract(start).normalize().scale(0.07));
				hook.moveTo(pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
				world.addFreshEntity(hook);
				grapplingHook = hook;
				return true;
			}
		}
		return false;
	}

	public void trackLodestone(Level world, BlockPos pos, Block waypoint) {
		if (world.getBlockState(pos).getBlock() == waypoint) {
			lodestoneTracked = true;
			lodestonePos = GlobalPos.of(world.dimension(), pos.immutable());
		}
	}

	public void reelGrapplingHook(Level world, Player player) {
		if (grapplingHook != null && grapplingHook.isAlive()) {
			Vec3 direction = grapplingHook.position().subtract(player.position()).normalize().scale(1.2);
			Vec3 motion = player.getDeltaMovement().add(direction);
			player.setDeltaMovement(motion);

			if (!world.isClientSide)
				grapplingHook.discard();
		}
	}

	public void setGrapplingHook(GrapplingHookEntity hook) {
		this.grapplingHook = hook;
	}

	public void setSurfacePos(BlockPos pos) {
		if (pos.equals(surfaceStart)) {
			surfaceStart = null;
		} else if (pos.equals(surfaceStop)) {
			surfaceStop = null;
		} else if (surfaceStart != null && surfaceStop != null) {
			surfaceStart = pos;
			surfaceStop = null;
		} else if (surfaceStart == null) {
			surfaceStart = pos;
		} else {
			surfaceStop = pos;
		}
	}

	public int createSurface(Level world, Player player) {
		BlockPos start = surfaceStart == null ? null : new BlockPos(surfaceStart);
		BlockPos stop = surfaceStop == null ? null : new BlockPos(surfaceStop);
		surfaceStart = null;
		surfaceStop = null;

		if (start == null || stop == null)
			return 0;

		// Too far away
		if (!player.blockPosition().closerThan(start, 100) || !player.blockPosition().closerThan(stop, 100))
			return 0;

		if (world.isEmptyBlock(start) || world.isEmptyBlock(stop))
			return 0;

		AABB surface = new AABB(start, stop);

		// Not a 2d surface
		if (surface.getXsize() > 0.1 && surface.getYsize() > 0.1 && surface.getZsize() > 0.1)
			return 0;

		Item item = world.getBlockState(start).getBlock().asItem();

		if (item == Items.AIR || !(item instanceof BlockItem))
			return 0;

		List<BlockPos> positions = BlockPos.betweenClosedStream(surface).map(b -> b.immutable())
				.collect(Collectors.toList());

		// Placement of surface blocks
		int count = 0;
		Inventory inv = player.getInventory();
		for (int i = 0; i < inv.getContainerSize(); i++) {
			if (inv.getItem(i).getItem() != item)
				continue;
			ItemStack stack = inv.removeItemNoUpdate(i);
			while (!positions.isEmpty()) {
				if (stack.isEmpty())
					break;

				BlockPos p = positions.remove(0);
				if (!world.isEmptyBlock(p))
					continue;

				BlockPlaceContext context = new DirectionalPlaceContext(world, p, Direction.NORTH, stack,
						Direction.DOWN);
				if (((BlockItem) item).place(context) == InteractionResult.FAIL)
					continue;

				count++;
			}

			inv.add(stack);
		}

		return count;
	}

	public void deserializeNBT(CompoundTag compound) {
		lodestoneTracked = compound.getBoolean("lodestoneTracked");
		if (lodestoneTracked) {
			lodestonePos = GlobalPos.CODEC.parse(NbtOps.INSTANCE, compound.get("lodestonePos")).result().orElse(null);
		}

		if (compound.contains("inventory"))
			inventory.deserializeNBT(compound.getCompound("inventory"));
	}

	public CompoundTag serializeNBT() {
		CompoundTag compound = new CompoundTag();
		if (lodestoneTracked) {
			GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, lodestonePos).resultOrPartial(s -> {
			}).ifPresent((lodestonePos) -> {
				compound.put("lodestonePos", lodestonePos);
			});
		}
		compound.putBoolean("lodestoneTracked", lodestoneTracked);

		compound.put("inventory", inventory.serializeNBT());

		return compound;
	}

	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class WizardProvider implements ICapabilitySerializable<CompoundTag> {

		private LazyOptional<Wizard> instance = LazyOptional.of(Wizard::new);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public CompoundTag serializeNBT() {
			return instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!"))
					.serializeNBT();
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!"))
					.deserializeNBT(nbt);
		}

		public static final ResourceLocation LOCATION = new ResourceLocation(Main.MODID, "wizard");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player)
				event.addCapability(LOCATION, new WizardProvider());
		}
	}
}
