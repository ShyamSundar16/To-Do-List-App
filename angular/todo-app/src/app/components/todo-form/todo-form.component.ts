import { Component, OnInit } from '@angular/core';
import { TodoService } from '../../services/todo.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
import { Todo } from '../../models/Todo';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-todo-form',
  templateUrl: './todo-form.component.html',
  styleUrls: ['./todo-form.component.scss']
})
export class TodoFormComponent implements OnInit {
  addTodoForm: FormGroup;
  statuses: string[] = ['Pending', 'In Progress', 'Completed']; // Define your status options here

  todo = '';

  constructor(private fb: FormBuilder, public todoService: TodoService, public userService: UserService) {
    this.addTodoForm = new FormGroup({
      title: new FormControl("", [Validators.required]),
      description: new FormControl("", [Validators.required]),
      status: new FormControl("", [Validators.required]),
      startDate: new FormControl("", [Validators.required]),
      endDate: new FormControl({ value: "", disabled: true }, [Validators.required]),
      reminderDate: new FormControl({ value: "", disabled: true }),
      effortRequired: new FormControl("", [Validators.required, Validators.min(1)]),
      category: new FormControl("")
    })

    this.addTodoForm.get('startDate')?.valueChanges.subscribe(startDate => {
      if (startDate) {
        this.addTodoForm.get('endDate')?.enable();
      } else {
        this.addTodoForm.get('endDate')?.disable();
        this.addTodoForm.get('reminderDate')?.disable();
      }
    });
    
    this.addTodoForm.get('endDate')?.valueChanges.subscribe(endDate => {
      const startDate = this.addTodoForm.get('startDate')?.value;
      if (startDate && endDate) {
        this.addTodoForm.get('reminderDate')?.enable();
      } else {
        this.addTodoForm.get('reminderDate')?.disable();
      }
    });
   }

  ngOnInit(): void {
  }

  get startDate() {
    return this.addTodoForm.get('startDate')?.value;
  }

  endDateFilter = (d: Date | null): boolean => {
    const startDate = this.startDate;
    return startDate ? d >= new Date(startDate) : true;
  };

  reminderDateFilter = (d: Date | null): boolean => {
    const startDate = this.startDate;
    const endDate = this.addTodoForm.get('endDate')?.value;
    return startDate && endDate ? d >= new Date(startDate) && d <= new Date(endDate) : startDate ? d >= new Date(startDate) : endDate ? d <= new Date(endDate) : true;
  };

  addTodo(){
    console.log(this.addTodoForm.value);
    this.todoService.saveTask(this.addTodoForm.value).subscribe((data) => {
      this.addTodoForm.reset();
    });
  }
}
