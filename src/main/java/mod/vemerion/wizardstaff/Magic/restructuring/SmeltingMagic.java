package mod.vemerion.wizardstaff.Magic.restructuring;

import java.util.List;
import java.util.Random;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class SmeltingMagic extends Magic {

	private RecipeType<? extends Recipe<Container>> recipeType;
	private int interval;
	private SoundEvent sound;
	private String recipeName;
	private int itemCost;

	public SmeltingMagic(MagicType<? extends SmeltingMagic> type) {
		super(type);
	}

	public SmeltingMagic setAdditionalParams(RecipeType<? extends Recipe<Container>> recipeType, int interval,
			SoundEvent sound, String recipeName, int itemCost) {
		this.recipeType = recipeType;
		this.interval = interval;
		this.sound = sound;
		this.recipeName = recipeName;
		this.itemCost = itemCost;
		return this;
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

	@SuppressWarnings("unchecked")
	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		recipeType = (RecipeType<Recipe<Container>>) MagicUtil.decode(buffer, Registry.RECIPE_TYPE);
		sound = MagicUtil.decode(buffer);
		interval = buffer.readInt();
		int nameLength = buffer.readInt();
		recipeName = buffer.readUtf(nameLength);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encode(buffer, recipeType, Registry.RECIPE_TYPE);
		MagicUtil.encode(buffer, sound);
		buffer.writeInt(interval);
		buffer.writeInt(recipeName.length());
		buffer.writeUtf(recipeName);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void readAdditional(JsonObject json) {
		recipeType = (RecipeType<Recipe<Container>>) MagicUtil.read(json, Registry.RECIPE_TYPE, "recipe_type");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
		interval = GsonHelper.getAsInt(json, "smelt_interval");
		recipeName = GsonHelper.getAsString(json, "recipe_name");
		itemCost = GsonHelper.getAsInt(json, "item_cost");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, recipeType, Registry.RECIPE_TYPE, "recipe_type");
		MagicUtil.write(json, sound, "sound");
		json.addProperty("smelt_interval", interval);
		json.addProperty("recipe_name", recipeName);
		json.addProperty("item_cost", itemCost);
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { new TranslatableComponent(recipeName) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new TranslatableComponent(recipeName) };
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide) {
			if (count % interval == 0 && count != getUseDuration(staff)) {
				Random rand = player.getRandom();

				List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class,
						player.getBoundingBox().inflate(2),
						e -> e.getItem().getCount() >= itemCost && !getRecipes(level, e).isEmpty());
				if (entities.isEmpty())
					return;

				ItemEntity itemEntity = entities.get(rand.nextInt(entities.size()));
				Container inv = new SimpleContainer(itemEntity.getItem());
				List<? extends Recipe<Container>> recipes = level.getRecipeManager().getRecipesFor(recipeType, inv,
						level);

				if (!recipes.isEmpty()) {
					playSoundServer(level, player, sound, 1, soundPitch(player));
					cost(player);
					Recipe<Container> recipe = recipes.get(rand.nextInt(recipes.size()));
					itemEntity.getItem().shrink(itemCost);
					itemEntity.spawnAtLocation(recipe.assemble(inv));
				}
			}
		}
	}

	private List<? extends Recipe<Container>> getRecipes(Level level, ItemEntity itemEntity) {
		Container inv = new SimpleContainer(itemEntity.getItem());
		return level.getRecipeManager().getRecipesFor(recipeType, inv, level);
	}
}
