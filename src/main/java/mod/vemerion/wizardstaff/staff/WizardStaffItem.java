package mod.vemerion.wizardstaff.staff;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.capability.ScreenAnimations;
import mod.vemerion.wizardstaff.entity.PumpkinMagicEntity;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.sound.WizardStaffTickableSound;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

public class WizardStaffItem extends Item {

	private static final int HOUR = 72000;

	public WizardStaffItem() {
		super(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)
				.setISTER(() -> WizardStaffTileEntityRenderer::new));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (playerIn.isCrouching()) { // Open inventory if crouching
			if (!worldIn.isRemote) {
				boolean shouldAnimate = ScreenAnimations.getScreenAnimations(playerIn).shouldAnimate();

				SimpleNamedContainerProvider provider = new SimpleNamedContainerProvider((id, inventory,
						player) -> new WizardStaffContainer(id, inventory, getHandler(itemstack), itemstack, shouldAnimate),
						new StringTextComponent("Wizard Staff"));
				NetworkHooks.openGui((ServerPlayerEntity) playerIn, provider,
						(buffer) -> buffer.writeBoolean(shouldAnimate));
			}
			return ActionResult.resultSuccess(itemstack);
		} else { // Use staff
			playerIn.setActiveHand(handIn);
			ItemStack magicStack = getMagic(itemstack);
			Item magic = magicStack.getItem();
			if (magic == Items.JUKEBOX) {
				if (worldIn.isRemote) {
					DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
						jukeboxStart(worldIn, playerIn, itemstack);
					});
				}
				return ActionResult.resultSuccess(itemstack);
			}
		}
		return ActionResult.resultPass(itemstack);
	}

	private WizardStaffHandler getHandler(ItemStack itemstack) {
		WizardStaffHandler handler = (WizardStaffHandler) itemstack
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				.orElseThrow(() -> new IllegalArgumentException("Wizard staff item is missing capability"));
		return handler;
	}

	public ItemStack getMagic(ItemStack itemstack) {
		return getHandler(itemstack).getStackInSlot(0);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return new WizardStaffCapabilityProvider();
	}

	private int staffDepth(ItemStack stack) {
		int i = 0;
		ItemStack current = stack;
		while (current.getItem() instanceof WizardStaffItem) {
			current = ((WizardStaffItem) current.getItem()).getMagic(current);
			i++;
		}
		return i;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		Item magic = getMagic(stack).getItem();
		if (magic == Items.ELYTRA) {
			return HOUR;
		} else if (magic == Main.WIZARD_STAFF_ITEM) {
			return 20;
		} else if (magic == Items.CLOCK) {
			return HOUR;
		} else if (magic == Items.WRITABLE_BOOK) {
			return 20;
		} else if (magic == Items.CARVED_PUMPKIN) {
			return 40;
		} else if (magic == Items.JUKEBOX) {
			return HOUR;
		} else if (magic == Items.BLAZE_POWDER) {
			return HOUR;
		} else if (magic == Items.LEATHER_HELMET) {
			return 20;
		} else if (magic == Items.EGG) {
			return 40;
		} else if (magic == Items.GOLD_INGOT) {
			return 25;
		} else {
			return 0;
		}
	}

	private void cost(PlayerEntity player, double amount) {
		int whole = Experience.add(player, amount);
		int exp = player.experienceTotal;

		if (exp < whole) {
			player.giveExperiencePoints(-exp);
			whole -= exp;
			player.attackEntityFrom(DamageSource.MAGIC, whole);
		} else {
			player.giveExperiencePoints(-whole);
		}
	}

	@Override
	public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		Item magic = getMagic(stack).getItem();
		if (livingEntityIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) livingEntityIn;
			if (magic == Items.ELYTRA) {
				elytra(worldIn, player, stack, count);
			} else if (magic == Items.CLOCK) {
				clock(worldIn, player, stack, count);
			} else if (magic == Items.JUKEBOX) {
				jukeboxTick(worldIn, player, stack, count);
			} else if (magic == Items.BLAZE_POWDER) {
				blazePowder(worldIn, player, stack, count);
			} else if (magic == Items.EGG) {
				eggTick(worldIn, player, stack, count);
			}
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		Item magic = getMagic(stack).getItem();
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			if (magic == Main.WIZARD_STAFF_ITEM) {
				wizardStaff(worldIn, player, stack);
				return new ItemStack(Items.AIR);
			} else if (magic == Items.WRITABLE_BOOK) {
				writableBook(worldIn, player, stack);
			} else if (magic == Items.CARVED_PUMPKIN) {
				carvedPumpkin(worldIn, player, stack);
			} else if (magic == Items.LEATHER_HELMET) {
				leatherHelmet(worldIn, player, stack);
			} else if (magic == Items.EGG) {
				eggStop(worldIn, player, stack);
			} else if (magic == Items.GOLD_INGOT) {
				goldIngot(worldIn, player, stack);
			}
		}
		return stack;
	}

	private void elytra(World world, PlayerEntity player, ItemStack stack, int count) {
		if (count % 5 == 0)
			player.playSound(Main.WOOSH_SOUND, 1, soundPitch());
		if (!world.isRemote) {
			cost(player, 1);
			Vec3d motion = player.getMotion();
			player.fallDistance = 0;
			Vec3d direction = Vec3d.fromPitchYaw(player.getPitchYaw()).scale(0.3);
			double x = motion.getX() < 3 ? direction.getX() : 0;
			double y = motion.getY() < 3 ? direction.getY() + 0.1 : 0;
			double z = motion.getZ() < 3 ? direction.getZ() : 0;
			player.addVelocity(x, y, z);
			player.velocityChanged = true;
		}
	}

	private void wizardStaff(World world, PlayerEntity player, ItemStack stack) {
		if (!world.isRemote) {
			int depth = staffDepth(stack);
			world.createExplosion(null, DamageSource.causePlayerDamage(player), player.getPosX(), player.getPosY(),
					player.getPosZ(), depth, true, Mode.DESTROY);
		}
	}

	private void clock(World world, PlayerEntity player, ItemStack stack, int count) {
		if (count % 7 == 0)
			player.playSound(Main.CLOCK_SOUND, 1, soundPitch());
		if (!world.isRemote) {
			cost(player, 1);
		}
		world.setDayTime(world.getDayTime() + 40);
	}

	private String[] wisdoms = { "All that glitters is gold", "Fear the old blood",
			"Every fleeing man must be caught. Every secret must be unearthed. Such is the conceit of the self-proclaimed seeker of truth.",
			"What we do in life echoes in eternity", "STEVEN LIVES", "What we've got here is... failure to communicate",
			"All those moments will be lost in time, like tears in rain",
			"A wizard is never late, nor is he early. He arrives precisely when he means to." };

	private void writableBook(World world, PlayerEntity player, ItemStack stack) {
		String wisdom = wisdoms[player.getRNG().nextInt(wisdoms.length)];
		player.playSound(Main.SCRIBBLE_SOUND, 3, soundPitch());
		if (!world.isRemote) {
			cost(player, 10);
			WizardStaffHandler handler = getHandler(stack);
			ItemStack book = handler.extractItem(0, 1, false);
			CompoundNBT tag = book.getOrCreateTag();
			ListNBT pages = new ListNBT();
			pages.add(StringNBT.valueOf(wisdom));
			tag.put("pages", pages);
			book.setTag(tag);
			handler.insertItem(0, book, false);
		}
	}

	private void carvedPumpkin(World world, PlayerEntity player, ItemStack stack) {
		player.playSound(Main.PUMPKIN_MAGIC_SOUND, 0.2f, soundPitch());
		if (!world.isRemote) {
			cost(player, 50);
			PumpkinMagicEntity entity = new PumpkinMagicEntity(Main.PUMPKIN_MAGIC_ENTITY, world, player);
			entity.setPositionAndRotation(player.getPosX(), player.getPosY() + 2, player.getPosZ(), player.rotationYaw,
					0);
			world.addEntity(entity);
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void jukeboxStart(World world, PlayerEntity player, ItemStack stack) {
		WizardStaffTickableSound sound = new WizardStaffTickableSound(player, stack);
		Minecraft.getInstance().getSoundHandler().play(sound);
	}

	private void jukeboxTick(World world, PlayerEntity player, ItemStack stack, int count) {
		List<Entity> entities = world.getEntitiesInAABBexcluding(player, player.getBoundingBox().grow(4),
				(e) -> e instanceof LivingEntity && e.isNonBoss());
		for (Entity e : entities) {
			LivingEntity living = (LivingEntity) e;
			if (!world.isRemote) {
				living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 2, 100));
				if (e.ticksExisted % 5 == 0)
					living.swingArm(Hand.MAIN_HAND);
				if (e.ticksExisted % 7 == 0)
					living.swingArm(Hand.OFF_HAND);
			} else {
				if (e.ticksExisted % 5 == 0)
					living.limbSwingAmount = 1 + player.getRNG().nextFloat() - 0.5f;
			}
		}
		if (!world.isRemote)
			cost(player, 2 + entities.size());
	}

	private void blazePowder(World world, PlayerEntity player, ItemStack stack, int count) {
		if (count % 7 == 0)
			player.playSound(Main.BURNING_SOUND, 1, soundPitch());
		if (!world.isRemote) {
			ServerWorld serverWorld = (ServerWorld) world;
			Random rand = player.getRNG();
			cost(player, 0.3);
			Vec3d offset = Vec3d.fromPitchYaw(player.getPitchYaw());
			for (Entity e : world.getEntitiesInAABBexcluding(player, player.getBoundingBox().grow(0.3).offset(offset),
					(e) -> e instanceof LivingEntity)) {
				e.attackEntityFrom(DamageSource.causePlayerDamage(player), 2);
				e.setFire(2);
			}

			for (int i = 0; i < 3; i++) {
				double distance = rand.nextDouble() + 1;
				Vec3d direction = Vec3d.fromPitchYaw(player.rotationPitch + rand.nextFloat() * 30 - 15,
						player.rotationYaw + rand.nextFloat() * 30 - 15);
				Vec3d particlePos = player.getPositionVec().add(0, 1.5, 0).add(direction.scale(distance));
				serverWorld.spawnParticle(Main.MAGIC_FLAME_PARTICLE_TYPE, particlePos.x, particlePos.y, particlePos.z,
						0, 0, 0, 0, 1);
			}
		}
	}

	private void leatherHelmet(World world, PlayerEntity player, ItemStack stack) {
		player.playSound(Main.PLOP_SOUND, 1, soundPitch());
		if (!world.isRemote) {
			cost(player, 30);
			WizardStaffHandler handler = getHandler(stack);
			ItemStack helmet = handler.extractItem(0, 1, false);
			ItemStack wizardHat = new ItemStack(Main.WIZARD_HAT_ITEM);
			CompoundNBT tag = helmet.getOrCreateTag();
			if (tag.contains("display")) {
				CompoundNBT display = tag.getCompound("display");
				int color = display.getInt("color");

				tag = wizardHat.getOrCreateTag();
				display = wizardHat.getOrCreateChildTag("display");
				display.putInt("color", color);
			}
			handler.insertItem(0, wizardHat, false);
		}
	}

	private void eggTick(World world, PlayerEntity player, ItemStack stack, int count) {
		if (count % 10 == 0) {
			player.playSound(Main.RAY_SOUND, 0.6f, 0.95f + Item.random.nextFloat() * 0.05f);
		}
		if (!world.isRemote) {
			Vec3d direction = Vec3d.fromPitchYaw(player.getPitchYaw());
			Entity target = Helper.findTargetLine(player.getPositionVec().add(0, 1.5, 0), direction, 7, world, player);
			if (target != null) {
				Random rand = player.getRNG();
				ServerWorld serverWorld = (ServerWorld) world;
				Vec3d pos = player.getPositionVec().add(0, 1.5, 0).add(direction);
				for (int i = 0; i < 25; i++) {
					serverWorld.spawnParticle(new MagicDustParticleData(0.8f + rand.nextFloat() * 0.2f,
							rand.nextFloat() * 0.2f, 0.8f + rand.nextFloat() * 0.2f, 1), pos.x, pos.y, pos.z, 1, 0, 0,
							0, 0.1);
					pos = pos.add(direction.scale(0.3));
					if (target.getBoundingBox().intersects(new AxisAlignedBB(pos, pos).grow(0.25)))
						break;
				}
			}
		}
	}

	private void eggStop(World world, PlayerEntity player, ItemStack stack) {
		if (!world.isRemote) {
			Entity target = Helper.findTargetLine(player.getPositionVec().add(0, 1.5, 0),
					Vec3d.fromPitchYaw(player.getPitchYaw()), 7, world, player);
			if (target != null) {
				target.playSound(Main.PLOP_SOUND, 1, soundPitch());
				SpawnEggItem egg = null;
				for (SpawnEggItem e : SpawnEggItem.getEggs()) {
					if (e.getType(null) == target.getType()) {
						egg = e;
						break;
					}
				}
				if (egg != null) {
					ItemEntity eggEntity = new ItemEntity(world, target.getPosX(), target.getPosY(), target.getPosZ(),
							new ItemStack(egg));
					world.addEntity(eggEntity);
					target.remove();
					cost(player, 200);
				}
			}
		}
	}

	private void goldIngot(World world, PlayerEntity player, ItemStack stack) {
		if (!world.isRemote) {
			Vec3d direction = Vec3d.fromPitchYaw(player.getPitchYaw());
			Vec3d start = player.getPositionVec().add(0, 1.5, 0).add(direction.scale(0.2));
			Vec3d stop = start.add(direction.scale(4.5));
			BlockRayTraceResult result = world
					.rayTraceBlocks(new RayTraceContext(start, stop, BlockMode.COLLIDER, FluidMode.NONE, player));
			if (result.getType() == Type.BLOCK && world.getBlockState(result.getPos()).getBlock() == Blocks.STONE) {
				BlockPos pos = result.getPos();
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STONE_BREAK,
						SoundCategory.PLAYERS, 1.5f, soundPitch());
				cost(player, 50);
				world.setBlockState(pos, Blocks.GOLD_ORE.getDefaultState());
			}

		}
	}

	private float soundPitch() {
		return 0.8f + Item.random.nextFloat() * 0.4f;
	}

	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT result = new CompoundNBT();
		CompoundNBT tag = super.getShareTag(stack);
		CompoundNBT cap = getHandler(stack).serializeNBT();
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
			getHandler(stack).deserializeNBT(nbt.getCompound("cap"));
		}
	}

}
