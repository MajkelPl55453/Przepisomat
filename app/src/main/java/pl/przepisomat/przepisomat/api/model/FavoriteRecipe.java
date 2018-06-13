package pl.przepisomat.przepisomat.api.model;

import io.realm.RealmObject;

/**
 * Created by Majkel on 2018-06-11.
 */

public class FavoriteRecipe extends RealmObject {
    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    private Long recipeId;
}
