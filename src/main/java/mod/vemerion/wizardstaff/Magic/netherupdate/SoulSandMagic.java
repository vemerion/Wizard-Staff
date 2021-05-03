package mod.vemerion.wizardstaff.Magic.netherupdate;

import java.util.Random;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.entity.MagicSoulSandArmEntity;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SoulSandMagic extends Magic {

	private static final float WIDTH = 3;
	private static final float LENGTH = 15;
	private static final int COUNT = 10;
	
	public SoulSandMagic(MagicType<? extends SoulSandMagic> type) {
		super(type);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forwardWaving;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(ModSounds.PUMPKIN_MAGIC, 0.2f, soundPitch(player));
		if (!world.isRemote) {
			cost(player);
			spawnArms(world, player);
		}

		return super.magicFinish(world, player, staff);
	}

	private void spawnArms(World world, PlayerEntity player) {
		Random rand = player.getRNG();
		Vector3d direction = Vector3d.fromPitchYaw(0, player.rotationYaw);
		Vector3d side = direction.rotateYaw(90);
		for (int i = 0; i < COUNT; i++) {
			float distance = i / (float) COUNT * (LENGTH - 1) + 1 + rand.nextFloat() * 0.2f;
			float offset = rand.nextFloat() * WIDTH - WIDTH / 2;
			Vector3d position = Vector3d.copy(new BlockPos(player.getPositionVec())).add(direction.x * distance + side.x * offset, 0,
					direction.z * distance + side.z * offset);
			position = findValidPosition(position, world);
			if (position != null) {
				MagicSoulSandArmEntity arm = new MagicSoulSandArmEntity(ModEntities.MAGIC_SOUL_SAND_ARM, world, player);
				arm.setPosition(position.x, position.y, position.z);
				world.addEntity(arm);
			}
		}
	}

	private Vector3d findValidPosition(Vector3d position, World world) {
		int[] heights = new int[] { 0, -1, 1 };
		for (int height : heights) {
			BlockPos pos = new BlockPos(position).up(height);
			if (!world.getBlockState(pos).isSolid() && world.getBlockState(pos.down()).isSolid())
				return position.add(0, height, 0);
		}
		return null;
	}

}
