import { PostDTOInterface } from "./post-dto.model";

export interface RegisteredUserPageInterface {
    id: string;
    username: string;
    posts: PostDTOInterface[];
}

export class RegisteredUserPage implements RegisteredUserPageInterface {
    id: string;
    username: string;
    posts: PostDTOInterface[];
  
    constructor(id:string = "", username:string = "", posts:PostDTOInterface[] = []) {
      this.id = id;
      this.username = username;
      this.posts = posts;
    }
}