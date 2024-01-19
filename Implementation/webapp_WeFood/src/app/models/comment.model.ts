export interface CommentInterface {
    username: string;
    text: string;
    timestamp: Date;
}

export class Comment implements CommentInterface {
    username: string;
    text: string;
    timestamp: Date;
  
    constructor(username:string = "", text:string = "", timestamp:Date = new Date()) {
        this.username = username;
        this.text = text;
        this.timestamp = timestamp;
    }
}