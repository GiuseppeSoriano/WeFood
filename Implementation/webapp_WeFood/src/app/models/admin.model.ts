export interface AdminInterface {
    username: string;
    password: string;
}

export class Admin implements AdminInterface {
    username: string;
    password: string;
  
    constructor(username:string = "", password:string = "") {
      this.username = username;
      this.password = password;
    }
}