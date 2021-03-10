package mod.vemerion.wizardstaff.Magic.restructuring;

import java.util.List;
import java.util.Random;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class SmeltingMagic extends Magic {

	private IRecipeType<IRecipe<IInventory>> recipeType;
	private int interval;
	private SoundEvent sound;
	private String recipeName;

	public SmeltingMagic(String registryName) {
		super(registryName);
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		recipeType = (IRecipeType<IRecipe<IInventory>>) MagicUtil.decode(buffer, Registry.RECIPE_TYPE);
		sound = MagicUtil.decode(buffer, ForgeRegistries.SOUND_EVENTS);
		interval = buffer.readInt();
		int nameLength = buffer.readInt();
		recipeName = buffer.readString(nameLength);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, recipeType, Registry.RECIPE_TYPE);
		MagicUtil.encode(buffer, sound);
		buffer.writeInt(interval);
		buffer.writeInt(recipeName.length());
		buffer.writeString(recipeName);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void readAdditional(JsonObject json) {
		recipeType = (IRecipeType<IRecipe<IInventory>>) MagicUtil.read(json, Registry.RECIPE_TYPE, "recipe_type");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
		interval = JSONUtils.getInt(json, "smelt_interval");
		recipeName = JSONUtils.getString(json, "recipe_name");
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { new TranslationTextComponent(recipeName) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new TranslationTextComponent(recipeName) };
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (!world.isRemote) {
			if (count % interval == 0 && count != getUseDuration(staff)) {
				Random rand = player.getRNG();

				List<ItemEntity> entities = world.getEntitiesWithinAABB(ItemEntity.class,
						player.getBoundingBox().grow(2), e -> !getRecipes(world, e).isEmpty());
				if (entities.isEmpty())
					return;

				ItemEntity itemEntity = entities.get(rand.nextInt(entities.size()));
				IInventory inv = new Inventory(itemEntity.getItem());
				List<IRecipe<IInventory>> recipes = world.getRecipeManager().getRecipes(recipeType, inv, world);
				if (!recipes.isEmpty()) {
					playSoundServer(world, player, sound, 1, soundPitch(player));
					cost(player);
					IRecipe<IInventory> recipe = recipes.get(rand.nextInt(recipes.size()));
					itemEntity.getItem().shrink(getRecipeCost(recipe));
					itemEntity.entityDropItem(recipe.getCraftingResult(inv));
				}
			}
		}
	}
	
	private List<IRecipe<IInventory>> getRecipes(World world, ItemEntity itemEntity) {
		IInventory inv = new Inventory(itemEntity.getItem());
		return world.getRecipeManager().getRecipes(recipeType, inv, world);
	}

	private int getRecipeCost(IRecipe<IInventory> recipe) {
		int cost = 0;
		for (Ingredient ingredient : recipe.getIngredients())
			for (ItemStack stack : ingredient.getMatchingStacks())
				cost += stack.getCount();
		return cost;
	}

}
