package mod.vemerion.wizardstaff.Magic.netherupdate;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderMagic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GlowstoneDustMagic extends Magic {

	private static final ResourceLocation GLOWSTONE_DUST = new ResourceLocation("forge", "dusts/glowstone");

	@Override
	public int getUseDuration(ItemStack staff) {
		return 30;
	}

	@Override
	public boolean isMagicItem(Item item) {
		return ItemTags.getCollection().getOrCreate(GLOWSTONE_DUST).contains(item);
	}

	@Override
	public RenderMagic renderer() {
		return WizardStaffTileEntityRenderer::circling;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			cost(player, 50);
			for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, player.getBoundingBox().grow(10),
					e -> e != player)) {
				e.addPotionEffect(new EffectInstance(Effects.GLOWING, 20 * 10));
			}
		}
		return super.magicFinish(world, player, staff);
	}

}
