package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;

public class NetherBrickMagic extends Magic {
	private static final ResourceLocation NETHER_BRICK = new ResourceLocation("forge", "ingots/nether_brick");

	@Override
	public int getUseDuration(ItemStack staff) {
		return 20;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return ItemTags.getCollection().getTagByID(NETHER_BRICK).contains(item);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}
	
	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (world.getDimensionKey() == World.THE_NETHER) {
			player.playSound(Main.WARP_SOUND, 0.8f, soundPitch(player));
			if (!world.isRemote) {
				cost(player, 60);
				BlockPos fortressPos = ((ServerWorld) world).func_241117_a_(Structure.FORTRESS, new BlockPos(player.getPositionVec()), 100,
						false);
				if (fortressPos != null) {
					player.lookAt(EntityAnchorArgument.Type.EYES, Vector3d.copy(fortressPos));
				}
			}
		} else {
			if (!world.isRemote) {
				world.createExplosion(null, player.getPosX(), player.getPosY(), player.getPosZ(), 1,
						Explosion.Mode.BREAK);
			}
			return ItemStack.EMPTY;
		}
		return super.magicFinish(world, player, staff);
	}

}
