package mod.vemerion.wizardstaff.Magic.original;

import java.util.Random;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.particle.MagicDustParticleData;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EggMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return 40;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.EGG;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (count % 10 == 0) {
			player.playSound(Main.RAY_SOUND, 0.6f, 0.95f + player.getRNG().nextFloat() * 0.05f);
		}
		if (!world.isRemote) {
			Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw());
			Entity target = Helper.findTargetLine(player.getPositionVec().add(0, 1.5, 0), direction, 7, world, player);
			if (target != null) {
				Random rand = player.getRNG();
				ServerWorld serverWorld = (ServerWorld) world;
				Vector3d pos = player.getPositionVec().add(0, 1.5, 0).add(direction);
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

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			Entity target = Helper.findTargetLine(player.getPositionVec().add(0, 1.5, 0),
					Vector3d.fromPitchYaw(player.getPitchYaw()), 7, world, player);
			if (target != null) {
				target.playSound(Main.PLOP_SOUND, 1, soundPitch(player));
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
		return super.magicFinish(world, player, staff);
	}
	
	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

}
