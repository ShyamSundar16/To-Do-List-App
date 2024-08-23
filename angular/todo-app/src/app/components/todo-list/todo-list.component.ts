import { AfterViewInit, ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { TodoService } from '../../services/todo.service';
import { ActivatedRoute } from '@angular/router';
import { Todo } from '../../models/Todo';
import { UserService } from '../../services/user.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { EditTaskDialogComponent } from '../edit-task-dialog/edit-task-dialog.component';



@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['title', 'description', 'effortRequired', 'category', 'status'];
  dataSource = new MatTableDataSource<any>();

  @Input() isFavorite: boolean;
  @Output() selectedChange = new EventEmitter<boolean>();
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  paginatedTasks: any[] = [];
  todoList: Todo[] = [];
  constructor(public todoService: TodoService, public route: ActivatedRoute, public userService: UserService,  private cdr: ChangeDetectorRef, public dialog: MatDialog) {
   }

   editTask(task: any): void {
    const dialogRef = this.dialog.open(EditTaskDialogComponent, {
      width: '250px',
      data: { task }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Update the task with the new data
        this.todoService.updateTask(task).subscribe((res: any) => {
          console.log(res);
       });
        const index = this.paginatedTasks.findIndex(t => t.id === task.id);
        if (index !== -1) {
          this.paginatedTasks[index] = result;
        }
      }
    });
  }

  ngOnInit(): void {
    this.getTasks();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.paginator.page.subscribe(() => this.updatePaginatedTasks());
    this.updatePaginatedTasks(); 
  }
  getTasks() {
    const sampleTasks = [
      { id: 1, title: 'Task 1', description: 'Description for Task 1', effortRequired: 1, category: 'Developement', status: 'IN_PROGRESS', startDate: '21/08/24', endDate: '29/08/24', isFavorite: true },
      { id: 2, title: 'Task 2', description: 'Description for Task 2' , effortRequired: 1, category: 'Developement', status: 'COMPLETED', startDate: '21/08/24', endDate: '29/08/24', isFavorite: false },
      { id: 3, title: 'Task 3', description: 'Description for Task 3' , effortRequired: 1, category: 'Developement', status: 'PENDING' , startDate: '21/08/24', endDate: '29/08/24', isFavorite: true},
      { id: 4, title: 'Task 4', description: 'Description for Task 4' , effortRequired: 1, category: 'Developement', status: 'COMPLETED' , startDate: '21/08/24', endDate: '29/08/24', isFavorite: false},
      { id: 5, title: 'Task 5', description: 'Description for Task 5' , effortRequired: 1, category: 'Developement', status: 'PENDING', startDate: '21/08/24', endDate: '29/08/24', isFavorite: true},
      { id: 3, title: 'Task 3', description: 'Description for Task 3' , effortRequired: 1, category: 'Developement', status: 'PENDING' , startDate: '21/08/24', endDate: '29/08/24', isFavorite: false},
      { id: 4, title: 'Task 4', description: 'Description for Task 4' , effortRequired: 1, category: 'Developement', status: 'COMPLETED' , startDate: '21/08/24', endDate: '29/08/24', isFavorite: false},
      { id: 5, title: 'Task 5', description: 'Description for Task 5' , effortRequired: 1, category: 'Developement', status: 'PENDING' , startDate: '21/08/24', endDate: '29/08/24', isFavorite: false}
    ];
    this.dataSource.data = sampleTasks;
    this.updatePaginatedTasks();
  
    // this.todoService.getTaskByUserId(this.userService.user.email).subscribe((res: any) => {
    //   this.todoList = res;
    //   console.log(res);
    //   this.dataSource.data = res;
    //   this.dataSource.paginator = this.paginator;
    //   this.updatePaginatedTasks();
    // });
    
  }
  updatePaginatedTasks() {
    if (this.paginator) {
      const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
      const endIndex = startIndex + this.paginator.pageSize;
      this.paginatedTasks = this.dataSource.data.slice(startIndex, endIndex);
      this.cdr.detectChanges();
    }
  }

  toggleSelected(task) {
    task.isFavorite = !task.isFavorite;
    this.todoService.updateTask(task).subscribe((res: any) => {
       console.log(res);
    });
  }
  
}
