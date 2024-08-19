import { Role } from "./Role";

export class Token {
    sub: string = "";
    role: Role[] = [];
    exp: number = 0;
    iat: number = 0;

}