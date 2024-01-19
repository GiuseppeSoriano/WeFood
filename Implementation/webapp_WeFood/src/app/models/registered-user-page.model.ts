import { PostDTOInterface } from "./post-dto.model";

export interface RegisteredUserPageInterface {
    _id: string;
    username: string;
    posts: PostDTOInterface[];
}

export class RegisteredUserPage implements RegisteredUserPageInterface {
    _id: string;
    username: string;
    posts: PostDTOInterface[];
  
    constructor(id:string = "", username:string = "", posts:PostDTOInterface[] = []) {
      this._id = id;
      this.username = username;
      this.posts = posts;
    }
}