export interface IngredientInterface {
    name: string;
    calories: number;
}

export class Ingredient implements IngredientInterface {
    name: string;
    calories: number;
    
    constructor(name:string = "", calories:number = 0) {
      this.name = name;
      this.calories = calories;
    }
}