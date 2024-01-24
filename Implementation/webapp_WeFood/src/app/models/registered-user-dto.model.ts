export interface RegisteredUserDTOInterface {
    id: string;
    username: string;
}

export class RegisteredUserDTO implements RegisteredUserDTOInterface {
    id: string;
    username: string;
  
    constructor(id:string="", username:string = "") {
      this.id = id;
      this.username = username;
    }
}