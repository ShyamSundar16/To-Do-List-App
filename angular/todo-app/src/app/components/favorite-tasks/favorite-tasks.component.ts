import { Component, OnInit } from '@angular/core';
import { TodoService } from '../../services/todo.service';
import { UserService } from '../../services/user.service';
import { filter } from 'rxjs/operators'; // Import the 'filter' operator
import { Todo } from '../../models/Todo';

@Component({
  selector: 'app-favorite-tasks',
  templateUrl: './favorite-tasks.component.html',
  styleUrl: './favorite-tasks.component.scss'
})
export class FavoriteTasksComponent implements OnInit {
  favoriteTasks: Todo[] = [];

  constructor(private todoService: TodoService, private userService: UserService) {}

  ngOnInit(): void {
    this.todoService.getTaskByUserId(this.userService.user.email)
    .pipe(filter(task => task.isFavorite)) // Apply the 'filter' operator to the observable
    .subscribe(filteredTasks => {
      this.favoriteTasks = [filteredTasks];
    });
  }
}
