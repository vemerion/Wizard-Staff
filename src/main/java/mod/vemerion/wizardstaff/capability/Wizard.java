package mod.vemerion.wizardstaff.capability;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.entity.GrapplingHookEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.ItemStackHandler;

// Class for holding data on player related to magics
public class Wizard implements INBTSerializable<CompoundNBT> {
	@CapabilityInject(Wizard.class)
	public static final Capability<Wizard> CAPABILITY = null;
	
	public static final int INVENTORY_SIZE = 3 * 9;

	private GlobalPos lodestonePos;
	private boolean lodestoneTracked;

	private GrapplingHookEntity grapplingHook;

	private BlockPos surfaceStart, surfaceStop;

	private LinkedList<GlobalPos> revertPositions = new LinkedList<>();
	
	private ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE);

	public Wizard() {

	}
	
	public ItemStackHandler getInventory() {
		return inventory;
	}

	public static Wizard getWizard(PlayerEntity player) {
		return player.getCapability(CAPABILITY)
				.orElseThrow(() -> new IllegalArgumentException("Player is missing wizard capability"));
	}

	public static LazyOptional<Wizard> getWizardOptional(PlayerEntity player) {
		return player.getCapability(CAPABILITY);
	}

	public void tick(PlayerEntity player) {
		if (!player.world.isRemote) {
			if (player.ticksExisted % 20 == 0) {
				revertPositions.addFirst(GlobalPos.getPosition(player.world.getDimensionKey(), player.getPosition()));
				if (revertPositions.size() > 5)
					revertPositions.removeLast();
			}
		}
	}

	public BlockPos revertPosition(PlayerEntity player) {
		if (revertPositions.size() < 5)
			return null;

		GlobalPos pos = revertPositions.getLast();
		return pos.getDimension() == player.world.getDimensionKey() ? pos.getPos() : null;
	}

	public boolean lodestoneTeleport(ServerPlayerEntity player, Block waypoint) {
		if (lodestoneTracked && lodestonePos != null && player.world.getDimensionKey() == lodestonePos.getDimension()
				&& (player.world.getBlockState(lodestonePos.getPos()).getBlock() == waypoint)) {
			lodestoneTracked = false;
			BlockPos destination = lodestonePos.getPos();
			player.teleport(player.getServerWorld(), destination.getX(), destination.getY() + 1, destination.getZ(),
					player.rotationYaw, player.rotationPitch);
			player.world.destroyBlock(destination, false);
			return true;
		}
		return false;
	}

	public boolean throwGrapplingHook(World world, PlayerEntity player) {
		if (!world.isRemote) {
			Vector3d start = player.getEyePosition(0.5f);
			Vector3d end = start.add(Vector3d.fromPitchYaw(player.getPitchYaw()).scale(10));
			BlockRayTraceResult raytrace = world
					.rayTraceBlocks(new RayTraceContext(start, end, BlockMode.OUTLINE, FluidMode.NONE, player));
			if (raytrace.getType() == Type.BLOCK) {
				GrapplingHookEntity hook = new GrapplingHookEntity(world, player);
				Vector3d pos = raytrace.getHitVec().subtract(end.subtract(start).normalize().scale(0.07));
				hook.setLocationAndAngles(pos.x, pos.y, pos.z, player.rotationYaw, player.rotationPitch);
				world.addEntity(hook);
				grapplingHook = hook;
				return true;
			}
		}
		return false;
	}

	public void trackLodestone(World world, BlockPos pos, Block waypoint) {
		if (world.getBlockState(pos).getBlock() == waypoint) {
			lodestoneTracked = true;
			lodestonePos = GlobalPos.getPosition(world.getDimensionKey(), pos.toImmutable());
		}
	}

	public void reelGrapplingHook(World world, PlayerEntity player) {
		if (grapplingHook != null && grapplingHook.isAlive()) {
			Vector3d direction = grapplingHook.getPositionVec().subtract(player.getPositionVec()).normalize()
					.scale(1.2);
			Vector3d motion = player.getMotion().add(direction);
			player.setMotion(motion);

			if (!world.isRemote)
				grapplingHook.remove();
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

	public int createSurface(World world, PlayerEntity player) {
		BlockPos start = surfaceStart == null ? null : new BlockPos(surfaceStart);
		BlockPos stop = surfaceStop == null ? null : new BlockPos(surfaceStop);
		surfaceStart = null;
		surfaceStop = null;

		if (start == null || stop == null)
			return 0;

		// Too far away
		if (!player.getPosition().withinDistance(start, 100) || !player.getPosition().withinDistance(stop, 100))
			return 0;

		if (world.isAirBlock(start) || world.isAirBlock(stop))
			return 0;

		AxisAlignedBB surface = new AxisAlignedBB(start, stop);

		// Not a 2d surface
		if (surface.getXSize() > 0.1 && surface.getYSize() > 0.1 && surface.getZSize() > 0.1)
			return 0;

		Item item = world.getBlockState(start).getBlock().asItem();

		if (item == Items.AIR || !(item instanceof BlockItem))
			return 0;

		List<BlockPos> positions = BlockPos.getAllInBox(surface).map(b -> b.toImmutable()).collect(Collectors.toList());

		// Placement of surface blocks
		int count = 0;
		PlayerInventory inv = player.inventory;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (inv.getStackInSlot(i).getItem() != item)
				continue;
			ItemStack stack = inv.removeStackFromSlot(i);
			while (!positions.isEmpty()) {
				if (stack.isEmpty())
					break;

				BlockPos p = positions.remove(0);
				if (!world.isAirBlock(p))
					continue;

				BlockItemUseContext context = new DirectionalPlaceContext(world, p, Direction.NORTH, stack,
						Direction.DOWN);
				if (((BlockItem) item).tryPlace(context) == ActionResultType.FAIL)
					continue;

				count++;
			}

			inv.addItemStackToInventory(stack);
		}

		return count;
	}

	public void deserializeNBT(CompoundNBT compound) {
		lodestoneTracked = compound.getBoolean("lodestoneTracked");
		if (lodestoneTracked) {
			lodestonePos = GlobalPos.CODEC.parse(NBTDynamicOps.INSTANCE, compound.get("lodestonePos")).result()
					.orElse(null);
		}
		
		if (compound.contains("inventory"))
			inventory.deserializeNBT(compound.getCompound("inventory"));
	}

	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		if (lodestoneTracked) {
			GlobalPos.CODEC.encodeStart(NBTDynamicOps.INSTANCE, lodestonePos).resultOrPartial(s -> {
			}).ifPresent((lodestonePos) -> {
				compound.put("lodestonePos", lodestonePos);
			});
		}
		compound.putBoolean("lodestoneTracked", lodestoneTracked);
		
		compound.put("inventory", inventory.serializeNBT());
		
		return compound;
	}

	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class WizardProvider implements ICapabilitySerializable<INBT> {

		private LazyOptional<Wizard> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public INBT serializeNBT() {
			return CAPABILITY.getStorage().writeNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			CAPABILITY.getStorage().readNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null,
					nbt);
		}

		public static final ResourceLocation LOCATION = new ResourceLocation(Main.MODID, "wizard");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity)
				event.addCapability(LOCATION, new WizardProvider());
		}
	}

	public static class WizardStorage implements IStorage<Wizard> {

		@Override
		public INBT writeNBT(Capability<Wizard> capability, Wizard instance, Direction side) {
			return instance.serializeNBT();

		}

		@Override
		public void readNBT(Capability<Wizard> capability, Wizard instance, Direction side, INBT nbt) {
			instance.deserializeNBT((CompoundNBT) nbt);
		}
	}
}
