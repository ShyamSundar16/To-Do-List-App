import { Component, OnInit, Input } from '@angular/core';

import { ToastrService } from 'ngx-toastr';
import { Todo } from '../../models/Todo';
import { TodoService } from '../../services/todo.service';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styles: []
})
export class TodoComponent implements OnInit {
  // @Input() todoInput;
  @Input() todoInput: Todo;

  completed: boolean = false;
  todo: Todo;

  constructor(public todoService: TodoService, private toasterService: ToastrService) { }

  ngOnInit(): void {
  }

  onChange() {
    console.log("changed");
    this.completed = !this.completed;
    this.completed ? this.toasterService.success(`Todo succesfully completed`, 'completed') : '';
  }

  onCliCk(e) {
    console.log("Clicked");
    console.log(e);
  }

  toggleClass() {
    if (this.completed) {
      // return 'list-item-success';
      return { 'list-group-item-success': this.completed, 'border-primary': this.completed };

    }
  }

  deleteTodo(item) {
    // this.todo = item;
    // this.todoService.deleteTodo(item);
    // this.toasterService.error(`Todo ${item.id} Deleted!`, 'Deleted Successfuly');
  }
  isFavorite() {
    // this.todoInput.isFavorite = !this.todoInput.isFavorite;
    // if (this.todoInput.isFavorite) {

    //   this.toasterService.success('Todo Added to Favorite');

    //   this.todoService.fav.unshift(this.todoInput);

    //   localStorage.setItem("favorite", JSON.stringify(this.todoService.fav));

    // }
    // else {
    //   this.toasterService.error('Todo Removed from Favorite');
    //   let index = this.todoService.todoList.indexOf(this.todo);
    //   this.todoService.fav.splice(index, 1);

    //   localStorage.setItem("favorite", JSON.stringify(this.todoService.fav));

    // }
  }

}
