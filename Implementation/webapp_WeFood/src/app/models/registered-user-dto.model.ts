export interface RegisteredUserDTOInterface {
    _id: string;
    username: string;
}

export class RegisteredUserDTO implements RegisteredUserDTOInterface {
    _id: string;
    username: string;
  
    constructor(id:string="", username:string = "") {
      this._id = id;
      this.username = username;
    }
}