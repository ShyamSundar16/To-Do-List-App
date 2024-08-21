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
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit {
  displayedColumns: string[] = ['title', 'description', 'effortRequired', 'category', 'status'];
  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  todoList: Todo[] = [];
  constructor(public todoService: TodoService, public route: ActivatedRoute, public userService: UserService) {

   }
  ngOnInit(): void {
    this.getTasks();
  }

  getTasks() {
    const sampleTasks = [
      { id: 1, title: 'Task 1', description: 'Description for Task 1', effortRequired: 1, category: 'Developement', status: 'IN_PROGRESS' },
      { id: 2, title: 'Task 2', description: 'Description for Task 2' , effortRequired: 1, category: 'Developement', status: 'COMPLETED' },
      { id: 3, title: 'Task 3', description: 'Description for Task 3' , effortRequired: 1, category: 'Developement', status: 'PENDING' },
      { id: 4, title: 'Task 4', description: 'Description for Task 4' , effortRequired: 1, category: 'Developement', status: 'COMPLETED' },
      { id: 5, title: 'Task 5', description: 'Description for Task 5' , effortRequired: 1, category: 'Developement', status: 'PENDING' }
    ];
  
    this.dataSource.data = sampleTasks;
    this.dataSource.paginator = this.paginator;
  
    // this.todoService.getTaskByUserId(this.userService.user.email).subscribe((res: any) => {
    //   this.todoList = res;
    //   this.dataSource.data = res;
    //   this.dataSource.paginator = this.paginator;
    // });
  }

}
