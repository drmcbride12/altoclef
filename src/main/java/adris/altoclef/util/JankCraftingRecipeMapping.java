package adris.altoclef.util;

import java.util.*;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.core.Holder;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;
import net.minecraft.world.item.crafting.display.RecipeDisplayId;

/**
 * For crafting table/inventory recipe book crafting, we need to figure out identifiers given a recipe.
 */
public class JankCraftingRecipeMapping {
    private static final ContextMap EMPTY_DISPLAY_CONTEXT = new ContextMap.Builder().create(new ContextKeySet.Builder().build());
    private static final HashMap<Item, List<RecipeDisplayEntry>> _recipeMapping = new HashMap<>();

    private static void reloadRecipeMapping() {
        if (Minecraft.getInstance().player != null) {
            for (RecipeCollection collection : Minecraft.getInstance().player.getRecipeBook().getCollections()) {
                for (RecipeDisplayEntry entry : collection.getRecipes()) {
                    for (ItemStack result : entry.resultItems(EMPTY_DISPLAY_CONTEXT)) {
                        Item output = result.getItem();
                        if (!_recipeMapping.containsKey(output)) {
                            _recipeMapping.put(output, new ArrayList<>());
                        }
                        _recipeMapping.get(output).add(entry);
                    }
                }
            }
        }
    }

    public static Optional<RecipeDisplayId> getMinecraftMappedRecipe(CraftingRecipe recipe, Item output) {
        if (_recipeMapping.isEmpty()) {
            reloadRecipeMapping();
        }
        if (_recipeMapping.containsKey(output)) {
            for (RecipeDisplayEntry checkRecipe : _recipeMapping.get(output)) {
                // Check for item count/satisfiability and not shape satisfiability (that would be annoying)
                // Assumes there are no 2 recipes with the same output and same inputs in a different order.
                List<ItemTarget> toSatisfy = Arrays.stream(recipe.getSlots()).filter(itemTarget -> itemTarget != null && !itemTarget.isEmpty()).collect(Collectors.toList());
                for (Ingredient checkIngredient : checkRecipe.craftingRequirements().orElse(Collections.emptyList())) {
                    if (checkIngredient.isEmpty())
                        continue;
                    // Remove from "toSatisfy" if we find something that fits
                    outer:
                    for (int i = 0; i < toSatisfy.size(); ++i) {
                        ItemTarget check = toSatisfy.get(i);
                        for (Holder<Item> match : checkIngredient.items().toList()) {
                            if (check.matches(match.value())) {
                                toSatisfy.remove(i);
                                break outer;
                            }
                        }
                    }
                }
                /*int i = -1; // ++i first
                boolean found = true;
                for (Object ingredientObj : checkRecipe.getIngredients()) {
                    ++i;
                    // Out of range
                    if (i >= recipe.getSlotCount()) {
                        found = false;
                        break;
                    }
                    ItemTarget ourIngredient = recipe.getSlot(i);
                    Ingredient checkIngredient = (Ingredient) ingredientObj;
                    // If our ingredient is null, our check ingredient MUST be empty (Empty = null)
                    if (ourIngredient == null || ourIngredient.isEmpty()) {
                        if (checkIngredient.isEmpty())
                            continue;
                        found = false;
                        break;
                    }
                    // At least one item must satisfy to move on.
                    if (Arrays.stream(checkIngredient.getMatchingStacks()).noneMatch(itemStack -> ourIngredient.matches(itemStack.getItem()))) {
                        found = false;
                        break;
                    }
                }
                if (found)
                    return Optional.of(checkRecipe);
                */
                // We satisfied every material, so assume it's the right recipe.
                if (toSatisfy.isEmpty()) {
                    return Optional.of(checkRecipe.id());
                }
            }
        }
        return Optional.empty();
    }
}
