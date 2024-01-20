import { Pipe, PipeTransform } from '@angular/core';
import { IngredientInterface } from '../models/ingredient.model';

@Pipe({
  name: 'filterIngredient'
})
export class FilterIngredientPipe implements PipeTransform {
  transform(items: IngredientInterface[], searchText: string, maxResults: number = 5): IngredientInterface[] {
    if (!items) return [];
    if (!searchText) return items;  //.slice(0, maxResults)

    searchText = searchText.toLowerCase();
    return items.filter(item => item.name && item.name.toLowerCase().startsWith(searchText));
  }
}
