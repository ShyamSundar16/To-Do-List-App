import { Component, OnInit, ViewChild } from '@angular/core';
//import { Todo } from 'src/app/models/Todo';
import { TodoService } from '../../services/todo.service';
import { ActivatedRoute } from '@angular/router';
import { Todo } from '../../models/Todo';
import { UserService } from '../../services/user.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';


@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styles: []
})
export class TodoListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'title', 'description'];
  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  todoList: Todo[] = [];
  constructor(public todoService: TodoService, public route: ActivatedRoute, public userService: UserService) {

   }
  ngOnInit(): void {
    this.getTasks();
  }

  getTasks() {
    this.todoService.getTaskByUserId(this.userService.user.email).subscribe((res: any) => {
      this.todoList = res;
      this.dataSource.data = res;
      this.dataSource.paginator = this.paginator;
    });
  }

}
