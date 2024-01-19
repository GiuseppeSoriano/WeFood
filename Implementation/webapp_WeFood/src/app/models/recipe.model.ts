import { PostDTOInterface } from "./post-dto.model";

export interface RecipeInterface {
    name: string;
    image: string;
    steps: string[];
    ingredients: Map<string, number>;
    totalCalories: number;
}

export class Recipe implements RecipeInterface {
    name: string;
    image: string;
    steps: string[];
    ingredients: Map<string, number>;
    totalCalories: number;
  
    constructor(name:string = "", image:string = "", steps:string[] = [], ingredients:Map<string, number> = new Map<string, number>(), totalCalories:number = 0) {
        this.name = name;
        this.image = image;
        this.steps = steps;
        this.ingredients = ingredients;
        this.totalCalories = totalCalories;
    }
}