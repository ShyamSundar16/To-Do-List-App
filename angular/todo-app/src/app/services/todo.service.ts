import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Todo } from "../models/Todo";
import { UserService } from "./user.service";

@Injectable({"providedIn": "root"})
export class TodoService{
    private newTodo = new Todo();

    private url: string = "http://localhost:8989/taskservice/api/v1/user";
    // private url: string = "http://ec2-3-142-134-192.us-east-2.compute.amazonaws.com:8989/api/usermangement/users";

    constructor(private httpClient: HttpClient, public userService: UserService){
    }

    public set todo(newT:Todo){
        this.newTodo.id= newT.id;
        this.newTodo.userId= newT.userId;
        this.newTodo.title = newT.title; 
        this.newTodo.description = newT.description; 
        this.newTodo.startDate= newT.startDate;
        this.newTodo.endDate= newT.endDate;
        this.newTodo.status= newT.status;
        this.newTodo.effortRequired= newT.effortRequired;
        this.newTodo.category= newT.category;
        this.newTodo.reminderDate= newT.reminderDate;
    }

    public get todo(){
        return this.newTodo;
    }

    saveTask(todo: Todo) {
        todo.userId = this.userService.user.email;
        console.log("Before Save");
        console.log(todo);
        return this.httpClient.post(this.url+"/add-list", todo);
    }
    
    updateTask(todo: Todo) {
        return this.httpClient.put(this.url + "/update/" + todo.id, todo);
    }

    getAllTasks() {
        return this.httpClient.get(this.url + "/list/all");
    }

    getTaskById() {
        return this.httpClient.get(this.url + "/list/"+this.todo.id);
    }

    getTaskByUserId(userId: string){
        return this.httpClient.get<Todo>(this.url + "/" + userId);
    }

    deleteTask(id: string) {
        return this.httpClient.delete(this.url + "/list/" + id);
    }
}