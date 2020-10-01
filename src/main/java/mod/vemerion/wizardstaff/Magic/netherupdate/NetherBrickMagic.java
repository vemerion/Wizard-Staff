package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

public class NetherBrickMagic extends Magic {
	private static final ResourceLocation NETHER_BRICK = new ResourceLocation("forge", "ingots/nether_brick");

	@Override
	public int getUseDuration(ItemStack staff) {
		return 20;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return ItemTags.getCollection().get(NETHER_BRICK).contains(item);
	}

	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (world.getDimension().getType() == DimensionType.THE_NETHER) {
			if (!world.isRemote) {
				cost(player, 60);
				BlockPos fortressPos = ((ServerWorld) world).findNearestStructure("Fortress", player.getPosition(), 100, false);
				if (fortressPos != null) {
					player.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(fortressPos));
				}
			}
		} else {
			if (!world.isRemote) {
				world.createExplosion(null, player.getPosX(), player.getPosY(), player.getPosZ(), 1, Explosion.Mode.BREAK);
			}
			return ItemStack.EMPTY;
		}
		return super.magicFinish(world, player, staff);
	}

}
