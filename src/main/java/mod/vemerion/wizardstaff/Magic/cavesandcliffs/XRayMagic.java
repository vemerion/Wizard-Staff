package mod.vemerion.wizardstaff.Magic.cavesandcliffs;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.RegistryMatch;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

public class XRayMagic extends Magic {

	private int range;
	private RegistryMatch<Block> match;

	public XRayMagic(MagicType<? extends XRayMagic> type) {
		super(type);
	}

	public XRayMagic setAdditionalParams(int range, RegistryMatch<Block> match) {
		this.range = range;
		this.match = match;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		range = GsonHelper.getAsInt(json, "range");
		match = RegistryMatch.read(ForgeRegistries.BLOCKS, GsonHelper.getAsJsonObject(json, "match"));
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("range", range);
		json.add("match", match.write());
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		range = buffer.readInt();
		match = RegistryMatch.decode(ForgeRegistries.BLOCKS, buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeInt(range);
		match.encode(buffer);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::circling;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::swinging;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { match.getName(), new TextComponent(String.valueOf(range)) };
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { match.getName() };
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide) {
			cost(player);
		} else {
			findOres(Helper.randPosInBox(new AABB(player.blockPosition()).inflate(range), player.getRandom()), player,
					level);
		}
	}

	private void findOres(BlockPos start, Player player, Level level) {
		Wizard.getWizardOptional(player).ifPresent(w -> {
			BlockPos.betweenClosedStream(new AABB(start).inflate(8)).forEach(p -> {
				if (match.test(level.getBlockState(p).getBlock()))
					w.getXRayed().add(p.immutable());
			});
		});
	}

}
