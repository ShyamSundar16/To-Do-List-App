import { Component, OnInit } from '@angular/core';
//import { Todo } from 'src/app/models/Todo';
import { TodoService } from '../../services/todo.service';
import { ActivatedRoute } from '@angular/router';
import { Todo } from '../../models/Todo';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styles: []
})
export class TodoListComponent implements OnInit {

  // todoList: Todo[] = [
  //   {
  //     id:1,
  //     title: 'Todo One',
  //     isCompleted: false,
  //     isFavorite: false,
  //     date: new Date('4-15-2020')
  //   },
  //   {
  //     id:1,
  //     title: 'Todo Two',
  //     isCompleted: false,
  //     isFavorite: false,
  //     date: new Date('5-15-2020')
  //   },
  //   {
  //     id:1,
  //     title: 'Todo Three',
  //     isCompleted: false,
  //     isFavorite: false,
  //     date: new Date('6-15-2020')
  //   }
  // ];

  // constructor() { }
  constructor(public todoService: TodoService, public route: ActivatedRoute) { }

  viewList: boolean = true;


  ngOnInit(): void {
    this.route.url.subscribe(data => {
      // console.log(data[0].path);
      if (data[0].path == 'list') {
        this.viewList = true;
      }
      else {
        this.viewList = false;
        //this.todoService.updateFav();
        console.log()
      }
    })
  }

}
