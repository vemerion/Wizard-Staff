package mod.vemerion.wizardstaff.Magic.cavesandcliffs;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import mod.vemerion.wizardstaff.Helper.Helper;
import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

public class PlaceLightRandomMagic extends Magic {

	private int radius;
	private int lightLevel;
	private BlockItem item;

	public PlaceLightRandomMagic(MagicType<? extends PlaceLightRandomMagic> type) {
		super(type);
	}

	public PlaceLightRandomMagic setAdditionalParams(int radius, int lightLevel, BlockItem item) {
		this.radius = radius;
		this.lightLevel = lightLevel;
		this.item = item;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		radius = GsonHelper.getAsInt(json, "radius");
		lightLevel = GsonHelper.getAsInt(json, "light_level");
		if (MagicUtil.read(json, ForgeRegistries.ITEMS, "item") instanceof BlockItem blockItem)
			item = blockItem;
		else
			throw new JsonParseException("Item for PlaceLightRandomMagic must be a BlockItem");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("radius", radius);
		json.addProperty("light_level", lightLevel);
		MagicUtil.write(json, item, "item");
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		radius = buffer.readInt();
		lightLevel = buffer.readInt();
		item = (BlockItem) MagicUtil.decode(buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeInt(radius);
		buffer.writeInt(lightLevel);
		MagicUtil.encode(buffer, item);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::spinMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { item.getName(item.getDefaultInstance()), radius, lightLevel };
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide) {
			var box = new AABB(player.blockPosition()).inflate(radius);
			for (int i = 0; i < 100; i++) {
				var p = Helper.randPosInBox(box, player.getRandom());

				if (level.getBrightness(LightLayer.BLOCK, p) > lightLevel)
					continue;

				BlockPlaceContext context = new DirectionalPlaceContext(level, p, Direction.NORTH,
						item.getDefaultInstance(), Direction.DOWN);
				if (item.place(context) != InteractionResult.FAIL) {
					cost(player);
					break;
				}
			}
		}
	}

}
