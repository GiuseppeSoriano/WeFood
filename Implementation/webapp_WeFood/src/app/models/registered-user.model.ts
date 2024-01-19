export interface RegisteredUserInterface {
    _id: string;
    username: string;
    password: string;
    name: string;
    surname: string;
}

export class RegisteredUser implements RegisteredUserInterface {
    _id: string;
    username: string;
    password: string;
    name: string;
    surname: string;
  
    constructor(id:string="", username:string = "", password:string = "", name:string = "", surname:string = "") {
      this._id = id;
      this.username = username;
      this.password = password;
      this.name = name;
      this.surname = surname;
    }
}