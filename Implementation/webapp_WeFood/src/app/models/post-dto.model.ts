export interface PostDTOInterface {
    id: string;
    image: string;
    recipeName: string;
}

export class PostDTO implements PostDTOInterface {
    id: string;
    image: string;
    recipeName: string;
  
    constructor(id:string = "", image:string = "", recipeName:string = "") {
        this.id = id;
        this.image = image;
        this.recipeName = recipeName;
    }
}