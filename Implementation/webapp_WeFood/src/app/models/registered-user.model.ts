export interface RegisteredUserInterface {
    username: string;
    password: string;
    name: string;
    surname: string;
}

export class RegisteredUser implements RegisteredUserInterface {
    username: string;
    password: string;
    name: string;
    surname: string;
  
    constructor(username:string = "", password:string = "", name:string = "", surname:string = "") {
      this.username = username;
      this.password = password;
      this.name = name;
      this.surname = surname;
    }
}