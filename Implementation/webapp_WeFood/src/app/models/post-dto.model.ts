export interface PostDTOInterface {
    _id: string;
    image: string;
    recipeName: string;
}

export class PostDTO implements PostDTOInterface {
    _id: string;
    image: string;
    recipeName: string;
  
    constructor(id:string = "", image:string = "", recipeName:string = "") {
        this._id = id;
        this.image = image;
        this.recipeName = recipeName;
    }
}