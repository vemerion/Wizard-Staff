package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
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

public class ObsidianMagic extends Magic {

	public ObsidianMagic(String name) {
		super(name);
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
		if (!world.isRemote && world.getDimensionKey() == World.OVERWORLD) {
			Vector3d spawnPos = player.getPositionVec()
					.add(Vector3d.fromPitchYaw(player.rotationPitch, player.rotationYaw).scale(2));
			spawnPos = new Vector3d(spawnPos.x, Math.max(player.getPosY(), spawnPos.y), spawnPos.z);
			BlockPos pos = new BlockPos(spawnPos);
			if (!world.getBlockState(pos).isSolid() && !world.getBlockState(pos.up()).isSolid()) {
				cost(player);
				NetherPortalEntity portal = new NetherPortalEntity(Main.NETHER_PORTAL_ENTITY, world);
				portal.setLocationAndAngles(spawnPos.x, spawnPos.y, spawnPos.z, player.rotationYaw, 0);
				world.addEntity(portal);
				playSoundServer(world, player, Main.PORTAL_SOUND, 1, soundPitch(player));
			}
		}
		return super.magicFinish(world, player, staff);
	}

}
