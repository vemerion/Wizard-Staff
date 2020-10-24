package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.entity.NetherPortalEntity;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ObsidianMagic extends Magic {

	@Override
	public int getUseDuration(ItemStack staff) {
		return 60;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return item == Items.OBSIDIAN;
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
		if (!world.isRemote && player.dimension == DimensionType.OVERWORLD) {
			Vec3d spawnPos = player.getPositionVec()
					.add(Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw).scale(2));
			spawnPos = new Vec3d(spawnPos.x, Math.max(player.getPosY(), spawnPos.y), spawnPos.z);
			BlockPos pos = new BlockPos(spawnPos);
			if (!world.getBlockState(pos).isSolid() && !world.getBlockState(pos.up()).isSolid()) {
				cost(player, 100);
				NetherPortalEntity portal = new NetherPortalEntity(Main.NETHER_PORTAL_ENTITY, world);
				portal.setLocationAndAngles(spawnPos.x, spawnPos.y, spawnPos.z, player.rotationYaw, 0);
				world.addEntity(portal);
				playSoundServer(world, player, Main.PORTAL_SOUND, 1, soundPitch(player));
			}
		}
		return super.magicFinish(world, player, staff);
	}

}
