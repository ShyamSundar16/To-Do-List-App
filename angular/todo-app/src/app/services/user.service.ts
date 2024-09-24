import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../models/User";

@Injectable({"providedIn": "root"})
export class UserService{
    private u = new User();
    private url: string = "http://k8s-default-awsingre-a48ac6e098-1533015257.us-east-1.elb.amazonaws.com/usermanagement";
    private authenticationurl: string = "http://k8s-default-awsingre-a48ac6e098-1533015257.us-east-1.elb.amazonaws.com/authenticate/token"

    constructor(private httpClient: HttpClient){

    }
    public set user(newU:User){
        this.u.id= newU.email;
        this.u.username= newU.email;
        this.u.valid = newU.valid; 
        this.u.name = newU.name; 
        this.u.email= newU.email;
        this.u.phone= newU.phone;
        this.u.password= newU.password;
        this.u.role= newU.role;

    }

    public get user(){
        return this.u;
    }

    getUserById(email: string){
        return this.httpClient.get<User>(this.url + "/" + email);
        
    }
    getAllUsers() {
        return this.httpClient.get(this.url);
    }

    saveUser(user: User) {
        return this.httpClient.post(this.url+"/register", user);
    }

    deleteUser(email: string) {
        return this.httpClient.delete(this.url + "/" + email);
    }

    updateUser(user: User) {
        return this.httpClient.put(this.url + "/" + user.email, user);
    }
}