import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthUser } from "../models/AuthUser";
import { User } from "../models/User";

@Injectable({"providedIn": "root"})
export class AuthUserService{
    private u = new AuthUser();

    private authenticationurl: string = "http://localhost:8989/usermangement/authenticate/token"

    constructor(private httpClient: HttpClient){

    }
    public set user(newU:AuthUser){
        this.u.username= newU.username;
        this.u.password= newU.password;
    }

    public get user(){
        return this.u;
    }

    authenticateUser(user: AuthUser){
        return this.httpClient.post(this.authenticationurl, user);
    }
}